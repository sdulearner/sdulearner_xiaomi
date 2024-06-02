package com.example.xiaomi2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import kotlin.collections.ArrayDeque;

public class ContactsActivity extends AppCompatActivity {
    private static final String TAG = "ContactsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        Button readBtn = findViewById(R.id.btn_read);
        readBtn.setOnClickListener(v -> {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
                readContacts();
            } else {
                ActivityCompat.requestPermissions(ContactsActivity.this,
                        new String[]{Manifest.permission.READ_CONTACTS}, 123);
            }

        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 123) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
                readContacts();
            }
        }
    }

    private void readContacts() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<String> contacts = getContactsList();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TextView contactsTv = findViewById(R.id.tv_contacts);
                        StringBuilder sb = new StringBuilder();
                        for (String contact : contacts) {
                            sb.append(contact).append("\n");
                        }
                        contactsTv.setText(sb.toString());
                    }
                });
            }
        }).start();
    }

    private List<String> getContactsList() {
        List<String> list = new ArrayDeque<>();
        ContentResolver contentResolver = getContentResolver();
        String[] projections = new String[]{
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME
        };
        Cursor cursor = contentResolver.query(
                ContactsContract.Contacts.CONTENT_URI,
                projections,
                null,
                null,
                null
        );
        try {
            if (cursor != null && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    String contactId = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
                    String contactName = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME));
                    Log.d(TAG, "getContactsList: " + contactId + ":" + contactName);
                    Cursor phoneCursor = contentResolver.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?",
                            new String[]{contactId},
                            null
                    );
                    if (phoneCursor != null && phoneCursor.moveToNext()) {
                        String phoneNumber = phoneCursor.getString(
                                phoneCursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        Log.d(TAG, "Name:" + contactName + ",Phone:" + phoneNumber);
                        list.add("Name:" + contactName + ",Phone:" + phoneNumber);
                    }
                }
            }
        } catch (Exception e) {
            Log.d(TAG, "error", e);
        } finally {
            if (cursor != null) cursor.close();
        }
        return list;
    }
}