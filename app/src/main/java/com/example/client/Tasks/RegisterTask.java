package com.example.client.Tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.client.PersonData;
import com.example.client.ProxyServer;
import com.example.client.Requests.RegisterRequest;
import com.example.client.Results.RegisterResult;
import com.example.client.Results.Result;

import java.lang.reflect.Proxy;

public class RegisterTask extends AsyncTask<String, String, Result>
{
    private Listener listener;
    public RegisterTask(Listener l)
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
        String email = strings[4];
        String lastname = strings[5];
        String gender = strings[6];
        String firstname = strings[7];

        ProxyServer proxy = new ProxyServer(serverIp,serverPortNum); // FIXME: figure this out!
        RegisterRequest request = new RegisterRequest(username, password, email, lastname, gender.charAt(0), firstname);
        Result result = proxy.register(request);
        if(result.isSuccess())
        {
            PersonData.setCurrentAuth(((RegisterResult) result).getAuthToken());
            String personID = ((RegisterResult) result).getPersonID();
            PersonData.setMainPersonID(personID);
        }
        return result;
    }

    protected void onPostExecute(Result result)
    {
        listener.onFinished(result);
    }
}
