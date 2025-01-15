package pl.sliepov.egzamin.infrastructure.web.discipline.dto;

public record UpdateDisciplineDto(
        String name,
        String professor) {

    public void validate() {
        if (name != null && name.isBlank()) {
            throw new IllegalArgumentException("Nazwa nie może być pusta");
        }
        if (professor != null && professor.isBlank()) {
            throw new IllegalArgumentException("Nazwisko prowadzącego nie może być puste");
        }
    }
}