package org.devict.movie.db.service;

import org.assertj.core.api.Assertions;
import org.devict.movie.db.entity.Credit;
import org.devict.movie.db.repository.CreditRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

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
   public void testSave()
   {
      Credit credit = new Credit("Jay Leno", 1, "host", "A2");

      Mockito.when(creditRepository.save(credit)).then(invocation -> {
         Credit toReturn = invocation.getArgument(0);

         toReturn.setId(15L);

         return toReturn;
      });
      Credit result = jpaCreditService.save(credit);

      Assertions.assertThat(result)
         .isNotNull()
         .hasNoNullFieldsOrProperties()
         .extracting("id").isEqualTo(15)
         .extracting("theMovieDBid").isEqualTo(1)
      ;
   }
}
