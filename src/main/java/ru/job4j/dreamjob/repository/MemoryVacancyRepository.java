package ru.job4j.dreamjob.repository;

import ru.job4j.dreamjob.model.Vacancy;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MemoryVacancyRepository implements VacancyRepository {

    private static final MemoryVacancyRepository INSTANCE = new MemoryVacancyRepository();

    private int nextId = 1;

    private final Map<Integer, Vacancy> vacancies = new HashMap<>();

    private MemoryVacancyRepository() {
        save(new Vacancy(0, "Intern Java Developer", "В поиске Intern", LocalDateTime.now()));
        save(new Vacancy(0, "Junior Java Developer", "В поиске Junior", LocalDateTime.of(2024, 8, 30, 0, 0)));
        save(new Vacancy(0, "Junior+ Java Developer", "В поиске Junior+", LocalDateTime.now()));
        save(new Vacancy(0, "Middle Java Developer", "В поиске Middle", LocalDateTime.of(2024, 8, 27, 0, 0)));
        save(new Vacancy(0, "Middle+ Java Developer", "В поиске Middle+", LocalDateTime.now()));
        save(new Vacancy(0, "Senior Java Developer", "В поиске Senior", LocalDateTime.of(2024, 9, 9, 0, 0)));
    }

    public static MemoryVacancyRepository getInstance() {
        return INSTANCE;
    }

    @Override
    public Vacancy save(Vacancy vacancy) {
        vacancy.setId(nextId++);
        vacancies.put(vacancy.getId(), vacancy);
        return vacancy;
    }

    @Override
    public void deleteById(int id) {
        vacancies.remove(id);
    }

    @Override
    public boolean update(Vacancy vacancy) {
        return vacancies.computeIfPresent(vacancy.getId(),
                (id, oldVacancy) -> new Vacancy(oldVacancy.getId(),
                        vacancy.getTitle(),
                        vacancy.getDescription(),
                        vacancy.getCreationDate())) != null;
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
