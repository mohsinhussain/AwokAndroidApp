package com.awok.moshin.awok.Models;

/**
 * Created by shon on 1/21/2016.
 */
public class Profile_CountryModel {

        private String name;
        private int id;

        boolean selected = false;

        public Profile_CountryModel() {
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Profile_CountryModel(String name,int id,boolean selected) {
            this.name = name;
            this.id=id;


            this.selected=selected;

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


