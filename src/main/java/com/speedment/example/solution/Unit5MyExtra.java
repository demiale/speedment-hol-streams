package com.speedment.example.solution;

import com.speedment.common.tuple.Tuple2;
import com.speedment.common.tuple.Tuple3;
import com.speedment.common.tuple.Tuples;
import com.speedment.example.domainmodel.sakila.sakila.sakila.actor.Actor;
import com.speedment.example.domainmodel.sakila.sakila.sakila.film.Film;
import com.speedment.example.domainmodel.sakila.sakila.sakila.film_actor.FilmActor;
import com.speedment.example.domainmodel.sakila.sakila.sakila.film_actor.FilmActorManager;
import com.speedment.example.unit.Unit5Extra;
import com.speedment.runtime.join.Join;
import com.speedment.runtime.join.JoinComponent;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public final class Unit5MyExtra implements Unit5Extra {

    @Override
    public Map<Actor, Long> actorToFilmCount(JoinComponent joinComponent) {
        // This is a predefined JOIN which is given for the task at hand
        // This join can produce streams with Tuple3<FilmActor, Film, Actor> elements
        // The FilmActor can be extracted by invoking Tuple3::get0
        // The Film can be extracted by invoking Tuple3::get1
        // The Actor can be extracted by invoking Tuple3::get2
        Join<Tuple3<FilmActor, Film, Actor>> join = joinComponent
            .from(FilmActorManager.IDENTIFIER)
            .innerJoinOn(Film.FILM_ID).equal(FilmActor.FILM_ID)
            .innerJoinOn(Actor.ACTOR_ID).equal(FilmActor.ACTOR_ID)
            .build(Tuples::of);

        return join.stream()
                .collect(Collectors
                        .groupingBy(Tuple3::get2, Collectors.mapping(Tuple3::get1, Collectors.counting())));
    }

    @Override
    public Map<Actor, List<Film>> filmographies(JoinComponent joinComponent) {
        Join<Tuple2<Film, Actor>> join = joinComponent
            .from(FilmActorManager.IDENTIFIER)
            .innerJoinOn(Film.FILM_ID).equal(FilmActor.FILM_ID)
            .innerJoinOn(Actor.ACTOR_ID).equal(FilmActor.ACTOR_ID)
            .build((fa, f, a) -> Tuples.of(f, a)); // Apply a custom constructor, discarding FilmActor

        return join.stream()
                .collect(Collectors
                        .groupingBy(Tuple2::get1, Collectors.mapping(Tuple2::get0, Collectors.toList())));
    }

    @Override
    public Map<String, List<String>> filmographiesNames(JoinComponent joinComponent) {

        Join<Tuple2<Film, Actor>> join = joinComponent.from(FilmActorManager.IDENTIFIER)
                .innerJoinOn(Film.FILM_ID).equal(FilmActor.FILM_ID)
                .innerJoinOn(Actor.ACTOR_ID).equal(FilmActor.ACTOR_ID)
                .build((fa, f, a) -> Tuples.of(f, a));

        return join.stream().collect(Collectors.groupingBy(Tuple2.<Film, Actor>getter1().andThen(Actor::getLastName),
                Collectors.mapping(Tuple2.<Film, Actor>getter0().andThen(Film::getTitle), Collectors.toList())));

    }

    @Override
    public Map<Actor, Map<String, Long>> pivot(JoinComponent joinComponent) {


        Join<Tuple2<Actor, Film>> join = joinComponent.from(FilmActorManager.IDENTIFIER)
                .innerJoinOn(Actor.ACTOR_ID).equal(FilmActor.ACTOR_ID)
                .innerJoinOn(Film.FILM_ID).equal(FilmActor.FILM_ID)
                .build((fa, a, f) -> Tuples.of(a, f));

        return join.stream().collect(Collectors.groupingBy(Tuple2::get0,
                Collectors.groupingBy(Tuple2.<Actor, Film>getter1().andThen(Film.RATING), Collectors.counting())));
    }

    @Override
    public LongStream factorials() {
        return LongStream.empty();
    }

    @Override
    public Stream<BigInteger> bigFactorials() {
        return Stream.empty();
    }

}
