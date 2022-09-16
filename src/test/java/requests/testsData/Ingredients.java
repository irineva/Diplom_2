package requests.testsData;

import java.util.ArrayList;
import java.util.List;

public class Ingredients {
    private List<String> ingredients = new ArrayList<>();


    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredient) {
        this.ingredients.add(ingredient);
    }
}


