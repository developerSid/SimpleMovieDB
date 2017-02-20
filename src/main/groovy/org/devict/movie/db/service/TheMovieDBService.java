package org.devict.movie.db.service;

import java.util.List;
import java.util.Optional;
import org.devict.movie.db.entity.Credit;
import org.devict.movie.db.entity.Movie;

/**
 * Created by developerSid on 1/20/17.
 *
 * Defines access to the https://www.themoviedb.org API
 */
public interface TheMovieDBService
{
   Optional<Movie> loadMovie(int id);
   List<Credit> loadCredits(Movie movie);
}
