package org.devict.movie.db.service;

import org.devict.movie.db.entity.Credit;

import java.util.Collection;
import java.util.List;

/**
 * Created by developerSid on 1/12/17.
 *
 * Describes the operations provided for saving and loading people
 */
public interface CreditService
{
   List<Credit> saveAll(Collection<Credit> credits);
}
