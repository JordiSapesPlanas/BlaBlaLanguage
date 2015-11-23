package com.example.jordi.blablalanguage.Activitys;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jordi.blablalanguage.Adapters.meetingAdapter;
import com.example.jordi.blablalanguage.Models.User;
import com.example.jordi.blablalanguage.Models.Utils;
import com.example.jordi.blablalanguage.R;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;


/**
 * Created by vitorlui on 22/11/2015.
 */
public class CreateUserActivity extends AppCompatActivity {
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    SharedPreferences sharedPreferences;

    // UI references.
    private EditText mNameView;
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private EditText mBirthdayView;
    private EditText mFacebookView;
    private EditText mSexView;
    private View mProgressView;
    private View mLoginFormView;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createuser);


        // Set up the form.
        mNameView = (EditText) findViewById(R.id.name);
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mBirthdayView = (EditText) findViewById(R.id.birthday);
        mSexView = (EditText) findViewById(R.id.sex);
        mFacebookView = (EditText) findViewById(R.id.facebook);
        mPasswordView = (EditText) findViewById(R.id.password);

        Button mSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Register();
            }
        });
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        Utils utils = new Utils();
        String email =  utils.getKey(this,"USER_LOGGED");

        mEmailView.setText(email);
        mEmailView.setEnabled(false);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
       // client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void Register() {

        try {

            Utils utils = new Utils();
            // Reset errors.
            mEmailView.setError(null);
            mPasswordView.setError(null);
            mNameView.setError(null);
            //mNameView.setError(null);

            // Store values at the time of the login attempt.
            String name = mNameView.getText().toString();
            String email = mEmailView.getText().toString();
            String password = mPasswordView.getText().toString();
            String sex = mSexView.getText().toString();
            String facebook = mFacebookView.getText().toString();
            String birthday = mBirthdayView.getText().toString();

            boolean cancel = false;
            View focusView = null;

            // Check for a valid name.
            if (TextUtils.isEmpty(name)) {
                mNameView.setError(getString(R.string.error_field_required));
                focusView = mNameView;
                cancel = true;
            }


            // Check for a valid password, if the user entered one.
            if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
                mPasswordView.setError(getString(R.string.error_invalid_password));
                focusView = mPasswordView;
                cancel = true;
            }

            // Check for a valid email address.
            if (TextUtils.isEmpty(email)) {
                mEmailView.setError(getString(R.string.error_field_required));
                focusView = mEmailView;
                cancel = true;
            } else if (!isEmailValid(email)) {
                mEmailView.setError(getString(R.string.error_invalid_email));
                focusView = mEmailView;
                cancel = true;
            }

            if (cancel) {
                // There was an error; don't attempt login and focus the first
                // form field with an error.
                focusView.requestFocus();
            } else {

                User newUser = new User(name, email.trim(), password, facebook, sex, birthday);

               if(!newUser.Save(this)) {
                    alert("Save error, try again!");
                }else{
                    utils.saveKey(this, "USER_LOGGED",email.trim());
                    startActivity(new Intent(getApplicationContext(), SearchMeetingActivity.class));
                    alert("Welcome " + name.trim().split(" ")[0]);
                    this.finish();
                }


                User user = new User();

                User u = user.getUserById(this,email.trim());

               String nome =  u.getName();

                /*String result = sharedPreferences.getString("USERT", "");
                alert("Result: "+ result);*/

                //User testValue = newUser.getUserById(this, email);

                /*
                    alert("Nome:" + testValue.getName());

                    alert("Birthday: " + testValue.getBirthday());

                    alert("Sex: " + testValue.getSex());

                    alert("Facebook: " + testValue.getFacebookProfile());

                    alert("Foto: " + testValue.getPhoto());

                    alert("Pass: " + testValue.getPass());
                */

                //Toast.makeText(getApplicationContext(), "Validating google user", Snackbar.LENGTH_LONG).show();

                //startActivity(new Intent(getApplicationContext(), CreateUserActivity.class));

            /*
            //If is null then send to register
            if(user.findUser(email,password)==null){

                startActivity(new Intent(getApplicationContext(), CreateUserActivity.class));

            }else{
                //logged


                //sharedPreferences.edit().putString("password", password);
                editor.putString("email", email);
                editor.commit();

                Toast.makeText(getApplicationContext(),
                        "saved to shared preferences user email "+ sharedPreferences.getString("email", "h"),
                        Toast.LENGTH_SHORT).show();

                startActivity(new Intent(getApplicationContext(), SearchMeetingActivity.class));


            }
            */

                // this.finish();
            }
        }
        catch (Exception e){
          alert("Error: "+ e.toString());
          System.out.println(e.toString());
        }
    }

    private void alert(String alert){
        Toast.makeText(getApplicationContext(), alert, 10000).show();
    }
    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }
}
