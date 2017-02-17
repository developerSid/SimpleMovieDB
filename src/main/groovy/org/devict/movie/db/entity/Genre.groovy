package org.devict.movie.db.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.EqualsAndHashCode
import groovy.transform.Sortable
import groovy.transform.ToString

import javax.persistence.*
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

/**
 * Created by developerSid on 1/22/17.
 *
 * Entity container for the movie genre captured from The Movie DB
 */
@Entity
@Sortable(excludes = "movies")
@EqualsAndHashCode
@ToString(includeNames = true, includeFields = true)
@Table(name = "genre", indexes = @Index(columnList = "name"))
@JsonIgnoreProperties(ignoreUnknown = true)
class Genre extends Storable
{
   @NotNull
   @Size(min = 2, max = 100)
   @Column(unique = true, nullable = false, length = 100)
   String name

   @NotNull
   @JsonProperty(value = "id")
   @Column(name = "tmdb_id", nullable = false)
   Integer theMovieDBid

   @ManyToMany(mappedBy = "genres")
   List<Movie> movies = []

   Genre()
   {

   }
   Genre(String name, int theMovieDBid)
   {
      this.name = name
      this.theMovieDBid = theMovieDBid
   }

   @JsonProperty(value = "id")
   void setTheMovieDBid(Integer theMovieDBid)
   {
      this.theMovieDBid = theMovieDBid
   }
}
