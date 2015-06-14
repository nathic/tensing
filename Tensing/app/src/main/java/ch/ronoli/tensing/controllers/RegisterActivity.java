package ch.ronoli.tensing.controllers;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

import ch.ronoli.tensing.MainActivity;
import ch.ronoli.tensing.R;

/**
 * Created by nathic on 13.06.2015.
 */
public class RegisterActivity extends Activity{

    private String mPassword;
    private String mUser;
    private EditText mUserView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private Button buttonRegister;
    private final String TAG = "RegisterActivity";

    private AsyncTask mRegTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        mUserView = (EditText) findViewById(R.id.user);
        mPasswordView = (EditText) findViewById(R.id.password);
        mLoginFormView = findViewById(R.id.registration_form);
        mProgressView = findViewById(R.id.registration_progress);

        buttonRegister = (Button) findViewById(R.id.button_register);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });
    }

    private void register() {
        if (mRegTask != null && !mRegTask.isCancelled()) {
            Toast.makeText(this, R.string.registration_already_running, Toast.LENGTH_SHORT).show();
            return;
        }

        mUserView.setError(null);
        mPasswordView.setError(null);

        mUser = mUserView.getText().toString();
        mPassword = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(mUser)) {
            mUserView.setError(getString(R.string.error_field_required));
            focusView = mUserView;
            cancel = true;
        }
        if (TextUtils.isEmpty(mPassword)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {

            InputMethodManager imm =
                    (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(mLoginFormView.getWindowToken(), 0);
            showProgress(true);
            registerInBackground();

        }
    }

    private void registerInBackground() {
        mRegTask = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try{
                    Class.forName("com.mysql.jdbc.Driver").newInstance();
                    Connection connection = DriverManager.getConnection("jdbc:mysql://tensing-staefa.ch:3306/adressverwaltung", "tensing-staefa-a", "sting87");
                    Statement statement = connection.createStatement();
                    boolean found = false;
                    String query = "SELECT * FROM users where name = ";
                    query = query +"'" +mUser+"'";
                    ResultSet result = statement.executeQuery(query);
                    while(result.next()){
                        Log.i(TAG, result.getString("name"));
                        Log.i(TAG, result.getString("password"));
                        MessageDigest md = MessageDigest.getInstance("MD5");
                        md.update(mPassword.getBytes());
                        byte[] digest = md.digest();
                        StringBuffer sb = new StringBuffer();
                        for (byte b : digest) {
                            sb.append(String.format("%02x", b & 0xff));
                        }
                        String md5Password = sb.toString();

                        Log.i(TAG, md5Password);
                        if(result.getString("password").equals(md5Password)){
                            Bundle accountInfo = new Bundle();
                            accountInfo.putString("user", mUser);
                            accountInfo.putString("password", mPassword);

                            final Account account = new Account(mUser, "ch.ronoli.tensing");

                            if (AccountManager.get(getApplication()).addAccountExplicitly(account, null, accountInfo)) {
                                found = true;
                                startMainActivity();
                            }
                        }
                    }
                    if(!found){
                        showProgress(false);
                        cancel(true);
                    }
                } catch(Exception e){
                    System.err.println("Error");
                    showProgress(false);
                    cancel(true);
                }
                return null;
            }
        }.execute(null, null, null);
    }

    private void showProgress(final boolean show) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        });
    }

    public String md5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i=0; i<messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
    public void startMainActivity(){
        Intent mainActivity = new Intent(this, MainActivity.class);
        finish();
        startActivity(mainActivity);
        Log.i("Registration", "Registration erfolgreich");
    }
}
