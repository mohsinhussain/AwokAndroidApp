package com.awok.moshin.awok.Models;

/**
 * Created by moshin on 9/7/2015.
 */

public class Categories {

    String id;
    String name;
    String sort;
    String image;
    String depthLevel;


    public Categories() {
    }

    public Categories(String id, String name, String sort, String image, String depthLevel) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.sort = sort;
        this.depthLevel = depthLevel;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDepthLevel() {
        return depthLevel;
    }

    public void setDepthLevel(String depthLevel) {
        this.depthLevel = depthLevel;
    }
}
