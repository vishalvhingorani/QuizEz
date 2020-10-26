package com.androiddev.quizez;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class leaderboardRecyclerView extends RecyclerView.Adapter<leaderboardRecyclerView.MyViewHolder> {

    ArrayList<String> nameList;
    ArrayList<String> scoreList;

    public leaderboardRecyclerView(ArrayList<String> nameList, ArrayList<String> scoreList) {

        this.nameList = nameList;
        this.scoreList = scoreList;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.leaderboardcard,parent,false);
        leaderboardRecyclerView.MyViewHolder myViewHolder = new MyViewHolder(cardView);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.name.setText(nameList.get(position));
        holder.score.setText(scoreList.get(position));
        holder.index.setText(String.valueOf(position+1));
    }


    @Override
    public int getItemCount() {
        return nameList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView score;
        TextView index;
        public MyViewHolder(@NonNull View itemView) {

            super(itemView);

            name = itemView.findViewById(R.id.nameText);
            score = itemView.findViewById(R.id.scoreText);
            index = itemView.findViewById(R.id.indexText);


        }
    }
}
