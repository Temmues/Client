package com.example.client;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.client.Models.Person;
import com.example.client.Requests.RegisterRequest;
import com.example.client.Requests.Request;
import com.example.client.Results.Result;
import com.example.client.Tasks.FillTask;
import com.example.client.Tasks.Listener;
import com.example.client.Tasks.LoginTask;
import com.example.client.Tasks.RegisterTask;
import com.example.client.R;

public class LoginFragment extends Fragment implements Listener, FillTask.fillListener
{
    private RadioGroup genderButtons;
    private Request loginRequest;
    private RegisterRequest registerRequest;
    private EditText serverHost;
    private EditText serverPort;
    private EditText username;
    private EditText password;
    private EditText firstName;
    private EditText lastName;
    private EditText email;
    private RadioButton female;
    private RadioButton male;
    private Button login;
    private Button register;
    private boolean operatonSuccess;// our model
    private char gender = '0';

    // we have a data built into our fragment that gets put into the task somehow
    // could we use the listener for this?

    public LoginFragment()
    {
        operatonSuccess = false;
    }

    public void setModel(PersonData data)
    {

    }
    public static LoginFragment newInstance() // I dont know if I need this
    {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.login, container, false);
        serverHost = (EditText) v.findViewById(R.id.serverHost);
        serverPort = (EditText) v.findViewById(R.id.serverPort);
        username = (EditText) v.findViewById(R.id.username);
        password = (EditText) v.findViewById(R.id.password);
        firstName = (EditText) v.findViewById(R.id.firstname);
        lastName = (EditText) v.findViewById(R.id.lastname);
        email = (EditText) v.findViewById(R.id.email);
        genderButtons = (RadioGroup) v.findViewById(R.id.gender_buttons);


        female = (RadioButton) v.findViewById(R.id.female);
        male = (RadioButton) v.findViewById(R.id.male);
        login = (Button) v.findViewById(R.id.login);
        register = (Button) v.findViewById(R.id.register);

        genderListeners();

        username.addTextChangedListener(loginTextWatcher);
        password.addTextChangedListener(loginTextWatcher);
        serverHost.addTextChangedListener(loginTextWatcher);
        serverPort.addTextChangedListener(loginTextWatcher);

        username.addTextChangedListener(registerTextWatcher);
        password.addTextChangedListener(registerTextWatcher);
        serverHost.addTextChangedListener(registerTextWatcher);
        serverPort.addTextChangedListener(registerTextWatcher);
        firstName.addTextChangedListener(registerTextWatcher);
        lastName.addTextChangedListener(registerTextWatcher);
        email.addTextChangedListener(registerTextWatcher);

        register.setOnClickListener(new View.OnClickListener() {
            //lets set up functions
            @Override
            public void onClick(View v) {
                regiButtonClicked();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginRequest = new Request(username.getText().toString(), password.getText().toString());
                logButtonClicked();
            }
        });
        return v;
    }

    private void logButtonClicked()
    {
        LoginTask task = new LoginTask(this);
        try
        {
            task.execute(serverHost.getText().toString(), serverPort.getText().toString(),
                    username.getText().toString(), password.getText().toString());
        }
        catch(Exception e)
        {
            Toast.makeText(this.getContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void regiButtonClicked()
    {
        RegisterTask task = new RegisterTask(this);
        try
        {
            task.execute(serverHost.getText().toString(), serverPort.getText().toString(), username.getText().toString(), password.getText().toString(), email.getText().toString(),
                    lastName.getText().toString(), Character.toString(gender), firstName.getText().toString());
        }
        catch(Exception e)
        {
            Toast.makeText(this.getContext(),e.toString(), Toast.LENGTH_SHORT).show();
        }

        PersonData.setLoggedIn(true);
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }
    private TextWatcher loginTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            login.setEnabled(!widgetToString(username).isEmpty() && !widgetToString(password).isEmpty()
            && !widgetToString(serverPort).isEmpty() && !widgetToString(serverHost).isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private TextWatcher registerTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count)
        {
            register.setEnabled(!widgetToString(username).isEmpty() && !widgetToString(password).isEmpty()
                    && !widgetToString(serverPort).isEmpty() && !widgetToString(serverHost).isEmpty()
            && !widgetToString(firstName).isEmpty() && !widgetToString(lastName).isEmpty() &&
                    !widgetToString(email).isEmpty() && genderButtons.getCheckedRadioButtonId() != -1);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private String widgetToString(EditText widget)
    {
        return widget.getText().toString().trim();
    }

    private void genderListeners()
    {
        female.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                gender = 'f';
                register.setEnabled(!widgetToString(username).isEmpty() && !widgetToString(password).isEmpty()
                        && !widgetToString(serverPort).isEmpty() && !widgetToString(serverHost).isEmpty()
                        && !widgetToString(firstName).isEmpty() && !widgetToString(lastName).isEmpty() &&
                        !widgetToString(email).isEmpty());
            }
        });

        male.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                gender = 'm';
                register.setEnabled(!widgetToString(username).isEmpty() && !widgetToString(password).isEmpty()
                        && !widgetToString(serverPort).isEmpty() && !widgetToString(serverHost).isEmpty()
                        && !widgetToString(firstName).isEmpty() && !widgetToString(lastName).isEmpty() &&
                        !widgetToString(email).isEmpty());
            }
        });

    }

    @Override
    public void onError(Error e)
    {
        //process error
    }

    @Override
    public void onFinished(Result result)
    {
        if(result.isSuccess())
        {
            initiateFill();
        }
        else
        {
            Toast.makeText(this.getContext(),result.getMessage() ,Toast.LENGTH_SHORT).show();
        }
    }

    public void initiateFill()
    {
        FillTask fillTask = new FillTask(this);
        fillTask.execute(serverHost.getText().toString(), serverPort.getText().toString(), PersonData.getCurrentAuth());
    }

    @Override
    public void onComplete(Result result)
    {
        Person mainPerson = PersonData.getMainPerson();
        Toast.makeText(this.getContext(),result.getMessage() ,Toast.LENGTH_SHORT).show();
        PersonData.setLoggedIn(true);
        PersonData.matchColors();
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }
}
