package com.example.fantuan.andrioddemos.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fantuan.andrioddemos.R;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    // 添加点击事件
    // 1.添加接口
    public interface OnItemClickListener{
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    public OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }



    private List<String> mDataSet;

    public MyAdapter(List<String> myDateSet) {
        mDataSet = myDateSet;
    }
    // 删除一条数据，有动画效果
    public void delateData(int position){
        mDataSet.remove(position);
        notifyItemRemoved(position);
    }
    // 添加一条数据，有动画效果
    public void addData(int position) {
        if (position >= 1) {
            mDataSet.add(mDataSet.get(position));
            notifyItemInserted(position);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.my_text_view, viewGroup, false);
        MyViewHolder viewHolder = new MyViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.textView.setText(mDataSet.get(i));

        setUpItemEvent(myViewHolder);
    }

    private void setUpItemEvent(MyViewHolder holder) {
        if (listener != null) {
            final int layoutPosition = holder.getLayoutPosition();
            holder.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(v,layoutPosition);
                }
            });

            holder.textView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.onItemLongClick(v, layoutPosition);
                    return false;
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.item_tv);
        }
    }
}
