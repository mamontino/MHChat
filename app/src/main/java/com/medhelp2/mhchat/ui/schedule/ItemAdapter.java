package com.medhelp2.mhchat.ui.schedule;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.medhelp2.mhchat.R;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.DateViewHolder> {

    private Context context;
    private List<String> list;

    public class DateViewHolder extends RecyclerView.ViewHolder {
        public TextView title;

        public DateViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.tv_date_item_row);
        }
    }


    public ItemAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public DateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_date, parent, false);

        return new DateViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final DateViewHolder holder, int position) {
        String album = list.get(position);
        holder.title.setText(album);
    }

//    /**
//     * Showing popup menu when tapping on 3 dots
//     */
//    private void showPopupMenu(View view) {
//        // inflate menu
//        PopupMenu popup = new PopupMenu(mContext, view);
//        MenuInflater inflater = popup.getMenuInflater();
//        inflater.inflate(R.menu.menu_album, popup.getMenu());
//        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
//        popup.show();
//    }
//
//    /**
//     * Click listener for popup menu items
//     */
//    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {
//
//        public MyMenuItemClickListener() {
//        }
//
//        @Override
//        public boolean onMenuItemClick(MenuItem menuItem) {
//            switch (menuItem.getItemId()) {
//                case R.id.action_add_favourite:
//                    Toast.makeText(mContext, "Add to favourite", Toast.LENGTH_SHORT).show();
//                    return true;
//                case R.id.action_play_next:
//                    Toast.makeText(mContext, "Play next", Toast.LENGTH_SHORT).show();
//                    return true;
//                default:
//            }
//            return false;
//        }
//    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
