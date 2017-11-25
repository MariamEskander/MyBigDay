package com.android.mybigday.ui.fragment.firstFragment;

import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.mybigday.R;
import com.android.mybigday.data.model.Plan;
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

public class PlansAdapter extends RecyclerView.Adapter<PlansAdapter.viewHolder> {


    protected ArrayList<Plan> mItems;
    protected LayoutInflater mInflater;
    private FirstFragment mContext;


    public PlansAdapter(FirstFragment context, ArrayList<Plan> data) {
        mInflater = LayoutInflater.from(context.getActivity());
        this.mContext = context;
        this.mItems = data;

    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = mInflater.inflate(R.layout.plan_list_item, viewGroup, false);
        final viewHolder holder = new viewHolder(view);
        return holder;

    }

    public void setItems(ArrayList<Plan> items) {
        mItems = items;
    }

    @Override
    public void onBindViewHolder(viewHolder viewHolder, int position) {
        final Plan plan = mItems.get(position);
        viewHolder.setData(plan,position);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void add(Plan item) {
        mItems.add(item);
        notifyItemInserted(mItems.size() - 1);
    }

    public void addAll(ArrayList<Plan> arrayList) {
        for (Plan listView : arrayList) {
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

    class viewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.name)
        TextView name;


        @BindView(R.id.people)
        TextView people;

        @BindView(R.id.txt)
        TextView txt;

        @BindView(R.id.cost)
        TextView cost;

        @BindView(R.id.costText)
        TextView costText;

        @BindView(R.id.more)
        ImageView more;

        @BindView(R.id.place)
        TextView place;


        int mPosition;


        viewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        void setData(Plan data, final int position) {
            Log.i("data plan", ""+position);
            if (data.getCost().equals("0")){
                setUpText(cost,"Cost is not decided yet");
                costText.setVisibility(View.GONE);
                txt.setVisibility(View.GONE);
            }else {
                costText.setVisibility(View.VISIBLE);
                setUpText(costText,"LE");
                txt.setVisibility(View.VISIBLE);
                setUpText(cost,data.getCost());
            }

            if (data.getPlace().equals("0")){
                setUpText(place,"Place is not decided yet");
            }else {
                setUpText(place,data.getPlace());
            }

            setUpText(people, data.getGuests());
            setUpText(name, data.getName());

            more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popup = new PopupMenu(mContext.getActivity(), v);
                    popup.getMenuInflater().inflate(R.menu.popup,
                            popup.getMenu());
                    popup.show();
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.edit:
                                    Toast.makeText(mContext.getActivity(),"Edit Plan",Toast.LENGTH_LONG).show();
                                    break;
                                case R.id.delete:
                                    Toast.makeText(mContext.getActivity(),"Delete Plan",Toast.LENGTH_LONG).show();
                                    break;
                            }
                            return true;
                        }
                    });

                }
            });
        }
    }
}
