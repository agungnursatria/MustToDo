package com.anb.mywishlist;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.anb.mywishlist.Models.Todo;
import com.anb.mywishlist.common.ParseDate;

import java.util.Date;

public class CreateCardActivity extends AppCompatActivity {

    int id;
    EditText txtCreateTitle,txtCreateDesc;
    TextView txtCreatedTime;
    Long time = new Date().getTime();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_card);

        txtCreateTitle = findViewById(R.id.todo_create_title);
        txtCreateDesc = findViewById(R.id.todo_create_description);
        txtCreatedTime = findViewById(R.id.todo_create_time);


        txtCreatedTime.setText("Created At: " + ParseDate.parseDate(time, ParseDate.DATE_PATTERN));
        setListeners();

    }

    private void setListeners() {
        txtCreateTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                getSupportActionBar().setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_create, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                String title = txtCreateTitle.getText().toString();
                String description = txtCreateDesc.getText().toString();

                Intent returnIntent = new Intent();
                returnIntent.putExtra("action","save");
                returnIntent.putExtra(Todo._TITLE,title);
                returnIntent.putExtra(Todo._DESCRIPTION,description);
                returnIntent.putExtra(Todo._TIME,time);
                returnIntent.putExtra(Todo._COMPLETED,false);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
