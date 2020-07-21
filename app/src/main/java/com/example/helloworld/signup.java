package com.example.helloworld;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class signup extends AppCompatActivity {



    EditText name,pass,email,repass;
    ProgressBar progressBar;
    View view1;
    FirebaseAuth myAuth;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        name = findViewById ( R.id.txtName );
        pass = findViewById ( R.id.txtPass );
        email = findViewById ( R.id.txtEmail );
        repass = findViewById ( R.id.txtRepass );
        progressBar = findViewById ( R.id.progressBar );
        view1 = findViewById ( R.id.view1);
        myAuth = FirebaseAuth.getInstance ();
        reference = FirebaseDatabase.getInstance ().getReference ().child ( "Auth" );


    }



    public void signUp(View view){

        progressBar.setVisibility ( View.VISIBLE );
        view1.setVisibility ( View.VISIBLE );
        final DatabaseReference reference = FirebaseDatabase.getInstance ().getReference ().child ( "Auth");
        final FirebaseAuth myAuth = FirebaseAuth.getInstance ();
        myAuth.createUserWithEmailAndPassword ( email.getText ().toString (),pass.getText ().toString ()).addOnCompleteListener ( new OnCompleteListener <AuthResult> ( ) {
            @Override
            public void onComplete( @NonNull Task<AuthResult> task ) {

                if(task.isSuccessful ()){


                    HashMap<String,String> map = new HashMap <> (  );
                    map.put ( "Name",name.getText ().toString () );
                    map.put ( "MobNo",repass.getText ().toString () );
                 DatabaseReference  reference2 = reference.child ( myAuth.getCurrentUser ().getUid () );

                    reference2.setValue ( map ).addOnCompleteListener ( new OnCompleteListener <Void> ( ) {
                        @Override
                        public void onComplete( @NonNull Task <Void> task ) {

                            if(task.isSuccessful ()){

                                Toast.makeText ( signup.this,"User created",Toast.LENGTH_SHORT).show ();
                                progressBar.setVisibility ( View.INVISIBLE );
                                view1.setVisibility ( View.INVISIBLE );
                                Intent intent = new Intent ( signup.this,MainActivity.class );
                                startActivity ( intent );
                                finish();

                            }else{

                                Toast.makeText ( signup.this," Failed to created",Toast.LENGTH_SHORT).show ();
                                progressBar.setVisibility ( View.INVISIBLE );
                                view1.setVisibility ( View.INVISIBLE );

                            }
                        }
                    } );

                }else{

                    Toast.makeText( signup.this," Failed to created",Toast.LENGTH_SHORT).show ();
                    progressBar.setVisibility(View.INVISIBLE);
                    view1.setVisibility (View.INVISIBLE);

                }
            }
        } );
    }
}
