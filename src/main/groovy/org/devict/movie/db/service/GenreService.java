package org.devict.movie.db.service;

import org.devict.movie.db.entity.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;

/**
 * Created by developerSid on 1/22/17.
 */
public interface GenreService
{
   List<Genre> saveAll(Collection<Genre> genres);
   Page<Genre> loadAll(Pageable pageable);
}
