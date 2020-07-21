package com.example.helloworld;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    Button signup,login;
    EditText email,pass;
    ProgressBar progressBar2;
    View view2;
    FirebaseAuth myAuth;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        signup=findViewById(R.id.btnsignup);
        login=findViewById(R.id.btnlogin);
        email = findViewById ( R.id.editTextemail );
        pass = findViewById ( R.id.editTextPass );
        progressBar2 = findViewById ( R.id.progressBar2 );
        view2 = findViewById ( R.id.view2 );
        myAuth = FirebaseAuth.getInstance ();
        reference = FirebaseDatabase.getInstance().getReference ().child ( "Auth" );
        if(myAuth.getCurrentUser () != null){

            DatabaseReference reference2 = reference.child ( myAuth.getCurrentUser ().getUid () );
            reference2.addListenerForSingleValueEvent ( new ValueEventListener ( ) {
                @Override
                public void onDataChange( @NonNull DataSnapshot dataSnapshot ) {
                    try {
                        HashMap <String, String> map = ( HashMap <String, String> ) dataSnapshot.getValue ( );
                        DataModel.name = map.get ( "Name" );
                        DataModel.mobNo = map.get ( "MobNo" );
                        Intent i = new Intent ( MainActivity.this , dashboard.class );
                        startActivity ( i );

                    }catch (Exception e){

                        System.out.print ( "Exception" );

                    }

                }

                @Override
                public void onCancelled( @NonNull DatabaseError databaseError ) {

                }
            } );




        }

    }

    public void signup(View v)
    {
        Intent i= new Intent(MainActivity.this,signup.class);
        startActivity(i);
    }

    public void login(View v)
    {

        progressBar2.setVisibility ( View.VISIBLE );
        view2.setVisibility ( View.VISIBLE );
        myAuth.signInWithEmailAndPassword ( email.getText ().toString (),pass.getText ().toString () ).addOnCompleteListener ( new OnCompleteListener <AuthResult> ( ) {
            @Override
            public void onComplete( @NonNull Task<AuthResult> task ) {
                if(task.isSuccessful ()){

                    DatabaseReference reference2 = reference.child ( myAuth.getCurrentUser ().getUid () );
                    reference2.addListenerForSingleValueEvent ( new ValueEventListener ( ) {
                        @Override
                        public void onDataChange( @NonNull DataSnapshot dataSnapshot ) {

                            try {

                                HashMap <String, String> map = ( HashMap <String, String> ) dataSnapshot.getValue ( );
                                DataModel.name = map.get ( "Name" );
                                DataModel.mobNo = map.get ( "MobNo" );
                                progressBar2.setVisibility ( View.INVISIBLE );
                                view2.setVisibility ( View.INVISIBLE );
                                Intent i = new Intent ( MainActivity.this , dashboard.class );
                                startActivity ( i );

                            }catch (Exception e){

                                System.out.print ( "Exception" );
                                Log.i("Exe",e.toString());
                                progressBar2.setVisibility ( View.INVISIBLE );
                                view2.setVisibility ( View.INVISIBLE );

                            }
                            progressBar2.setVisibility ( View.INVISIBLE );
                            view2.setVisibility ( View.INVISIBLE );
                        }

                        @Override
                        public void onCancelled( @NonNull DatabaseError databaseError ) {

                            progressBar2.setVisibility ( View.INVISIBLE );
                            view2.setVisibility ( View.INVISIBLE );
                        }
                    } );


                }else{

                    Toast.makeText ( MainActivity.this,task.getException ().getMessage (),Toast.LENGTH_SHORT ).show ();
                    progressBar2.setVisibility ( View.INVISIBLE );
                    view2.setVisibility ( View.INVISIBLE );


                }
            }
        } );


    }
}
