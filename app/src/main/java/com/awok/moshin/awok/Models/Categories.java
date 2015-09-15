package com.awok.moshin.awok.Models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by moshin on 9/7/2015.
 */

public class Categories implements Serializable {

    int id;
    String name;
    String image;
    int parentId;
    int depthLevel;


    public Categories() {
    }

    public Categories(int id, String name, int parentId, String image, int depthLevel) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.parentId = parentId;
        this.depthLevel = depthLevel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getDepthLevel() {
        return depthLevel;
    }

    public void setDepthLevel(int depthLevel) {
        this.depthLevel = depthLevel;
    }
}
