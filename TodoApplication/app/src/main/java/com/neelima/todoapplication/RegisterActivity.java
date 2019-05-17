package com.neelima.todoapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    EditText usn, name, password, confirmPassword;
    Button signUpcnfrm, cancelbtn;
    String  naame, paassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name = (EditText) findViewById(R.id.name);
        password = (EditText) findViewById(R.id.password);
        confirmPassword = (EditText) findViewById(R.id.confirmPassword);
        signUpcnfrm = (Button) findViewById(R.id.registerCnfrm);
        cancelbtn = (Button) findViewById(R.id.cancelBtn);
        signUpcnfrm.setOnClickListener(this);
        cancelbtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String finalPassword = confirmPassword.getText().toString();
        String newPassword = password.getText().toString();
        if (v.equals(signUpcnfrm)) {
            if (finalPassword.equals(newPassword)) {
                new Update().execute();
                Toast.makeText(this, "Registered", Toast.LENGTH_LONG).show();
                Intent it5 = new Intent(this, LoginActivity.class);
                startActivity(it5);
            } else {
                Toast.makeText(this, "Password does not match", Toast.LENGTH_LONG).show();
            }
        }
        if (v.equals(cancelbtn)) {
            Toast.makeText(this, "Just press back button, SIMPLE !!!", Toast.LENGTH_LONG).show();

        }
    }

    class Update extends AsyncTask<String, Void, Long> {


        protected Long doInBackground(String... urls) {

            naame = name.getText().toString();
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("https://homeomorphic-string.000webhostapp.com/pcode.php");
            paassword = password.getText().toString();
            try {

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("Name", naame));
                nameValuePairs.add(new BasicNameValuePair("Password", paassword));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));


                HttpResponse response = httpclient.execute(httppost);
                Log.i("Response:", response.toString());


            } catch (Exception e) {
                Log.i("Response:",e.toString());
            }
            return null;

        }

        protected void onProgressUpdate(Integer... progress) {

        }

        protected void onPostExecute(Long result) {

        }
    }
}

