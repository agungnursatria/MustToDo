package com.anb.mywishlist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.anb.mywishlist.Models.Todo;
import com.anb.mywishlist.common.ParseDate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Agung Nursatria on 3/21/2018.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {

    private ArrayList<Todo> todos;
    private Context context;

    public RecyclerAdapter(ArrayList<Todo> todos, Context context) {
        this.todos = todos;
        this.context = context;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.lst_card,parent,false);
        return new RecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.title.setText(todos.get(position).title);
        holder.description.setText(todos.get(position).description);
        holder.time.setText(ParseDate.parseDate(todos.get(position).time, ParseDate.DATE_PATTERN));
        holder.completed.setChecked(todos.get(position).completed);
        holder.card_id.setText(String.valueOf(todos.get(position).id));
    }

    @Override
    public int getItemCount() {
        return todos.size();
    }

    public void add(Todo todo){
        todos.add(todo);
        notifyDataSetChanged();
    }

    public void delete(Todo todo){
        Todo tod = searchTodo(todo);
        int index = todos.indexOf(tod);
        todos.remove(tod);
        notifyItemRemoved(index);
    }
    public void update(Todo todo,Boolean completed){
        Todo tod = searchTodo(todo);
        int index = todos.indexOf(tod);
        todos.get(index).completed = completed;
        notifyItemChanged(index);
    }

    private Todo searchTodo(Todo todo){
        for (Todo tod:todos) {
            if (tod.id == todo.id)
                return tod;
        }
        return null;
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public RelativeLayout cardBackground;
        public CardView card;
        public TextView card_id;
        public TextView title;
        public TextView description;
        public TextView time;
        public CheckBox completed;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            cardBackground  = itemView.findViewById(R.id.card_background);
            card            = itemView.findViewById(R.id.card_todo);
            card_id         = itemView.findViewById(R.id.card_id);
            title           = itemView.findViewById(R.id.title);
            description     = itemView.findViewById(R.id.description);
            time            = itemView.findViewById(R.id.time);
            completed       = itemView.findViewById(R.id.completed);

            card.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context,CardDetailActivity.class);
            intent.putExtra(Todo._ID,Integer.parseInt(card_id.getText().toString()));
            intent.putExtra(Todo._TITLE,title.getText().toString());
            intent.putExtra(Todo._DESCRIPTION,description.getText().toString());

            //Dikarenakan telah diparsing ke String, jadi string dikirimnya
            intent.putExtra(Todo._TIME,time.getText().toString());

            intent.putExtra(Todo._COMPLETED,completed.isChecked());
            ((Activity) context).startActivityForResult(intent,2);
        }
    }
}

