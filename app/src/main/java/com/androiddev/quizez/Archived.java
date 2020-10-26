package com.androiddev.quizez;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class Archived extends AppCompatActivity {

    RecyclerView recyclerViewArchived;
    RecyclerView.LayoutManager layoutManager;
    static ArrayList<String> archivedList = new ArrayList<String>();
    static ArrayList<String> archivedListID = new ArrayList<String>();
    static RecyclerViewAdapter recyclerViewAdapterArchived;
    SwipeRefreshLayout swipeRefreshLayout;
    static ImageView noquizimg;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Archived");
        setContentView(R.layout.activity_archived);

        noquizimg = (ImageView) findViewById(R.id.noquizimgArchive);
        noquizimg.setVisibility(View.INVISIBLE);


        archivedList = home.getarchTitle();
        archivedListID = home.getarchId();

        recyclerViewArchived = findViewById(R.id.recyclerViewArchive);
        layoutManager = new GridLayoutManager(this, 2);
        recyclerViewArchived.setLayoutManager(layoutManager);
        recyclerViewArchived.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerViewAdapterArchived = new RecyclerViewAdapter(archivedList,archivedListID,this,true);
        recyclerViewArchived.setAdapter(recyclerViewAdapterArchived);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerViewArchived);



        sortMyLists();
        recyclerViewAdapterArchived.notifyDataSetChanged();
        imgrefArch();

        swipeRefreshLayout = findViewById(R.id.swipeRefreshArchive);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                archivedList = home.getarchTitle();
                archivedListID = home.getarchId();
                sortMyLists();
                recyclerViewAdapterArchived = new RecyclerViewAdapter(archivedList,archivedListID,Archived.this,true);
                recyclerViewArchived.setAdapter(recyclerViewAdapterArchived);
                recyclerViewAdapterArchived.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
                imgrefArch();
            }
        });


    }

    public static void imgrefArch()
    {
        if (recyclerViewAdapterArchived.getItemCount()==0){
            noquizimg.setVisibility(View.VISIBLE);
        }
        else {
            noquizimg.setVisibility(View.INVISIBLE);
        }
    }

    public void sortMyLists(){

        for(int i = 0; i < archivedListID.size();i++){
            for (int j = 1;j<archivedListID.size()-i;j++){
                if(Integer.parseInt(archivedListID.get(j-1))>Integer.parseInt(archivedListID.get(j))){
                    String tempID = archivedListID.get(j-1);
                    String temp = archivedList.get(j-1);
                    archivedListID.set(j-1,archivedListID.get(j));
                    archivedList.set(j-1,archivedList.get(j));
                    archivedListID.set(j,tempID);
                    archivedList.set(j,temp);
                }
            }
        }

    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            final int position = viewHolder.getAdapterPosition();

            if (ItemTouchHelper.LEFT == direction || ItemTouchHelper.RIGHT == direction) {
                final String unarch = archivedList.get(position);
                final String unarchID = archivedListID.get(position);
                archivedList.remove(position);
                archivedListID.remove(position);
                home.exsql("DELETE FROM archivelist WHERE id = " + unarchID);
                home.exsql("INSERT INTO mainlist (titles, id) VALUES ('"+unarch+"',"+unarchID+")");
                recyclerViewAdapterArchived.notifyItemRemoved(position);
                recyclerViewAdapterArchived.notifyDataSetChanged();
                //home.recyclerViewAdapter.notifyDataSetChanged();
                imgrefArch();
                Snackbar.make(recyclerViewArchived,"Unarchived " + unarch, Snackbar.LENGTH_LONG)
                        .setAction("Undo", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                archivedList.add(position, unarch);
                                archivedListID.add(position,unarchID);
                                home.exsql("DELETE FROM mainlist WHERE id = " + unarchID);
                                home.exsql("INSERT INTO archivelist (titles, id) VALUES ('"+unarch+"',"+unarchID+")");
                                //home.recyclerViewAdapter.notifyDataSetChanged();
                                recyclerViewAdapterArchived.notifyDataSetChanged();
                                imgrefArch();
                            }
                        }).show();
            }

        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(Archived.this,R.color.unarchiveGreen))
                    .addSwipeLeftActionIcon(R.drawable.ic_baseline_unarchive_24)
                    .addSwipeRightBackgroundColor(ContextCompat.getColor(Archived.this,R.color.unarchiveGreen))
                    .addSwipeRightActionIcon(R.drawable.ic_baseline_unarchive_24)
                    .create()
                    .decorate();

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };

    @Override
    public void onBackPressed() {
        finish();
        Intent tohome = new Intent(getApplicationContext(),home.class);
        tohome.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(tohome);
    }

}