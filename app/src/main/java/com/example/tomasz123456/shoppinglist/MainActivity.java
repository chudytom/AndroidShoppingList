package com.example.tomasz123456.shoppinglist;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity
implements  MainFragment.OnFragmentInteractionListener{
//    private RecyclerView shoppingRecyclerView;
//    private RecyclerView.Adapter shoppingListAdapter;
//    private ArrayList<String> shoppingList = null;
//    private RecyclerView.LayoutManager shoppingLayoutManager;

//    private List<Movie> movieList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
//
//        shoppingItemsAdapter = new ShoppingItemsAdapter(shoppingList);
//        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
//        recyclerView.setLayoutManager(mLayoutManager);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.setAdapter(shoppingItemsAdapter);
//
//        chosenComparator = loadComparatorFromSettings(sortingPreferenceName);
//        loadDataFromMemory(listFilename);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            displaySettings();
//            return true;
//        }
////        if (id == R.id.action_sort_name) {
////            chosenComparator = new ShoppingItemNameComparator();
////            sortItems();
////            return true;
////        }
////        if (id == R.id.action_sort_count) {
////            chosenComparator = new ShoppingItemCountComparator();
////            sortItems();
////            return true;
////        }
//        if(id == R.id.action_add_item){
//            addItemClicked();
//        }
//
//        if(id == R.id.action_clear_list){
//            clearItemsClicked();
//        }
//
//        return super.onOptionsItemSelected(item);
//    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}

