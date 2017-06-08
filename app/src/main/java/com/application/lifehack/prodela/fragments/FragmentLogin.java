package com.application.lifehack.prodela.fragments;



import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.application.lifehack.prodela.R;
import com.application.lifehack.prodela.classes.CFunction;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentLogin extends Fragment implements View.OnClickListener{
    View _view;
    Button _button, _buttonMasuk;
    CFunction _function;
    EditText _editTextEmail, _textViewPassword;
    public FragmentLogin() {
        // Required empty public constructor
    }
    public void initiate(){
        _button                 = (Button) _view.findViewById(R.id.buttonLogin);
        _button.setOnClickListener(this);
        _buttonMasuk            = (Button) _view.findViewById(R.id.buttonDaftar);
        _buttonMasuk.setOnClickListener(this);
        _editTextEmail = (EditText) _view.findViewById(R.id.editTextEmails);
        _textViewPassword       = (EditText) _view.findViewById(R.id.editTextPassword);

        _function = new CFunction(getActivity(),getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         _view = inflater.inflate(R.layout.fragment_login,container,false);
        initiate();

        return _view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonLogin:

               if(_function.isInternet()){
                   login();
               }
                break;
            case R.id.buttonDaftar:
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.mainFrameLayout,new FragmentDaftar())
                        .addToBackStack(null)
                        .commit();
                break;

        }
    }

    public void login() {

        String email        = _editTextEmail.getText().toString();
        String password     = _textViewPassword.getText().toString();
        String jsonString = String.format(
                        "{" +
                            "email: '%s'," +
                            "password:'%s'" +
                        "}",email,password);
        try {
            JSONObject jsonObject;
            jsonObject = new JSONObject(jsonString);
            _function.jsonRequest("login", jsonObject,"login");

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }



}
