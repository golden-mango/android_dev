package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

    private TextView mknownAsTextView;
    private TextView mingredientsTextView;
    private TextView moriginTextView;
    private TextView mdescriptionTextView;
    private Sandwich sandwich = null;
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

        mdescriptionTextView = (TextView) findViewById(R.id.description_tv);
        mingredientsTextView = (TextView) findViewById(R.id.ingredients_tv);
        moriginTextView = (TextView) findViewById(R.id.origin_tv);
        mknownAsTextView = (TextView) findViewById(R.id.also_known_tv);

        populateUI();

        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {

        if (sandwich.getAlsoKnownAs() == null) {
            mknownAsTextView.setText("no other names known");
        } else {
            int i = 0;
            for (String s : sandwich.getAlsoKnownAs()) {
                if (i == sandwich.getAlsoKnownAs().size() - 1) {
                    mknownAsTextView.append(s + ".");
                } else {
                    mknownAsTextView.append(s + ", ");
                }
                i++;
            }
        }
        moriginTextView.setText(sandwich.getPlaceOfOrigin());
        mdescriptionTextView.setText(sandwich.getDescription());

        if (sandwich.getIngredients() == null) {
            mingredientsTextView.setText("cannot find ingredients, look at the picture");
        } else {
            int i = 0;
            for (String s : sandwich.getIngredients()) {
                if (i == sandwich.getIngredients().size() - 1) {
                    mingredientsTextView.append(s + ".");
                } else {
                    mingredientsTextView.append(s + ", ");
                }
                i++;
            }
        }



    }
}
