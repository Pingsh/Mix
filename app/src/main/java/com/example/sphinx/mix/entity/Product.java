package com.example.sphinx.mix.entity;

import com.example.sphinx.mix.R;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;


public class Product implements Serializable {
    public String name;
    public String price;
    public final int image;
    public final int color;

    private Product(String name, String price, int image, int color) {
        this.name = name;
        this.price = price;
        this.image = image;
        this.color = color;
        check();
    }

    private void check() {
        if (name == null || name.length() == 0) {
            name = "尽请期待";
        }
        if (price == null || price.length() == 0) {
            price = "¥ ?";
        }
    }

    public static List<Product> createFakeProducts() {
        return Arrays.asList(
                new Product("球鞋", "¥ 49", R.drawable.img_sneaker, R.color.product_yellow),
                new Product("不知道啥鞋", "¥ 579", R.drawable.img_sandal, R.color.product_green),
                new Product("高跟鞋", "¥ 899", R.drawable.img_shoe, R.color.product_blue),
                new Product("滑冰鞋", "¥ 29", R.drawable.img_ice_skate, R.color.product_purple),
                new Product("球鞋", null, R.drawable.img_sneaker, R.color.red_dark)

        );
    }
}
