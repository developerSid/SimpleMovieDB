package org.devict.movie.db.service;

import org.devict.movie.db.entity.Genre;
import org.devict.movie.db.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by developerSid on 1/22/17.
 *
 * A JPA database based implemenation of {@link GenreService}
 */
@Service
public class JpaGenreService implements GenreService
{
   private final GenreRepository genreRepository;

   @Autowired
   public JpaGenreService(GenreRepository genreRepository)
   {
      this.genreRepository = genreRepository;
   }

   @Override
   @Transactional
   public List<Genre> saveAll(Collection<Genre> genres)
   {
      List<Genre> foundGenres = genreRepository.findByNameInOrderByNameAsc(genres.stream().map(Genre::getName).collect(Collectors.toList()));
      Collection<Genre> toSave = genres;

      if(!foundGenres.isEmpty())
      {
         toSave = new ArrayList<>();

         for(Genre genre : genres)
         {
            if(Collections.binarySearch(foundGenres, genre, Comparator.comparing(Genre::getName)) < 0)
            {
               toSave.add(genre);
            }
         }
      }

      if(!toSave.isEmpty())
      {
         List<Genre> toReturn = new ArrayList<>(toSave.size() + foundGenres.size());

         toReturn.addAll(genreRepository.save(toSave));
         toReturn.addAll(foundGenres);

         return toReturn;
      }
      else
      {
         return foundGenres;
      }
   }

   @Override
   public Page<Genre> loadAll(Pageable pageable)
   {
      return genreRepository.findAll(pageable);
   }
}
