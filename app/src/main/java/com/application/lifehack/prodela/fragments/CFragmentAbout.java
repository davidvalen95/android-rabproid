package com.application.lifehack.prodela.fragments;

import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.application.lifehack.prodela.R;

/**
 * Created by valkyrie on 3/19/17.
 */

public class CFragmentAbout extends Fragment {

    private TextView _tvDisclaimer, _tvDefinisi;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view       = inflater.inflate(R.layout.fragment_about_us,container, false);

        _tvDefinisi     = (TextView) view.findViewById(R.id.definisi);
        _tvDisclaimer   = (TextView) view.findViewById(R.id.rabpro);
        TextView[] tvArray = {_tvDefinisi,_tvDisclaimer};


        for(TextView tvCurrent : tvArray) {
           tvCurrent.setTypeface(null, Typeface.BOLD);
        }
        return view;
    }
}
