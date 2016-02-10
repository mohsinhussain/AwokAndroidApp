package com.awok.moshin.awok.Models;

/**
 * Created by shon on 1/27/2016.
 */
public class OffersModel {
    private String name;
    private String link;
    private String id;
    private String image;
    private String desc;
    private String key;
    private String value;
    public String view="N";

    boolean selected = false;

    public OffersModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public OffersModel(String name,String id,String desc,String image,String key,String value,String  view,String link) {
        this.name = name;
        this.id=id;
        this.desc=desc;
        this.image=image;
        this.key=key;
        this.value=value;
        this.view=view;
        this.link=link;








    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
