package com.cs3714.sojo.proj;

/**
 * Created by SOJO on 8/8/15.
 */
public class Ingredient {

    private int id;
    private String name;
    private String type;

    public Ingredient() {
    }

    public Ingredient(String name, String type) {
        super();
        this.name = name;
        this.type = type;
    }

    //getters & setters
    @Override
    public String toString() {
        return "Book [id=" + id + ", name=" + name
                + ", type=" + type + "]";
    }

    public String getName(){
        return name;
    }
    public String getType(){
        return type;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setType(String type){
        this.type = type;
    }
    public int getID(){
        return id;
    }
    public void setID(int i){
        id = i;
    }
}
