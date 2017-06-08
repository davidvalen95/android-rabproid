package com.application.lifehack.prodela.fragments;



import android.graphics.Color;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.support.v7.widget.AppCompatRadioButton;
import android.widget.Toast;

import com.application.lifehack.prodela.R;
import com.application.lifehack.prodela.classes.CFunction;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentHitung extends Fragment implements View.OnClickListener{
    public static final String LANTAI_BANGUNAN = "lantaiBangunan";
    public static final String LUAS_BANGUNGAN = "luasBangunan";
    Toolbar _toolbar;
    ActionBar _actionBar;
    View _view;
    Button _buttonHitung;
    EditText _editTextLantaiBangunan;
    RadioGroup _rg;
    CFunction _function;
    public FragmentHitung() {
        // Required empty public constructor
    }
    public void initiate() {

        /*((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);*/
        _function = new CFunction(getActivity(),getContext());
        _buttonHitung = (Button) _view.findViewById(R.id.buttonHitung);
        _buttonHitung.setOnClickListener(this);

        _editTextLantaiBangunan = (EditText) _view.findViewById(R.id.editTextLuasBangunan);
        _rg = (RadioGroup) _view.findViewById(R.id.radioGroupLantaiBangunan) ;


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        _view = inflater.inflate(R.layout.fragment_hitung,container,false);
        // Inflate the layout for this fragment
        initiate();

        AdView mAdView = (AdView) _view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        return _view;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.buttonHitung:
                if(!_function.isInternet()){
                    Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString(LUAS_BANGUNGAN, _editTextLantaiBangunan.getText().toString());
                int id = _rg.getCheckedRadioButtonId();
                RadioButton rb = (RadioButton) _view.findViewById(id);
                bundle.putString(LANTAI_BANGUNAN, rb.getText().toString());

                FragmentWebView FWV = new FragmentWebView();
                FWV.setArguments(bundle);

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.mainFrameLayout,FWV)
                        .addToBackStack(null)
                        .commit();
                break;
        }
    }
}
