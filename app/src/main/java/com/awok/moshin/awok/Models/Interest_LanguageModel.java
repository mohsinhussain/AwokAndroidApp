package com.awok.moshin.awok.Models;

/**
 * Created by shon on 1/21/2016.
 */
public class Interest_LanguageModel {

        private String name;
        private int id;

        boolean selected = false;

        public Interest_LanguageModel() {
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Interest_LanguageModel(String name,int id,boolean selected) {
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
