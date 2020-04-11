package com.example.client.Tasks;

import android.os.AsyncTask;

import com.example.client.PersonData;
import com.example.client.ProxyServer;
import com.example.client.Requests.Request;
import com.example.client.Results.LoginResult;
import com.example.client.Results.Result;

public class LoginTask extends AsyncTask<String, String, Result>
{
    private Listener listener;

    public interface fillListener
    {
        void onComplete(Result result);
        void onError(Error e);
    }

    public LoginTask(Listener l)
    {
        listener = l;
    }

    @Override
    protected Result doInBackground(String... strings)
    {
        String serverIp = strings[0];
        int serverPortNum = 0;
        try
        {
            serverPortNum = Integer.parseInt(strings[1]);
        }
        catch(NumberFormatException e)
        {
            return new Result("Invalid Server Port: " + strings[1], false);
        }
        String username = strings[2];
        String password = strings[3];

        Request loginRequest = new Request(username, password);
        ProxyServer proxy = new ProxyServer(serverIp, serverPortNum);
        Result result = proxy.login(loginRequest);
        if(result.isSuccess())
        {
            PersonData.setCurrentAuth(((LoginResult) result).getAuthToken());
            String personID = ((LoginResult) result).getPersonID();
            PersonData.setMainPersonID(personID);
        }
        else
        {
            result.setMessage("Login failure!");
        }
        return result;
    }

    protected void onPostExecute(Result result)
    {
        listener.onFinished(result);
    }

}
