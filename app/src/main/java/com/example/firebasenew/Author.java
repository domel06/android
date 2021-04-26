package com.example.firebasenew;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Author {
    private String authorId;
    private String authorName;
    private String authorSubject;
    public Author(){

    }

    public Author(String authorId, String authorName, String authorSubject) {
        this.authorId = authorId;
        this.authorName = authorName;
        this.authorSubject = authorSubject;
    }

    public String getAuthorId() {
        return authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getAuthorSubject() {
        return authorSubject;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public void setAuthorSubject(String authorSubject) {
        this.authorSubject = authorSubject;
    }
}
