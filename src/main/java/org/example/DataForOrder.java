package org.example;

import java.util.List;

public class DataForOrder {
    private List<String> ingredients;

    public DataForOrder(List<String> ingredients) {
        this.ingredients = ingredients;
    }
    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }
}
