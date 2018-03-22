package com.anb.mywishlist;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.anb.mywishlist.Models.Todo;
import com.anb.mywishlist.common.ParseDate;

public class CardDetailActivity extends AppCompatActivity {

    EditText txtTitle,txtDesc;
    TextView time;
    CheckBox chxCompleted;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_detail);

        txtTitle = findViewById(R.id.todo_title);
        txtDesc= findViewById(R.id.todo_description);
        time = findViewById(R.id.todo_time);
        chxCompleted = findViewById(R.id.todo_completed);

        Intent intent = getIntent();
        Bundle extra = intent.getExtras();
        id = extra.getInt(Todo._ID);
        txtTitle.setText(extra.getString(Todo._TITLE));
        txtDesc.setText(extra.getString(Todo._DESCRIPTION));
        time.setText("Created At: " + extra.getString(Todo._TIME));
        chxCompleted.setChecked(extra.getBoolean(Todo._COMPLETED));
        getSupportActionBar().setTitle(txtTitle.getText().toString());


        if (chxCompleted.isChecked()){
            chxCompleted.setClickable(false);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                Intent returnIntent = new Intent();
                returnIntent.putExtra("action","delete");
                returnIntent.putExtra(Todo._ID,id);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            case R.id.action_back:
                returnIntent = new Intent();
                returnIntent.putExtra("action","back");
                returnIntent.putExtra(Todo._ID,id);
                returnIntent.putExtra(Todo._COMPLETED,chxCompleted.isChecked());
                setResult(Activity.RESULT_OK,returnIntent);
                finish();

        }
        return super.onOptionsItemSelected(item);
    }


}
