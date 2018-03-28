package com.example.tomasz123456.shoppinglist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ShoppingItemsAdapter extends RecyclerView.Adapter<ShoppingItemsAdapter.MyViewHolder> {

    private List<ShoppingItem> shoppingItemsList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, price;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            price = (TextView) view.findViewById(R.id.price);
        }
    }


    public ShoppingItemsAdapter(List<ShoppingItem> shoppingItemsList) {
        this.shoppingItemsList = shoppingItemsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shopping_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ShoppingItem shoppingItem = shoppingItemsList.get(position);
        holder.name.setText(shoppingItem.getName());
        holder.price.setText(Integer.toString( shoppingItem.getCount()));
    }

    @Override
    public int getItemCount() {
        return shoppingItemsList.size();
    }
}