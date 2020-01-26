package com.ngra.system137.models;


public class ModelSpinnerItem {

//    @SerializedName("id")
    int id;

//    @SerializedName("title")
    String title;

    public ModelSpinnerItem() {
    }

    public ModelSpinnerItem(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
