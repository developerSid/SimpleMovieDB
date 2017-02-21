package org.devict.movie.db.repository;

import org.devict.movie.db.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by developerSid on 1/11/17.
 *
 * The {@link Movie} repository
 */
@Repository
public interface MovieRepository extends JpaRepository<Movie, Long>
{
   Page<Movie> findByTitleContainingIgnoreCase(@Param("title") String title, Pageable pageable);

   @Query(value =
      "SELECT m " +
      "FROM Movie m " +
      "   JOIN m.directors c " +
      "WHERE c.name = ?1")
   Page<Movie> findAllMoviesByDirector(String directorName, Pageable pageable);

   @Query(nativeQuery = true,
      value =
      "SELECT m.* " +
      "FROM Movie m " +
      "   JOIN Movie_Director md " +
      "     ON m.id = md.movie_id " +
      "   JOIN Credit c " +
      "     ON md.director_id = c.id " +
      "WHERE c.name = ?1"
   )
   List<Movie> findAllMoviesByDirectorNative(String directorName);
}
