package com.example.client.Tasks;

import com.example.client.Results.Result;

public interface Listener
{
    void onError(Error e);
    void onFinished(Result result);
}