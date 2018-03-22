package com.anb.mywishlist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.anb.mywishlist.Models.DB;
import com.anb.mywishlist.Models.Todo;
import com.anb.mywishlist.Models.TodoRepository;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TodoRepository todoRepository;
    private RecyclerAdapter recyclerAdapter;
    private ArrayList<Todo> todos = new ArrayList<Todo>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setLogo(R.drawable.ic_action_todo);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        todoRepository = new TodoRepository(MainActivity.this);
        loadData();
        recyclerAdapter = new RecyclerAdapter(todos,this);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(recyclerAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,CreateCardActivity.class);
                startActivityForResult(intent,1);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                if (data.getStringExtra("action").equals("save"))
                {
                    Todo todo = new Todo();
                    todo.title = data.getStringExtra(Todo._TITLE);
                    todo.description = data.getStringExtra(Todo._DESCRIPTION);
                    todo.time = data.getLongExtra(Todo._TIME,0);
                    todo.completed =  data.getBooleanExtra(Todo._COMPLETED,false);

                    todo.id = todoRepository.insert(todo);
                    recyclerAdapter.add(todo);
                    Toast.makeText(this, "Successfull created : " + todo.title, Toast.LENGTH_LONG).show();
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        } else if (requestCode == 2) {
            if(resultCode == Activity.RESULT_OK){
                if (data.getStringExtra("action").equals("delete"))
                {
                    Todo deletedTodo = todoRepository.getTodoById(data.getIntExtra(Todo._ID,0));
                    todoRepository.delete(deletedTodo);
                    recyclerAdapter.delete(deletedTodo);
                    Toast.makeText(this, "Successfull Deleted : " + deletedTodo.title, Toast.LENGTH_LONG).show();
                }
                else if (data.getStringExtra("action").equals("back"))
                {
                    Todo updatedTodo = todoRepository.getTodoById(data.getIntExtra(Todo._ID,0));
                    boolean completed = data.getBooleanExtra(Todo._COMPLETED,false);
                    todoRepository.update(updatedTodo,completed);
                    recyclerAdapter.update(updatedTodo,completed);
                    Toast.makeText(this, "Successfull Updating : " + updatedTodo.title, Toast.LENGTH_LONG).show();
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    private void loadData(){
        todos = todoRepository.getTodoList();
    }
}
