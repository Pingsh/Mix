package com.example.sphinx.mix.adapter;

import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sphinx.mix.R;

import java.util.List;

/**
 * 简单的RecyclerViewAdapter
 */
public class ListRecyclerAdapter extends Adapter<ListRecyclerAdapter.DefineViewHolder> {

    private List<String> list;

    public ListRecyclerAdapter(List<String> list) {
        this.list = list;
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public void onBindViewHolder(DefineViewHolder viewHolder, int position) {
        viewHolder.setData(list.get(position));
    }

    @Override
    public DefineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_rv, parent, false);
        return new DefineViewHolder(view);
    }

    static class DefineViewHolder extends ViewHolder {

        TextView tvTitle;

        public DefineViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_item);
        }

        public void setData(String data) {
            tvTitle.setText(data);
        }

    }

}
