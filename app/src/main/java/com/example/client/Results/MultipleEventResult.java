package com.example.client.Results;


import com.example.client.Models.Event;

/**
 * Result to be returned by EventService "multipleEvent" Method
 */
public class MultipleEventResult extends com.example.client.Results.Result
{
    /**
     * List of multiple events
     */
    private Event[] data;

    public Event[] getData()
    {
        return data;
    }

    /**
     * Parameterized Constructor
     * @param data
     * @param message
     */
    public MultipleEventResult(Event[] data, String message, boolean success)
    {
        super(message, success);
        this.data = data;
    }
}
