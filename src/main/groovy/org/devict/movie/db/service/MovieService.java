package org.devict.movie.db.service;

import org.devict.movie.db.entity.Movie;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Created by developerSid on 1/12/17.
 *
 * Defines how to access movies in the system.
 */

public interface MovieService
{
   Movie saveMovie(Movie movie);
   List<Movie> findMovie(String title, Pageable pageable);
   Optional<Movie> findMovie(Long id);
   Optional<Movie> loadMovieGraph(Long id);
   List<Movie> findByDirectorName(String directorName);
}
