package com.example.tomasz123456.shoppinglist;

//import android.support.v4.app.FragmentTransaction;
//import android.app.Fragment;

//import android.app.FragmentTransaction;
//import android.app.FragmentTransaction;
//import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

//import android.support.v4.app.Fragment;

public class MainActivity extends AppCompatActivity
implements  MainFragment.OnFragmentInteractionListener,
AppPreferencesFragment.OnFragmentInteractionListener{
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

//        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        displayMainApp(AppPreferencesFragment.SortCriteria.Name);
    }

    private void navigateToFragment(Fragment fragment){
        FragmentTransaction transaction =  getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_layout, fragment, "mainFragment");

        transaction.commit();
    }

//    public void onButtonClicked(View view){
//        displayMainApp(AppPreferencesFragment.SortCriteria.Count);
//    }

    public void displaySettings(){
        Fragment preferenceFragment = AppPreferencesFragment.newInstance("One", "Two");
        navigateToFragment(preferenceFragment);
    }

    public void displayMainApp(AppPreferencesFragment.SortCriteria sortCriteria){

        Fragment mainFragment = MainFragment.newInstance(sortCriteria.ordinal(), "two");
        navigateToFragment(mainFragment);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}

