# BudgetApp: o projekcie 💰

Jest to mój główny projekt, który wciąż rozwijam wykorzystująca Spring Boot'a. Za pomocą aplikacji możemy kontrolować budżet osobisty, uznania i obciążenia.
Dodatkową funkcją jest zarządzanie nieruchomościami: wynajem pokoi/mieszkań. Wykorzystuję REST API do komunikacji.
Dane są przechowywane w relacyjnej bazie danych PostgreSQL. Użytkownik ma możliwość stworzenia konta (autoryzacja oraz autentykacja za pomocą tokena JWT) stworzona przy 
pomocy Spring Security. Zaimplementowałem walidację obiektów, oraz wyświetlanie komunitaków o błędach wykorzystując wzorzec projektowy łańcuch zobowiązań. 
Filtrowanie pobieranych przychodów wykorzystując wzorzec projektowy metoda wytwórcza. Aplikacja posiada mappery, które mapują Dto<=>Encje, wykorzystuję przy tym wzorzec
projektowy budowniczy, który tworzy obiekty.

# Jak włączyć aplikację:
1. Otwórz projekt w InteliJ, przejdź do: budgetApp/src/main/java/com/salem/budgetApp/BudgetAppApplication.java
prawy klik na Application.java -> Odpal 'Application.main()'
2. Otwórz główny folder budgetApp -> Otwórz cmd i wpisz: mvn spring-boot:run
3. Otwórz aplikacje: Aplikacja włączy się pod adresem http://localhost:8080/

# Co możesz znaleźć w moim projekcie
- REST API
- CRUD
- DTO
- Wzorce projektowe: budowniczy, łańcuch zobowiązań, metoda wytwórcza
- validatory
- mappery
- logi aplikacji
- testy jednostkowe oraz integracyjne

# Użyte technologie
- Java 11
- Spring Boot
- Hibernate/JPA
- PostgreSQL
- JUnit
- AssertJ
- Liquibase
- Mockito
#
