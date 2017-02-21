package org.devict.movie.db.repository;

import org.devict.movie.db.entity.Movie;
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
   List<Movie> findByTitleContainingIgnoreCase(@Param("title") String title, Pageable pageable);

   @Query(value =
        "select m "
      + "from Movie m "
      + "   join m.directors c "
      + "where c.name = ?1")
   List<Movie> findAllMoviesByDirector(String directorName);
}
