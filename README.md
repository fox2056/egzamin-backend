# System Egzaminacyjny

System do zarządzania pytaniami egzaminacyjnymi z różnych dyscyplin.

## Funkcjonalności

- Zarządzanie dyscyplinami (przedmiotami)
- Zarządzanie pytaniami (dodawanie, usuwanie, modyfikacja)
- Import pytań z pliku JSON
- Obsługa pytań jednokrotnego i wielokrotnego wyboru
- System oceniania pytań (łapka w górę/dół)
- System testów z automatycznym sprawdzaniem
- Losowy wybór pytań z zachowaniem proporcji między dyscyplinami

## Endpointy API

### Pytania

- `POST /api/questions/import` - import pytań z pliku JSON
  - Content-Type: `multipart/form-data`
  - Parametr: `file` - plik JSON z pytaniami
- `GET /api/questions/{id}` - pobranie pojedynczego pytania
- `GET /api/questions/discipline/{disciplineId}` - pobranie wszystkich pytań z danej dyscypliny
- `DELETE /api/questions/{id}` - usunięcie pytania
- `PATCH /api/questions/{questionId}/discipline?newDisciplineId={id}` - zmiana dyscypliny pytania

### Dyscypliny

- `GET /api/disciplines` - lista wszystkich dyscyplin
- `GET /api/disciplines/{id}` - pobranie pojedynczej dyscypliny
- `DELETE /api/disciplines/{id}` - usunięcie dyscypliny

### Oceny pytań

- `POST /api/questions/{questionId}/ratings?isPositive={true/false}&comment={text}` - dodanie oceny pytania
- `GET /api/questions/{questionId}/ratings/stats` - pobranie statystyk ocen pytania

### Testy

- `POST /api/tests` - rozpoczęcie nowego testu
- `GET /api/tests/student/{email}` - pobranie historii testów studenta
- `GET /api/tests/{id}` - pobranie szczegółów testu
- `GET /api/tests/{id}/questions` - pobranie pytań dla testu
- `POST /api/tests/{id}/submit` - przesłanie odpowiedzi i zakończenie testu

## Formaty danych

### Format pliku do importu pytań (JSON)

```json
[
  {
    "disciplineName": "Algorytmy tekstowe",
    "content": "Który algorytm wyszukiwania danych w tekście porównuje znaki od końca wzorca?",
    "type": "SINGLE_CHOICE",
    "correctAnswers": ["Algorytm Boyer-Moore"],
    "incorrectAnswers": [
      "Naiwne wyszukiwanie",
      "Algorytm KMP",
      "Algorytm Rabin-Karp"
    ]
  }
]
```

### Format rozpoczęcia testu

```json
{
  "studentName": "Jan Kowalski",
  "studentEmail": "jan.kowalski@example.com",
  "includedDisciplineIds": [1, 2, 3],
  "excludedDisciplineIds": [4, 5],
  "numberOfQuestions": 10
}
```

### Format odpowiedzi dla pytań testu

```json
{
  "questions": [
    {
      "id": 1,
      "content": "Treść pytania",
      "type": "SINGLE_CHOICE",
      "correctAnswers": ["Odpowiedź A"],
      "incorrectAnswers": ["Odpowiedź B", "Odpowiedź C"],
      "disciplineId": 1
    }
  ],
  "message": "Uwaga: Dostępnych jest tylko 8 pytań z wybranych dyscyplin, zamiast żądanych 10 pytań.",
  "hasWarning": true
}
```

### Format przesyłania odpowiedzi testu

```json
[
  {
    "questionId": 1,
    "selectedAnswers": ["Odpowiedź A"]
  },
  {
    "questionId": 2,
    "selectedAnswers": ["Odpowiedź B", "Odpowiedź C"]
  }
]
```

## Uruchomienie

1. Wymagania:

   - Java 23
   - Gradle
   - Mysql

2. Konfiguracja:

   - Ustaw zmienne środowiskowe:
     - `DB_URL` - URL do bazy danych
     - `DB_USER` - nazwa użytkownika bazy danych
     - `DB_PASSWORD` - hasło do bazy danych

3. Uruchomienie aplikacji:

```bash
./gradlew bootRun
```

## Szczegóły implementacji

- Architektura heksagonalna (ports & adapters)
- Domain-Driven Design (DDD)
- Spring Boot 3.x
- Spring Data JPA
- Mysql
