package com.yokmama.learn10.chapter08.lesson38;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.EditText;


public class MainActivity extends FragmentActivity {
    private EditText mEditName;
    private EditText mEditAge;
    private EditText mEditEmailAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEditName = (EditText) findViewById(R.id.editName);
        mEditAge = (EditText) findViewById(R.id.editAge);
        mEditEmailAddress = (EditText) findViewById(R.id.editEmailAddress);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callActivity();
            }
        });

        if (getIntent() != null && getIntent().hasExtra("contact")) {
            Contact contact = getIntent().getParcelableExtra("contact");
            mEditName.setText(contact.getName());
            mEditAge.setText(String.valueOf(contact.getAge()));
            mEditEmailAddress.setText(contact.getEmail());
        }
    }


    private void callActivity() {

        Contact contact = new Contact();
        contact.setName(mEditName.getText().toString());
        contact.setAge(Integer.parseInt(mEditAge.getText().toString()));
        contact.setEmail(mEditEmailAddress.getText().toString());

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("contact", contact);

        startActivity(intent);
    }
}
