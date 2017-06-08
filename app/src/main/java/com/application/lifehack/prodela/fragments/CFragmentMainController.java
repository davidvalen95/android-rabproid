package com.application.lifehack.prodela.fragments;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.application.lifehack.prodela.R;
import com.application.lifehack.prodela.adapter.CDrawerItemAdapter;

/**
 * Created by valkyrie on 3/10/17.
 */

public class CFragmentMainController extends Fragment {
    @Nullable
    public static final String kNOT_HOME    = "notHome";
    public static final String kHOME        = "home";
    ActionBar                   _actionBar;
    ActionBarDrawerToggle       _drawerTogle;
    DrawerLayout                _drawerLayout;
    ListView                    _drawerMenu;
    Toolbar                     _toolbar;
    View                        _view;

    private void initiate(){

        _drawerLayout       = (DrawerLayout) _view.findViewById(R.id.drawerLayout);
        _drawerMenu         = (ListView) _view.findViewById(R.id.drawerItem);
        _toolbar            = (Toolbar) _view.findViewById(R.id.my_toolbar);




        _toolbar.setTitleTextColor(Color.parseColor("#fff9c4"));
        _toolbar.setSubtitleTextColor(Color.parseColor("#fff9c4"));
        _toolbar.bringToFront();
        _toolbar.getParent().requestLayout();
        ((AppCompatActivity) getActivity()).setSupportActionBar(_toolbar);
        _actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        //_actionBar.setTitle("Input Data");
        _actionBar.setDisplayHomeAsUpEnabled(true);
        _actionBar.setHomeButtonEnabled(true);

        //# ujicoba, ternyata tidak perlu, sudah di drawerLayout.post(runnable(togle.synstate));
        //_actionBar.setHomeAsUpIndicator(R.drawable.ic_drawer);




        //# setting drawerlayout
        _drawerTogle        = new ActionBarDrawerToggle(getActivity(),_drawerLayout,_toolbar,R.string.drawer_open,R.string.drawer_close){
            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                //_actionBar.setTitle("tes");
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //_actionBar.setTitle("tes2");
            }

        };
        _drawerLayout.addDrawerListener(_drawerTogle);
        _drawerLayout.post(new Runnable() {
            @Override
            public void run() {
                _drawerTogle.syncState();
            }
        });
        //# setting drawermenu menggunakan listview dengan baseadapter
        _drawerMenu.setAdapter(new CDrawerItemAdapter(getContext()));
        _drawerMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //menggunakan view untuk fetch item

                switch (i){
                    case 0:
                        replaceFragmentWithLogic(new FragmentHitung());
                        _actionBar.setTitle("Count");
                        break;
                    case 1:
                        replaceFragmentWithLogic(new CFragmentAbout());
                        _actionBar.setTitle("About Us");
                        break;
                    case 2:
                        replaceFragmentWithLogic(new FragmentLogin());
                        break;
                }//# switch
                _drawerLayout.closeDrawer(Gravity.LEFT);
            }
        });



        getActivity().getSupportFragmentManager().removeOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                _actionBar.setTitle("Hitung Data");
            }
        });
    }//initiate

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        _drawerTogle.onConfigurationChanged(newConfig);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        _view = inflater.inflate(R.layout.fragment_main, container,false);
        initiate();
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentMainControllerFrameLayout, new FragmentHitung(),kHOME)
                .commit();
        Log.d("customTrace","are we here");
        _view.setFocusableInTouchMode(true);
        _view.requestFocus();
        _view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if( keyCode == KeyEvent.KEYCODE_BACK ) {
                    replaceFragmentWithLogic(new FragmentHitung());
                    return true;
                } else {
                    return false;
                }
            }
        });
        return _view;
    }



    private void replaceFragmentWithLogic(Fragment fragment){

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        if(fragment instanceof FragmentHitung){
            if (getActivity().getSupportFragmentManager().getBackStackEntryCount() != 0) {
                //# kalo sekarang bukan Home tapi klik menu Home, maka di pop
                transaction.remove(getActivity().getSupportFragmentManager().findFragmentByTag(kNOT_HOME)).commit();
                getActivity()
                        .getSupportFragmentManager()
                        .popBackStack(kNOT_HOME,FragmentManager.POP_BACK_STACK_INCLUSIVE);
                return;
            }else{
                return;
            }
        }

        //# maksimal backstack  cuman 1.

        transaction.replace(R.id.fragmentMainControllerFrameLayout, fragment,kNOT_HOME);
        if(getActivity().getSupportFragmentManager().getBackStackEntryCount() == 0){
            transaction.addToBackStack(kNOT_HOME);
        }
        transaction.commit();
    }


}
