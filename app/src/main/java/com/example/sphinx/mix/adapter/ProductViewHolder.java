package com.example.sphinx.mix.adapter;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;

import com.example.sphinx.mix.R;
import com.example.sphinx.mix.databinding.ItemProductBinding;
import com.example.sphinx.mix.entity.Product;


class ProductViewHolder extends RecyclerView.ViewHolder {
    private final ItemProductBinding binding;

    ProductViewHolder(ItemProductBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    void bind(Product product) {
        binding.setProduct(product);

        final GradientDrawable gradientDrawable = (GradientDrawable) ContextCompat.getDrawable(
            itemView.getContext(), R.drawable.bg_product);

        gradientDrawable.setColor(ContextCompat.getColor(
            itemView.getContext(), product.color));

        gradientDrawable.setSize(itemView.getWidth(), getDrawableHeight());

        gradientDrawable.mutate();

        binding.imgProduct.setBackground(gradientDrawable);
        binding.imgProduct.setImageResource(product.image);
    }

    private int getDrawableHeight() {
        final Context context = itemView.getContext();

        return getAdapterPosition() % 2 == 0
            ? context.getResources().getDimensionPixelOffset(R.dimen.product_regular_height)
            : context.getResources().getDimensionPixelOffset(R.dimen.product_large_height);
    }
}
