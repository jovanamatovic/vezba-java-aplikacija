package com.vezbajavaaplikacija;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

public class CartActivity extends AppCompatActivity {

    LinearLayout cartContainer;
    TextView totalSum;
    Button btnConfirmOrder;

    Map<Integer, Double> prices = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cartContainer = findViewById(R.id.cartContainer);
        totalSum = findViewById(R.id.totalSum);
        btnConfirmOrder = findViewById(R.id.btnConfirmOrder);

        prices.put(R.drawable.ball, 15.99);
        prices.put(R.drawable.ball2, 16.99);
        prices.put(R.drawable.shoes, 49.99);
        prices.put(R.drawable.shoes2, 55.99);
        prices.put(R.drawable.shorts, 19.99);
        prices.put(R.drawable.shorts2, 25.99);

        refreshCart();

        btnConfirmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.cartItems.clear();
                finish();
            }
        });
    }

    private void refreshCart() {

        cartContainer.removeAllViews();

        double total = 0.0;

        for (int i = 0; i < MainActivity.cartItems.size(); i++) {
            int imageId = MainActivity.cartItems.get(i);

            total += prices.getOrDefault(imageId, 0.0);

            View cartItem = LayoutInflater.from(this).inflate(R.layout.cart_item, cartContainer, false);
            ImageView itemImage = cartItem.findViewById(R.id.itemImage);
            TextView itemPrice = cartItem.findViewById(R.id.itemPrice);
            Button btnRemove = cartItem.findViewById(R.id.btnRemove);

            itemImage.setImageResource(imageId);
            itemPrice.setText(String.format("$%.2f", prices.getOrDefault(imageId, 0.0)));


            int finalI = i;
            btnRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity.cartItems.remove(finalI);
                    refreshCart();
                }
            });

            cartContainer.addView(cartItem);
        }

        totalSum.setText(String.format("Total: $%.2f", total));
    }
}