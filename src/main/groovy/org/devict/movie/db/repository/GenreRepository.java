package org.devict.movie.db.repository;

import org.devict.movie.db.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by developerSid on 1/22/17.
 *
 * Repository for {@link Genre}
 */
@Repository
public interface GenreRepository extends JpaRepository<Genre, Long>
{
   List<Genre> findByNameInOrderByNameAsc(Iterable<String> genres);
}
