package com.example.helloworld;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class dashboard extends AppCompatActivity {

    ImageView bgapp;
    Animation bganim,frombottom;
    LinearLayout textwelcome,texthome,txtmenu;
    TextView welcomeName;
    Button btnbulk,btnAbout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dashboard);
        btnbulk =(Button) findViewById(R.id.btnbulk);
        frombottom=AnimationUtils.loadAnimation(this,R.anim.frombottom);
        bgapp=findViewById(R.id.imageView2);
        textwelcome=(LinearLayout)findViewById(R.id.txtwelcome);
        texthome=findViewById(R.id.txthome);
        txtmenu=findViewById(R.id.txtmenu);
        btnAbout=findViewById(R.id.btnAbout);
        bganim= AnimationUtils.loadAnimation(this,R.anim.bganim);
        bgapp.animate().translationY(-1800).setDuration(800).setStartDelay(300);
        textwelcome.animate().translationY(140).alpha(0).setDuration(800).setStartDelay(300);
        texthome.startAnimation(frombottom);
        txtmenu.startAnimation(frombottom);

        welcomeName = findViewById ( R.id.txtWelcome );

        welcomeName.setText ( "Welcome "+ DataModel.name );

    }
    public void logOut( View view){

        FirebaseAuth myAuth = FirebaseAuth.getInstance ();
        myAuth.signOut ();
        Intent intent=new Intent(dashboard.this,MainActivity.class);
        startActivity(intent);

    }

    public void spot(View view){

        Intent intent = new Intent ( dashboard.this,Needy.class);
        startActivity ( intent );
        DataModel.modeSelected = 0;
    }

    public void donate(View view){

        Intent intent = new Intent ( dashboard.this,Needy.class );
        startActivity ( intent );
        DataModel.modeSelected = 1;

    }
    public void bdonate(View view){
        Intent intent=new Intent(dashboard.this,MapsDbulk.class);
        startActivity(intent);
        DataModel.modeSelected = 1;
    }
    public void oNeed(View view)
    {
        Intent intent=new Intent(dashboard.this,OrganizationNeed.class);
        startActivity(intent);
    }
    public void News(View view)
    {
        Intent intent=new Intent(dashboard.this,News.class);
        startActivity(intent);
    }
    public void Contactus(View view)
    {
        Intent intent=new Intent(dashboard.this,ContactUs.class);
        startActivity(intent);
    }

    public void About(View view) {
        Intent intent=new Intent(dashboard.this,About.class);
        startActivity(intent);
    }
}
