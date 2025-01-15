package pl.sliepov.egzamin.infrastructure.web.discipline;

import pl.sliepov.egzamin.infrastructure.persistence.discipline.DisciplineEntity;

import java.io.Serializable;

/**
 * DTO for {@link DisciplineEntity}
 */
public record DisciplineEntityDto(
        Long id,
        String name,
        String professor
) implements Serializable {
}