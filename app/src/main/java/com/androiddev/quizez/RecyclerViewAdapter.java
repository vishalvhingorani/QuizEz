package com.androiddev.quizez;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    ArrayList<String> quizList;
    ArrayList<String> quizIDlist;
    Context context;
    Boolean archive;



    public RecyclerViewAdapter(ArrayList quizList,ArrayList<String> quizIDlist, Context context, boolean archive) {
        this.quizList = quizList;
        this.context = context;
        this.quizIDlist = quizIDlist;
        this.archive = archive;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.quiz_card,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(cardView);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.quizCardTitle.setText(quizList.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent go2play = new Intent(context,getName.class);
                go2play.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                go2play.putExtra("ID",quizIDlist.get(position));
                go2play.putExtra("NameQ",quizList.get(position));
                context.startActivity(go2play);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View v) {

                if(!archive){
                    new AlertDialog.Builder(context)
                            .setIcon(android.R.drawable.ic_menu_delete)
                            .setTitle("Delete Quiz")
                            .setMessage("Are you sure you want to delete " + quizList.get(position) + "?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    try{
                                        home.deleteTable(quizIDlist.get(position));
                                        home.myQuizzes.remove(position);
                                        home.myQuizzesID.remove(position);
                                        home.imgref();
                                        home.recyclerViewAdapter.notifyDataSetChanged();
                                    }catch (Exception e){
                                        Snackbar.make(v,"Error: Unable to Delete Quiz, try Archiving instead.",Snackbar.LENGTH_LONG).show();
                                    }
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).show();
                }
                else {
                    Snackbar.make(v,"Unarchive Quiz to Delete",Snackbar.LENGTH_SHORT).show();

                }


                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return quizList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView quizCardTitle;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            quizCardTitle = itemView.findViewById(R.id.quizCardTitle);


        }
    }
}
