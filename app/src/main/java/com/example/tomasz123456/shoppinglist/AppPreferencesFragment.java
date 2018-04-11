package com.example.tomasz123456.shoppinglist;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceFragment;
//import android.support.v4.app.Fragment;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;

import java.io.Console;
import java.util.List;


public class AppPreferencesFragment extends PreferenceFragmentCompat
implements SharedPreferences.OnSharedPreferenceChangeListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public static final String KEY_LIST_PREFERENCE = "sortCriteria";

    private ListPreference mListPreference;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private MainActivity.SortCriteria sortCriteria;

    private OnFragmentInteractionListener mListener;

    public AppPreferencesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AppPreferencesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AppPreferencesFragment newInstance(String param1, String param2) {
        AppPreferencesFragment fragment = new AppPreferencesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);

        return fragment;
    }

//    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.fragment_app_preferences, rootKey);
        mListPreference = (ListPreference) findPreference("sortCriteria");
    }


    @Override
    public void onResume() {
        super.onResume();
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    //The problem is that somehow the context for this button is set to mainAtivity and that function is run
    public void onButtonClicked(View view){
        displayMainApp();
    }

    private void displayMainApp(){
        System.out.println("Change of screen requested");
        ((MainActivity)getActivity()).displayMainApp(sortCriteria);
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
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        System.out.println("Prefences changed " + key);
        if (key.equals(KEY_LIST_PREFERENCE)) {
            String sortValue = mListPreference.getValue();
            int sortInt =  Integer.parseInt(sortValue);
            System.out.println("Value to save (int) is: " + sortInt);
            sortCriteria = convertToSortCritera(sortInt);
            System.out.println("Value to save is: " + sortCriteria);
            SharedPreferencesHelper.SaveSortCriteria(getContext(), sortCriteria);
            System.out.println("Value of list changed");
        }
    }

    private MainActivity.SortCriteria convertToSortCritera(int value){
        switch (value){
            case 0:
                return MainActivity.SortCriteria.Name;
            case 1:
                return MainActivity.SortCriteria.Count;
            default:
                return MainActivity.SortCriteria.Name;
        }
    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
