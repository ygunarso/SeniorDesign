package com.example.seniordesign;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddActivity extends AppCompatActivity {

    private Button buttonAdd;
    private EditText appName;
    String id;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            id = user.getUid();
        }

        buttonAdd = findViewById(R.id.buttonAdd);
        appName = findViewById(R.id.appName);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateForm()) {
                    return;
                }
                addNewAppliance(id, appName.getText().toString());
                finish();
            }
        });
    }

    private void addNewAppliance(String id, String appliance) {
        mDatabase.child(id).push().setValue(appliance);
    }

    private boolean validateForm() {
        boolean valid = true;

        String app = appName.getText().toString();
        if (TextUtils.isEmpty(app)) {
            appName.setError("Required.");
            valid = false;

        } else {
            appName.setError(null);
        }

        return valid;
    }
}
