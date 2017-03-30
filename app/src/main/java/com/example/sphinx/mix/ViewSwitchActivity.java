package com.example.sphinx.mix;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.sphinx.mix.adapter.OnItemSelectedListener;
import com.example.sphinx.mix.adapter.ProductAdapter;
import com.example.sphinx.mix.adapter.ProductItemPaddingDecoration;
import com.example.sphinx.mix.databinding.ActivityViewSwitchBinding;
import com.example.sphinx.mix.entity.Product;
import com.example.sphinx.mix.view.OrderDialogFragment;

import java.util.List;

public class ViewSwitchActivity extends AppCompatActivity {
    private List<Product> mProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityViewSwitchBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_view_switch);
        mProducts = Product.createFakeProducts();

        initToolbar();
        initRecycler(binding.productsRecycler);
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initRecycler(RecyclerView productsRecycler) {
        productsRecycler.setHasFixedSize(true);

        productsRecycler.setAdapter(new ProductAdapter(mProducts));
        productsRecycler.addItemDecoration(new ProductItemPaddingDecoration(this));
        productsRecycler.addOnItemTouchListener(new OnItemSelectedListener(this) {
            @Override
            public void onItemSelected(RecyclerView.ViewHolder holder, int position) {
                //弹窗
                OrderDialogFragment.newInstance(mProducts.get(position)).show(getSupportFragmentManager(), null);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }
}
