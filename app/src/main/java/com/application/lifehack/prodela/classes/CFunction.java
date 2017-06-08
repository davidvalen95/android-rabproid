package com.application.lifehack.prodela.classes;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.application.lifehack.prodela.R;
import com.application.lifehack.prodela.fragments.CFragmentMainController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Valkeyri on 1/18/2017.
 */
public class CFunction {

    Activity _activity;
    Context _context;
    ProgressDialog _progressDialog;
    String _nama;
    public CFunction(Activity activity, Context context){
        _activity       = activity;
        _context        = context;
        _progressDialog = new ProgressDialog(_activity);
    }

    public boolean isInternet(){

        ConnectivityManager connectivityManager = (ConnectivityManager)_activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            return true;
        }else {
            Toast.makeText(_activity, "No internet connection", Toast.LENGTH_SHORT).show();
            return false;
        }

    }

    public void jsonRequest(String extraUrl,JSONObject json, final String type){
        final String url = _activity.getResources().getString(R.string.BASE_URL)+extraUrl;
        RequestQueue queue = Volley.newRequestQueue(_context);

        final JSONObject jsonObject = json;


        StringRequest sr = new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject responseJson = new JSONObject(response);
                    if(type.equals("login")) {
                        Boolean authen = Boolean.valueOf(responseJson.get("authen").toString());
                        if (authen) {
                            SharedPreferences sharedPref = _activity.getPreferences(Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putString(_activity.getString(R.string.namaLogin), responseJson.get("nama").toString());
                            editor.commit();
                            Toast.makeText(_activity, "Welcome "+responseJson.get("nama").toString(), Toast.LENGTH_SHORT).show();
                            ((AppCompatActivity) _activity).getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.mainFrameLayout, new CFragmentMainController())
                                    .commit();
                            /*Uri uri = Uri.parse("http://www.google.com");
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            _activity.startActivity(intent);*/


                        }else{
                            showMessegeDialog("Oh dear","Wrong authentication or not yet verified","Ok");
                        }
                    }else if(type.equals("daftar")){
                        Boolean daftar = Boolean.valueOf(responseJson.get("daftar").toString());
                        if(daftar){
                            showMessegeDialog("Congratulation","Your account has been created. Please verify your email","Ok");
                            ((AppCompatActivity) _activity).getSupportFragmentManager()
                                    .popBackStack();
                        }else{
                            String message = responseJson.getString("message");
                            if(!TextUtils.isEmpty("message")){
                                AlertDialog.Builder builder = new AlertDialog.Builder(_context);
                                builder.setTitle("Oops");
                                builder.setMessage(message);
                                builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        dialog.dismiss();
                                    }
                                });
                                builder.show();
                            }
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("customTrace",response);
                dismissDialog();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(_activity, "Connection problem", Toast.LENGTH_SHORT).show();
                Log.d("customTrace", "" + error+"++"+jsonObject.toString());
                dismissDialog();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();

                try {
                    JSONObject json = jsonObject;
                    Iterator<String> temp = json.keys();
                    while (temp.hasNext()) {
                        String key = temp.next();

                        Object value = json.get(key);
                        Log.d("customTrace",key+": "+value);
                        params.put(key,value.toString());
                    }
                } catch (JSONException e) {
                    Log.d("customTrace",e.toString());
                }
                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };
        String title="";
        if(type.equals("login")){
            title = "Logging in";
        }else if(type.equals("daftar")){
            title="Signing up";
        }
        createDialog(title);
        queue.add(sr);



    }

    private void createDialog(final String title){
        _activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                _progressDialog.setTitle(title);
                _progressDialog.setCancelable(false); // disable dismiss by tapping outside of the dialog
                _progressDialog.show();
                // To dismiss the dialog
                //_progressDialog.dismiss();
            }
        });
    }
    private void dismissDialog(){
        _activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(_progressDialog.isShowing()){
                    _progressDialog.dismiss();
                }
            }
        });
    }

    public void showMessegeDialog(String title,String message, String positive, String negative){
        new AlertDialog.Builder(_context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })

                .show();
    }
    public void showMessegeDialog(String title,String message, String positive){
        new AlertDialog.Builder(_context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(positive, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })


                .show();
    }
}
