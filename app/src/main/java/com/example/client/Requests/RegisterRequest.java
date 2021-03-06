package com.example.client.Requests;

/**
 * Request to Register a new user
 */
public class RegisterRequest extends Request
{
    @Override
    public String toString() {
        return "RegisterRequest{" +
                "email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender=" + gender +
                '}';
    }

    /**
     * register account email
     */
    private String email;
    /**
     * register account firstname
     */
    private String firstName;
    /**
     * register account lastname
     */
    private String lastName;
    /**
     * register account gender
     */
    private char gender;


    /**
     * Parameterized constructor
     * @param username
     * @param password
     * @param email
     * @param lastName
     * @param gender
     * @param firstName
     */
    public RegisterRequest(String username, String password, String email, String lastName, char gender, String firstName)
    {
        super(username,password);
        this.email = email;
        this.lastName = lastName;
        this.gender = gender;
        this.firstName = firstName;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public String getEmail()
    {
        return email;
    }

    public String getLastName()
    {
        return lastName;
    }

    public char getGender()
    {
        return gender;
    }
}
