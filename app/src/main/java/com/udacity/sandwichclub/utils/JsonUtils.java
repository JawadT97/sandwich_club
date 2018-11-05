package com.udacity.sandwichclub.utils;
import com.udacity.sandwichclub.model.Sandwich;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Arrays;
import java.util.List;
public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) throws JSONException {
        Sandwich sw = new Sandwich() ;

        JSONObject sandwichJSON=new JSONObject(json);
        JSONObject name = sandwichJSON.getJSONObject("name");

        String mainN = name.getString("mainName");
        sw.setMainName(mainN);

        String arr = name.getString("alsoKnownAs");
        arr = arr.substring(1,arr.length()-1);
        arr = arr.replaceAll("\"","");
        String [] AKA =arr.split(",");
        List<String> alsoKnownAs = Arrays.asList(AKA);
        sw.setAlsoKnownAs(alsoKnownAs);

        String placeOfOrigin = sandwichJSON.getString("placeOfOrigin");
        sw.setPlaceOfOrigin(placeOfOrigin);

        String description = sandwichJSON.getString("description");
        sw.setDescription(description);

        String imgURL = sandwichJSON.getString("image");
        sw.setImage(imgURL);

        arr = sandwichJSON.getString("ingredients");
        arr = arr.substring(1,arr.length()-1);
        arr = arr.replaceAll("\"","");
        String [] ingr =arr.split(",");
        List<String> ingredients = Arrays.asList(ingr);
        sw.setIngredients(ingredients);

        return sw;
    }
}
