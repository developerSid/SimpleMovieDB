package org.devict.movie.db;

import java.util.Optional;
import java.util.stream.IntStream;
import org.devict.movie.db.entity.Movie;
import org.devict.movie.db.processor.LoadCreditsProcessor;
import org.devict.movie.db.processor.LoadMovieProcessor;
import org.devict.movie.db.processor.SaveCreditsProcessor;
import org.devict.movie.db.service.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * Created by developerSid on 2/19/17.
 *
 * Loads data
 */
@Component
@Profile({"development", "mysql", "postgres", "sqlserver"})
public class InitialDataLoad implements CommandLineRunner
{
   private static final Logger logger = LoggerFactory.getLogger(InitialDataLoad.class);
   private final MovieService movieService;
   private final LoadMovieProcessor loadMovieEventConsumer;
   private final LoadCreditsProcessor loadCreditsProcessor;
   private final SaveCreditsProcessor saveCreditsProcessor;

   @Autowired
   public InitialDataLoad(MovieService movieService, LoadMovieProcessor loadMovieEventConsumer, LoadCreditsProcessor loadCreditsProcessor, SaveCreditsProcessor saveCreditsProcessor)
   {
      this.movieService = movieService;
      this.loadMovieEventConsumer = loadMovieEventConsumer;
      this.loadCreditsProcessor = loadCreditsProcessor;
      this.saveCreditsProcessor = saveCreditsProcessor;
   }

   @Override
   public void run(String... args) throws Exception
   {
      IntStream.of(330459, 603, 10249, 9942, 154, 272, 137106, 11528, 284052, 1726, 1771).boxed()
         .map(loadMovieEventConsumer)
         .filter(Optional::isPresent)
         .map(Optional::get)
         .map(movieService::save)
         .map(loadCreditsProcessor)
         .map(saveCreditsProcessor)
         .map(movieService::save)
         .map(Movie::getTitle)
         .forEach(m -> logger.info("Movie loaded {}", m))
      ;
   }
}
