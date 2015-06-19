package com.ganatragmail.sagar.photogallery;


public class GalleryItem {

    private String mCaption;
    private String mId;
    private String mUrlSmall;
    private  String mUrlMedium;
    private String mOwner;

    public String toString(){
        return mCaption;
    }

    public String getCaption() {
        return mCaption;
    }

    public void setCaption(String caption) {
        this.mCaption = caption;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getUrlSmall() {
        return mUrlSmall;
    }


    public void setUrlSmall(String url_m) {
        mUrlSmall = url_m;
    }


    public String getUrlMedium() {
        return mUrlMedium;
    }

    public void setUrlMedium(String mUrl_m) {
        this.mUrlMedium = mUrl_m;
    }

    public String getOwner() {
        return mOwner;
    }

    public void setOwner(String mOwner) {
        this.mOwner = mOwner;
    }

    public String getPhotoURL(){
        return "http://www.flickr.com/photos/" + mOwner + "/" +mId;
    }
}
