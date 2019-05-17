package com.neelima.todoapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class LoginActivity extends Activity implements View.OnClickListener {
    EditText name;
    String matchname;
    String matchpassword;

    AQuery aQuery;
    List<String> Namelist = new ArrayList<String>();
    List<String> Passwordlist = new ArrayList<String>();

    EditText password;
    Button login,register;
    TextView forgot;
    int listLength;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        aQuery = new AQuery(this);
        setContentView(R.layout.activity_login);
        name = (EditText) findViewById(R.id.name);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.LogIn);
        register = (Button) findViewById(R.id.Register);
        forgot = (TextView) findViewById(R.id.forgot);
        login.setOnClickListener(this);
        register.setOnClickListener(this);
        forgot.setOnClickListener(this);
        fetchdata();

    }

    public void fetchdata() {
        aQuery.ajax("https://homeomorphic-string.000webhostapp.com/gdat.php", JSONArray.class, new AjaxCallback<JSONArray>() {
            @Override
            public void callback(String url, JSONArray array, AjaxStatus status) {
                super.callback(url, array, status);
                Log.i("Result", url + "response" + array);
                try {
                    listLength = array.length();
                    for (int j = 0; j < array.length(); j++) {
                        JSONObject object = array.getJSONObject(j);
                        Namelist.add(object.getString("Name"));
                        Passwordlist.add(object.getString("Password"));
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }



    private boolean isValidPassword(String pass) throws JSONException {
        if (pass != null && pass.length() >= 4) {
            int i;
            matchname = name.getText().toString();
            matchpassword = password.getText().toString();
            for (i = 0; i < listLength; i++) {
                if (matchname.equals(Namelist.get(i))) {
                    if (matchpassword.equals(Passwordlist.get(i))) {
                        return true;

                    }
                }


            }
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchdata();

    }

    @Override
    public void onClick(View v) {
        if (v.equals(forgot)) {
            Toast.makeText(this, "Eat more almonds for good brain!", Toast.LENGTH_LONG).show();
        }

        if (v.equals(register)) {
            Intent it564 = new Intent(this, RegisterActivity.class);
            startActivity(it564);
        }
        if (v.equals(login)) {

            String pass = password.getText().toString();
            try {
                if (!isValidPassword(pass)) {
                    password.setError("Invalid Password");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                if (isValidPassword(pass)) {
                    Intent it5 = new Intent(this, MainActivity.class);
                    startActivity(it5);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

}


