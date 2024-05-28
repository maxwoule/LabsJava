import java.util.*;
import java.util.stream.Collectors;

public class CinemaDatabase {
    Set<Film> films;
    Set<Actor> actors;
    Map<Actor, Set<Film>> actorFilmMap;

    public CinemaDatabase() {
        this.films = new HashSet<>();
        this.actors = new HashSet<>();
        this.actorFilmMap = new HashMap<>();
    }

    // Додати фільм
    public void addFilm(Film film) {
        films.add(film);
        for (Actor actor : film.actors) {
            actors.add(actor);
            actor.films.add(film);

            // Додати актора та фільм у Map
            actorFilmMap.computeIfAbsent(actor, k -> new HashSet<>()).add(film);
        }
    }

    // Завдання 1: Визначити, чи є актор, який не зіграв в жодному фільмі
    public boolean hasActorWithNoFilms() {
        return actors.stream().anyMatch(actor -> actor.films.isEmpty());
    }

    // Завдання 2: Скласти список акторів, з якими коли-небудь в одному фільмі грав заданий актор
    public Set<Actor> getCoactors(Actor targetActor) {
        return targetActor.films.stream()
                .flatMap(film -> film.actors.stream())
                .filter(actor -> !actor.equals(targetActor))
                .collect(Collectors.toSet());
    }

    public Set<String> getCoactorsNames(Actor targetActor) {
        return getCoactors(targetActor).stream()
                .map(actor -> actor.name)
                .collect(Collectors.toSet());
    }

    // Завдання 3: Знайти фільм з найбільшою кількістю акторів
    public Film findFilmWithMostActors() {
        return films.stream()
                .max(Comparator.comparingInt(film -> film.actors.size()))
                .orElse(null);
    }

    // Додатковий метод: Вивести всі фільми для заданого актора
    public Set<String> getFilmsForActor(Actor actor) {
        return Optional.ofNullable(actorFilmMap.get(actor))
                .orElse(Collections.emptySet())
                .stream()
                .map(film -> film.title)
                .collect(Collectors.toSet());
    }

    // Метод для виводу інформації про акторів у фільмі
    public void printActorsInFilm(Film film) {
        System.out.println("Actors in film \"" + film.title + "\":");
        for (Actor actor : film.actors) {
            System.out.println("- " + actor.name);
        }
    }
}
