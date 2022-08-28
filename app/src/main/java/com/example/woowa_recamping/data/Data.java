package com.example.woowa_recamping.data;

public class Data {
    private String title;
    private String content;
    private int price;
    private int image;
    public Data(String title, String content, int price) {
        this.title = title;
        this.content = content;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public int getPrice() {
        return price;
    }

    public int getImage() {
        return image;
    }
}
