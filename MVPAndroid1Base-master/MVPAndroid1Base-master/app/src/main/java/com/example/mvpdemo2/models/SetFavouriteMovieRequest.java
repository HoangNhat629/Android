package com.example.mvpdemo2.models;

public class SetFavouriteMovieRequest {

    /**
     * media_type : movie
     * media_id : 550
     * favorite : true
     */

    private String media_type;
    private int media_id;
    private boolean favorite;

    public SetFavouriteMovieRequest(int media_id, boolean favorite) {
        this.media_type = "movie";
        this.media_id = media_id;
        this.favorite = favorite;
    }

    public String getMedia_type() {
        return media_type;
    }

    public void setMedia_type(String media_type) {
        this.media_type = media_type;
    }

    public int getMedia_id() {
        return media_id;
    }

    public void setMedia_id(int media_id) {
        this.media_id = media_id;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}
