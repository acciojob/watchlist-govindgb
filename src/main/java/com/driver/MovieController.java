package com.driver;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {


    //this list record the movie list
    private List<Movie> moviesList = new ArrayList<>();

    //this list record the Director  list
    private List<Director> DirectorList = new ArrayList<>();

    //list of director and movie nam

    private List<Pair> pairsList = new ArrayList<>();

    //this hashmap map the movie name and Movie object
    HashMap<String,Movie> Movie_map = new HashMap<>();
    //this @PathVariable int x map the director name and Director object
    HashMap<String,Director> Director_map = new HashMap<>();


    @PostMapping("/add-movie")
    public ResponseEntity<Movie> addMovie(@RequestBody Movie movie){
        moviesList.add(movie);
        Movie_map.put(movie.getName(),movie);
        return  new ResponseEntity<>(movie, HttpStatus.CREATED);
    }
    //http://localhost:8030/movies/add-movie

    @PostMapping("/add-director")
    public ResponseEntity<Director> addDirector(@RequestBody Director director)
    {
        DirectorList.add(director);
        Director_map.put(director.getName(),director);
        return  new ResponseEntity<>(director, HttpStatus.CREATED);
    }
  //http://localhost:8030/movies/add-director


    //Pair an existing movie and director
    @PutMapping("/add-movie-director-pair/{director_name}/{movie_name}")
    public ResponseEntity<Pair> addMovieDirectorPair(@PathVariable String director_name,@PathVariable String movie_name)
    {
        Pair pair = new Pair(director_name,movie_name);
        pairsList.add(pair);
        return  new ResponseEntity<>(pair, HttpStatus.CREATED);
    }
    //http://localhost:8030/movies/add-movie-director-pair/{director_name}/{movie_name}



    //return movie object by movie name
    @GetMapping("/get-movie-by-name/{name}")
    public Movie getMovieByName(@PathVariable String name)
    {

        if(Movie_map.containsKey(name))
        {
            return Movie_map.get(name);
        }
        return null;
    }
    //http://localhost:8030/movies/get-movie-by-name/{name}

    //Get Director by director name
    @GetMapping("/get-director-by-name/{name}")
    public Director getDirectorByName(@PathVariable String name)
    {

       if(Director_map.containsKey(name))
       {
           return Director_map.get(name);
       }
       return null;

    }
    //http://localhost:8030/movies/get-director-by-name/{name}

    //Get List of movies name for a given director name
    @GetMapping("/get-movies-by-director-name/{director}")
    public List<String> getMoviesByDirectorName(@PathVariable String name)
    {
        List<String> m = new ArrayList<>();

        for(Pair pair :pairsList)
        {
           if(pair.getDirector_name() == name)
           {
               m.add(pair.getMovie_name());
           }
        }
        return m;
    }
    //http.//localhost:8030/movies/get-movies-by-director-name/{director}

    //Get List of all movies added
    @GetMapping("/get-all-movies")
    public List<String> findAllMovies()
    {
        List<String> m = new ArrayList<>();

        for(Movie movie : moviesList)
        {
            m.add(movie.getName());
        }
        return m;
    }
    //http://localhost:8030/movies/get-all-movies

    //Delete a director and its movies from the records
    @DeleteMapping("/delete-director-by-name/{name}")
    public ResponseEntity  deleteAllDirectors(@PathVariable String name)
    {
        for(Pair pair : pairsList)
        {
            if(pair.getDirector_name().equals(name))
            {
               String movie_name = pair.getMovie_name();
               Movie movie = Movie_map.get(movie_name);
               moviesList.remove(movie);
            }
        }
        Director director = Director_map.get(name);
        DirectorList.remove(director);
        return  new ResponseEntity<>(HttpStatus.CREATED);
    }
    //http://localhost:8030/delete-director-by-name/{name}

    //Delete all directors and all movies by them from the records
    @DeleteMapping("/delete-director-by-name")
    public ResponseEntity deleteAllDirectors()
    {
        moviesList.clear();
        DirectorList.clear();
        return  new ResponseEntity<>(HttpStatus.CREATED);
    }
    //http://localhost:8030/movies/delete-director-by-name






//    @GetMapping("/check")
//    public String check()
//    {
//        return "running";
//    }
    //http://localhost/movies/check


}
