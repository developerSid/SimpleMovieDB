package org.devict.movie.db.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Streams;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.devict.movie.db.entity.Credit;
import org.devict.movie.db.entity.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

/**
 * Created by developerSid on 1/20/17.
 *
 * Maps {@link TheMovieDBService} calls to the Movito API wrapper
 */
@Service
public class RestfulTheMovieDBService implements TheMovieDBService
{
   private final String apiKey;
   private final RestOperations restTemplate;

   @Autowired
   public RestfulTheMovieDBService(@Value("${tmdb.api.key}") String apiKey, RestOperations restTemplate)
   {
      this.apiKey = apiKey;
      this.restTemplate = restTemplate;
   }

   @Override
   public Optional<Movie> loadMovie(int id)
   {
      return Optional.ofNullable(
         restTemplate.getForObject(
            "https://api.themoviedb.org/3/movie/{movie_id}?api_key={api_key}&language=en-US",
            Movie.class,
            id,
            apiKey
         )
      );
   }

   @Override
   public List<Credit> loadCredits(Movie movie)
   {
      return restTemplate.execute(
         "https://api.themoviedb.org/3/movie/{movie_id}/credits?api_key={api_key}",
         HttpMethod.GET,
         request ->{},
         this::extract,
         movie.getTheMovieDBid(),
         apiKey
      );
   }

   private List<Credit> extract(ClientHttpResponse response) throws IOException
   {
      List<Credit> toReturn = new LinkedList<>();

      ObjectMapper mapper = new ObjectMapper();
      JsonNode rootNode = mapper.readTree(response.getBody());

      toReturn.addAll(extractCast(rootNode.get("cast")));
      toReturn.addAll(extractDirector(rootNode.get("crew")));

      return toReturn;
   }

   private List<Credit> extractCast(JsonNode cast)
   {
      List<Credit> toReturn = new LinkedList<>();

      cast.iterator().forEachRemaining(j ->
         toReturn.add(
            new Credit(
               j.get("name").asText(),
               j.get("id").asInt(),
               j.get("character").asText(),
               j.get("credit_id").asText()
            )
      ));

      return toReturn;
   }

   private List<Credit> extractDirector(JsonNode crew)
   {
      return Streams.stream(crew.iterator())
         .filter(j -> j.get("job").asText("").equalsIgnoreCase("director"))
         .map(j ->
            new Credit(
               j.get("name").asText(),
               j.get("id").asInt(),
               j.get("job").asText(),
               j.get("credit_id").asText()
            )
         )
         .collect(Collectors.toList());
   }
}
