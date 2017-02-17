package org.devict.movie.db.entity.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Created by developerSid on 1/31/17.
 *
 * JPA converter for Java 8 Date time api {@link LocalDateTime} since JPA 2.1 does not support that type yet
 */
@Converter(autoApply = true)
public class LocalDateTimeAttributeConverter implements AttributeConverter<LocalDateTime, Timestamp>
{
   @Override
   public Timestamp convertToDatabaseColumn(LocalDateTime locDateTime)
   {
      return (locDateTime == null ? null : Timestamp.valueOf(locDateTime));
   }

   @Override
   public LocalDateTime convertToEntityAttribute(Timestamp sqlTimestamp)
   {
      return (sqlTimestamp == null ? null : sqlTimestamp.toLocalDateTime());
   }
}