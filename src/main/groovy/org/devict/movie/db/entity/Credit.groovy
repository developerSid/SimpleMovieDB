package org.devict.movie.db.entity

import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.EqualsAndHashCode
import groovy.transform.Sortable
import groovy.transform.ToString

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Index
import javax.persistence.Table
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size
import java.time.LocalDateTime

/**
 * Created by developerSid on 1/12/17.
 *
 * Models a person can be anyone involved with a movie
 */
@Entity
@Sortable
@EqualsAndHashCode
@ToString(includeNames = true, includeFields = true)
@Table(name = "credit", indexes = @Index(columnList = "name"))
class Credit extends Storable
{
   @NotNull
   @Size(min = 2, max = 150)
   @Column(length = 150, nullable = false)
   String name

   @NotNull
   @JsonProperty(value = "id")
   @Column(name = "tmdb_id", unique = true, nullable = false)
   Integer theMovieDBid

   @NotNull
   @Size(min = 3, max = 150)
   @Column(length = 150, nullable = false)
   String job

   @NotNull
   @Size(min = 10, max = 100)
   @Column(length = 100, nullable = false)
   String creditId

   Credit()
   {

   }

   Credit(String name, int theMovieDBid, String job, String creditId)
   {
      this.name = name
      this.theMovieDBid = theMovieDBid
      this.job = job
      this.creditId = creditId
   }
}
