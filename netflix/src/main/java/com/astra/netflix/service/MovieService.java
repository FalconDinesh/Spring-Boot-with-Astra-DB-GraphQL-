package com.astra.netflix.service;

import com.datastax.astra.sdk.AstraClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Component
@PropertySource(value = "classpath:query.properties")
public class MovieService {

    @Autowired
    AstraClient astraClient;

    @Value("${DESC_KEYSPACE}")
    String descQuery;

    @Value("${FETCH_MOVIES}")
    String getAllMoviesQuery;

    @Value("${ADD_MOVIE}")
    String addMovieBaseQuery;

    @Value("${GET_MOVIE_USING_NAME}")
    String getMovieInfoQuery;

    @Value("${UPDATE_MOVIE_RATING}")
    String updateMovieBaseQuery;

    @Value("${KEYSPACE}")
    String keyspace;

    private static final Logger logger = LogManager.getLogger(MovieService.class);

    public String descDatabase(String keyspace){
        String descKeyspaceQuery = descQuery.replace("KEYSPACE_NAME",keyspace);
        logger.info("query from conf file: " + descKeyspaceQuery);

        return  astraClient.apiStargateGraphQL().cqlSchema().query(descKeyspaceQuery);
    }

    public String getAllMovies(){
        logger.info("Fetch movies query: " + getAllMoviesQuery);
        return astraClient.apiStargateGraphQL().cqlKeyspace(keyspace)
                .query(getAllMoviesQuery);
    }

    public String addMovie(Map<String,Object> movieInfo){
        String addMovieQuery = formAddMovieQuery(movieInfo);
        logger.info("Add movie query: " + addMovieQuery);
        return astraClient.apiStargateGraphQL().cqlKeyspace(keyspace).query(addMovieQuery);
    }

    public String getMovieInfo(String movieName){
        String getMovieQuery = getMovieInfoQuery.replace("MOVIE_NAME", movieName);
        logger.info("Get Movie query: " + getMovieQuery);
        return astraClient.apiStargateGraphQL().cqlKeyspace(keyspace).query(getMovieQuery);
    }

    public String updateMovieRating(Map<String,Object> updateInfo){
        String updateMovieQuery = formUpdateMovieQuery(updateInfo);
        logger.info("Update Query: " + updateMovieQuery);
        return astraClient.apiStargateGraphQL().cqlKeyspace(keyspace).query(updateMovieQuery);
    }

    private String formUpdateMovieQuery(Map<String,Object> updateInfo){
        logger.debug("Update Base Query: " + updateMovieBaseQuery);

        return updateMovieBaseQuery.replace("MOVIE_NAME", updateInfo.get("name").toString())
                .replace("MOVIE_ID", updateInfo.get("id").toString())
                .replace("MOVIE_LANGUAGE", updateInfo.get("language").toString())
                .replace("MOVIE_IMDB_RATING", updateInfo.get("rating").toString());
    }

    private String formAddMovieQuery(Map<String,Object> movieInfo){
        logger.debug("Base query: " + addMovieBaseQuery);

        return addMovieBaseQuery.replace("RANDOM_UUID", UUID.randomUUID().toString())
                .replace("MOVIE_NAME",movieInfo.get("name").toString())
                .replace("MOVIE_LANGUAGE",movieInfo.get("language").toString())
                .replace("MOVIE_GENRE",movieInfo.get("genre").toString())
                .replace("MOVIE_YEAR",movieInfo.get("year").toString())
                .replace("MOVIE_IMDB_RATING",movieInfo.get("imdb_rating").toString())
                .replace("MOVIE_LENGTH",movieInfo.get("length").toString());
    }
}