package com.example.client;

import com.example.client.Models.Event;
import com.example.client.Models.Person;
import com.example.client.Requests.RegisterRequest;
import com.example.client.Requests.Request;
import com.example.client.Results.MultipleEventResult;
import com.example.client.Results.MultiplePersonResult;
import com.example.client.Results.RegisterResult;
import com.example.client.Results.Result;

import org.junit.Test;

import java.lang.reflect.Proxy;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ProxyServerTest {

    final int port = 8080;
    final String name = "localhost";
    private Request logRequest = new Request("username","password");
    private RegisterRequest regRequest = new RegisterRequest("username","password","email","lastname",'m',"firstname");

    /**
     * A clear for testing purposes
     */
    public void clear()
    {
        ProxyServer proxy = new ProxyServer(name, port);
        proxy.clear();
    }

    @Test
    public void testLogin()
    {
        ProxyServer proxy = new ProxyServer("localhost",8080);
        proxy.clear();
        proxy.register(regRequest);
        Result result = proxy.login(logRequest);
        assertTrue(result.isSuccess());  //login success
        logRequest.setPassword("something different");
        result = proxy.login(logRequest);
        assertFalse(result.isSuccess()); //login with false password
        logRequest.setPassword("password"); //reset password
    }

    @Test
    public void testRegister()
    {

        ProxyServer proxy = new ProxyServer("localhost",8080);
        proxy.clear();
        Result result = proxy.register(regRequest);
        assertTrue(result.isSuccess()); //Register success

        result = proxy.register(regRequest);
        assertFalse(result.isSuccess()); //Attempt to register same user
    }

    @Test
    public void testGetPeople()
    {
        String authToken = prepReg();
        ProxyServer proxy = new ProxyServer("localhost", 8080);
        Result result = proxy.getPeople(authToken);
        assertTrue(result.isSuccess());   // getPerson success
        MultiplePersonResult personResult = (MultiplePersonResult) result;
        Person[] personList = personResult.getData();
        assertEquals(personList.length,31); // 31 returned persons for 4 generations

        result = proxy.getPeople("garbageAuth");
        assertFalse(result.isSuccess()); //garbage auth token returns failed result
    }

    @Test
    public void testGetEvent()
    {
        String authToken = prepReg();
        ProxyServer proxy = new ProxyServer("localhost", 8080);
        Result result = proxy.getEvent(authToken);
        assertTrue(result.isSuccess());   // getEvents success
        MultipleEventResult eventResult = (MultipleEventResult) result;
        Event[] eventList = eventResult.getData();
        assertEquals(eventList.length,91); // 31 returned persons for 4 generations

        result = proxy.getEvent("garbageAuth");
        assertFalse(result.isSuccess()); //garbage auth token returns failed result
    }

    /**
     * register prep for getPerson/getEvent tests
     * @return
     */
    private String prepReg()
    {
        ProxyServer proxy = new ProxyServer("localhost",8080);
        proxy.clear();
        Result result = proxy.register(regRequest);
        assertTrue(result.isSuccess()); // register success
        RegisterResult regRes = (RegisterResult) result;
        return regRes.getAuthToken();
    }
}
