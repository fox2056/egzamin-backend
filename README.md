# System Egzaminacyjny

System do zarządzania pytaniami egzaminacyjnymi i przeprowadzania testów.

## Funkcjonalności

### Zarządzanie Pytaniami

- Tworzenie pytań jednokrotnego i wielokrotnego wyboru
- Edycja i usuwanie pytań
- Automatyczne usuwanie pustych dyscyplin po usunięciu ostatniego pytania
- System oceniania pytań (pozytywne/negatywne) z komentarzami

### Zarządzanie Dyscyplinami

- Tworzenie i edycja dyscyplin
- Usuwanie dyscyplin
- Łączenie dyscyplin wraz z przenoszeniem pytań

### Testy

- Generowanie testów z wybranych dyscyplin
- Wykluczanie wybranych dyscyplin z testu
- Zapisywanie wyników testów
- Statystyki testów

## API Endpoints

### Pytania

```http
POST   /api/questions                     # Tworzenie pytania
GET    /api/questions/{id}                # Pobieranie pytania z ocenami
GET    /api/questions/discipline/{id}     # Lista pytań z dyscypliny (z ocenami)
PATCH  /api/questions/{id}/discipline     # Zmiana dyscypliny pytania
DELETE /api/questions/{id}                # Usuwanie pytania

# Oceny pytań
POST   /api/questions/{id}/ratings        # Dodawanie oceny
GET    /api/questions/{id}/ratings/stats  # Statystyki ocen
```

### Dyscypliny

```http
POST   /api/disciplines                   # Tworzenie dyscypliny
GET    /api/disciplines                   # Lista dyscyplin
GET    /api/disciplines/{id}              # Szczegóły dyscypliny
PATCH  /api/disciplines/{id}              # Aktualizacja dyscypliny
DELETE /api/disciplines/{id}              # Usuwanie dyscypliny
POST   /api/disciplines/{sourceId}/merge/{targetId}  # Łączenie dyscyplin
```

### Testy

```http
POST   /api/tests                         # Tworzenie testu
GET    /api/tests/{id}                    # Pobieranie testu
POST   /api/tests/{id}/submit            # Przesyłanie odpowiedzi
GET    /api/tests/student/{email}        # Historia testów studenta
```

## Przykłady Użycia

### Ocenianie Pytania

```json
POST /api/questions/1/ratings?isPositive=true
{
    "comment": "Bardzo dobre pytanie"
}
```

### Łączenie Dyscyplin

```http
POST /api/disciplines/1/merge/2
```

Przenosi wszystkie pytania z dyscypliny o ID=1 do dyscypliny o ID=2 i usuwa pierwszą dyscyplinę.

## Technologie

- Java 23
- Spring Boot 3.x
- Spring Data JPA
- H2/MySQL
- Gradle

## 👥 Autorzy

- Oleksii Sliepov

## 📄 Licencja

Ten projekt jest licencjonowany na warunkach [MIT License](LICENSE).
