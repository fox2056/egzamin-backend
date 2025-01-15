package pl.sliepov.egzamin.domain.model.discipline;


public class Discipline {

    private  Long id;
    private String name;
    private String professor;

    public Discipline(Long id, String name, String professor) {
        this.id = id;
        this.name = name;
        this.professor = professor;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getProfessor() {
        return professor;
    }
}
