package com.medhelp2.mhchat.ui.select;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.medhelp2.mhchat.data.model.CategoryResponse;
import com.medhelp2.mhchat.data.model.Doctor;
import com.medhelp2.mhchat.data.model.ServiceResponse;

import java.util.List;

public class SelectAdapter extends BaseAdapter implements SpinnerAdapter
{
    private final Context context;
    private List<CategoryResponse> listSpec;
    private List<Doctor> listDoctor;
    private List<ServiceResponse> listService;

    SelectAdapter(Context context, @NonNull List<CategoryResponse> listSpec,
            @NonNull List<Doctor> listDoctor, @NonNull List<ServiceResponse> listService)
    {
        this.context = context;
        this.listSpec = listSpec;
        this.listDoctor = listDoctor;
        this.listService = listService;
    }

    public int getCount()
    {
        if (listDoctor.size() > 0)
        {
            return listDoctor.size();
        }
        if (listSpec.size() > 0)
        {
            return listSpec.size();
        }
        if (listService.size() > 0)
        {
            return listService.size();
        }
        return 0;
    }

    public Object getItem(int i)
    {
        if (listDoctor.size() > 0)
        {
            return listDoctor.get(i);
        }
        if (listSpec.size() > 0)
        {
            return listSpec.get(i);
        }
        if (listService.size() > 0)
        {
            return listService.get(i);
        }
        return 0;
    }

    public long getItemId(int i)
    {
        return (long) i;
    }


    @Override
    public View getDropDownView(int position, View view, ViewGroup parent)
    {
        TextView txt = new TextView(context);
        txt.setPadding(16, 16, 16, 16);
        txt.setTextSize(18);
        txt.setGravity(Gravity.CENTER_VERTICAL);
        txt.setTextColor(Color.parseColor("#FFFFFF"));

        if (listDoctor.size() > 0)
        {
            txt.setText(listDoctor.get(position).getFullName());
        }
        if (listSpec.size() > 0)
        {
            txt.setText(listSpec.get(position).getTitle());
        }
        if (listService.size() > 0)
        {
            txt.setText(listService.get(position).getTitle());
        }
        return txt;
    }

    public View getView(int i, View view, ViewGroup viewgroup)
    {
        TextView txt = new TextView(context);
        txt.setGravity(Gravity.START);
        txt.setPadding(16, 16, 16, 16);
        txt.setTextSize(16);
        txt.setTextColor(Color.parseColor("#FFFFFF"));

        if (listDoctor.size() > 0)
        {
            txt.setText(listDoctor.get(i).getFullName());
        }
        if (listSpec.size() > 0)
        {
            txt.setText(listSpec.get(i).getTitle());
        }
        if (listService.size() > 0)
        {
            txt.setText(listService.get(i).getTitle());
        }
        return txt;
    }
}