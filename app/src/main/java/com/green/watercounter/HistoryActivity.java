package com.green.watercounter;

import android.content.Intent;
import android.graphics.Color;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    private static final String TAG = "History Activity: ";

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private Toolbar toolbar;

    private ArrayList<Daily> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        //initialize
        initRecyclerView();
        Log.d(TAG, "recyclerviewmade");

        //toolbar
        toolbar = findViewById(R.id.toolBar);
        toolbar.setTitle("Water Counter");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.inflateMenu(R.menu.menu_main);

        //TOOLBAR
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_show:
                        Log.d(TAG, "Toolbar action clicked");

                        Intent intent = new Intent(HistoryActivity.this, MainActivity.class);
                        startActivity(intent);

                    default:
                        return HistoryActivity.super.onOptionsItemSelected(menuItem);
                }
                //return false;
            }
        });

    }

    private void initRecyclerView() {
        Log.d(TAG, "Initializing recycler view...");

        recyclerView = findViewById(R.id.my_recycler_view);
        MyAdapter adapter = new MyAdapter(this, retrieveList());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private ArrayList<Daily> retrieveList() {
        Log.d(TAG, "Retrieving list...");

        Intent intent = getIntent();
        list = new ArrayList<Daily>();
        list = intent.getParcelableArrayListExtra("WeekList");
        Log.d(TAG, "list was passed");
        return list;
    }
}
