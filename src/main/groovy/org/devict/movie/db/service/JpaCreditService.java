package org.devict.movie.db.service;

import org.devict.movie.db.entity.Credit;
import org.devict.movie.db.repository.CreditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by developerSid on 1/12/17.
 *
 * Provides the JPA based Credit Service
 */
@Service
public class JpaCreditService implements CreditService
{
   private final CreditRepository creditRepository;

   public JpaCreditService(@Autowired CreditRepository creditRepository)
   {
      this.creditRepository = creditRepository;
   }

   @Override
   public List<Credit> saveAll(Collection<Credit> credits)
   {
      List<Credit> foundCredits = creditRepository.findByTheMovieDBidInOrderByNameAsc(credits.stream().map(Credit::getTheMovieDBid).collect(Collectors.toList()));
      Collection<Credit> toSave = credits;

      if(!foundCredits.isEmpty())
      {
         toSave = new ArrayList<>();

         for(Credit genre : credits)
         {
            if(Collections.binarySearch(foundCredits, genre, Comparator.comparing(Credit::getName)) < 0)
            {
               toSave.add(genre);
            }
         }
      }

      if(!toSave.isEmpty())
      {
         List<Credit> toReturn = new ArrayList<>(toSave.size() + foundCredits.size());

         toReturn.addAll(creditRepository.save(toSave));
         toReturn.addAll(foundCredits);

         return toReturn;
      }
      else
      {
         return foundCredits;
      }
   }
}
