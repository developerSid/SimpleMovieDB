package org.devict.movie.db.service;

import org.assertj.core.api.Assertions;
import org.devict.movie.db.entity.Credit;
import org.devict.movie.db.repository.CreditRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.BeanUtils;

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
   public void testSaveNonExisting()
   {
      Credit creditOne = new Credit("Bill Murray", 1, "Dr. Peter Venkman", "A1");
      Credit creditTwo = new Credit("Dan Aykroyd", 2, "Dr. Raymon Stanze", "A3");
      Credit creditThree = new Credit("Harold Ramis", 3, "Dr. Egon Spengler", "A2");
      Credit creditFour = new Credit("Ernie Hudson", 4, "Winston Zeddmore", "A4");
      List<Credit> credits = Arrays.asList(creditOne, creditTwo, creditThree, creditFour);

      Credit savedCreditOne = new Credit();
      BeanUtils.copyProperties(creditOne, savedCreditOne);
      Credit savedCreditTwo = new Credit();
      BeanUtils.copyProperties(creditTwo, savedCreditTwo);
      Credit savedCreditThree = new Credit();
      BeanUtils.copyProperties(creditThree, savedCreditThree);
      Credit savedCreditFour = new Credit();
      BeanUtils.copyProperties(creditFour, savedCreditFour);

      Mockito.when(creditRepository.findByTheMovieDBidInOrderByNameAsc(Arrays.asList(1, 2, 3, 4))).thenReturn(Collections.emptyList());
      Mockito.when(creditRepository.save(credits)).then(new Answer<List<Credit>>()
      {
         long id = 1;

         @Override
         public List<Credit> answer(InvocationOnMock invocation) throws Throwable
         {
            List<Credit> credits = invocation.getArgument(0);

            return credits.stream().map(c ->
            {
               Credit toReturn = new Credit();

               BeanUtils.copyProperties(c, toReturn);
               toReturn.setId(id++);
               toReturn.setCreated(LocalDateTime.of(2017, Month.FEBRUARY, 21, 7, 0));
               toReturn.setUpdated(LocalDateTime.of(2017, Month.FEBRUARY, 21, 7, 0));

               return toReturn;
            }).collect(Collectors.toList());
         }
      });

      List<Credit> result = jpaCreditService.saveAll(credits);

      Assertions.assertThat(result)
         .isNotEmpty()
         .containsOnly(savedCreditOne, savedCreditTwo, savedCreditThree, savedCreditFour)
      ;
      Assertions.assertThat(result.get(0)).hasNoNullFieldsOrProperties();
      Assertions.assertThat(result.get(1)).hasNoNullFieldsOrProperties();
      Assertions.assertThat(result.get(2)).hasNoNullFieldsOrProperties();
      Assertions.assertThat(result.get(3)).hasNoNullFieldsOrProperties();

      InOrder inOrder = Mockito.inOrder(creditRepository);

      inOrder.verify(creditRepository, Mockito.calls(1)).findByTheMovieDBidInOrderByNameAsc(Arrays.asList(1, 2, 3, 4));
      inOrder.verify(creditRepository, Mockito.calls(1)).save(credits);
      inOrder.verifyNoMoreInteractions();
   }

   @Test
   public void testSaveWhenSomeExist()
   {
      Credit creditOne = new Credit("Bill Murray", 1, "Dr. Peter Venkman", "A1");
      Credit creditTwo = new Credit("Dan Aykroyd", 2, "Dr. Raymon Stanze", "A3");
      Credit creditThree = new Credit("Harold Ramis", 3, "Dr. Egon Spengler", "A2");
      Credit creditFour = new Credit("Ernie Hudson", 4, "Winston Zeddmore", "A4");
      List<Credit> credits = Arrays.asList(creditOne, creditTwo, creditThree, creditFour);

      Credit savedCreditOne = new Credit();
      BeanUtils.copyProperties(creditOne, savedCreditOne);
      Credit savedCreditTwo = new Credit();
      BeanUtils.copyProperties(creditTwo, savedCreditTwo);
      Credit savedCreditThree = new Credit();
      BeanUtils.copyProperties(creditThree, savedCreditThree);
      Credit savedCreditFour = new Credit();
      BeanUtils.copyProperties(creditFour, savedCreditFour);

      Mockito.when(creditRepository.findByTheMovieDBidInOrderByNameAsc(Arrays.asList(1, 2))).thenReturn(Collections.singletonList(savedCreditTwo));
      Mockito.when(creditRepository.save(Collections.singletonList(savedCreditOne))).thenReturn(Collections.singletonList(savedCreditOne));

      List<Credit> result = jpaCreditService.saveAll(credits);

      Assertions.assertThat(result)
         .isNotEmpty()
         .containsOnly(savedCreditOne, savedCreditTwo)
      ;

      Mockito.verify(creditRepository).findByTheMovieDBidInOrderByNameAsc(Arrays.asList(1, 2, 3, 4));
      Mockito.verify(creditRepository).save(Collections.singletonList(creditOne));

      InOrder inOrder = Mockito.inOrder(creditRepository);

      inOrder.verify(creditRepository, Mockito.calls(1)).findByTheMovieDBidInOrderByNameAsc(Arrays.asList(1, 2, 3, 4));
      inOrder.verify(creditRepository, Mockito.calls(1)).save(Collections.singletonList(creditOne));
      inOrder.verifyNoMoreInteractions();
   }

   /*@Test
   public void testSaveWhenSomeExist()
   {
      List<Credit> saveList = Arrays.asList(
         new Credit("Bill Murray", 1, "Dr. Peter Venkman", "A2"),
         new Credit("Dan Aykroyd", 2, "Dr. Raymon Stanze", "A3"),
         new Credit("Harold Ramis", 3, "Dr. Egon Spengler", "A4"),
         new Credit("Ernie Hudson", 4, "Winston Zeddmore", "A5"),
         new Credit("Ivan Reitman", 5, "Director", "A6")
      );
      List<Credit> exists = Arrays.asList(
         new Credit(1L, "Dan Aykroyd", 2, "Dr. Raymon Stanze", "A3", LocalDateTime.of(2017, Month.FEBRUARY, 20, 7, 0), LocalDateTime.of(2017, Month.FEBRUARY, 20, 7, 0)),
         new Credit(2L, "Harold Ramis", 3, "Dr. Egon Spengler", "A4", LocalDateTime.of(2017, Month.FEBRUARY, 20, 7, 0), LocalDateTime.of(2017, Month.FEBRUARY, 20, 7, 0))
      );

      Mockito.when(creditRepository.findByTheMovieDBidInOrderByNameAsc(Mockito.anyIterable())).thenReturn(Collections.emptyList());
      List<Credit> result = jpaCreditService.saveAll(saveList);
      Mockito.when(creditRepository.save(saveList)).then(new Answer<List<Credit>>()
      {
         long id = 20;

         @Override
         public List<Credit> answer(InvocationOnMock invocation) throws Throwable
         {
            List<Credit> credits = invocation.getArgument(0);

            return credits.stream().map(c ->
            {
               if(c.getId() != null)
               {
                  c.setId(id++);
                  c.setCreated(LocalDateTime.of(2017, Month.FEBRUARY, 21, 7, 0));
               }

               c.setUpdated(LocalDateTime.of(2017, Month.FEBRUARY, 21, 7, 0));

               return c;
            }).collect(Collectors.toList());
         }
      });

      Assertions.assertThat(result).isNotNull();
      Assertions.assertThat(result)
         .isNotEmpty()
         .isSorted()
      ;

   }*/
}
