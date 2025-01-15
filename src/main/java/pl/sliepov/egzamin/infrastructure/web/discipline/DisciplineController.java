package pl.sliepov.egzamin.infrastructure.web.discipline;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.sliepov.egzamin.application.usecase.discipline.ManageDisciplinesService;
import pl.sliepov.egzamin.domain.model.discipline.Discipline;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/disciplines")
@AllArgsConstructor
public class DisciplineController {

    private final ManageDisciplinesService manageDisciplinesService;

    @PostMapping
    public ResponseEntity<DisciplineEntityDto> createDiscipline(@RequestBody DisciplineEntityDto dto) {
        Discipline created = manageDisciplinesService.createDiscipline(dto.name(), dto.professor());
        DisciplineEntityDto result = new DisciplineEntityDto(created.getId(), created.getName(), created.getProfessor());

        URI location = URI.create("/api/disciplines/" + created.getId());
        return ResponseEntity.created(location).body(result);
    }

    @GetMapping
    public ResponseEntity<List<DisciplineEntityDto>> getAllDisciplines() {
        List<Discipline> disciplines = manageDisciplinesService.getAllDisciplines();


        return ResponseEntity.ok(disciplines.stream().map(d -> new DisciplineEntityDto(d.getId(), d.getName(), d.getProfessor())).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DisciplineEntityDto> getDiscipline(@PathVariable Long id) {
        Discipline discipline = manageDisciplinesService.getDiscipline(id);
        DisciplineEntityDto result = new DisciplineEntityDto(discipline.getId(), discipline.getName(), discipline.getProfessor());
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDiscipline(@PathVariable Long id) {
        manageDisciplinesService.deleteDiscipline(id);
        return ResponseEntity.noContent().build();
    }

}
