package com.example.tomasz123456.shoppinglist;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
//import android.app.Fragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class MainFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private int sortInt;
    private String mParam2;

    // Possible problems with the Context. It's possible it needs to be passed in a constructor

    private RecyclerView recyclerView;
    private ShoppingItemsAdapter shoppingItemsAdapter;
    private Comparator<ShoppingItem> chosenComparator;

    private List<ShoppingItem> shoppingList = new ArrayList<>();
    private ShoppingItemsProvider itemsProvider = new ShoppingItemsProvider();
    private String listFilename = "shoppingListFile";
    private String sortingPreferenceName = "sortingPreference";

    private OnFragmentInteractionListener mListener;

    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(int param1, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);

        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setHasOptionsMenu(true);


        if (getArguments() != null) {
            sortInt = getArguments().getInt(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        shoppingItemsAdapter = new ShoppingItemsAdapter(shoppingList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getCurrentContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(shoppingItemsAdapter);

//        chosenComparator = loadComparatorFromSettings(sortingPreferenceName);
        chosenComparator = new ShoppingItemNameComparator();
        loadDataFromMemory(listFilename);


        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
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
        new ReadFromMemoryAsync(filename, getCurrentContext(), new ReadFromMemoryAsync.AsyncResponse(){

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

    private void displaySettings(){
        ((MainActivity)getActivity()).displaySettings();
    }

//    private void displaySettings() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(getCurrentContext());
//        builder.setTitle(R.string.settings_criteria);
//
//        LinearLayout layout = new LinearLayout(getCurrentContext() );
//        layout.setOrientation(LinearLayout.VERTICAL);
//        layout.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.FILL_PARENT,
//                RecyclerView.LayoutParams.FILL_PARENT));
//
//        final RadioGroup group = new RadioGroup(getCurrentContext()  );
//
//        RadioButton nameRadio = new RadioButton(getCurrentContext());
//        nameRadio.setText(R.string.action_sort_name);
//        nameRadio.setId(R.id.nameOptionId);
//
//        group.getCheckedRadioButtonId();
//
//        RadioButton countRadio = new RadioButton(getCurrentContext());
//        countRadio.setText(R.string.action_sort_count);
//        countRadio.setId(R.id.countOptionId);
//
//        group.addView(nameRadio);
//        group.addView(countRadio);
//
//        builder.setView(group);
//
//        builder.setPositiveButton(R.string.action_ok, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                int id = group.getCheckedRadioButtonId();
//                if (id == R.id.nameOptionId){
//
//                    setSortingPreference(SortCriteria.Name);
//                }else if (id== R.id.countOptionId){
//                    setSortingPreference(SortCriteria.Count);
//                }
//                new WriteToMemoryAsync(listFilename, getCurrentContext(), shoppingList)
//                        .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
////                saveShoppingListInMemory(shoppingList, listFilename);
//                sortItems();
//            }
//        });
//        builder.setNegativeButton(R.string.action_cancel, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.cancel();
//            }
//        });
//        builder.show();
//    }

//    private void setSortingPreference(SortCriteria criteria) {
//        saveSettings(criteria, sortingPreferenceName);
//        chosenComparator = SortCriteriaToComparator(criteria);
//        sortItems();
//    }


//    private void saveSettings(SortCriteria criteria, String settingsKey){
//        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putInt(settingsKey, criteria.ordinal());
//        editor.commit();
//    }

//    private Comparator<ShoppingItem> loadComparatorFromSettings(String settingsKey) {
//        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
//        int value = sharedPreferences.getInt(settingsKey, 1);
//        SortCriteria criteria =  SortCriteria.values()[value] ;
//        return SortCriteriaToComparator(criteria);
//    }

    private void sortItems() {
        resolveSortCriteria();
        Collections.sort(shoppingList, chosenComparator);
        shoppingItemsAdapter.notifyDataSetChanged();
    }

    private void resolveSortCriteria() {
        if(sortInt == 0){
            chosenComparator = new ShoppingItemNameComparator();
        }
        else if (sortInt == 1){
            chosenComparator = new ShoppingItemCountComparator();
        }
    }

    private void clearItemsClicked() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getCurrentContext());
        builder.setTitle(R.string.clear_list_confirmation);
        builder.setPositiveButton(R.string.action_yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                shoppingList.clear();
                new WriteToMemoryAsync(listFilename, getCurrentContext(), shoppingList)
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
        AlertDialog.Builder builder = new AlertDialog.Builder(getCurrentContext());

        LinearLayout layout = new LinearLayout(getCurrentContext() );
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.FILL_PARENT,
                RecyclerView.LayoutParams.FILL_PARENT));

        final TextView nameLabel = new TextView(getCurrentContext());
        nameLabel.setGravity(View.TEXT_ALIGNMENT_CENTER);
        nameLabel.setTextSize(20);
        nameLabel.setText(R.string.name_request);
        final EditText nameInput = new EditText(getCurrentContext());

        final TextView countLabel = new TextView(getCurrentContext());
        countLabel.setGravity(View.TEXT_ALIGNMENT_CENTER);
        countLabel.setTextSize(20);
        countLabel.setText(R.string.count_request);
        final NumberPicker countInput = new NumberPicker(getCurrentContext());
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
                new WriteToMemoryAsync(listFilename, getCurrentContext(), shoppingList)
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


    public enum SortCriteria{
        Name, Count;
    }

    private Context getCurrentContext()
    {
        return getContext();
    }
}
