package com.example.client.Tasks;

import android.os.AsyncTask;

import com.example.client.PersonData;
import com.example.client.ProxyServer;
import com.example.client.Results.Result;

public class FillTask extends AsyncTask<String, String,Result>
{
    private fillListener listener;

    public interface fillListener
    {
        void onComplete(Result result);
    }
    public FillTask(fillListener l)
    {
        listener = l;
    }

    @Override
    protected Result doInBackground(String... strings)
    {
        String serverIp = strings[0];
        int serverPortNum = Integer.parseInt(strings[1]);
        String authToken = strings[2];
        ProxyServer proxy = new ProxyServer(serverIp, serverPortNum);
        try
        {
            proxy.fill(PersonData.instance(), authToken);
        }
        catch(Exception e)
        {
            return new Result(e.toString(),false);
        }
        String message = "Welcome " + PersonData.getMainPerson().getFirstName() + " " + PersonData.getMainPerson().getLastName();
        return new Result(message, true);
    }
    protected void onPostExecute(Result result)
    {
        listener.onComplete(result);
    }

}
