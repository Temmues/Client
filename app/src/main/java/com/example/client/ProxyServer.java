
package com.example.client;
import android.renderscript.ScriptGroup;
import android.util.Log;

import com.example.client.Models.Event;
import com.example.client.Models.Person;
import com.example.client.Requests.RegisterRequest;
import com.example.client.Requests.Request;
import com.example.client.Results.LoginResult;
import com.example.client.Results.MultipleEventResult;
import com.example.client.Results.MultiplePersonResult;
import com.example.client.Results.PersonResult;
import com.example.client.Results.RegisterResult;
import com.example.client.Results.Result;
import com.example.client.Tasks.LoginTask;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;



public class ProxyServer
{
    static String serverIp;
    static int serverPortNum;
    String currentPersonID;
    final static String TAG = "ProxyServer";

    public ProxyServer(String serverIp, int serverPortNum)
    {
        ProxyServer.serverIp = serverIp;
        ProxyServer.serverPortNum = serverPortNum;
    }

    /**
     * clear for testing purposes
     */
    public void clear()
    {
        try
        {
            URL url = new URL("http://" + serverIp + ":" + serverPortNum + "/clear");
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("POST");
            http.setDoOutput(false);
            http.connect();

            if(http.getResponseCode() == HttpURLConnection.HTTP_OK)
            {
                InputStream input = http.getInputStream();
                String outputResult = readString(input);
            }
            else
            {
                System.out.println("Clear failed" + http.getResponseCode());
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    /**
     * Send login command to server and process reply
     * @param r
     * @return result
     */
    public Result login(Request r)
    {
        try
        {
            URL url = new URL("http://" + serverIp + ":" + serverPortNum + "/user/login");
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("POST");
            http.setDoOutput(true); // we have a request body
            Log.d(TAG,"Attempting Connection");
            http.connect(); // connect
            Log.d(TAG," Connection made");
            Gson g = new Gson();
            String json = g.toJson(r);
            OutputStream reqBody = http.getOutputStream();
            writeString(json, reqBody);
            reqBody.close();

            if(http.getResponseCode() == HttpURLConnection.HTTP_OK)
            {
                InputStream respBody = http.getInputStream();
                String respData = readString(respBody);
                Gson gson = new Gson();
                LoginResult loginResult = gson.fromJson(respData, LoginResult.class);
                return loginResult;
                //still need to create and return login response
            }
            else
            {
                throw new Exception("Incorrect response code!");
            }
        }
        catch(Exception e)
        {
            Result result = new Result("Error logging in!", false);
            return result;
        }
    }

    /**
     * Send Register command to server and process reply
     * @param r
     * @return result
     */
    public Result register(RegisterRequest r)
    {
        try
        {
            URL url = new URL("http://" + serverIp + ":" + serverPortNum + "/user/register");
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("POST");
            http.setDoOutput(true);
            http.addRequestProperty("Accept", "application/json");
            http.connect();

            Gson gson = new Gson();
            String json = gson.toJson(r);
            OutputStream reqBody = http.getOutputStream();
            writeString(json, reqBody);
            reqBody.close();
            if(http.getResponseCode() == HttpURLConnection.HTTP_OK)
            {
                InputStream resBody = http.getInputStream();
                String resJson = readString(resBody);
                RegisterResult result = gson.fromJson(resJson, RegisterResult.class);
                currentPersonID = result.getPersonID();
                return result;
            }
            else
            {
                throw new Exception("Incorrect response code! " +  http.getResponseCode());
            }
        }
        catch (Exception e)
        {
            Result result = new Result("Error registering in!",false);
            return result;
        }
    }

    /**
     * Retrieve persons related to logged in user
     * @param authToken
     * @return user family data
     */
    protected Result getPeople(String authToken)
    {
        try
        {
            URL url = new URL("http://" + serverIp + ":" + serverPortNum + "/person");
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("GET");
            //http.setDoOutput(true);
            http.addRequestProperty("Accept", "application/json");
            http.addRequestProperty("Authorization", authToken);
            http.connect();
            // we do not have to send anything
            int response = http.getResponseCode();
            if(response == HttpURLConnection.HTTP_OK)
            {
                InputStream inputStream = http.getInputStream();
                Gson g = new Gson();
                String json = readString(inputStream);
                MultiplePersonResult result = g.fromJson(json, MultiplePersonResult.class);
                return result;
            }
            else
            {
                throw new Exception("Response failure" + response);
            }
        }
        catch(Exception e)
        {
            Result result = new Result("Error getting people!" + e.toString(), false);
            return result;
        }
    }

    /**
     * Retrieve events related to logged in user
     * @param authToken
     * @return
     */
    protected Result getEvent(String authToken)
    {
        try
        {
            URL url = new URL("http://" + serverIp + ":" + serverPortNum + "/event");
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("GET");
            http.setRequestProperty("Authorization", authToken);
            http.setRequestProperty("Accept","application/json");
            http.connect();

            int response = http.getResponseCode();
            if(response == HttpURLConnection.HTTP_OK)
            {
                InputStream input = http.getInputStream();
                String json = readString(input);
                Gson g = new Gson();
                MultipleEventResult result = g.fromJson(json, MultipleEventResult.class);
                return result;
            }
            else
            {
                throw new Exception("Failure " + response);
            }
        }
        catch(Exception e)
        {
            Result result = new Result("Error! "+ e.toString(), false);
            return result;
        }
    }

    public void fill(PersonData data, String authToken) throws Exception
    {

        Result personResult = getPeople(authToken);
        Result eventResult = getEvent(authToken);
        if(personResult.isSuccess() && eventResult.isSuccess())
        {
            MultiplePersonResult personsResult = (MultiplePersonResult) personResult;
            data.fillPeople(personsResult.getData());
            MultipleEventResult eventsResult = (MultipleEventResult) eventResult;
            data.fillEvents(eventsResult.getData());
            PersonData.setMainPerson();
        }
        else
        {
            throw new Exception(personResult.getMessage() + eventResult.getMessage()); //FIXME: specify result
        }
    }

    private static void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }
    private static String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }
}
