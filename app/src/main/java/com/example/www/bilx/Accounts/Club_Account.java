package com.example.www.bilx.Accounts;

import android.app.Notification;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.www.bilx.Fragments.Club.ClubActivities;
import com.example.www.bilx.Fragments.Club.ClubProfile;
import com.example.www.bilx.Fragments.Club.Notifications_Clubs;
import com.example.www.bilx.Fragments.Information.Club_Information;
import com.example.www.bilx.Fragments.Club.SettingsFragment_club;
import com.example.www.bilx.Login_Activity;
import com.example.www.bilx.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


/**
 *  The club account.
 *  @author Hanzallah Burney
 */

public class Club_Account extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static int RESULT_LOAD_IMAGE = 1;

    private TextView navEmail;
    private TextView navUsername;
    private FirebaseAuth firebaseAuth;
    private SettingsFragment_club settingsFragmentClub;
    public static int count;
    private ImageButton imageButton;
    Timer timer = new Timer();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.club__account);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                //--------------------------------- Starts Notification------------------------------
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Notifications");
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()){
                            final DataSnapshot ds2 = ds;
                            try {
                                final DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Check Notify")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getDisplayName()).child("Both");
                                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.getValue().toString().contains("true")) {
                                            if (ds2.toString().contains("Both")) {
                                                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext())
                                                        .setSmallIcon(R.mipmap.ic_launcher)
                                                        .setContentTitle("Bilkent Notification")
                                                        .setContentText(ds2.child("Message").getValue().toString().substring(ds2.child("Message").
                                                                getValue().toString().indexOf('=') + 1, ds2.child("Message").getValue().toString().indexOf('}')))
                                                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                                                Notification notification = mBuilder.build();
                                                NotificationManagerCompat.from(getApplicationContext()).notify(0, notification);

                                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Check Notify")
                                                        .child(FirebaseAuth.getInstance().getCurrentUser().getDisplayName()).child("Both");
                                                Map check = new HashMap();
                                                check.put("Bool", "false");
                                                reference.setValue(check);
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                            } catch (NullPointerException e){
                                // Null
                            }
                            //User----------------------------------------------------
                            try {
                                final DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Check Notify")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getDisplayName()).child("Clubs");
                                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.getValue().toString().contains("true")) {
                                            if (ds2.toString().contains("Clubs")) {
                                                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext())
                                                        .setSmallIcon(R.mipmap.ic_launcher)
                                                        .setContentTitle("Bilkent Notification")
                                                        .setContentText(ds2.child("Message").getValue().toString().substring(ds2.child("Message").
                                                                getValue().toString().indexOf('=') + 1, ds2.child("Message").getValue().toString().indexOf('}')))
                                                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                                                Notification notification = mBuilder.build();
                                                NotificationManagerCompat.from(getApplicationContext()).notify(1, notification);

                                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Check Notify")
                                                        .child(FirebaseAuth.getInstance().getCurrentUser().getDisplayName()).child("Clubs");
                                                Map check = new HashMap();
                                                check.put("Bool", "false");
                                                reference.setValue(check);
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                            } catch (NullPointerException e){
                                // Null
                            }
                            //end
                        }
                        //--------------------------------- Ends Notification------------------------------
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }
        };



        timer.scheduleAtFixedRate(timerTask, 0, 5*1000);

        /**
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
         **/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Change theme to dark
        settingsFragmentClub = new SettingsFragment_club();
        if (FirebaseAuth.getInstance().getCurrentUser().getDisplayName() != null){
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Dark Mode").
                    child(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue().toString().contains("true")){
                        getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                        int[][] states = new int[][] {
                                new int[] { android.R.attr.state_enabled}, // enabled
                                new int[] {-android.R.attr.state_enabled}, // disabled
                                new int[] {-android.R.attr.state_checked}, // unchecked
                                new int[] { android.R.attr.state_pressed}  // pressed
                        };

                        int[] colors = new int[] {
                                Color.WHITE,
                                Color.WHITE,
                                Color.GREEN,
                                Color.YELLOW
                        };

                        ColorStateList myList = new ColorStateList(states, colors);

                        navigationView.setItemTextColor(myList);
                        navigationView.setItemIconTintList(myList);
                    }
                    else{
                        getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }


        if (count == 0){
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new ClubActivities()).commit();
            this.getSupportActionBar().setTitle(R.string.clubActivities);
            navigationView.setCheckedItem(R.id.nav_clubactivities);
        }
        else{
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, settingsFragmentClub).commit();
            this.getSupportActionBar().setTitle(R.string.title_activity_settings);
            navigationView.setCheckedItem(R.id.nav_settings);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.club__account, menu);

        // Set email in navigation drawer to user's email
        firebaseAuth = FirebaseAuth.getInstance();
        navEmail = (TextView) findViewById(R.id.navBar_email);
        navEmail.setText(firebaseAuth.getCurrentUser().getEmail());

        // Set username in navigation drawer to user's username
        navUsername = (TextView) findViewById(R.id.navBar_username);
        navUsername.setText(firebaseAuth.getCurrentUser().getDisplayName());

        imageButton = (ImageButton) findViewById(R.id.club_imageButton);

        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageRef = firebaseStorage.getReference();
        storageRef.child(firebaseAuth.getCurrentUser().getDisplayName() + ".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri.toString()).into(imageButton);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.putExtra("outputX", 400);
                intent.putExtra("outputY", 400);
                intent.putExtra("aspectX", 1);
                intent.putExtra("aspectY", 1);
                intent.putExtra("crop", true);
                intent.putExtra("return-data", true);

                startActivityForResult(intent, RESULT_LOAD_IMAGE);
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.nav_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_clubactivities) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new ClubActivities()).commit();
            this.getSupportActionBar().setTitle(R.string.clubActivities);
        } else if (id == R.id.nav_clubnotifications) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new Notifications_Clubs()).commit();
            this.getSupportActionBar().setTitle(R.string.Notifications);
        } else if (id == R.id.nav_profile) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new ClubProfile()).commit();
            this.getSupportActionBar().setTitle(R.string.clubProfile);
        } else if (id == R.id.nav_settings) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, settingsFragmentClub).commit();
            this.getSupportActionBar().setTitle(R.string.title_activity_settings);
        } else if (id == R.id.nav_info) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new Club_Information()).commit();
            this.getSupportActionBar().setTitle(R.string.Information);
        } else if (id == R.id.nav_logout) {
            timer.cancel();
            count = 0;
            FirebaseAuth.getInstance().signOut();
            Intent logout = new Intent(Club_Account.this, Login_Activity.class);
            startActivity(logout);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Bundle bundle = data.getExtras();
            Bitmap image = bundle.getParcelable("data");
            imageButton.setImageBitmap(image);

            FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
            StorageReference storageRef = firebaseStorage.getReferenceFromUrl("gs://cs-bilx.appspot.com");
            StorageReference profileRef = storageRef.child(firebaseAuth.getCurrentUser().getDisplayName() + ".jpg");
            StorageReference profileImagesRef = storageRef.child("images/" + firebaseAuth.getCurrentUser().getDisplayName() + ".jpg");

            profileRef.getName().equals(profileImagesRef.getName());
            profileRef.getPath().equals(profileImagesRef.getPath());

            imageButton.setDrawingCacheEnabled(true);
            imageButton.buildDrawingCache();
            Bitmap bitmap = imageButton.getDrawingCache();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[]  bytes = baos.toByteArray();

            UploadTask uploadTask = profileRef.putBytes(bytes);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                }
            });
        }
    }
}
