package org.devict.movie.db.processor;

import java.util.function.Function;
import org.devict.movie.db.entity.Movie;
import org.devict.movie.db.service.CreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by developerSid on 2/10/17.
 *
 * Saves credits from a movie
 */
@Service
public class SaveCreditsProcessor implements Function<Movie, Movie>
{
   private final CreditService creditService;

   @Autowired
   public SaveCreditsProcessor(CreditService creditService)
   {
      this.creditService = creditService;
   }

   @Override
   public Movie apply(Movie movie)
   {
      creditService.saveAll(movie.getCast());
      creditService.saveAll(movie.getDirectors());

      return movie;
   }
}
