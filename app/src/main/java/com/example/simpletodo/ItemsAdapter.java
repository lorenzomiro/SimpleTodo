package com.example.simpletodo;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {

   public interface onClickListener {

        void onItemClicked(int position);

    }

    public interface onLongClickListener {

        void onItemLongClicked(int position);

    }

    List<String> items;

    onLongClickListener longClickListener;

    onClickListener clickListener;

    public ItemsAdapter(List<String> items, onLongClickListener longClickListener, onClickListener clickListener) {

        this.items = items;

        this.longClickListener = longClickListener;

        this.clickListener = clickListener;

    }

    //default constructor for ItemsAdapter class

    public ItemsAdapter(List<String> items, onLongClickListener onLongClickListener) {

        this.items = items;

    }

    //create view holder

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //use layout inflator to inflate a view

        View todoView = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);

        //wrap it in a view holder, then return it

        return new ViewHolder(todoView);

    }

    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        //get item @ current position

        String item = items.get(position);

        //bind item to specified view holder

        holder.bind(item);

    }

    //tells RV the list lengths
    @Override
    public int getItemCount() {

        return items.size();

    }


    //container to provide easy access to views that represent each row of the list
    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvItem;

        //update view

        public ViewHolder(@NonNull View itemView) {

            super(itemView);

            tvItem = itemView.findViewById(android.R.id.text1);

        }

        //update view inside of viewholder

        public void bind(String item) {

            tvItem.setText(item);

            tvItem.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {

                    clickListener.onItemClicked(getAdapterPosition());

                }

            });

            tvItem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    //let listener know which position was long clicked

                    longClickListener.onItemLongClicked(getAdapterPosition());

                    return true;
                }
            });
        }

    }

}


