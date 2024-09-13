package ru.job4j.dreamjob.repository;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Vacancy;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
@Repository
public class MemoryVacancyRepository implements VacancyRepository {

    private final AtomicInteger nextId = new AtomicInteger(1);

    private final Map<Integer, Vacancy> vacancies = new ConcurrentHashMap<>();

    private MemoryVacancyRepository() {
        save(new Vacancy(0, "Intern Java Developer", "В поиске Intern", LocalDateTime.now(), false, 1));
        save(new Vacancy(0, "Junior Java Developer", "В поиске Junior", LocalDateTime.of(2024, 8, 30, 0, 0), true, 1));
        save(new Vacancy(0, "Junior+ Java Developer", "В поиске Junior+", LocalDateTime.now(), false, 3));
        save(new Vacancy(0, "Middle Java Developer", "В поиске Middle", LocalDateTime.of(2024, 8, 27, 0, 0), true, 1));
        save(new Vacancy(0, "Middle+ Java Developer", "В поиске Middle+", LocalDateTime.now(), true, 2));
        save(new Vacancy(0, "Senior Java Developer", "В поиске Senior", LocalDateTime.of(2024, 9, 9, 0, 0), true, 1));
    }

    @Override
    public Vacancy save(Vacancy vacancy) {
        vacancy.setId(nextId.getAndIncrement());
        vacancies.put(vacancy.getId(), vacancy);
        return vacancy;
    }

    @Override
    public boolean deleteById(int id) {
        return vacancies.remove(id) != null;
    }

    @Override
    public boolean update(Vacancy vacancy) {
        return vacancies.computeIfPresent(vacancy.getId(), (id, oldVacancy) -> {
            return new Vacancy(
                    oldVacancy.getId(), vacancy.getTitle(), vacancy.getDescription(),
                    vacancy.getCreationDate(), vacancy.getVisible(), vacancy.getCityId()
            );
        }) != null;
    }

    @Override
    public Optional<Vacancy> findById(int id) {
        return Optional.ofNullable(vacancies.get(id));
    }

    @Override
    public Collection<Vacancy> findAll() {
        return vacancies.values();
    }
}
