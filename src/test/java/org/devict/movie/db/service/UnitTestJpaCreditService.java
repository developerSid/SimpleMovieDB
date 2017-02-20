package org.devict.movie.db.service;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
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
      Credit creditTwo = new Credit("Dan Aykroyd", 2, "Dr. Raymond Stanze", "A3");
      Credit creditThree = new Credit("Harold Ramis", 3, "Dr. Egon Spengler", "A2");
      Credit creditFour = new Credit("Ernie Hudson", 4, "Winston Zeddmore", "A4");
      List<Credit> credits = Arrays.asList(creditOne, creditTwo, creditThree, creditFour);
      Set<Credit> saveSet = new TreeSet<>(Comparator.comparing(Credit::getTheMovieDBid));
      saveSet.addAll(credits);

      Credit savedCreditOne = new Credit();
      BeanUtils.copyProperties(creditOne, savedCreditOne);
      savedCreditOne.setId(1L);
      savedCreditOne.setCreated(LocalDateTime.of(2017, Month.FEBRUARY, 21, 7, 0));
      savedCreditOne.setUpdated(LocalDateTime.of(2017, Month.FEBRUARY, 21, 7, 0));
      Credit savedCreditTwo = new Credit();
      BeanUtils.copyProperties(creditTwo, savedCreditTwo);
      savedCreditTwo.setId(2L);
      savedCreditTwo.setCreated(LocalDateTime.of(2017, Month.FEBRUARY, 21, 7, 0));
      savedCreditTwo.setUpdated(LocalDateTime.of(2017, Month.FEBRUARY, 21, 7, 0));
      Credit savedCreditThree = new Credit();
      BeanUtils.copyProperties(creditThree, savedCreditThree);
      savedCreditThree.setId(3L);
      savedCreditThree.setCreated(LocalDateTime.of(2017, Month.FEBRUARY, 21, 7, 0));
      savedCreditThree.setUpdated(LocalDateTime.of(2017, Month.FEBRUARY, 21, 7, 0));
      Credit savedCreditFour = new Credit();
      BeanUtils.copyProperties(creditFour, savedCreditFour);
      savedCreditFour.setId(4L);
      savedCreditFour.setCreated(LocalDateTime.of(2017, Month.FEBRUARY, 21, 7, 0));
      savedCreditFour.setUpdated(LocalDateTime.of(2017, Month.FEBRUARY, 21, 7, 0));

      Mockito.when(creditRepository.findByTheMovieDBidInOrderByNameAsc(Arrays.asList(1, 2, 3, 4))).thenReturn(Collections.emptyList());
      Mockito.when(creditRepository.save(saveSet)).then(new Answer<List<Credit>>()
      {
         long id = 1;

         @Override
         public List<Credit> answer(InvocationOnMock invocation) throws Throwable
         {
            Collection<Credit> credits = invocation.getArgument(0);

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
      inOrder.verify(creditRepository, Mockito.calls(1)).save(saveSet);
      inOrder.verifyNoMoreInteractions();
   }

   @Test
   public void testSaveWhenSomeExist()
   {
      Credit creditOne = new Credit("Bill Murray", 1, "Dr. Peter Venkman", "A1");
      Credit creditTwo = new Credit("Dan Aykroyd", 2, "Dr. Raymon Stanze", "A2");
      Credit creditThree = new Credit("Harold Ramis", 3, "Dr. Egon Spengler", "A3");
      Credit creditFour = new Credit("Ernie Hudson", 4, "Winston Zeddmore", "A4");
      List<Credit> credits = Arrays.asList(creditOne, creditTwo, creditThree, creditFour);
      Set<Credit> saveSet = new TreeSet<>(Comparator.comparing(Credit::getTheMovieDBid));
      saveSet.add(creditThree);
      saveSet.add(creditFour);

      Credit savedCreditOne = new Credit();
      BeanUtils.copyProperties(creditOne, savedCreditOne);
      savedCreditOne.setId(1L);
      savedCreditOne.setCreated(LocalDateTime.of(2017, Month.FEBRUARY, 21, 7, 0));
      savedCreditOne.setUpdated(LocalDateTime.of(2017, Month.FEBRUARY, 21, 7, 0));
      Credit savedCreditTwo = new Credit();
      BeanUtils.copyProperties(creditTwo, savedCreditTwo);
      savedCreditTwo.setId(2L);
      savedCreditTwo.setCreated(LocalDateTime.of(2017, Month.FEBRUARY, 21, 7, 0));
      savedCreditTwo.setUpdated(LocalDateTime.of(2017, Month.FEBRUARY, 21, 7, 0));
      Credit savedCreditThree = new Credit();
      BeanUtils.copyProperties(creditThree, savedCreditThree);
      savedCreditThree.setId(3L);
      savedCreditThree.setCreated(LocalDateTime.of(2017, Month.FEBRUARY, 21, 8, 0));
      savedCreditThree.setUpdated(LocalDateTime.of(2017, Month.FEBRUARY, 21, 8, 0));
      Credit savedCreditFour = new Credit();
      BeanUtils.copyProperties(creditFour, savedCreditFour);
      savedCreditFour.setId(4L);
      savedCreditFour.setCreated(LocalDateTime.of(2017, Month.FEBRUARY, 21, 8, 0));
      savedCreditFour.setUpdated(LocalDateTime.of(2017, Month.FEBRUARY, 21, 8, 0));

      Mockito.when(creditRepository.findByTheMovieDBidInOrderByNameAsc(Arrays.asList(1, 2, 3, 4))).thenReturn(Arrays.asList(savedCreditOne, savedCreditTwo));
      Mockito.when(creditRepository.save(saveSet)).then(new Answer<List<Credit>>()
      {
         long id = 3;

         @Override
         public List<Credit> answer(InvocationOnMock invocation) throws Throwable
         {
            Collection<Credit> credits = invocation.getArgument(0);

            return credits.stream().map(c ->
            {
               Credit toReturn = new Credit();

               BeanUtils.copyProperties(c, toReturn);
               toReturn.setId(id++);
               toReturn.setCreated(LocalDateTime.of(2017, Month.FEBRUARY, 21, 8, 0));
               toReturn.setUpdated(LocalDateTime.of(2017, Month.FEBRUARY, 21, 8, 0));

               return toReturn;
            }).collect(Collectors.toList());
         }
      });

      List<Credit> result = jpaCreditService.saveAll(credits);

      Assertions.assertThat(result)
         .isNotEmpty()
         .containsOnly(savedCreditOne, savedCreditTwo, savedCreditThree, savedCreditFour)
      ;

      Mockito.verify(creditRepository).findByTheMovieDBidInOrderByNameAsc(Arrays.asList(1, 2, 3, 4));
      Mockito.verify(creditRepository).save(saveSet);

      InOrder inOrder = Mockito.inOrder(creditRepository);

      inOrder.verify(creditRepository, Mockito.calls(1)).findByTheMovieDBidInOrderByNameAsc(Arrays.asList(1, 2, 3, 4));
      inOrder.verify(creditRepository, Mockito.calls(1)).save(saveSet);
      inOrder.verifyNoMoreInteractions();
   }

   @Test
   public void testSaveOnAllExistingAlready()
   {
      Credit creditOne = new Credit("Bill Murray", 1, "Dr. Peter Venkman", "A1");
      Credit creditTwo = new Credit("Dan Aykroyd", 2, "Dr. Raymon Stanze", "A3");
      Credit creditThree = new Credit("Harold Ramis", 3, "Dr. Egon Spengler", "A2");
      Credit creditFour = new Credit("Ernie Hudson", 4, "Winston Zeddmore", "A4");
      List<Credit> credits = Arrays.asList(creditOne, creditTwo, creditThree, creditFour);

      Credit savedCreditOne = new Credit();
      BeanUtils.copyProperties(creditOne, savedCreditOne);
      savedCreditOne.setId(1L);
      savedCreditOne.setCreated(LocalDateTime.of(2017, Month.FEBRUARY, 21, 7, 0));
      savedCreditOne.setUpdated(LocalDateTime.of(2017, Month.FEBRUARY, 21, 7, 0));
      Credit savedCreditTwo = new Credit();
      BeanUtils.copyProperties(creditTwo, savedCreditTwo);
      savedCreditTwo.setId(2L);
      savedCreditTwo.setCreated(LocalDateTime.of(2017, Month.FEBRUARY, 21, 7, 0));
      savedCreditTwo.setUpdated(LocalDateTime.of(2017, Month.FEBRUARY, 21, 7, 0));
      Credit savedCreditThree = new Credit();
      BeanUtils.copyProperties(creditThree, savedCreditThree);
      savedCreditThree.setId(4L);
      savedCreditThree.setCreated(LocalDateTime.of(2017, Month.FEBRUARY, 21, 8, 0));
      savedCreditThree.setUpdated(LocalDateTime.of(2017, Month.FEBRUARY, 21, 8, 0));
      Credit savedCreditFour = new Credit();
      BeanUtils.copyProperties(creditFour, savedCreditFour);
      savedCreditFour.setId(4L);
      savedCreditFour.setCreated(LocalDateTime.of(2017, Month.FEBRUARY, 21, 8, 0));
      savedCreditFour.setUpdated(LocalDateTime.of(2017, Month.FEBRUARY, 21, 8, 0));

      Mockito.when(creditRepository.findByTheMovieDBidInOrderByNameAsc(Arrays.asList(1, 2, 3, 4))).thenReturn(Arrays.asList(savedCreditOne, savedCreditTwo, savedCreditFour, savedCreditThree));

      List<Credit> result = jpaCreditService.saveAll(credits);

      Assertions.assertThat(result)
         .isNotEmpty()
         .containsOnly(savedCreditOne, savedCreditTwo, savedCreditThree, savedCreditFour)
      ;

      Mockito.verify(creditRepository).findByTheMovieDBidInOrderByNameAsc(Arrays.asList(1, 2, 3, 4));

      InOrder inOrder = Mockito.inOrder(creditRepository);

      inOrder.verify(creditRepository, Mockito.calls(1)).findByTheMovieDBidInOrderByNameAsc(Arrays.asList(1, 2, 3, 4));
      inOrder.verify(creditRepository, Mockito.never()).save(Mockito.anyCollection());
      inOrder.verifyNoMoreInteractions();
   }
}
