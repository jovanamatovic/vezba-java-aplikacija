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

    // kontejner u kojem se nalaze proizvodi dodati u korpu
    LinearLayout cartContainer;
    // sumirana cena svih proizvoda u korpi
    TextView totalSum;
    // dugme za potvrdjivanje kupovine
    Button btnConfirmOrder;

    // kreiranje mape za cenu proizvoda u korpi
    Map<Integer, Double> prices = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cartContainer = findViewById(R.id.cartContainer);
        totalSum = findViewById(R.id.totalSum);
        btnConfirmOrder = findViewById(R.id.btnConfirmOrder);

        // dodavanje slike i cene proizvoda u mapu
        prices.put(R.drawable.ball, 15.99);
        prices.put(R.drawable.ball2, 16.99);
        prices.put(R.drawable.shoes, 49.99);
        prices.put(R.drawable.shoes2, 55.99);
        prices.put(R.drawable.shorts, 19.99);
        prices.put(R.drawable.shorts2, 25.99);

        // automatsko refreshovanje kartice svaki put kada se doda novi proizvod ili ukloni
        refreshCart();

        // posle klika, samo obrise proizvode iz korpe i vrati na pocetnu stranu
        btnConfirmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.cartItems.clear();
                finish();
            }
        });
    }

    // funkcija za refreshovanje korpe svaki put kada se nesto doda ili ukloni iz nje
    private void refreshCart() {

        // ovo se koristi da bi mogla lista da se rebuilduje opet na osnovu stanja korpe
        cartContainer.removeAllViews();

        // ova vrednost prati ukupnu cenu svih proizvoda u korpi, i updatuje se automatski kada se nesto doda ili ukloni
        double total = 0.0;

        // for petlja koja prolazi kroz svaki proizvod u korpi, svaki proizvod ima svoj imageId**
        for (int i = 0; i < MainActivity.cartItems.size(); i++) {
            int imageId = MainActivity.cartItems.get(i);

            // proverava cenu svakog proizvoda u korpi na osnovu imageId
            // getOrDefaul samo osigurava da ukoliko se vrednost ne nalazi u mapi, cena ce mu biti 0.0 (to se u ovom slucaju nece desiti nikad)
            total += prices.getOrDefault(imageId, 0.0);

            // kreira novi view na osnovu cart_item.xml u /res/layout
            View cartItem = LayoutInflater.from(this).inflate(R.layout.cart_item, cartContainer, false);
            // reference iz cart_item.xml
            ImageView itemImage = cartItem.findViewById(R.id.itemImage);
            TextView itemPrice = cartItem.findViewById(R.id.itemPrice);
            Button btnRemove = cartItem.findViewById(R.id.btnRemove);

            // postavlja image i cenu za svaki proizvod
            itemImage.setImageResource(imageId);
            itemPrice.setText(String.format("$%.2f", prices.getOrDefault(imageId, 0.0)));


            int finalI = i;
            // klikom se brise proizvod iz korpe i automatski poziva refreshCart() funkciju da se i na ekranu prikaze promena
            btnRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity.cartItems.remove(finalI);
                    refreshCart();
                }
            });

            // ovde se dodaje cart_item layout na ekran
            cartContainer.addView(cartItem);
        }

        // ovde se totalSum vrednost updatuje na osnovu cene proizvoda u korpi
        totalSum.setText(String.format("Total: $%.2f", total));
    }
}