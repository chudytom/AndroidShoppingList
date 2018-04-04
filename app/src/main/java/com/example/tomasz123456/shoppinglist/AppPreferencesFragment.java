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


public class AppPreferencesFragment extends PreferenceFragmentCompat
implements SharedPreferences.OnSharedPreferenceChangeListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public static final String KEY_LIST_PREFERENCE = "listPref";

    private ListPreference mListPreference;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private SortCriteria sortCriteria;

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
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//
//        addPreferencesFromResource(R.layout.fragment_app_preferences);
//    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.fragment_app_preferences, rootKey);
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
////        setButtonEnabled(false);
//        return inflater.inflate(R.xml.fragment_app_preferences, container, false);
//    }

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

//    private void setButtonEnabled(Boolean enabled){
//        Button button =  getView().findViewById(R.id.accept_button);
//        button.setEnabled(enabled);
//    }

//    public void onRadioButtonClicked(View view) {
//        // Is the button now checked?
//        boolean checked = ((RadioButton) view).isChecked();
//
//        // Check which radio button was clicked
//        switch(view.getId()) {
//            case R.id.radio_sort_count:
//                if (checked) {
//                    // Pirates are the best
//                    sortCriteria = SortCriteria.Count;
////                    setButtonEnabled(true);
//                }
//                    break;
//            case R.id.radio_sort_name:
//                if (checked) {
//                    sortCriteria = SortCriteria.Name;
////                    setButtonEnabled(true);
//                }
//                    // Ninjas rule
//                    break;
//        }
//    }

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
            sortCriteria = convertToSortCritera(sortInt);
            mListPreference.setSummary("Current value is " + mListPreference.getEntry().toString());
            displayMainApp();
            System.out.println("Value of list changed");
        }
    }

    private SortCriteria convertToSortCritera(int value){
        switch (value){
            case 0:
                return SortCriteria.Name;
            case 1:
                return SortCriteria.Count;
            default:
                return SortCriteria.Name;
        }
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

    public enum SortCriteria{
        Name, Count;
    }
}
