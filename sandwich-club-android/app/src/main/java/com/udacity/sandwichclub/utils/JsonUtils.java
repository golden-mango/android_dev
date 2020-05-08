package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class JsonUtils {

    /*
    example json string
    {
    "name":{"mainName":"Ham and cheese
            sandwich","alsoKnownAs":[]},
    "placeOfOrigin":"",
    "description":"A ham and cheese
            sandwich is a common type of sandwich. It is made by putting cheese and sliced ham
            between two slices of bread. The bread is sometimes buttered and/or toasted. Vegetables
            like lettuce, tomato, onion or pickle slices can also be included. Various kinds of
            mustard and mayonnaise are also
            common.",
    "image":"https://upload.wikimedia.org/wikipedia/commons/thumb/5/50/Grilled_ham_and_cheese_014.JPG/800px-Grilled_ham_and_cheese_014.JPG",
    "ingredients":["Sliced bread","Cheese","Ham"]}
     */


    // Added throws JSON exception, otherwise need try-catch around JSONObject declaration
    public static Sandwich parseSandwichJson(String json) throws JSONException {



        //json fields that we will get
        final String NAME = "name";
        final String MAIN_NAME = "mainName";
        final String ALSO_KNOWN_As_Arr = "alsoKnownAs";
        final String ORIGIN = "placeOfOrigin";
        final String DESCRIPTION = "description";
        final String IMAGE = "image";
        final String INGREDIENTS_ARR = "ingredients";

        JSONObject sandwichJSON = new JSONObject(json);

        JSONObject sandwichName = sandwichJSON.getJSONObject(NAME);

        List<String> otherNames = new LinkedList<String>();
        JSONArray alsoKnownAsJSONArr = sandwichName.getJSONArray(ALSO_KNOWN_As_Arr);
        for (int i = 0; i < alsoKnownAsJSONArr.length(); i++) {
            otherNames.add(alsoKnownAsJSONArr.getString(i));
        }

        List<String> ingredients = new LinkedList<String>();
        JSONArray ingredientArr = sandwichJSON.getJSONArray(INGREDIENTS_ARR);
        for (int i = 0; i < ingredientArr.length(); i++) {
            ingredients.add(ingredientArr.getString(i));
        }

        return new Sandwich(
                sandwichName.getString(MAIN_NAME),
                otherNames,
                sandwichJSON.getString(ORIGIN),
                sandwichJSON.getString(DESCRIPTION),
                sandwichJSON.getString(IMAGE),
                ingredients
        );

    }
}
