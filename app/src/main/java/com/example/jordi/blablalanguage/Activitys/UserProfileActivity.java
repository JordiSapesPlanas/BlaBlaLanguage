package com.example.jordi.blablalanguage.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jordi.blablalanguage.Models.User;
import com.example.jordi.blablalanguage.Models.Utils;
import com.example.jordi.blablalanguage.R;

public class UserProfileActivity extends AppCompatActivity {

    // UI references.
    private TextView mLabelView;
    private EditText mNameView;
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private EditText mBirthdayView;
    private EditText mFacebookView;
    private EditText mSexView;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        // Set up the form.
        mLabelView = (TextView) findViewById(R.id.username);
        mNameView = (EditText) findViewById(R.id.name);
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mBirthdayView = (EditText) findViewById(R.id.birthday);
        mSexView = (EditText) findViewById(R.id.sex);
        mFacebookView = (EditText) findViewById(R.id.facebook);
        mPasswordView = (EditText) findViewById(R.id.password);

        Utils utils = new Utils();
        String email =  utils.getKey(this,"USER_LOGGED");

        User user = new User();

        user = user.getUserById(this,email);

        mLabelView.setText(email);
        mEmailView.setText(email);
        mNameView.setText(user.getName());
        mBirthdayView.setText(user.getBirthday());
        mFacebookView.setText(user.getFacebookProfile());
        mSexView.setText(user.getSex());

        mEmailView.setEnabled(false);

        Button mSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Update();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void Update() {

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

            if (cancel) {
                // There was an error; don't attempt login and focus the first
                // form field with an error.
                focusView.requestFocus();
            } else {

                alert("Salvo");
                this.finish();

                User newUser = new User();
                User tmp = newUser.getUserById(this,email.trim());
                User update = new User(name, email.trim(), tmp.getPass(), facebook, sex, birthday);

                if(!update.Save(this)) {
                    alert("Error, try again!");
                }else{
                    alert("Saved");
                    this.finish();
                }
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


}


