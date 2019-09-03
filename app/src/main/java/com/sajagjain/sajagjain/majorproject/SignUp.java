package com.sajagjain.sajagjain.majorproject;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A login screen that offers login via email/password.
 */
public class SignUp extends AppCompatActivity {

    FirebaseAuth mAuth;
    DatePicker datePicker;
    Button signup;
    EditText firstName,dob,lastName,email,password,reEnterPassword;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
//        getWindow().setBackgroundDrawable(ContextCompat.getDrawable(this,R.drawable.app_background));
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        // Set up the register form.
        signup=(Button)findViewById(R.id.email_sign_up_button);
        firstName=(EditText)findViewById(R.id.firstName);
        lastName=(EditText)findViewById(R.id.lastName);
        email=(EditText)findViewById(R.id.signup_email);
        password=(EditText)findViewById(R.id.sign_up_password);
        reEnterPassword=(EditText)findViewById(R.id.reenterpassword);
        dob=(EditText)findViewById(R.id.date_of_birth);
        progressBar=(ProgressBar)findViewById(R.id.signup_progress);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#3badcf"), PorterDuff.Mode.SRC_IN);



        signup.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                progressBar.setVisibility(View.VISIBLE);
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                if(emailCheck(email.getText().toString())&&passwordCheck(password.getText().toString(),reEnterPassword.getText()
                        .toString())&&restFeildCheck(firstName.getText().toString(),lastName.getText().toString(),dob.getText()
                        .toString())){


                    Users user=new Users(firstName.getText().toString(),lastName.getText().toString(),
                            dob.getText().toString(),email.getText().toString(),password.getText().toString());
                    saveInFirebase(user);



                }
                progressBar.setVisibility(View.GONE);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            }
        });


    }

    private boolean restFeildCheck(String firstName,String lastName,String dob){
        if(firstName.equals("")){
            Toast.makeText(SignUp.this,"Enter First Name",Toast.LENGTH_LONG).show();
            return false;
        }else if(lastName.equals("")){
            Toast.makeText(SignUp.this,"Enter Last Name",Toast.LENGTH_LONG).show();
            return false;
        }else if(dob.equals("")){
            Toast.makeText(SignUp.this,"Enter Date of Birth",Toast.LENGTH_LONG).show();
            return false;
        }else if(!dob.contains("/")&&(dob.length()==10)){
            Toast.makeText(SignUp.this,"Enter Date in proper format DD/MM/YYYY",Toast.LENGTH_LONG).show();
            return false;
        }else{
            return true;
        }
    }


    private boolean passwordCheck(String password,String reEnterPassword){
        if(password.equals(reEnterPassword)){
            if(password.length()<8){
                Toast.makeText(SignUp.this,"Password should be greater than 8 characters",Toast.LENGTH_LONG).show();
            }else{

                Pattern p = Pattern.compile("[a-zA-Z]");
                Matcher m = p.matcher(password); // get a matcher object
                Pattern p2 = Pattern.compile("[\\W]");
                Matcher m2 = p2.matcher(password); // get a matcher object
                Pattern p3 = Pattern.compile("[0-9]");
                Matcher m3 = p3.matcher(password); // get a matcher object
                boolean flag1=false,flag2=false,flag3=false;
                while(m.find()) {
                    flag1=true;

                }
                while(m2.find()){
                    flag2=true;

                }
                while(m3.find()){
                    flag3=true;

                }
                if(flag1&&flag2&&flag3) {
                    if (Pattern.matches("\\S",password)){
                        Toast.makeText(SignUp.this,"Password should not contain spaces",Toast.LENGTH_LONG).show();
                    }else{
                        return true;
                    }

                }else{
                    Toast.makeText(this,"Password should contain alphanumeric characters",Toast.LENGTH_LONG).show();
                }
            }
        }else{
            Toast.makeText(SignUp.this,"Reenter Password does not match Password",Toast.LENGTH_LONG).show();
        }
        return false;
    }


    private boolean emailCheck(String email){
        if(!email.contains("@")&&(!email.contains(".com")||!email.contains(".in"))){
            Toast.makeText(SignUp.this,"Please enter a valid email address",Toast.LENGTH_LONG).show();
        }else{
            return true;
        }
        return false;
    }



    public void saveInFirebase(final Users user) {
       final DatabaseReference databaseProducts= FirebaseDatabase.getInstance().getReference("users");

// this is to generate unique key if you dont have a distinct feild use it in databaseProducts.child(id).setValue(barcode,name,price,weight)
// String id = databaseProducts.push().getKey();

        mAuth=FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(user.getEmail(),user.getPassword())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("AUTH", "Registration Successfull");
                            FirebaseUser currentUser = mAuth.getCurrentUser();
                            databaseProducts.child(databaseProducts.push().getKey()).setValue(user);

                                updateUI(currentUser);



                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("AUTH", "", task.getException());
                            Toast.makeText(SignUp.this, "Registration Failed. Please Try Again",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });



    }
    public static String encodeString(String string) {
        return string.replace(".", ",");
    }

    public static String decodeString(String string) {
        return string.replace(",", ".");
    }
    private void updateUI(FirebaseUser user){
        if(user==null){
            startActivity(new Intent(SignUp.this,LoginActivity.class));
        }else{
            startActivity(new Intent(SignUp.this,MainActivity.class));
        }
    }
}

