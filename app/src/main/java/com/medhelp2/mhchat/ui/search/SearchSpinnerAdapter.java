package com.medhelp2.mhchat.ui.search;


import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.medhelp2.mhchat.data.model.CategoryResponse;

import java.util.List;

class SearchSpinnerAdapter extends BaseAdapter implements SpinnerAdapter {

    private final Context context;
    private List<CategoryResponse> list;

    SearchSpinnerAdapter(Context context, List<CategoryResponse> response) {
        this.list = response;
        this.context = context;
    }

    public int getCount() {
        return list.size();
    }

    public Object getItem(int i) {
        return list.get(i);
    }

    public long getItemId(int i) {
        return (long) i;
    }


    @Override
    public View getDropDownView(int position, View view, ViewGroup parent) {
        TextView txt = new TextView(context);
        txt.setPadding(16, 16, 16, 16);
        txt.setTextSize(18);
        txt.setGravity(Gravity.CENTER_VERTICAL);
        txt.setText(list.get(position).getTitle());
        txt.setTextColor(Color.parseColor("#FFFFFF"));
        return txt;
    }

    public View getView(int i, View view, ViewGroup viewgroup) {
        TextView txt = new TextView(context);
        txt.setGravity(Gravity.START);
        txt.setPadding(16, 16, 16, 16);
        txt.setTextSize(16);
        txt.setText(list.get(i).getTitle());
        txt.setTextColor(Color.parseColor("#FFFFFF"));
        return txt;
    }

}