package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = null;
        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }


    TextView tv_aka = null;
    TextView tv_origin = null;
    TextView tv_desc = null;
    TextView tv_ing = null;
    private void populateUI(Sandwich sw) {
        tv_aka= (TextView) findViewById(R.id.also_known_tv);
        tv_origin= (TextView) findViewById(R.id.place_of_origin_tv);
        tv_desc= (TextView) findViewById(R.id.description_tv);
        tv_ing= (TextView) findViewById(R.id.ingredients_tv);
        String[] stringArray = sw.getAlsoKnownAs().toArray(new String[0]);
        for(String str : stringArray){
            tv_aka.append(str+"\n");
        }

        tv_origin.setText(sw.getPlaceOfOrigin());

        stringArray = sw.getIngredients().toArray(new String[0]);
        for(String str : stringArray){
            tv_ing.append(str+"\n");
        }

        tv_desc.setText(sw.getDescription());
    }
}
