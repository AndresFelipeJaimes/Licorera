package com.licorera;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class DetailedActivity extends AppCompatActivity {
    ImageView detailedImg;
    TextView price,rating,description;
    Button addToCart;
    ImageView addItem,removeItem;
    Toolbar toolbar;
    ViewAllModel viewAllModel =null;
    @Override
    protected void onCreate(Bundle savedInstaceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Object object = getIntent().getSerializableExtra("detail");
        if(object instanceof ViewAllModel){
            viewAllModel =(ViewAllModel) object;
        }


        detailedImg = findViewById(R.id.detailed_img);
        addItem = findViewById(R.id.add_item);
        removeItem = findViewById(R.id.remove_item);
        price = findViewById(R.id.detailed_price);
        description = findViewById(R.id.detailed_desc);
        if(viewAllModel!=null){
            Glide.with(getApplicationContext()),load(viewAllModel.getImg_url()).into(detailedImg);
            rating.setText(viewAllModel.getRating());
            description.setText(viewAllModel.getDescription());
            price.setText("price"+viewAllModel.getPrice());

        }
        addToCart = findViewById(R.id.add_to_cart_button);
    }
}
