package com.awok.moshin.awok.Models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by moshin on 9/7/2015.
 */

public class Categories implements Serializable {

    String id;
    String name;
    String parentId;


    public Categories() {
    }

    public Categories(String id, String name, String parentId) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
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

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

}
