package ru.job4j.dreamjob.repository;

import ru.job4j.dreamjob.model.Candidate;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MemoryCandidateRepository implements CandidateRepository {

    private static final MemoryCandidateRepository INSTANCE = new MemoryCandidateRepository();

    private int nextId = 1;

    private final Map<Integer, Candidate> candidates = new HashMap<>();

    public static MemoryCandidateRepository getInstance() {
        return INSTANCE;
    }

    private MemoryCandidateRepository() {
        save(new Candidate(0, "Василий Васильков", "Junior", LocalDateTime.now()));
        save(new Candidate(0, "Андрей Андреев", "Junior", LocalDateTime.of(2024, 9, 10, 0, 0)));
        save(new Candidate(0, "Петр Петров", "Junior+", LocalDateTime.now()));
        save(new Candidate(0, "Алена Аленкина", "Middle", LocalDateTime.of(2024, 9, 7, 0, 0)));
    }

    @Override
    public Candidate save(Candidate candidate) {
        candidate.setId(nextId++);
        candidates.put(candidate.getId(), candidate);
        return candidate;
    }

    @Override
    public void deleteById(int id) {
        candidates.remove(id);
    }

    @Override
    public boolean update(Candidate candidate) {
        return candidates.computeIfPresent(candidate.getId(),
                (id, oldCandidate) -> new Candidate(oldCandidate.getId(),
                        candidate.getName(),
                        candidate.getDescription(),
                        candidate.getCreationDate())) != null;
    }

    @Override
    public Optional<Candidate> findById(int id) {
        return Optional.ofNullable(candidates.get(id));
    }

    @Override
    public Collection<Candidate> findAll() {
        return candidates.values();
    }
}
