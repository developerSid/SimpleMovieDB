package org.devict.movie.db.repository;

import org.devict.movie.db.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by developerSid on 1/11/17.
 *
 * The {@link Movie} repository
 */
@Repository
public interface MovieRepository extends JpaRepository<Movie, Long>
{
   Page<Movie> findByTitleContainingIgnoreCase(@Param("title") String title, Pageable pageable);

   @EntityGraph(value = "graph.movies.complete", type = EntityGraphType.LOAD)
   Optional<Movie> findMovieById(Long id);

   @Query(value =
        "select m "
      + "from Movie m "
      + "   join m.directors c "
      + "where c.name = ?1")
   Page<Movie> findAllMoviesByDirector(String directorName, Pageable pageable);
}
