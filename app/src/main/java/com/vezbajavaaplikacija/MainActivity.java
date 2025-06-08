package com.vezbajavaaplikacija;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    LinearLayout itemContainer;
    Button btnGoToCart;
    public static ArrayList<Integer> cartItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        itemContainer = findViewById(R.id.itemContainer);
        btnGoToCart = findViewById(R.id.btnGoToCart);

        int[] images = {
                R.drawable.ball,
                R.drawable.ball2,
                R.drawable.shoes,
                R.drawable.shoes2,
                R.drawable.shorts,
                R.drawable.shorts2
        };

        Map<Integer, String> prices = new HashMap<>();
        prices.put(R.drawable.ball, "$15.99");
        prices.put(R.drawable.ball2, "$16.99");
        prices.put(R.drawable.shoes, "$49.99");
        prices.put(R.drawable.shoes2, "$55.99");
        prices.put(R.drawable.shorts, "$19.99");
        prices.put(R.drawable.shorts2, "$25.99");

        for (int imageId : images) {
            addItemToLayout(imageId, prices.get(imageId));
        }

        btnGoToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CartActivity.class));
            }
        });
    }

    private void addItemToLayout(int imageId, String price) {

        View itemView = LayoutInflater.from(this).inflate(R.layout.main_item, itemContainer, false);
        ImageView itemImage = itemView.findViewById(R.id.itemImage);
        Button addToCart = itemView.findViewById(R.id.btnAddToCart);
        TextView itemPrice = itemView.findViewById(R.id.itemPrice);

        itemImage.setImageResource(imageId);
        itemPrice.setText(price);

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartItems.add(imageId);
            }
        });

        itemContainer.addView(itemView);
    }
}