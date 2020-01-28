package com.example.seniordesign;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity {

    private Button profile, signOut, save, appliances;
    private TextView name;
    String nameText, id;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            nameText = user.getDisplayName();
            id = user.getUid();
        }
        setContentView(R.layout.activity_home);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        //views
        name = findViewById(R.id.name);
        name.setText(nameText);

        //buttons
        profile = findViewById(R.id.profile);
        signOut = findViewById(R.id.signOut);
        save = findViewById(R.id.save);
        appliances = findViewById(R.id.add_appliance);

        mDatabase.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                LinearLayout ll = findViewById(R.id.appLl);
                ll.removeAllViews();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String app = snapshot.getValue(String.class);
                    Switch s = new Switch(getApplicationContext());
                    s.setText(app);
                    ll.addView(s);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(startIntent);
            }
        });

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Recommend.class));
            }
        });

        appliances.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AddActivity.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menuLogout:
                finish();
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.menuSettings:
                Toast.makeText(this, "You clicked settings!", Toast.LENGTH_LONG).show();
                break;
        }
        return true;
    }

}
