package com.androiddev.quizez;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class home extends AppCompatActivity   {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    static RecyclerViewAdapter recyclerViewAdapter;
    static ArrayList<String> myQuizzesID = new ArrayList<String>();
    static ArrayList<String> myQuizzes = new ArrayList<String>();
    public static Cursor c;
    public static SQLiteDatabase testt;
    SwipeRefreshLayout swipeRefreshLayout;
    static ImageView noquizimg;

    public void go2create() {
        Intent go2create = new Intent(getApplicationContext(),createNew.class);
        go2create.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(go2create);
    }

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setImageResource(R.drawable.ic_baseline_add_24);
        fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.mainblue)));
        getSupportActionBar().setTitle("Home");
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                go2create();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        /*navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                if(destination.getId()==R.id.settings){
                    Toast.makeText(home.this, "Go to Settings", Toast.LENGTH_SHORT).show();
                }
            }
        });*/

        noquizimg = (ImageView) findViewById(R.id.noquizimg);
        noquizimg.setVisibility(View.INVISIBLE);

        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                myQuizzes = getmainTitle();
                myQuizzesID = getmainId();
                sortMyLists();
                recyclerViewAdapter = new RecyclerViewAdapter(myQuizzes,myQuizzesID,home.this,false);
                recyclerView.setAdapter(recyclerViewAdapter);
                recyclerViewAdapter.notifyDataSetChanged();
                imgref();
                swipeRefreshLayout.setRefreshing(false);

            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.home:
                        Intent go2home = new Intent(getApplicationContext(),home.class);
                        go2home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(go2home);
                        return true;

                    case R.id.archived:
                        Intent go2Archive = new Intent(getApplicationContext(),Archived.class);
                        go2Archive.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(go2Archive);
                        return true;

                    case R.id.createnew:
                        go2create();
                        return true;
                    case R.id.settings:
                        Intent go2settings = new Intent(getApplicationContext(),settings.class);
                        go2settings.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(go2settings);
                        return true;
                    default:
                        return false;
                }
            }
        });

        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.usernameTextview);
        navUsername.setText(MainActivity.prefs.getString("userfirst","Null"));

        testt = this.openOrCreateDatabase("testttquiz",MODE_PRIVATE,null);
        testt.execSQL("CREATE TABLE IF NOT EXISTS mainlist (titles VARCHAR, id INT(8))");
        testt.execSQL("CREATE TABLE IF NOT EXISTS archivelist (titles VARCHAR, id INT(8))");

        myQuizzes = getmainTitle();
        myQuizzesID = getmainId();
        sortMyLists();

        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerViewAdapter = new RecyclerViewAdapter(myQuizzes,myQuizzesID,this,false);
        recyclerView.setAdapter(recyclerViewAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        recyclerViewAdapter.notifyDataSetChanged();
        imgref();


    }

    public static void imgref()
    {
        if (recyclerViewAdapter.getItemCount()==0){
            noquizimg.setVisibility(View.VISIBLE);
        }
        else {
            noquizimg.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(R.drawable.ic_baseline_exit_to_app_24)
                .setTitle("Exit QuizEz")
                .setMessage("Are you sure you want to exit the application?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }

    public void sortMyLists(){

        for(int i = 0; i < myQuizzesID.size();i++){
            for (int j = 1;j<myQuizzesID.size()-i;j++){
                if(Integer.parseInt(myQuizzesID.get(j-1))>Integer.parseInt(myQuizzesID.get(j))){
                    String tempID = myQuizzesID.get(j-1);
                    String temp = myQuizzes.get(j-1);
                    myQuizzesID.set(j-1,myQuizzesID.get(j));
                    myQuizzes.set(j-1,myQuizzes.get(j));
                    myQuizzesID.set(j,tempID);
                    myQuizzes.set(j,temp);
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
            if (ItemTouchHelper.LEFT == direction || ItemTouchHelper.RIGHT==direction) {
                final String arch = myQuizzes.get(position);
                final String archID = myQuizzesID.get(position);
                myQuizzes.remove(position);
                myQuizzesID.remove(position);
                testt.execSQL("DELETE FROM mainlist WHERE id = " + archID);
                testt.execSQL("INSERT INTO archivelist (titles, id) VALUES ('"+arch+"',"+archID+")");
                recyclerViewAdapter.notifyItemRemoved(position);
                imgref();
                //Archived.recyclerViewAdapterArchived.notifyDataSetChanged();
                Snackbar.make(recyclerView,"Archived " + arch, Snackbar.LENGTH_LONG)
                        .setAction("Undo", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                myQuizzes.add(position, arch);
                                myQuizzesID.add(position,archID);
                                testt.execSQL("DELETE FROM archivelist WHERE id = " + archID);
                                testt.execSQL("INSERT INTO mainlist (titles, id) VALUES ('"+arch+"',"+archID+")");
                                //Archived.recyclerViewAdapterArchived.notifyDataSetChanged();
                                recyclerViewAdapter.notifyItemInserted(position);
                                imgref();
                            }
                        }).show();
                }
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(home.this,R.color.archiveYellow))
                    .addSwipeLeftActionIcon(R.drawable.ic_baseline_archive_24)
                    .addSwipeRightBackgroundColor(ContextCompat.getColor(home.this,R.color.archiveYellow))
                    .addSwipeRightActionIcon(R.drawable.ic_baseline_archive_24)
                    .create()
                    .decorate();

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {

            case R.id.archivedmenu:
                Intent go2Archive = new Intent(getApplicationContext(),Archived.class);
                go2Archive.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(go2Archive);
                return true;

            case R.id.createnewmenu:

                go2create();
                return true;

            case R.id.settingsmenu:
                Intent go2settings = new Intent(getApplicationContext(),settings.class);
                go2settings.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(go2settings);
                return true;

            default:
                return false;
        }
    }



    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public static void exsql(String exec){
        testt.execSQL(exec);
    }

    public static void deleteTable(String name){
        testt.execSQL("DROP TABLE q" + name);
    }

    public static ArrayList<String> getQuestion(String id) {
        c = testt.rawQuery("SELECT * FROM q" + id + " ",null);
        ArrayList<String> gotquestion = new ArrayList<String>();
        int i = c.getColumnIndex("question");
        c.moveToFirst();
        while (!c.isAfterLast()){
            gotquestion.add(c.getString(i));
            c.moveToNext();
        }
        return gotquestion;
    }

    public static ArrayList<Integer> getDuration(String id) {
        c = testt.rawQuery("SELECT * FROM q" + id + " ",null);
        ArrayList<Integer> gotduration = new ArrayList<Integer>();
        int i = c.getColumnIndex("duration");
        c.moveToFirst();
        while (!c.isAfterLast()){
            gotduration.add(Integer.parseInt(c.getString(i)));
            c.moveToNext();
        }
        return gotduration;
    }

    public static ArrayList<Integer> getPoints(String id) {
        c = testt.rawQuery("SELECT * FROM q" + id + " ",null);
        ArrayList<Integer> gotpoints = new ArrayList<Integer>();
        int i = c.getColumnIndex("points");
        c.moveToFirst();
        while (!c.isAfterLast()){
            gotpoints.add(Integer.parseInt(c.getString(i)));
            c.moveToNext();
        }
        return gotpoints;
    }

    public static ArrayList<String> getLeaderboardName(String id) {
        c = testt.rawQuery("SELECT * FROM l" + id + " ",null);
        ArrayList<String> leaderboardname = new ArrayList<String>();
        int i = c.getColumnIndex("name");
        c.moveToFirst();
        while (!c.isAfterLast()){
            leaderboardname.add(c.getString(i));
            c.moveToNext();
        }
        return leaderboardname;
    }

    public static ArrayList<String> getLeaderboardScore(String id) {
        c = testt.rawQuery("SELECT * FROM l" + id + " ",null);
        ArrayList<String> leaderboardscore = new ArrayList<String>();
        int i = c.getColumnIndex("score");
        c.moveToFirst();
        while (!c.isAfterLast()){
            leaderboardscore.add(c.getString(i));
            c.moveToNext();
        }
        return leaderboardscore;
    }

    public static ArrayList<String> getmainTitle(){
        c = testt.rawQuery("SELECT * FROM mainlist ",null);
        ArrayList<String> gotMainTitle = new ArrayList<String>();
        int i = c.getColumnIndex("titles");
        if(c.getCount()!=0){
            c.moveToFirst();
            while (!c.isAfterLast()){
                gotMainTitle.add(c.getString(i));
                c.moveToNext();
            }
        }
        return gotMainTitle;

    }

    public static ArrayList<String> getarchTitle(){
        c = testt.rawQuery("SELECT * FROM archivelist ",null);
        ArrayList<String> gotMainTitle = new ArrayList<String>();
        int i = c.getColumnIndex("titles");
        if(c.getCount()!=0){
            c.moveToFirst();
            while (!c.isAfterLast()){
                gotMainTitle.add(c.getString(i));
                c.moveToNext();
            }
        }
        return gotMainTitle;

    }

    public static ArrayList<String> getmainId(){
        c = testt.rawQuery("SELECT * FROM mainlist ",null);
        ArrayList<String> gotMainId = new ArrayList<String>();
        int i = c.getColumnIndex("id");
        if(c.getCount()!=0){
            c.moveToFirst();
            while (!c.isAfterLast()){
                gotMainId.add(c.getString(i));
                c.moveToNext();
            }
        }

        return gotMainId;

    }

    public static ArrayList<String> getarchId(){
        c = testt.rawQuery("SELECT * FROM archivelist ",null);
        ArrayList<String> gotMainId = new ArrayList<String>();
        int i = c.getColumnIndex("id");
        if(c.getCount()!=0){
            c.moveToFirst();
            while (!c.isAfterLast()){
                gotMainId.add(c.getString(i));
                c.moveToNext();
            }
        }

        return gotMainId;

    }


    public static ArrayList<String> getOption0(String id) {
        c = testt.rawQuery("SELECT * FROM q" + id + " ",null);
        ArrayList<String> gotoption0 = new ArrayList<String>();
        int i = c.getColumnIndex("option0");
        c.moveToFirst();
        while (!c.isAfterLast()){
            gotoption0.add(c.getString(i));
            c.moveToNext();
        }
        return gotoption0;
    }

    public static ArrayList<String> getOption1(String id) {
        c = testt.rawQuery("SELECT * FROM q" + id + " ",null);
        ArrayList<String> gotoption1 = new ArrayList<String>();
        int i = c.getColumnIndex("option1");
        c.moveToFirst();
        while (!c.isAfterLast()){
            gotoption1.add(c.getString(i));
            c.moveToNext();
        }
        return gotoption1;
    }

    public static ArrayList<String> getOption2(String id) {
        c = testt.rawQuery("SELECT * FROM q" + id + " ",null);
        ArrayList<String> gotoption2 = new ArrayList<String>();
        int i = c.getColumnIndex("option2");
        c.moveToFirst();
        while (!c.isAfterLast()){
            gotoption2.add(c.getString(i));
            c.moveToNext();
        }
        return gotoption2;
    }

    public static ArrayList<String> getOption3(String id) {
        c = testt.rawQuery("SELECT * FROM q" + id + " ",null);
        ArrayList<String> gotoption3 = new ArrayList<String>();
        int i = c.getColumnIndex("option3");
        c.moveToFirst();
        while (!c.isAfterLast()){
            gotoption3.add(c.getString(i));
            c.moveToNext();
        }
        return gotoption3;
    }

    public static ArrayList<String> getCorrectAnswer(String id) {
        c = testt.rawQuery("SELECT * FROM q" + id + " ",null);
        ArrayList<String> gotans = new ArrayList<String>();
        int i = c.getColumnIndex("correctanswer");
        c.moveToFirst();
        while (!c.isAfterLast()){
            gotans.add(c.getString(i));
            c.moveToNext();
        }
        return gotans;
    }



    public static int getcCount(String id){
        c = testt.rawQuery("SELECT * FROM q" + id + " ",null);
        return c.getCount();
    }





}

