package com.example.client.Results;

import com.example.client.Models.Person;

/**
 * Result to be returned by EventService "multipleEvent" Method
 */
public class MultiplePersonResult extends com.example.client.Results.Result
{
    /**
     * List of multiple events
     */
    private Person[] data;
    /**
     * Result message
     */


    public Person[] getData()
    {
        return data;
    }
    /**
     * Parameterized Constructor
     * @param data
     * @param success
     */
    public MultiplePersonResult(Person[] data, boolean success)
    {
        super(null,success);
        this.data = data;
    }
}
