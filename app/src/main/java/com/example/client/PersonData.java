package com.example.client;

import android.util.Log;

import com.example.client.Models.Event;
import com.example.client.Models.Person;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeMap;

public class PersonData
{
    private static PersonData _instance;
    private static String currentAuth;
    private static Person mainPerson;
    private static String mainPersonID;
    private static boolean loggedIn;

    public static void setMainPersonID(String mainPersonID)
    {
        PersonData.mainPersonID = mainPersonID;
    }

    private static TreeMap<String, Person> people;
    private static TreeMap<String, Event> happenings;
    private static ArrayList<Event> lifeEvents;
    private static TreeMap<String, Float> colors;
    private static int colorIndex;
    //FIXME: paternal and maternal needed

    private static Float[] unsortedColors = {210.0f, 240.0f, 180.0f, 120.0f, 300.0f, 30.0f, 0.0f, 330.0f, 270.0f, 0f};
    private PersonData()
    {
        people = new TreeMap<String, Person>();
        happenings = new TreeMap<String, Event>();
        colors = new TreeMap<String, Float>();
        currentAuth = null;
        loggedIn = false;
        colorIndex = 0;
    }

    public static PersonData instance()
    {
        if(_instance == null)
        {
            _instance = new PersonData();
        }
        return _instance;
    }

    public static void matchColors()
    {
        ArrayList<String> eventTypes = new ArrayList<String>();
        Set<String> keys = happenings.keySet();
        //Log.d("TEMP", "keyNUM: " + keys.size());
        for(String x: keys)
        {
            //Log.d("TEMP", x);
            Event event = happenings.get(x);

            if(!eventTypes.contains(event.getEventType()))
            {
                eventTypes.add(event.getEventType());
                colors.put(event.getEventType(), getColor());
            }
        }

    }

    private static Float getColor()
    {
        int index = colorIndex;
        if(colorIndex == unsortedColors.length - 1)
        {
            colorIndex = 0;
        }
        else
        {
            colorIndex++;
        }
        return unsortedColors[index];
    }

    public static void setCurrentAuth(String currentAuth)
    {
        PersonData.currentAuth = currentAuth;
    }

    public static void setMainPerson()
    {
        PersonData.mainPerson = people.get(mainPersonID);
    }

    public static String getCurrentAuth()
    {
        return currentAuth;
    }

    public static Person getMainPerson()
    {
        return mainPerson;
    }
    public static Person getPerson(String ID)
    {
        return people.get(ID);
    }

    public static TreeMap<String, Float> getColors()
    {
        return colors;
    }

    public static boolean isLoggedIn()
    {
        return loggedIn;
    }

    public static void setLoggedIn(boolean status)
    {
        loggedIn = status;
    }

    public static TreeMap<String, Event> getHappenings() {
        return happenings;
    }

    public void fillPeople(Person[] inputPeople)
    {
        for(Person x: inputPeople)
        {
            people.put(x.getPersonID(), x);
        }

    }

    public void fillEvents(Event[] inputEvents)
    {
        for(Event x: inputEvents)
        {
            happenings.put(x.getPersonID(), x);
        }
    }
}
