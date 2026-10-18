package com.example.myuolhelpdeskapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RequestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        TextView tvGreeting = findViewById(R.id.tvGreeting);
        EditText etPhone = findViewById(R.id.etPhone);
        EditText etLink = findViewById(R.id.etLink);
        Button btnCallNumber = findViewById(R.id.btnCallNumber);
        Button btnOpenLink = findViewById(R.id.btnOpenLink);

        // S2 Q2: read the name, use "Student" if none was received
        String name = getIntent().getStringExtra(MainActivity.EXTRA_NAME);
        if (name == null || name.trim().isEmpty()) {
            name = getString(R.string.default_name);
        }
        tvGreeting.setText(getString(R.string.greeting, name));

        // S2 Q3: only 11 digits starting with 03, then dialer through openIntent()
        btnCallNumber.setOnClickListener(v -> {
            String number = etPhone.getText().toString().trim();
            if (!number.matches("^03\\d{9}$")) {
                etPhone.setError(getString(R.string.error_phone));
                return;
            }
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + number));
            MainActivity.openIntent(RequestActivity.this, intent);
        });

        // S2 Q4: reject empty, add https:// if missing, open through openIntent()
        btnOpenLink.setOnClickListener(v -> {
            String link = etLink.getText().toString().trim();
            if (TextUtils.isEmpty(link)) {
                etLink.setError(getString(R.string.error_link));
                return;
            }
            if (!link.startsWith("http://") && !link.startsWith("https://")) {
                link = "https://" + link;
            }
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
            MainActivity.openIntent(RequestActivity.this, intent);
        });
    }
}