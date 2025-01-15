package pl.sliepov.egzamin.infrastructure.persistence.discipline;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.sliepov.egzamin.domain.model.discipline.Discipline;

@Entity
@Table(name = "discipline")
@NoArgsConstructor
@Getter
public class DisciplineEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String professor;

    // mapowanie do domeny:
    public Discipline toDomain() {
        return new Discipline(id, name, professor);
    }


    // tworzenie encji z domeny
    public static DisciplineEntity fromDomain(Discipline discipline) {
        DisciplineEntity disciplineEntity = new DisciplineEntity();
        disciplineEntity.id = discipline.getId();
        disciplineEntity.name = discipline.getName();
        disciplineEntity.professor = discipline.getProfessor();
        return disciplineEntity;
    }
}
