package com.sajagjain.sajagjain.majorproject;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, OnClickListener {


    // UI references.
    private static int RC_SIGN_IN = 23;
    private static String TAG = "MAIN_ACTIVITY";
    private GoogleSignInClient mGoogleApiClient;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private EditText inputPassword, inputEmail;
    private Button signIn, signUp;
    private TextView forgetPassword;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        FirebaseAuth.getInstance().signOut();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        getWindow().setBackgroundDrawable(ContextCompat.getDrawable(this,R.drawable.app_background));
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        inputPassword = (EditText) findViewById(R.id.password_signin_activity);
        signIn = (Button) findViewById(R.id.email_sign_in_button);
        inputEmail = (EditText) findViewById(R.id.email_signin_activity);
        signUp = (Button) findViewById(R.id.button2);
        forgetPassword = (TextView) findViewById(R.id.forgetpassword);
        progressBar = (ProgressBar) findViewById(R.id.login_progress);
        progressBar.getIndeterminateDrawable()
                .setColorFilter(Color.parseColor("#3badcf"), PorterDuff.Mode.SRC_IN);


        inputPassword.setSelected(false);
        inputEmail.setSelected(false);
        // Set up the login form.
        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    Log.d("AUTH", "USER LOGGED IN" + user.getEmail());
                } else {
                    Log.d("AUTH", "user not logged in");
                }
            }
        };

        signUp.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignUp.class));
            }
        });

        forgetPassword.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ForgetPassword.class));
            }
        });
        signIn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                progressBar.setVisibility(View.VISIBLE);
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                final String email = inputEmail.getText().toString();
                final String password = inputPassword.getText().toString();

                if (email.equals("") || password.equals("")) {
                    progressBar.setVisibility(View.GONE);
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    Toast.makeText(LoginActivity.this, "Please Enter both Email ID and Password", Toast.LENGTH_LONG).show();
                } else {
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    // If sign in fails, display a message to the user. If sign in succeeds
                                    // the auth state listener will be notified and logic to handle the
                                    // signed in user can be handled in the listener.
                                    String emaill=email;
                                    String namee=email;
                                    if (!task.isSuccessful()) {
                                        // there was an error
                                        progressBar.setVisibility(View.GONE);
                                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                                        Toast.makeText(LoginActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();

                                    } else {
                                        progressBar.setVisibility(View.GONE);
                                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);


                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class)
                                                .putExtra("userid", emaill)
                                                .putExtra("username", namee);
                                        startActivity(intent);
                                        LoginActivity.this.finish();
                                    }
                                }
                            });
                }

            }
        });


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("246519424086-r986l0pvhdnh9c9i15r4g4v46gvore9m.apps.googleusercontent.com"/*getString(R.string.default_web_client_id)*/)
                .requestEmail().build();


        mGoogleApiClient = GoogleSignIn.getClient(this, gso);
//            mGoogleApiClient=new GoogleApiClient.Builder(LoginActivity.this)
//                    .enableAutoManage(this,this)
//                    .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
//                    .build();


        findViewById(R.id.google_login_button).setOnClickListener(this);


    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "Connection Failed");
    }

    private void signIn() {
        try {
            findViewById(R.id.google_login_button).setEnabled(false);
            Thread.sleep(2000);
            Intent signInIntent = mGoogleApiClient.getSignInIntent();
            Log.d("routecheck","signinaagye");
            startActivityForResult(signInIntent, RC_SIGN_IN);

        } catch (Exception e) {
            Toast.makeText(LoginActivity.this,"try again",Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {


            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            Log.d("routecheck","onactivityresult");
//            GoogleSignInResult result=Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (task.isComplete()) {
                GoogleSignInAccount account = null;
                try {
                    account = task.getResult(ApiException.class);
                } catch (ApiException e) {
                    e.printStackTrace();
                }
                firebaseAuthWithGoogle(account);
                Log.d("logincheckkr", "lgin done");

            } else {
                Log.d(TAG, "Login Failed");
            }
        }

    }

    @Override
    protected void onStop() {


        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
        super.onStop();

    }



    @Override
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            updateUI(currentUser);
        }

    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;

            }
        }, 2000);
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {

            startActivity(new Intent(getApplicationContext(), MainActivity.class).putExtra("userid", user.getEmail())
                    .putExtra("username", user.getDisplayName()).putExtra("userphoto", user.getPhotoUrl()));
        }
    }

    private void firebaseAuthWithGoogle(final GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                findViewById(R.id.google_login_button).setEnabled(false);

                startActivity(new Intent(LoginActivity.this, MainActivity.class).putExtra("userid", account.getEmail())
                        .putExtra("username", account.getDisplayName()).putExtra("userphoto", account.getPhotoUrl()));
                Log.d("AUTH", "sign in complete" + task.isSuccessful());
            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.google_login_button:
                signIn();
                break;

        }
    }
}

