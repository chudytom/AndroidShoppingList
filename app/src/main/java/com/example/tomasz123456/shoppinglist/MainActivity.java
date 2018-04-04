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
    private RecyclerView recyclerView;
    private ShoppingItemsAdapter shoppingItemsAdapter;
    private Comparator<ShoppingItem> chosenComparator;

    private List<ShoppingItem> shoppingList = new ArrayList<>();
    private ShoppingItemsProvider itemsProvider = new ShoppingItemsProvider();
    private String listFilename = "shoppingListFile";
    private String sortingPreferenceName = "sortingPreference";

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


    private Comparator<ShoppingItem> SortCriteriaToComparator(SortCriteria criteria){
        switch (criteria){
            case Name:
                return new ShoppingItemNameComparator();
            case Count:
                return  new ShoppingItemCountComparator();
            default:
                return new ShoppingItemNameComparator();
        }
    }


    private void loadDataFromMemory(String filename){
//        List<ShoppingItem> items = loadShoppingListFromMemory(filename);
//        final List<ShoppingItem> items = new ArrayList<>();
        shoppingList.clear();
        new ReadFromMemoryAsync(filename, getApplicationContext(), new ReadFromMemoryAsync.AsyncResponse(){

            @Override
            public void processFinish(List<ShoppingItem> output) {
                shoppingList.addAll(output);
            }
        }).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
//        for (ShoppingItem item: items) {
//            shoppingList.add(item);
//        }
        sortItems();
    }

    private void getSampleData() {

        List<ShoppingItem> items = itemsProvider.GetSampleItems();
        for (ShoppingItem item: items) {
            shoppingList.add(item);
        }
        shoppingItemsAdapter.notifyDataSetChanged();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            displaySettings();
            return true;
        }
//        if (id == R.id.action_sort_name) {
//            chosenComparator = new ShoppingItemNameComparator();
//            sortItems();
//            return true;
//        }
//        if (id == R.id.action_sort_count) {
//            chosenComparator = new ShoppingItemCountComparator();
//            sortItems();
//            return true;
//        }
        if(id == R.id.action_add_item){
            addItemClicked();
        }

        if(id == R.id.action_clear_list){
            clearItemsClicked();
        }

        return super.onOptionsItemSelected(item);
    }

    private void displaySettings() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.settings_criteria);

        LinearLayout layout = new LinearLayout(this );
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.FILL_PARENT,
                RecyclerView.LayoutParams.FILL_PARENT));

        final RadioGroup group = new RadioGroup(this  );

        RadioButton nameRadio = new RadioButton(this);
        nameRadio.setText(R.string.action_sort_name);
        nameRadio.setId(R.id.nameOptionId);

        group.getCheckedRadioButtonId();

        RadioButton countRadio = new RadioButton(this);
        countRadio.setText(R.string.action_sort_count);
        countRadio.setId(R.id.countOptionId);

        group.addView(nameRadio);
        group.addView(countRadio);

        builder.setView(group);

        builder.setPositiveButton(R.string.action_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int id = group.getCheckedRadioButtonId();
                if (id == R.id.nameOptionId){
                    setSortingPreference(SortCriteria.Name);
                }else if (id== R.id.countOptionId){
                    setSortingPreference(SortCriteria.Count);
                }
                new WriteToMemoryAsync(listFilename, getApplicationContext(), shoppingList)
                        .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
//                saveShoppingListInMemory(shoppingList, listFilename);
                sortItems();
            }
        });
        builder.setNegativeButton(R.string.action_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    private void setSortingPreference(SortCriteria criteria) {
        saveSettings(criteria, sortingPreferenceName);
        chosenComparator = SortCriteriaToComparator(criteria);
        sortItems();
    }


    private void saveSettings(SortCriteria criteria, String settingsKey){
        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(settingsKey, criteria.ordinal());
        editor.commit();
    }

    private Comparator<ShoppingItem> loadComparatorFromSettings(String settingsKey) {
        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        int value = sharedPreferences.getInt(settingsKey, 1);
        SortCriteria criteria =  SortCriteria.values()[value] ;
        return SortCriteriaToComparator(criteria);
    }

    private void sortItems() {
        Collections.sort(shoppingList, chosenComparator);
        shoppingItemsAdapter.notifyDataSetChanged();
    }

    private void clearItemsClicked() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.clear_list_confirmation);
        builder.setPositiveButton(R.string.action_yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                shoppingList.clear();
                new WriteToMemoryAsync(listFilename, getApplicationContext(), shoppingList)
                        .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
//                saveShoppingListInMemory(shoppingList, listFilename);
                shoppingItemsAdapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton(R.string.action_no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    private void addItemClicked() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LinearLayout layout = new LinearLayout(this );
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.FILL_PARENT,
                RecyclerView.LayoutParams.FILL_PARENT));

        final TextView nameLabel = new TextView(this);
        nameLabel.setGravity(View.TEXT_ALIGNMENT_CENTER);
        nameLabel.setTextSize(20);
        nameLabel.setText(R.string.name_request);
        final EditText nameInput = new EditText(this);

        final TextView countLabel = new TextView(this);
        countLabel.setGravity(View.TEXT_ALIGNMENT_CENTER);
        countLabel.setTextSize(20);
        countLabel.setText(R.string.count_request);
        final NumberPicker countInput = new NumberPicker(this);
        countInput.setMaxValue(100);

        layout.addView(nameLabel);
        layout.addView(nameInput);
        layout.addView(countLabel);
        layout.addView(countInput);
        builder.setView(layout);
        builder.setPositiveButton(R.string.action_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ShoppingItem item = new ShoppingItem(
                        capitalLetter(nameInput.getText().toString()),
                        countInput.getValue());
                shoppingList.add(item);
                new WriteToMemoryAsync(listFilename, getApplicationContext(), shoppingList)
                        .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
//                saveShoppingListInMemory(shoppingList, listFilename);
                sortItems();
            }
        });
        builder.setNegativeButton(R.string.action_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    public static String capitalLetter(String original){
        if(original.isEmpty())
            return  original;

        return  original.substring(0, 1).toUpperCase()
                + original.substring(1).toLowerCase();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public enum SortCriteria{
        Name, Count;
    }
}

