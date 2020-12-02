package com.example.recipe_hfyst1;

public class Recipe {

    private int _id;
    private String name;
    private String instructions;
    private String ingredients;
    private int rating = 0;

    public Recipe(){}

    public Recipe(int id, String name, String instructions, int rating){
        this._id = id;
        this.name = name;
        this.instructions = instructions;
        this.rating = rating;
    }

    public Recipe(String name, String instructions){
        this.name = name;
        this.instructions = instructions;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

}
