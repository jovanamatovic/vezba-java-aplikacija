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

    // kontejner za sve proizvode na main stranici
    LinearLayout itemContainer;
    // dugme za prelaz na stranicu za prikaz korpe
    Button btnGoToCart;
    // array lista svih proizvoda za korpu
    public static ArrayList<Integer> cartItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        itemContainer = findViewById(R.id.itemContainer);
        btnGoToCart = findViewById(R.id.btnGoToCart);

        // slike za sve proizvode u aplikaciji (lopta, patike i sorc)
        // nalaze se u /res/drawable
        int[] images = {
                R.drawable.ball,
                R.drawable.ball2,
                R.drawable.shoes,
                R.drawable.shoes2,
                R.drawable.shorts,
                R.drawable.shorts2
        };

        // hash mapa za cenu svakog proizvoda pojedinacno
        Map<Integer, String> prices = new HashMap<>();
        // dodavanje cene za svaki proizvod
        prices.put(R.drawable.ball, "$15.99");
        prices.put(R.drawable.ball2, "$16.99");
        prices.put(R.drawable.shoes, "$49.99");
        prices.put(R.drawable.shoes2, "$55.99");
        prices.put(R.drawable.shorts, "$19.99");
        prices.put(R.drawable.shorts2, "$25.99");

        // for petlja koja iterira kroz svaki imageId u images listi i za svaki imageId uzima odredjenu cenu svakog proizvoda
        for (int imageId : images) {
            // poziva addItemToLayout funkciju koja prikazuje proizvode na main stranici
            addItemToLayout(imageId, prices.get(imageId));
        }

        // klikom na ovaj button se prelazi na korpa stranicu uz pomoc ** startActivity i Intent**
        btnGoToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CartActivity.class));
            }
        });
    }

    // funkcija koja prikazuje proizvode na stranici
    private void addItemToLayout(int imageId, String price) {

        // ovde se **inflatuje** main_item.xml
        // to je u prevodu custom item layout kojim se lakse dizajniraju same kartice za proizvode na stranici
        // nalazi se u /res/layout
        View itemView = LayoutInflater.from(this).inflate(R.layout.main_item, itemContainer, false);
        ImageView itemImage = itemView.findViewById(R.id.itemImage);
        Button addToCart = itemView.findViewById(R.id.btnAddToCart);
        TextView itemPrice = itemView.findViewById(R.id.itemPrice);

        // postavlja sliku i cenu za svaki proizvod
        itemImage.setImageResource(imageId);
        itemPrice.setText(price);

        // kada se ovo dugme klikne, imageId kliknutog proizvoda se dodaje u globalnu listu **cartItems**, uz pomoc koje se nakon toga prikazuju na korpa stranici
        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartItems.add(imageId);
            }
        });

        // ovde se doda taj custom main_item.xml u activity_main.xml
        itemContainer.addView(itemView);
    }
}