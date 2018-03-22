package com.anb.mywishlist.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by Agung Nursatria on 3/21/2018.
 */

public class TodoRepository {

    private DB dbTodo;

    public TodoRepository(Context context){ dbTodo = new DB(context); }

    public int insert(Todo todo){

        //Open connection to write data
        SQLiteDatabase db = dbTodo.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Todo._TITLE, todo.title);
        values.put(Todo._DESCRIPTION, todo.description);
        values.put(Todo._TIME, todo.time);
        values.put(Todo._COMPLETED, todo.completed);

        // Inserting Row
        long student_Id = db.insert(Todo.TABLE, null, values);
        db.close(); // Closing database connection
        return (int) student_Id;
    }

    public void delete(Todo todo) {

        SQLiteDatabase db = dbTodo.getWritableDatabase();
        db.delete(Todo.TABLE, Todo._ID + "= ?", new String[] { String.valueOf(todo.id) });
        db.close(); // Closing database connection
    }

    public void update(Todo todo,boolean completed) {

        SQLiteDatabase db = dbTodo.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Todo._COMPLETED, completed);

        db.update(Todo.TABLE, values, Todo._ID + "= ?", new String[] { String.valueOf(todo.id) });
        db.close(); // Closing database connection
    }

    public ArrayList<Todo> getTodoList() {
        //Open connection to read only
        SQLiteDatabase db = dbTodo.getReadableDatabase();
        String selectQuery =  "SELECT  * FROM " + Todo.TABLE;

        ArrayList<Todo> todoList = new ArrayList<Todo>();

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Todo todo = new Todo();
                todo.id = cursor.getInt(cursor.getColumnIndex(Todo._ID));
                todo.title = cursor.getString(cursor.getColumnIndex(Todo._TITLE));
                todo.description = cursor.getString(cursor.getColumnIndex(Todo._DESCRIPTION));
                todo.time = cursor.getLong(cursor.getColumnIndex(Todo._TIME));
                todo.completed = (cursor.getInt(cursor.getColumnIndex(Todo._COMPLETED)) > 0);
                todoList.add(todo);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return todoList;

    }

    public Todo getTodoById(int id){
        SQLiteDatabase db = dbTodo.getReadableDatabase();
        String selectQuery =  "SELECT  * FROM " + Todo.TABLE  + " WHERE " + Todo._ID + "=?";

        Todo todo = new Todo();

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(id) } );

        if (cursor.moveToFirst()) {
            do {
                todo.id = cursor.getInt(cursor.getColumnIndex(Todo._ID));
                todo.title = cursor.getString(cursor.getColumnIndex(Todo._TITLE));
                todo.description = cursor.getString(cursor.getColumnIndex(Todo._DESCRIPTION));
                todo.time = cursor.getLong(cursor.getColumnIndex(Todo._TIME));
                todo.completed = (cursor.getInt(cursor.getColumnIndex(Todo._COMPLETED)) > 0);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return todo;
    }



}
