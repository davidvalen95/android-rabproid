package com.application.lifehack.prodela.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.multidex.MultiDex;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.application.lifehack.prodela.R;
import com.application.lifehack.prodela.fragments.CFragmentMainController;
import com.application.lifehack.prodela.fragments.FragmentHitung;
import com.application.lifehack.prodela.fragments.FragmentLogin;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;


public class ActivityMain extends AppCompatActivity implements View.OnClickListener{
    FrameLayout _frameLayout;
    FragmentManager _fragmentManager;
    public FragmentTransaction _fragmentTransaction;
    FragmentLogin _fragmentLogin;

    public void initiate(){
        //xml
        _frameLayout        = (FrameLayout) findViewById(R.id.mainFrameLayout);

        //FM
        _fragmentLogin          = new FragmentLogin();
        _fragmentManager        = getSupportFragmentManager();
        _fragmentTransaction    = _fragmentManager.beginTransaction();



        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        String nama= sharedPref.getString(getString(R.string.namaLogin), null);
        if(nama!=null){
            Toast.makeText(ActivityMain.this, "Welcome "+nama, Toast.LENGTH_SHORT).show();
            _fragmentTransaction.add(R.id.mainFrameLayout, new CFragmentMainController());

        }else
        {
            _fragmentTransaction.add(R.id.mainFrameLayout, new FragmentLogin());
        }

        _fragmentTransaction.commit( );
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_main);

        MobileAds.initialize(getApplicationContext(), getString(R.string.banner_ad_unit_id));

        initiate();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        MultiDex.install(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }
}
