package com.example.woowa_recamping;

public class Item {
    String title;
    String contents;
    int price;
    int id;
    double distance;

    public Item(String title, String contents, int price, int id, double distance) {
        this.title = title;
        this.contents = contents;
        this.price = price;
        this.id = id;
        this.distance = distance;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getId() { return id;}

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
