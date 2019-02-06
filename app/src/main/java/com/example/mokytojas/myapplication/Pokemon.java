package com.example.mokytojas.myapplication;

public class Pokemon {
    private int id;
    private String data;
    private String name;
    private double weight;
    private String cp;
    private String abilities;
    private String type;

    //skirtas kliento pusei
    public Pokemon(String name, double weight, String cp, String abilities, String type) {
        this.name = name;
        this.weight = weight;
        this.cp = cp;
        this.abilities = abilities;
        this.type = type;
    }

    //skirtas db
    public Pokemon(int id, String data, String name, double weight, String cp, String abilities, String type) {
        this.id = id;
        this.data = data;
        this.name = name;
        this.weight = weight;
        this.cp = cp;
        this.abilities = abilities;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getData() {
        return data;
    }

    public String getName() {
        return name;
    }

    public double getWeight() {
        return weight;
    }

    public String getCp() {
        return cp;
    }

    public String getAbilities() {
        return abilities;
    }

    public String getType() {
        return type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public void setAbilities(String abilities) {
        this.abilities = abilities;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Pokemon{" +
                "name='" + name + '\'' +
                ", weight=" + String.valueOf(weight) +
                ", cp='" + cp + '\'' +
                ", abilities='" + abilities + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

}
