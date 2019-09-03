package com.sajagjain.sajagjain.majorproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ContactUs extends AppCompatActivity {

    EditText emailid,askme;
    Button submit;
    ImageView close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
//        getWindow().setBackgroundDrawable(ContextCompat.getDrawable(this,R.drawable.app_background));
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        emailid=(EditText)findViewById(R.id.contact_us_emailid);
        askme=(EditText)findViewById(R.id.contactus_askus);
        submit=(Button)findViewById(R.id.contactus_submit_query);
        close=(ImageView)findViewById(R.id.contactus_close_button);



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 if(askMeCheck(askme.getText().toString())&&emailCheck(emailid.getText().toString())){
                     ContactUsQuery query=new ContactUsQuery(emailid.getText().toString(),askme.getText().toString());
                     DatabaseReference databaseProducts= FirebaseDatabase.getInstance().getReference("queries");
                     databaseProducts.child(databaseProducts.push().getKey()).setValue(query);
                     askme.getText().clear();
                     emailid.getText().clear();
                     Toast.makeText(ContactUs.this,"Query Submitted",Toast.LENGTH_LONG).show();
                 }
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContactUs.this.finish();
            }
        });

    }

    private boolean askMeCheck(String askme){
        if(askme.equals("")){
            Toast.makeText(this,"Enter some text",Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private boolean emailCheck(String email){
        if(!email.contains("@")&&(!email.contains(".com")||!email.contains(".in"))){
            Toast.makeText(ContactUs.this,"Please enter a valid email address",Toast.LENGTH_LONG).show();
            return false;
        }else{
            return true;

        }
    }

}
