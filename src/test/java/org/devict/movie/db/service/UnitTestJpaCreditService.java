package org.devict.movie.db.service;

import org.assertj.core.api.Assertions;
import org.devict.movie.db.entity.Credit;
import org.devict.movie.db.repository.CreditRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by developerSid on 2/17/17.
 *
 * Unit tests for {@link JpaCreditService}
 */
public class UnitTestJpaCreditService
{
   private CreditRepository creditRepository;
   private JpaCreditService jpaCreditService;

   @Before
   public void before()
   {
      this.creditRepository = Mockito.mock(CreditRepository.class);
      this.jpaCreditService = new JpaCreditService(creditRepository);
   }

   @Test
   public void testSaveAllNew()
   {
      List<Credit> saveList = Arrays.asList(
         new Credit("Bill Murray", 1, "Dr. Peter Venkman", "A2"),
         new Credit("Dan Aykroyd", 2, "Dr. Raymon Stanze", "A3"),
         new Credit("Harold Ramis", 3, "Dr. Egon Spengler", "A4"),
         new Credit("Ernie Hudson", 4, "Winston Zeddmore", "A5"),
         new Credit("Ivan Reitman", 5, "Director", "A6")
      );
      Mockito.when(creditRepository.findByTheMovieDBidInOrderByNameAsc(Mockito.anyIterable())).thenReturn(Collections.emptyList());
      Mockito.when(creditRepository.save(saveList)).then(new Answer<List<Credit>>()
      {
         long id = 1;

         @Override
         public List<Credit> answer(InvocationOnMock invocation) throws Throwable
         {
            List<Credit> credits = invocation.getArgument(0);

            return credits.stream().map(c ->
            {
               c.setId(id++);
               c.setCreated(LocalDateTime.of(2017, Month.FEBRUARY, 21, 7, 0));
               c.setUpdated(LocalDateTime.of(2017, Month.FEBRUARY, 21, 7, 0));

               return c;
            }).collect(Collectors.toList());
         }
      });
      List<Credit> result = jpaCreditService.saveAll(saveList);

      Assertions.assertThat(result).isNotNull();
      Assertions.assertThat(result).hasSize(5);
      Assertions.assertThat(result.get(0)).hasNoNullFieldsOrProperties();
      Assertions.assertThat(result.get(1)).hasNoNullFieldsOrProperties();
      Assertions.assertThat(result.get(2)).hasNoNullFieldsOrProperties();
      Assertions.assertThat(result.get(3)).hasNoNullFieldsOrProperties();
      Assertions.assertThat(result.get(4)).hasNoNullFieldsOrProperties();
   }
}
