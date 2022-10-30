package com.driver;

public class Pair {
   private String director_name;
   private String movie_name;

    public Pair(String director_name, String movie_name) {
        this.director_name = director_name;
        this.movie_name = movie_name;
    }

    public String getDirector_name() {
        return director_name;
    }

    public void setDirector_name(String director_name) {
        this.director_name = director_name;
    }

    public String getMovie_name() {
        return movie_name;
    }

    public void setMovie_name(String movie_name) {
        this.movie_name = movie_name;
    }
}
