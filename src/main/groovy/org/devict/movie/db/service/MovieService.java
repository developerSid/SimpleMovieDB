package org.devict.movie.db.service;

import org.devict.movie.db.entity.Movie;
import org.springframework.data.domain.Page;
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
   Movie save(Movie movie);
   Optional<Movie> find(Long id);
   Page<Movie> find(String title, Pageable pageable);
   Optional<Movie> loadMovieGraph(Long id);
   Page<Movie> findByDirectorName(String directorName, Pageable pageable);
}
