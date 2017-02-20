package org.devict.movie.db.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import groovy.transform.EqualsAndHashCode
import groovy.transform.Sortable
import groovy.transform.ToString
import org.devict.movie.db.entity.json.deserializer.LocalDateJsonDeserialzier

import javax.persistence.*
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size
import java.time.LocalDate

/**
 * Created by developerSid on 1/11/17.
 *
 * Represents a movie
 */
@Entity
@Sortable(excludes = ["genres", "directors", "cast"])
@EqualsAndHashCode(callSuper = true)
@ToString(includeNames = true, includeFields = true, includeSuper = true)
@Table(name = "movie", indexes = @Index(columnList = "title"))
@JsonIgnoreProperties(ignoreUnknown = true)
@NamedEntityGraph(
   name = "graph.movies.complete",
   attributeNodes = [
      @NamedAttributeNode(value = "genres", subgraph = "genres"),
      @NamedAttributeNode(value = "directors", subgraph = "directors"),
      @NamedAttributeNode(value = "cast", subgraph = "cast")
   ]
)
class Movie extends Storable
{
   @NotNull
   @Size(min = 2, max = 150)
   @Column(length = 150, nullable = false)
   String title

   @JsonProperty(value = "overview")
   @Lob
   String description

   @JsonDeserialize(using = LocalDateJsonDeserialzier.class)
   @JsonProperty(value = "release_date")
   @Column(nullable = true)
   LocalDate releaseDate

   @JsonProperty(value = "id")
   @Column(name = "tmdb_id", nullable = false)
   Integer theMovieDBid

   @ManyToMany(cascade = CascadeType.ALL)
   @JoinTable(name = "movie_genre",
      joinColumns = @JoinColumn(name = "movie_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "genre_id", referencedColumnName = "id")
   )
   List<Genre> genres = []

   @ManyToMany(cascade = CascadeType.ALL)
   @JoinTable(name = "movie_director",
      joinColumns = @JoinColumn(name = "movie_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "director_id", referencedColumnName = "id")
   )
   List<Credit> directors = []

   @ManyToMany(cascade = CascadeType.ALL)
   @JoinTable(name = "movie_cast",
      joinColumns = @JoinColumn(name = "movie_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "cast_id", referencedColumnName = "id")
   )
   List<Credit> cast = []

   Movie()
   {

   }

   Movie(String title, String description, int theMovieDBid, LocalDate releaseDate)
   {
      this(title, description, releaseDate, theMovieDBid, [], [])
   }

   Movie(String title, String description, LocalDate releaseDate, int theMovieDBid, List<Genre> genres, List<Credit> directors)
   {
      this.title = title
      this.description = description
      this.releaseDate = releaseDate
      this.theMovieDBid = theMovieDBid
      this.genres = genres
      this.directors = directors
   }

   @JsonProperty(value = "id") //have to put in an explicit setter so Jackson maps this correctly for rest
   void setTheMovieDBid(Integer theMovieDBid)
   {
      this.theMovieDBid = theMovieDBid
   }
}
