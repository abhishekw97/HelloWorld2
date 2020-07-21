package com.example.helloworld;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Needy extends FragmentActivity implements OnMapReadyCallback {

    private static int CAMERA_PIC_REQUEST = 1;
    private GoogleMap mMap;
    LocationManager manager;
    LocationListener locationListener;
    Marker marker;
    MarkerOptions markerOptions;
    Location location2;
    DatabaseReference reference;
    Location neddyLoation;
    Button spottedButton;
    Bitmap imageToUpload;
    FirebaseStorage firebaseStorage;
    StorageReference reference33;

    Location mLastLocation;
    Marker mCurrLocationMarker;
    GoogleApiClient mGoogleApiClient;
    public LocationRequest mLocationRequest;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_needy );
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = ( SupportMapFragment ) getSupportFragmentManager ( )
                .findFragmentById ( R.id.map );
        mapFragment.getMapAsync ( this );





        if(ContextCompat.checkSelfPermission (this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){


            ActivityCompat.requestPermissions (this, new String[]{Manifest.permission.CAMERA},1);
        }
        reference = FirebaseDatabase.getInstance ().getReference ().child ( "Location" );
        reference33 = FirebaseStorage.getInstance ().getReference ();

        spottedButton = findViewById (R.id.button2 );

        if(DataModel.modeSelected == 1){
            spottedButton.setVisibility ( View.INVISIBLE );
        }
        if(DataModel.modeSelected==2){
            spottedButton.setVisibility(View.INVISIBLE);
        }
        manager=( LocationManager )this.getSystemService (LOCATION_SERVICE);
        locationListener = new LocationListener ( ) {
            @Override
            public void onLocationChanged( Location location ) {

                mLastLocation = location;
                if (mCurrLocationMarker != null) {
                    mCurrLocationMarker.remove();
                }
                //Place current location marker
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title("Current Position");
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                mCurrLocationMarker = mMap.addMarker(markerOptions);

                //move map camera
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
                location2 = location;
            }

            @Override
            public void onStatusChanged( String provider, int status, Bundle extras ) {

            }

            @Override
            public void onProviderEnabled( String provider ) {

            }

            @Override
            public void onProviderDisabled( String provider ) {

            }
        };

        //Method to check Permissions

        if(ContextCompat.checkSelfPermission (this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){


            ActivityCompat.requestPermissions (this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);


        }else {
            manager.requestLocationUpdates ( LocationManager.GPS_PROVIDER , 900 , 0 , locationListener );
            location2 = manager.getLastKnownLocation ( LocationManager.GPS_PROVIDER );
        }




}
    @Override
    public void onMapReady( GoogleMap googleMap ) {
        mMap = googleMap;

        neddyLoation =  location2;
        // Add a marker in Sydney and move the camera
        try {

            LatLng location2 = new LatLng( neddyLoation.getLatitude ( ) , neddyLoation.getLongitude());

            markerOptions = new MarkerOptions ( )
                    .position ( location2 ).title ( "User Location" );
            marker = mMap.addMarker ( markerOptions );
            mMap.moveCamera ( CameraUpdateFactory.newLatLng ( location2 ) );
            CameraUpdate zoom=CameraUpdateFactory.zoomTo(20);
            mMap.animateCamera(zoom);
        }catch(Exception e){
            System.out.print(e);
        }
        if(DataModel.modeSelected == 1){

            reference.addListenerForSingleValueEvent ( new ValueEventListener ( ) {
                @Override
                public void onDataChange( @NonNull DataSnapshot dataSnapshot ) {

                    try {



                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                           Double latitude = Double.valueOf (  postSnapshot.child ( "latitude" ).getValue ().toString () );
                           Double longitude = Double.valueOf ( postSnapshot.child ( "longitude" ).getValue ().toString ()  );

                           long time   = Long.parseLong ( postSnapshot.child ( "time" ).getValue ().toString () );
                            Date date = new Date (  );
                            Long time2 = System.currentTimeMillis ();


                            if(time2 - time < 900000) {
                                LatLng latLng = new LatLng ( latitude , longitude );
                                markerOptions = new MarkerOptions ( ).position ( latLng ).title ( "Needy" );
                                mMap.addMarker ( markerOptions );
                            }
                        }
                    }catch (Exception e){

                        Toast.makeText ( Needy.this,e.getMessage ().toString (),Toast.LENGTH_SHORT ).show ();

                    }

                }

                @Override
                public void onCancelled( @NonNull DatabaseError databaseError ) {

                    Toast.makeText ( Needy.this,"Exception",Toast.LENGTH_SHORT ).show ();


                }
            } );




            mMap.setOnMarkerClickListener ( new GoogleMap.OnMarkerClickListener ( ) {
                @Override
                public boolean onMarkerClick( Marker marker ) {


                    Toast.makeText ( Needy.this,"Clicked",Toast.LENGTH_SHORT ).show ();
                    StorageReference referenceDownLoad = reference33.child ( String.valueOf ( marker.getPosition ().latitude ) );
                    referenceDownLoad.getBytes(1024*1024).addOnCompleteListener ( new OnCompleteListener <byte[]> ( ) {
                        @Override
                        public void onComplete( @NonNull Task <byte[]> task ) {

                                Bitmap bitmap = BitmapFactory.decodeByteArray ( task.getResult ( ) , 0 , task.getResult ( ).length );
                                showImageWithAlertBox ( bitmap );


                                if(task.isSuccessful ()){
                                    try {

                            }catch (Exception e){

                                Toast.makeText ( Needy.this,e.getMessage ().toString (),Toast.LENGTH_SHORT ).show ();


                            }

                        }else{

                                Toast.makeText ( Needy.this,task.getException ().getMessage (),Toast.LENGTH_SHORT ).show ();

                            }

                        }
                    } );
                    return false;
                }
            }
            );
        }
    }


    public void spotted( View view){

        final int CAMERA = 1;
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent ,CAMERA_PIC_REQUEST);

        neddyLoation = location2;
        reference.push ().setValue (neddyLoation).addOnCompleteListener ( new OnCompleteListener <Void> ( ) {
            @Override
            public void onComplete( @NonNull Task <Void> task ) {
                if(task.isSuccessful ()){

                    Intent intent = new Intent ( MediaStore.ACTION_IMAGE_CAPTURE );
                    startActivityForResult(intent,1);
                }
                else{
                    Toast.makeText ( Needy.this,"Failed",Toast.LENGTH_SHORT ).show ();
                }
            }
        });
    }


    @Override
    protected void onActivityResult( int requestCode , int resultCode , @Nullable Intent data ) {
        super.onActivityResult ( requestCode , resultCode , data );
        if(requestCode == 1) {
            try {
                imageToUpload = ( Bitmap ) data.getExtras ( ).get ( "data" );
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream ( );
                imageToUpload.compress ( Bitmap.CompressFormat.JPEG , 50 , byteArrayOutputStream );
                byte[] uploadData = byteArrayOutputStream.toByteArray ( );
                StorageReference reference22 = reference33.child ( String.valueOf ( neddyLoation.getLatitude ( ) ) );
                reference22.putBytes ( uploadData ).addOnCompleteListener ( new OnCompleteListener <UploadTask.TaskSnapshot> ( ) {
                    @Override
                    public void onComplete( @NonNull Task <UploadTask.TaskSnapshot> task ) {

                        if (task.isSuccessful ( )) {
                            Toast.makeText ( Needy.this , "Succesful" , Toast.LENGTH_SHORT ).show ( );
                            }
                        else {
                            Toast.makeText ( Needy.this , task.getException ().getMessage () , Toast.LENGTH_SHORT ).show ( );
                            }
                    }
                } );
            }catch (Exception e){
                System.out.print ( "Exception" );
            }
        }
    }




    void showImageWithAlertBox(Bitmap bitmap){

        final AlertDialog.Builder builder = new AlertDialog.Builder ( this );

        ImageView imageView = new ImageView ( this );
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams ( LinearLayout.LayoutParams.MATCH_PARENT , LinearLayout.LayoutParams.MATCH_PARENT );
        imageView.setLayoutParams ( params );
        imageView.setImageBitmap ( bitmap );
        builder.setTitle ( "Image of Needy" );
        builder.setView ( imageView );

        builder.setPositiveButton ( "Donate" , new DialogInterface.OnClickListener ( ) {
            @Override
            public void onClick( DialogInterface dialog , int which ) {
                Toast.makeText ( Needy.this,"Thanks for Help!",Toast.LENGTH_SHORT ).show ();
                dialog.cancel ();

            }
        } );
        builder.setNegativeButton ( "Cancel" , new DialogInterface.OnClickListener ( ) {
            @Override
            public void onClick( DialogInterface dialog , int which ) {
                dialog.cancel ();
            }
        } );
        AlertDialog alertDialog = builder.create ();
        alertDialog.show ();
    }
    //Function to To get Image
    @Override
    public void onRequestPermissionsResult( int requestCode, String[] permissions, int[] grantResults ) {
        super.onRequestPermissionsResult (requestCode, permissions, grantResults);

        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){


            if(ContextCompat.checkSelfPermission (this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){

                manager.requestLocationUpdates (LocationManager.GPS_PROVIDER,900,0,locationListener);
                neddyLoation = manager.getLastKnownLocation ( LocationManager.GPS_PROVIDER );


            }
        }
    };

}