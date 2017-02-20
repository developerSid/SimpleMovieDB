package org.devict.movie.db.processor;

import java.util.List;
import java.util.function.Function;
import org.apache.commons.lang3.StringUtils;
import org.devict.movie.db.entity.Credit;
import org.devict.movie.db.entity.Movie;
import org.devict.movie.db.service.CreditService;
import org.devict.movie.db.service.TheMovieDBService;
import org.devict.movie.db.service.copy.ObjectCopying;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by developerSid on 1/20/17.
 *
 * Loads directors associated with a movie and saves them to the the storage
 */
@Component
public class LoadCreditsProcessor implements Function<Movie, Movie>
{
   private final CreditService creditService;
   private final TheMovieDBService theMovieDBService;
   private final ObjectCopying objectCopying;

   @Autowired
   public LoadCreditsProcessor(CreditService creditService, TheMovieDBService theMovieDBService, ObjectCopying objectCopying)
   {
      this.creditService = creditService;
      this.theMovieDBService = theMovieDBService;
      this.objectCopying = objectCopying;
   }

   @Override
   public Movie apply(Movie movie)
   {
      List<Credit> credits = theMovieDBService.loadCredits(movie);

      movie = objectCopying.copy(movie);

      for(Credit credit: credits)
      {
         if(StringUtils.equalsIgnoreCase("director", credit.getJob().trim()))
         {
            movie.getDirectors().add(credit);
         }
         else if(StringUtils.length(credit.getJob()) > 3)
         {
            movie.getCast().add(credit);
         }
      }

      return movie;
   }
}
