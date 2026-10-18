package com.example.myuolhelpdeskapp;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // S2 Q1: public static final key constant
    public static final String EXTRA_NAME = "com.example.myuolhelpdeskapp.EXTRA_NAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText etName = findViewById(R.id.etName);
        Button btnWebsite = findViewById(R.id.btnWebsite);
        Button btnCall = findViewById(R.id.btnCall);
        Button btnMap = findViewById(R.id.btnMap);
        Button btnRequest = findViewById(R.id.btnRequest);

        // S1 Q2: Visit Website (implicit Intent, ACTION_VIEW)
        btnWebsite.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.uol.edu.pk"));
            openIntent(MainActivity.this, intent);
        });

        // S1 Q3: Call Help Desk (implicit Intent, ACTION_DIAL)
        btnCall.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:04212345678"));
            openIntent(MainActivity.this, intent);
        });

        // S1 Q4: Find Campus (implicit Intent, ACTION_VIEW with geo: Uri)
        btnMap.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("geo:0,0?q=University+of+Lahore"));
            openIntent(MainActivity.this, intent);
        });

        // S2 Q1: Send a Request (validation + explicit Intent + putExtra)
        btnRequest.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            if (TextUtils.isEmpty(name)) {
                etName.setError(getString(R.string.error_enter_name));
                return;
            }
            Intent intent = new Intent(MainActivity.this, RequestActivity.class);
            intent.putExtra(EXTRA_NAME, name);
            startActivity(intent);
        });
    }

    // S1 Q5: helper method with try-catch and Toast
    public static void openIntent(Context context, Intent intent) {
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, context.getString(R.string.error_no_app),
                    Toast.LENGTH_SHORT).show();
        }
    }
}