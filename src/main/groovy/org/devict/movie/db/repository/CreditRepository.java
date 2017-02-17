package org.devict.movie.db.repository;

import org.devict.movie.db.entity.Credit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by developerSid on 1/12/17.
 *
 * The {@link Credit} repository
 */
@Repository
public interface CreditRepository extends JpaRepository<Credit, Long>
{
   List<Credit> findByTheMovieDBidInOrderByNameAsc(Iterable<Integer> theMovieDBids);
}
