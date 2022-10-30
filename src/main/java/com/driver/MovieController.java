package com.driver;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("movies")
public class MovieController {








    //this hashmap map the movie name and Movie object
    HashMap<String,Movie> Movie_map = new HashMap<>();
    //this @PathVariable int x map the director name and Director object
    HashMap<String,List<String>> direct_movies_map = new HashMap<>();
    HashMap<String,Director> Director_map = new HashMap<>();


    @PostMapping("/add-movie")
    public String addMovie(@RequestBody Movie movie){
//        moviesList.add(movie);
        Movie_map.put(movie.getName(),movie);
        return "success";
    }
    //http://localhost:8030/movies/add-movie

    @PostMapping("/add-director")
    public String addDirector(@RequestBody Director director)
    {

        Director_map.put(director.getName(),director);
        return "success";
    }
  //http://localhost:8030/movies/add-director


    //Pair an existing movie and director
    @PutMapping("/add-movie-director-pair/{director_name}/{movie_name}")
    public String addMovieDirectorPair(@PathVariable String director_name,@PathVariable String movie_name)
    {

          if(direct_movies_map.containsKey(director_name))
          {
              direct_movies_map.get(director_name).add(movie_name);
          }
          else {
              List<String> movie_list = new ArrayList<>();
              movie_list.add(movie_name);
              direct_movies_map.put(director_name,movie_list);
          }
        return  "success";
    }
    //http://localhost:8030/movies/add-movie-director-pair/{director_name}/{movie_name}



    //return movie object by movie name
    @GetMapping("/get-movie-by-name/{name}")
    public  ResponseEntity<Movie>  getMovieByName(@PathVariable String name)
    {

        if(Movie_map.containsKey(name))
        {
            return  new ResponseEntity<>(Movie_map.get(name), HttpStatus.CREATED);

        }
        return null;
    }
    //http://localhost:8030/movies/get-movie-by-name/{name}

    //Get Director by director name
    @GetMapping("/get-director-by-name/{name}")
    public ResponseEntity<Director> getDirectorByName(@PathVariable String name)
    {

       if(Director_map.containsKey(name))
       {
           Director director = Director_map.get(name);
           return  new ResponseEntity<>(director, HttpStatus.CREATED);
       }
       return null;

    }
    //http://localhost:8030/movies/get-director-by-name/{name}

    //Get List of movies name for a given director name
    @GetMapping("/get-movies-by-director-name/{director}")
    public ResponseEntity<List<String>> getMoviesByDirectorName(@PathVariable String name)
    {
       if(direct_movies_map.containsKey(name))
       {
           return  new ResponseEntity<>(direct_movies_map.get(name), HttpStatus.CREATED);

       }
       return null;
    }
    //http.//localhost:8030/movies/get-movies-by-director-name/{director}

    //Get List of all movies added
    @GetMapping("/get-all-movies")
    public ResponseEntity<List<String>> findAllMovies()
    {
        List<String> m = new ArrayList<>();

        for(String name : Movie_map.keySet())
        {
            m.add(Movie_map.get(name).getName());
        }
        return  new ResponseEntity<>(m, HttpStatus.CREATED);
    }
    //http://localhost:8030/movies/get-all-movies

    //Delete a director and its movies from the records
    @DeleteMapping("/delete-director-by-name/{name}")
    public String deleteDirectorByName(@PathVariable String name)
    {
        List<String> list = direct_movies_map.get(name);

        direct_movies_map.remove(name);
        Director_map.remove(name);
        for(String key : list)
        {
            if(Movie_map.containsKey(key))
            {

                Movie_map.remove(key);
            }
        }

        return "success";
    }
    //http://localhost:8030/delete-director-by-name/{name}

    //Delete all directors and all movies by them from the records
    @DeleteMapping("/delete-director-by-name")
    public String deleteAllDirectors()
    {
        Movie_map.clear();
        Director_map.clear();
        return "success";
    }
    //http://localhost:8030/movies/delete-director-by-name






//    @GetMapping("/check")
//    public String check()
//    {
//        return "running";
//    }
    //http://localhost/movies/check


}
