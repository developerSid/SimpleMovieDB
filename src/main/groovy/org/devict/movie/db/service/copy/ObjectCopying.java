package org.devict.movie.db.service.copy;

/**
 * Created by developerSid on 2/5/17.
 *
 * Interface for copying objects deeply
 */
public interface ObjectCopying
{
   <T> T copy(T t);
}
