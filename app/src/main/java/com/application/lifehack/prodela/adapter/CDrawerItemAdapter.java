package com.application.lifehack.prodela.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.application.lifehack.prodela.R;
/**
 * Created by valkyrie on 3/11/17.
 */

public class CDrawerItemAdapter extends BaseAdapter {
    String[] _menuTitle;
    Context _context;
    Integer[] _gambar;
    public CDrawerItemAdapter(Context context){
        _menuTitle      = new String[]{"Calculate","About Us"};
        _context        = context;
        _gambar         = new Integer[] {R.drawable.d_calculator,R.drawable.d_information};
    }

    @Override
    public int getCount() {
        return _menuTitle.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View _view = inflater.inflate(R.layout.adapter_drawer_item,viewGroup,false);
        TextView tvTitle;
        ImageView ivIcon;
        tvTitle         = (TextView) _view.findViewById(R.id.drawerItemAdapterTitleItem);
        ivIcon          = (ImageView) _view.findViewById(R.id.drawerItemImageViewIcon);


        tvTitle.setText(_menuTitle[i]);


            ivIcon.setImageResource(_gambar[i]);

        return _view;
    }
}
