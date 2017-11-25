package com.android.mybigday.ui.fragment.firstFragment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.android.mybigday.MyBigDayApplication;
import com.android.mybigday.R;
import com.android.mybigday.data.database.DataBaseUtil;
import com.android.mybigday.data.database.MyBigDayDbHelper;
import com.android.mybigday.data.model.ToDoList;
import com.android.mybigday.data.model.ToDoListView;
import com.android.mybigday.util.GlobalVariables;
import com.android.mybigday.util.Log;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TodosAdapter extends RecyclerView.Adapter<TodosAdapter.TodoViewHolder> {


    protected ArrayList<ToDoListView> mItems;
    protected LayoutInflater mInflater;
    private FirstFragment mContext;


    public TodosAdapter(FirstFragment context, ArrayList<ToDoListView> data) {
        mInflater = LayoutInflater.from(context.getActivity());
        this.mContext = context;
        this.mItems = data;

    }

    @Override
    public TodoViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = mInflater.inflate(R.layout.todo_list_item, viewGroup, false);
        final TodoViewHolder holder = new TodoViewHolder(view);
        return holder;

    }

    public void setItems(ArrayList<ToDoListView> items) {
        mItems = items;
    }

    @Override
    public void onBindViewHolder(TodoViewHolder viewHolder, int position) {
        final ToDoListView toDoListView = mItems.get(position);
        viewHolder.setData(toDoListView, position);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void add(ToDoListView item) {
        mItems.add(item);
        notifyItemInserted(mItems.size() - 1);
    }

    public void addAll(ArrayList<ToDoListView> arrayList) {
        for (ToDoListView listView : arrayList) {
            add(listView);
        }
        notifyDataSetChanged();

    }

    void resetAdapter() {
        this.mItems = new ArrayList<>();

    }


    private void setUpText(TextView txt, String s) {
        txt.setText(s);
    }

    class TodoViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.todo)
        TextView todo;

        @BindView(R.id.checkbox)
        CheckBox checkbox;


        int mPosition;


        TodoViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        void setData(final ToDoListView data, final int position) {
            Log.i("data todos", ""+position);
            setUpText(this.todo, data.getTitle());
            if (data.isCheck())
                checkbox.setChecked(true);
            else
                checkbox.setChecked(false);

                checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, final boolean isChecked) {
                        final ExecutorService executor = Executors.newFixedThreadPool(5);
                        executor.execute(new Runnable() {
                            @Override
                            public void run() {
                                MyBigDayDbHelper db = MyBigDayDbHelper.GetFor(MyBigDayApplication.getAppContext());
                                db.open();
                                if (isChecked) {
                                    DataBaseUtil.insertOrUpdateTodo(db, new ToDoList(data.getId(), data.getTitle()));
                                } else {
                                    DataBaseUtil.deleteTodo(db, data.getId());

                                }
                                db.close();
                            }
                        });

                        executor.shutdown();
                    }
                });

        }

    }

}
