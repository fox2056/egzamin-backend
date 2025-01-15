package pl.sliepov.egzamin.infrastructure.web.test;

import java.util.List;

public record StartTestDto(
        String studentName,
        String studentEmail,
        List<Long> includedDisciplineIds,
        List<Long> excludedDisciplineIds,
        Integer numberOfQuestions) {
    public void validate() {
        if (studentName == null || studentName.isBlank()) {
            throw new IllegalArgumentException("Imię studenta jest wymagane");
        }
        if (studentEmail == null || studentEmail.isBlank()) {
            throw new IllegalArgumentException("Email studenta jest wymagany");
        }
        if (includedDisciplineIds == null || includedDisciplineIds.isEmpty()) {
            throw new IllegalArgumentException("Wymagana jest co najmniej jedna dyscyplina");
        }
        if (numberOfQuestions == null || numberOfQuestions < 1) {
            throw new IllegalArgumentException("Liczba pytań musi być większa od 0");
        }
    }
}