package com.example.www.bilx.Accounts;

import android.app.Activity;
import android.app.Notification;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.www.bilx.Fragments.User.NotificationsAdapter;
import com.example.www.bilx.Fragments.User.SettingsFragment_User;
import com.example.www.bilx.Fragments.User.Notifications_User;
import com.example.www.bilx.Fragments.User.SavedActivities;
import com.example.www.bilx.Fragments.User.Scheduler;
import com.example.www.bilx.Fragments.User.UserActivities;
import com.example.www.bilx.Fragments.User.UserClubs;
import com.example.www.bilx.Fragments.Information.User_Information;
import com.example.www.bilx.Fragments.User.UserNotificationObject;
import com.example.www.bilx.Login_Activity;
import com.example.www.bilx.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


/**
 *  The user account.
 *  @author Hanzallah Burney
 */

public class User_Account extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static int RESULT_LOAD_IMAGE = 1;

    private TextView navEmail;
    private TextView navUsername;
    private FirebaseAuth firebaseAuth;
    private SettingsFragment_User settingsFragmentUser;
    public static int count;
    private ImageButton imageButton;
    Timer timer = new Timer();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__account);
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
                                        if (dataSnapshot.getValue()!=null && dataSnapshot.getValue().toString().contains("true")) {
                                            if (ds2.toString().contains("Both")) {
                                                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext())
                                                        .setSmallIcon(R.mipmap.ic_launcher)
                                                        .setContentTitle("Bilkent Notification")
                                                        .setContentText(ds2.child("Message").getValue().toString().substring(ds2.child("Message").
                                                                getValue().toString().indexOf('=') + 1, ds2.child("Message").getValue().toString().indexOf('}')))
                                                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                                                Notification notification = mBuilder.build();
                                                NotificationManagerCompat.from(getApplicationContext()).notify(0, notification);

                                                //=================== For User Notifications ==================================================
                                                String s = ds2.child("Message").getValue().toString().substring(ds2.child("Message").
                                                        getValue().toString().indexOf('=') + 1, ds2.child("Message").getValue().toString().indexOf('}'));

                                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Notification List")
                                                        .child(FirebaseAuth.getInstance().getCurrentUser().getDisplayName()).child(s).child(s);

                                                ref.setValue(s);


                                                DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference().child("Notification List")
                                                        .child(FirebaseAuth.getInstance().getCurrentUser().getDisplayName()).child(s).child("Date");

                                                ref2.setValue((new Date()).getTime());

                                                // ===========================================================================================================================

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
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getDisplayName()).child("Users");
                                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.getValue()!=null && dataSnapshot.getValue().toString().contains("true")) {
                                            if (ds2.toString().contains("Users")) {
                                                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext())
                                                        .setSmallIcon(R.mipmap.ic_launcher)
                                                        .setContentTitle("Bilkent Notification")
                                                        .setContentText(ds2.child("Message").getValue().toString().substring(ds2.child("Message").
                                                                getValue().toString().indexOf('=') + 1, ds2.child("Message").getValue().toString().indexOf('}')))
                                                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                                                Notification notification = mBuilder.build();
                                                NotificationManagerCompat.from(getApplicationContext()).notify((int)Math.random()+1000, notification);

                                                //=================== For User Notifications ==================================================
                                                String s = ds2.child("Message").getValue().toString().substring(ds2.child("Message").
                                                        getValue().toString().indexOf('=') + 1, ds2.child("Message").getValue().toString().indexOf('}'));

                                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Notification List")
                                                        .child(FirebaseAuth.getInstance().getCurrentUser().getDisplayName()).child(s).child(s);

                                                ref.setValue(s);


                                                DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference().child("Notification List")
                                                        .child(FirebaseAuth.getInstance().getCurrentUser().getDisplayName()).child(s).child("Date");

                                                ref2.setValue((new Date()).getTime());

                                                // ===========================================================================================================================

                                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Check Notify")
                                                        .child(FirebaseAuth.getInstance().getCurrentUser().getDisplayName()).child("Users");
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
        settingsFragmentUser = new SettingsFragment_User();
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
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new com.example.www.bilx.Fragments.User.Scheduler()).commit();
            this.getSupportActionBar().setTitle("Scheduler");
            navigationView.setCheckedItem(R.id.nav_scheduler);
        }
        else{
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, settingsFragmentUser).commit();
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
        //getMenuInflater().inflate(R.menu.user__account, menu);

        // Set email in navigation drawer to user's email
        firebaseAuth = FirebaseAuth.getInstance();
        navEmail = (TextView) findViewById(R.id.navBar_email);
        navEmail.setText(firebaseAuth.getCurrentUser().getEmail());

        // Set username in navigation drawer to user's username
        navUsername = (TextView) findViewById(R.id.navBar_username);
        navUsername.setText(firebaseAuth.getCurrentUser().getDisplayName());


        imageButton = (ImageButton) findViewById(R.id.user_imageButton);

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
        if (id == R.id.action_time) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_scheduler) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new Scheduler()).commit();
            this.getSupportActionBar().setTitle(R.string.title_activity_user__account);
        } else if (id == R.id.nav_activities) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new UserActivities()).commit();
            this.getSupportActionBar().setTitle(R.string.allActivities_user);
        } else if (id == R.id.nav_savedActivities){
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new SavedActivities()).commit();
            this.getSupportActionBar().setTitle(R.string.myActivities_user);
        } else if (id == R.id.nav_clubs) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new UserClubs()).commit();
            this.getSupportActionBar().setTitle(R.string.clubs_user);
        } else if (id == R.id.nav_notifications) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new Notifications_User()).commit();
            this.getSupportActionBar().setTitle(R.string.notifications_user);
        } else if (id == R.id.nav_settings) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, settingsFragmentUser).commit();
            this.getSupportActionBar().setTitle(R.string.Settings);
        } else if (id == R.id.nav_info) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new User_Information()).commit();
            this.getSupportActionBar().setTitle(R.string.Information);
        }  else if (id == R.id.nav_logout) {
            timer.cancel();
            count = 0;
            FirebaseAuth.getInstance().signOut();
            Intent logout = new Intent(User_Account.this, Login_Activity.class);
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
