package org.devict.movie.db.processor;

import java.util.Optional;
import java.util.function.Function;
import org.devict.movie.db.entity.Movie;
import org.devict.movie.db.service.TheMovieDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by developerSid on 1/20/17.
 *
 * Handles loading movies based on a request to load the movie
 */
@Component
public class LoadMovieProcessor implements Function<Integer, Optional<Movie>>
{
   private final TheMovieDBService theMovieDBService;

   @Autowired
   public LoadMovieProcessor(TheMovieDBService theMovieDBService)
   {
      this.theMovieDBService = theMovieDBService;
   }

   @Override
   public Optional<Movie> apply(Integer movieId)
   {
      return theMovieDBService.loadMovie(movieId);
   }
}
