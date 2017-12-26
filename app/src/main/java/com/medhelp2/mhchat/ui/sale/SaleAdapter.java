package com.medhelp2.mhchat.ui.sale;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.medhelp2.mhchat.R;
import com.medhelp2.mhchat.data.model.SaleResponse;
import com.medhelp2.mhchat.ui.base.BaseViewHolder;
import com.medhelp2.mhchat.utils.view.ImageConverter;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Maybe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class SaleAdapter extends RecyclerView.Adapter<BaseViewHolder>
{
    @Inject
    SalePresenter presenter;

    private static final int VIEW_TYPE_NORMAL = 11;

    private Callback callback;
    private List<SaleResponse> response;

    public SaleAdapter(List<SaleResponse> response)
    {
        this.response = response;
    }

    public void setCallback(Callback callback)
    {
        this.callback = callback;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position)
    {
        holder.onBind(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        switch (viewType)
        {
            case VIEW_TYPE_NORMAL:
                return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sale, parent, false));
        }
        return new EmptyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_error_download, parent, false));
    }

    @Override
    public int getItemViewType(int position)
    {
        if (response != null && response.size() > 0)
        {
            return VIEW_TYPE_NORMAL;
        } else
        {
            return 0;
        }
    }

    @Override
    public int getItemCount()
    {
        if (response != null && response.size() > 0)
        {
            return response.size();
        } else
        {
            return 0;
        }
    }

    void addItems(List<SaleResponse> repoList)
    {
        response.clear();
        response.addAll(repoList);
        notifyDataSetChanged();
    }

    interface Callback
    {
        void onEmptyViewAddContactClick();
    }

    class ViewHolder extends BaseViewHolder
    {
        @BindView(R.id.sale_item_image)
        ImageView saleImage;

        @BindView(R.id.sale_item_description)
        TextView saleDescription;

        ViewHolder(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        protected void clear()
        {
            saleImage.setImageDrawable(null);
            saleDescription.setText("");
        }

        public void onBind(int position)
        {
            Timber.d(response.get(position).getSaleDescription());
            super.onBind(position);
            final SaleResponse repo = response.get(position);
            if (repo != null)
            {
                if (repo.getSaleImage() != null)
                {
                    Maybe<String> flowable = Maybe.just(repo.getSaleImage());

                    CompositeDisposable disposable = new CompositeDisposable();
                    disposable.add(flowable
                            .subscribeOn(Schedulers.computation())
                            .map(ImageConverter::convertBase64StringToBitmap)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(bitmap ->
                                            saleImage.setImageBitmap(bitmap)
                                    , throwable ->
                                    {
                                        Picasso.with(saleImage.getContext())
                                                .load(R.drawable.holder_sale)
                                                .into(saleImage);
                                        Timber.e("Ошибка загрузки изображения: " + throwable.getMessage());
                                    }
                            ));
                }

                if (repo.getSaleDescription() != null)
                {
                    saleDescription.setText(repo.getSaleDescription());
                }
            }
        }
    }

    class EmptyViewHolder extends BaseViewHolder
    {
//        @BindView(R.id.empty_image_add_contact)
//        ImageButton btnAddContact;
//
//        @BindView(R.id.empty_tv_add_contact)
//        TextView tvInfoMessage;

        EmptyViewHolder(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        protected void clear()
        {
        }

//        @OnClick(R.id.err_btn_retry)
//        void onClickAddContact()
//        {
//            if (callback != null)
//                callback.onEmptyViewAddContactClick();
//        }
    }
}
