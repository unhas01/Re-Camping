package com.example.woowa_recamping;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> implements RecyclerViewInterface {
    private final RecyclerViewInterface recyclerViewInterface;

    Context context;
    ArrayList<Item> items = new ArrayList<>();

    public ItemAdapter(Context context, ArrayList<Item> items, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.items = items;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.item, viewGroup, false);

        return new ViewHolder(itemView, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item = items.get(position);
        holder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onItemClick(int position) {

    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        TextView textView2;
        TextView textView3;
        ImageView image;
        TextView textView4;

        public ViewHolder(View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            textView = itemView.findViewById(R.id.item_title);
            textView2 = itemView.findViewById(R.id.item_contents);
            textView3 = itemView.findViewById(R.id.item_price);
            image = itemView.findViewById(R.id.item_imageView);
            textView4 = itemView.findViewById(R.id.item_distance);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (recyclerViewInterface != null) {
                        int pos = getAdapterPosition();

                        if (pos != RecyclerView.NO_POSITION) {
                            recyclerViewInterface.onItemClick(pos);
                        }
                    }
                }
            });
        }

        public void setItem(Item item) {
            textView.setText(item.getTitle());
            textView2.setText(item.getContents());
            textView3.setText("Prices: "+ String.valueOf(item.getPrice()) + "$");
            image.setImageResource(item.getId());
            textView4.setText("Distance: " + String.valueOf(item.getDistance()) + "km");
        }
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public Item getItem(int position) {
        return items.get(position);
    }

    public void setItem(int position, Item item) {
        items.set(position, item);
    }
}
