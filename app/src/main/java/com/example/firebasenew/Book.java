package com.example.firebasenew;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Book {
    private String id;
    private String bookName;
    private int price;

    public Book(){

    }
    public Book(String id, String bookName, int price) {
        this.id = id;
        this.bookName = bookName;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public String getBookName() {
        return bookName;
    }

    public int getPrice() {
        return price;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}

