package com.astra.netflix.controller;

import com.astra.netflix.service.MovieService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.*;
import static org.springframework.web.bind.annotation.RequestMethod.PATCH;

@RestController
@CrossOrigin(methods = { POST, GET, OPTIONS, PUT, DELETE, PATCH }, maxAge = 3600, allowedHeaders = {
        "x-requested-with", "origin", "content-type", "accept" }, origins = "*")
@Tag(name = "Product Service", description = "Provide crud operations for Movies")
@RequestMapping("api/graphql/")
public class NetflixController {

    @Autowired
    private MovieService movieService;

    @GetMapping("/describe-database/{keyspace}")
    public ResponseEntity<Object> descDatabase(HttpServletRequest req,
                                               @PathVariable(value = "keyspace") String keyspace) {
        return ResponseEntity.ok(movieService.descDatabase(keyspace));
    }

    @GetMapping("/get-all-movies")
    public ResponseEntity<Object> getAllMovies(HttpServletRequest req) {
        return ResponseEntity.ok(movieService.getAllMovies());
    }

    @PostMapping("/add-movie")
    public ResponseEntity<Object> addMovie(HttpServletRequest req,
                                           @RequestBody Map<String,Object> movieInfo){
        return ResponseEntity.ok(movieService.addMovie(movieInfo));
    }

    @GetMapping("/get-movie/{movie_name}")
    public ResponseEntity<Object> getMovieInfo(HttpServletRequest req,
                                               @PathVariable(value = "movie_name") String movieName){
        return ResponseEntity.ok(movieService.getMovieInfo(movieName));
    }

    @PostMapping("/update-movie")
    public ResponseEntity<Object> updateMovieRating(HttpServletRequest req,
                                                   @RequestBody Map<String ,Object> updateInfo){
        return ResponseEntity.ok(movieService.updateMovieRating(updateInfo));
    }
}
