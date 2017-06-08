package com.application.lifehack.prodela.fragments;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.application.lifehack.prodela.R;
import com.application.lifehack.prodela.classes.CFunction;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentDaftar extends Fragment implements View.OnClickListener{
    Toolbar _toolbar;
    ActionBar _actionBar;
    View _view;
    EditText[] _editText;
    Button _buttonDaftarSubmit;
    CFunction _function;
    public FragmentDaftar() {
        // Required empty public constructor
    }
    public void initiate(){
        _toolbar = (Toolbar) _view.findViewById(R.id.my_toolbar);
        _toolbar.setTitleTextColor(Color.parseColor("#fff9c4"));
        _toolbar.setSubtitleTextColor(Color.parseColor("#fff9c4"));

        ((AppCompatActivity)getActivity()).setSupportActionBar(_toolbar);
        _actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        _actionBar.setTitle("Signup");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
       /* _actionBar.setBackgroundDrawable(new ColorDrawable(0xff00DDED));
        _actionBar.setDisplayShowTitleEnabled(false);
        _actionBar.setDisplayShowTitleEnabled(true);*/
        _function = new CFunction (getActivity(),getContext());
        _buttonDaftarSubmit         = (Button) _view.findViewById(R.id.buttonDaftarSubmit);
        _buttonDaftarSubmit.setOnClickListener(this);
        _editText                   = new EditText[5];
        _editText[0]                = (EditText) _view.findViewById(R.id.editTextDaftarEmail);
        _editText[1]                = (EditText) _view.findViewById(R.id.editTextDaftarPassword);
        _editText[2]                = (EditText) _view.findViewById(R.id.editTextDaftarRetype);
        _editText[3]                = (EditText) _view.findViewById(R.id.editTextDaftarNamaLengkap);
        _editText[4]                = (EditText) _view.findViewById(R.id.editTextDaftarTelephone);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        _view = inflater.inflate(R.layout.fragment_daftar, container, false);

        initiate();
        return _view;
    }


   public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                ((AppCompatActivity) getActivity())
                        .getSupportFragmentManager()
                        .popBackStack();

                break;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.buttonDaftarSubmit:
                String password             = _editText[1].getText().toString();
                String retypePassword       = _editText[2].getText().toString();
                String messege = "";

                if(password.length() < 6 ){
                    messege += "Password must be at least 6 characters.\n";
                }else if(!password.equals(retypePassword)){
                    messege += "Password did not match\n";
                }

                if(!android.util.Patterns.EMAIL_ADDRESS.matcher(_editText[0].getText()).matches()){
                    messege += "Wrong email pattern.\n";

                }

                for(int i=0;i<_editText.length;i++){
                    if (_editText[i].getText().length()==0){
                        messege+="Fields cannot be empty.\n";
                        break;
                    }
                }
                 if(messege.equals("")){
                     String stringJson = "{" +
                             "email:'"+_editText[0].getText().toString() +
                             "',password:'"+_editText[1].getText().toString() +
                             "',nama:'"+_editText[3].getText().toString() +
                             "',noTlp:'"+_editText[4].getText().toString() +
                             "',veryCustom:'aktivasiEmail'," +
                             "aktivasi:'aktivasi'" +
                             "}";
                     try {
                         JSONObject jsonFormat = new JSONObject(stringJson);
                         _function.jsonRequest("signup",jsonFormat,"daftar");
                     } catch (JSONException e) {
                         e.printStackTrace();
                     }

                 }else
                    _function.showMessegeDialog("Oh dear", messege, "Ok");
                break;
        }
    }
}
