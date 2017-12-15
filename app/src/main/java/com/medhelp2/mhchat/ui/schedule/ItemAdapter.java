package com.medhelp2.mhchat.ui.schedule;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.medhelp2.mhchat.R;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.DateViewHolder>
{
    private Context context;
    private List<String> list;

    public class DateViewHolder extends RecyclerView.ViewHolder
    {
        public TextView title;

        public DateViewHolder(View view)
        {
            super(view);
            title = view.findViewById(R.id.tv_date_item_row);
        }
    }

    public ItemAdapter(Context context, List<String> list)
    {
        this.context = context;
        this.list = list;
    }

    @Override
    public DateViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_date, parent, false);

        return new DateViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final DateViewHolder holder, int position)
    {
        String album = list.get(position);
        holder.title.setText(album);
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }
}
