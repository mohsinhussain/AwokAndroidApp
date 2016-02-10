package com.awok.moshin.awok.Models;

/**
 * Created by shon on 1/30/2016.
 */
public class OffersCategoryModel {


        private String title;
        private String key;
        private String value;
        //private ArrayList<OffersSubCategory> subCat;
        private String subCat;
        public OffersCategoryModel()
        {

        }


        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

  /*  public ArrayList<OffersSubCategory> getSubCat() {
        return subCat;
    }

    public void setSubCat(ArrayList<OffersSubCategory> subCat) {
        this.subCat = subCat;
    }*/
  public String getSubCat() {
      return subCat;
  }

    public void setSubCat(String subCat) {
        this.subCat = subCat;
    }

    public OffersCategoryModel(String title,String key,String value,String subData)
        {
            this.title=title;
            this.key=key;
            this.value=value;
            this.subCat=subData;


        }


}
