package com.example.sphinx.mix.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;


import com.example.sphinx.mix.databinding.ItemProductBinding;
import com.example.sphinx.mix.entity.Product;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductViewHolder> {
    private final List<Product> products;

    public ProductAdapter(List<Product> products) {
        this.products = products;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final ItemProductBinding productBinding = ItemProductBinding.inflate(
            LayoutInflater.from(parent.getContext()), parent, false);

        return new ProductViewHolder(productBinding);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        holder.bind(products.get(position));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}
