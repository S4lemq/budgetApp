# BudgetApp: o projekcie 馃挵

Jest to m贸j g艂贸wny projekt, kt贸ry wci膮偶 rozwijam wykorzystuj膮ca Spring Boot'a. Za pomoc膮 aplikacji mo偶emy kontrolowa膰 bud偶et osobisty, uznania i obci膮偶enia.
Dodatkow膮 funkcj膮 jest zarz膮dzanie nieruchomo艣ciami: wynajem pokoi/mieszka艅. Wykorzystuj臋 REST API do komunikacji.
Dane s膮 przechowywane w relacyjnej bazie danych PostgreSQL. U偶ytkownik ma mo偶liwo艣膰 stworzenia konta (autoryzacja oraz autentykacja za pomoc膮 tokena JWT) stworzona przy 
pomocy Spring Security. Zaimplementowa艂em walidacj臋 obiekt贸w, oraz wy艣wietlanie komunitak贸w o b艂臋dach wykorzystuj膮c wzorzec projektowy 艂a艅cuch zobowi膮za艅. 
Filtrowanie pobieranych przychod贸w wykorzystuj膮c wzorzec projektowy metoda wytw贸rcza. Aplikacja posiada mappery, kt贸re mapuj膮 Dto<=>Encje, wykorzystuj臋 przy tym wzorzec
projektowy budowniczy, kt贸ry tworzy obiekty.

# Jak w艂膮czy膰 aplikacj臋
1. Otw贸rz projekt w InteliJ, przejd藕 do: budgetApp/src/main/java/com/salem/budgetApp/BudgetAppApplication.java
prawy klik na Application.java -> Odpal 'Application.main()'
2. Otw贸rz g艂贸wny folder budgetApp -> Otw贸rz cmd i wpisz: mvn spring-boot:run
3. Otw贸rz aplikacje: Aplikacja w艂膮czy si臋 pod adresem http://localhost:8080/

# Co mo偶esz znale藕膰 w moim projekcie
- REST API
- CRUD
- DTO
- Wzorce projektowe: budowniczy, 艂a艅cuch zobowi膮za艅, metoda wytw贸rcza
- validatory
- mappery
- logi aplikacji
- testy jednostkowe oraz integracyjne

# U偶yte technologie
- Java 11
- Spring Boot
- Hibernate/JPA
- PostgreSQL
- JUnit
- AssertJ
- Liquibase
- Mockito
#
