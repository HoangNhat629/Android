package com.example.mvpdemo2.models;

public class GetMovieAccountStatesResponse {

    /**
     * id : 545609
     * favorite : true
     * rated : false
     * watchlist : false
     */

    private int id;
    private boolean favorite;
    private boolean rated;
    private boolean watchlist;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public boolean isRated() {
        return rated;
    }

    public void setRated(boolean rated) {
        this.rated = rated;
    }

    public boolean isWatchlist() {
        return watchlist;
    }

    public void setWatchlist(boolean watchlist) {
        this.watchlist = watchlist;
    }
}
