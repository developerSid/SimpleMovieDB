package org.devict.movie.db.service;

import org.devict.movie.db.entity.Credit;
import org.devict.movie.db.entity.Genre;
import org.devict.movie.db.entity.Movie;
import org.devict.movie.db.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * Created by developerSid on 1/12/17.
 *
 * Standard JPA based Movie Service
 */
@Service
@Transactional //since movie contains a LOB, and Postgres stores LOBs across multiple storage subcomponents, we have to make anything that deals with Movie Transactional.
public class JpaMovieService implements MovieService
{
   private final MovieRepository movieRepository;
   private final GenreService genreService;
   private final CreditService creditService;

   @Autowired
   public JpaMovieService(MovieRepository movieRepository, GenreService genreService, CreditService creditService)
   {
      this.movieRepository = movieRepository;
      this.genreService = genreService;
      this.creditService = creditService;
   }

   @Override
   public Movie save(Movie movie)
   {
      List<Genre> savedGenres = genreService.saveAll(movie.getGenres());
      List<Credit> savedCast = creditService.saveAll(movie.getCast());
      List<Credit> savedDirectors = creditService.saveAll(movie.getDirectors());

      movie.setGenres(savedGenres);
      movie.setCast(savedCast);
      movie.setDirectors(savedDirectors);

      return movieRepository.save(movie);
   }

   @Override
   public Page<Movie> find(String title, Pageable pageable)
   {
      return movieRepository.findByTitleContainingIgnoreCase(title, pageable);
   }

   @Override
   public Optional<Movie> find(Long id)
   {
      return Optional.ofNullable(movieRepository.findOne(id));
   }

   @Override
   public Page<Movie> findByDirectorName(String directorName, Pageable pageable)
   {
      return movieRepository.findAllMoviesByDirector(directorName, pageable);
   }
}
