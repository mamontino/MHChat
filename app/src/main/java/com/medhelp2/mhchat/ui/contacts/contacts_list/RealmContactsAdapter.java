//package com.medhelp2.mhchat.ui.room.contacts_list;
//
//import android.content.Context;
//import android.support.v7.app.AlertDialog;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.EditText;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.medhelp2.mhchat.R;
//import com.medhelp2.mhchat.data.model.RoomResponse;
//
//import io.realm.Realm;
//import io.realm.RealmResults;
//
//
//public class RealmContactsAdapter extends RealmRecyclerViewAdapter<RoomResponse>
//{
//
//    final Context context;
//    private Realm realm;
//    private LayoutInflater inflater;
//
//    public RealmContactsAdapter(Context context)
//    {
//
//        this.context = context;
//    }
//
//    @Override
//    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
//    {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
//        return new CardViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position)
//    {
//
//        realm = Realm.getDefaultInstance();
//
//        final RoomResponse response = getItem(position);
//        final CardViewHolder holder = (CardViewHolder) viewHolder;
//
//        holder.textTitle.setText(response.getTitle());
//
////        if (response.getImageUrl() != null)
////        {
////            Glide.with(context)
////                    .load(response.getImageUrl().replace("https", "http"))
////                    .asBitmap()
////                    .fitCenter()
////                    .into(holder.imageBackground);
////        }
//
//        holder.card.setOnLongClickListener(v ->
//        {
//            RealmResults<RoomResponse> results = realm.where(RoomResponse.class).findAll();
//
//            RoomResponse b = results.get(position);
//            String title = b.getTitle();
//
//            realm.beginTransaction();
//
//            results.remove(position);
//            realm.commitTransaction();
//
//            notifyDataSetChanged();
//
//            Toast.makeText(context, title + " is removed from Realm", Toast.LENGTH_SHORT).show();
//            return false;
//        });
//
//        holder.card.setOnClickListener(v ->
//        {
//
//            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            View content = inflater.inflate(R.layout.item_contact, null);
//            final EditText editTitle = content.findViewById(R.id.contacts_tv_username);
//
//            editTitle.setText(response.getTitle());
//
//            AlertDialog.Builder builder = new AlertDialog.Builder(context);
//            builder.setView(content)
//                    .setTitle("Edit Book")
//                    .setPositiveButton(android.R.string.ok, (dialog, which) ->
//                    {
//
//                        RealmResults<RoomResponse> results = realm.where(RoomResponse.class).findAll();
//
//                        realm.beginTransaction();
//                        results.get(position).setTitle(editTitle.getText().toString());
//
//                        realm.commitTransaction();
//
//                        notifyDataSetChanged();
//                    })
//                    .setNegativeButton(android.R.string.cancel, (dialog, which) -> dialog.dismiss());
//            AlertDialog dialog = builder.create();
//            dialog.show();
//        });
//    }
//
//    public int getItemCount()
//    {
//
//        if (getRealmAdapter() != null)
//        {
//            return getRealmAdapter().getCount();
//        }
//        return 0;
//    }
//
//    private static class CardViewHolder extends RecyclerView.ViewHolder
//    {
//
//        RelativeLayout card;
//        TextView textTitle;
//
//        CardViewHolder(View itemView)
//        {
//            super(itemView);
//
//
//            textTitle = itemView.findViewById(R.id.contacts_tv_username);
//            card = itemView.findViewById(R.id.contacts_parent);
//
//        }
//    }
//}