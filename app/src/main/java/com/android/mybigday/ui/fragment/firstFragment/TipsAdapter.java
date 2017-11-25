package com.android.mybigday.ui.fragment.firstFragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.mybigday.R;
import com.android.mybigday.data.model.TipsListView;
import com.android.mybigday.util.Log;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mariam on 11/23/2017.
 */

public class TipsAdapter  extends RecyclerView.Adapter<TipsAdapter.TipViewHolder> {


    protected ArrayList<TipsListView> mItems;
    protected LayoutInflater mInflater;
    private FirstFragment mContext;


    public TipsAdapter(FirstFragment context, ArrayList<TipsListView> data) {
        mInflater = LayoutInflater.from(context.getActivity());
        this.mContext = context;
        this.mItems = data;

    }

    @Override
    public TipViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = mInflater.inflate(R.layout.tip_list_item, viewGroup, false);
        final TipViewHolder holder = new TipViewHolder(view);
        return holder;

    }

    public void setItems(ArrayList<TipsListView> items) {
        mItems = items;
    }

    @Override
    public void onBindViewHolder(TipViewHolder viewHolder, int position) {
        final TipsListView tipsListView = mItems.get(position);
        viewHolder.setData(tipsListView,position);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void add(TipsListView item) {
        mItems.add(item);
        notifyItemInserted(mItems.size() - 1);
    }

    public void addAll(ArrayList<TipsListView> arrayList) {
        for (TipsListView listView : arrayList) {
            add(listView);
        }
        notifyDataSetChanged();

    }

    void resetAdapter(){
        this.mItems = new ArrayList<>();

    }


    private void setUpText(TextView txt, String s) {
        txt.setText(s);
    }

    class TipViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.number)
        TextView number;

        @BindView(R.id.tip)
        TextView tip;

        @BindView(R.id.image)
        ImageView image;

        int mPosition;


        TipViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        void setData(TipsListView data, final int position) {
            Log.i("data tips", ""+position);
            setUpText(this.tip,data.getTitle());
            setUpText(this.number, String.valueOf(position+1));
            Glide.with(mContext.getActivity()).load(data.getImage()).asBitmap().placeholder(R.drawable.logo).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(image);


        }

    }

}
