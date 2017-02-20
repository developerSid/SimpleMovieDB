package org.devict.movie.db.entity

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import org.apache.commons.lang3.builder.CompareToBuilder
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate

import javax.persistence.*
import java.time.LocalDateTime

/**
 * Created by developerSid on 1/28/17.
 *
 * Contains the basic components of all the Entities described by the system.
 */
@EqualsAndHashCode
@ToString(includeNames = true, includeFields = true)
@MappedSuperclass
abstract class Storable implements Serializable, Comparable<Storable>
{
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   Long id

   @CreatedDate
   LocalDateTime created

   @LastModifiedDate
   LocalDateTime updated

   @PrePersist
   private void onCreate() {
      created = LocalDateTime.now()
      updated = created
   }

   @PreUpdate
   private void onUpdate() {
      updated = LocalDateTime.now()
   }

   @Override
   int compareTo(Storable o)
   {
      CompareToBuilder to = new CompareToBuilder()

      to.append(this.id, o.id)
      to.append(this.created, o.created)
      to.append(this.updated, o.updated)

      return to.build()
   }
}