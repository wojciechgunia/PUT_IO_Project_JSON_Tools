[![en](https://img.shields.io/badge/lang-en-red.svg)](https://github.com/wojciechgunia/PUT_IO_Project_JSON_Tools/README-en.md)

# JSON Tools - Backend

### GUI Angular: [JSON Tools FE](https://github.com/wojciechgunia/PUT_IO_Project_JSON_Tools_FE)

### Autorzy wykonanego projektu:
<img src="https://skillicons.dev/icons?i=github" height="25" alt="github logo"/> [Wojciech Gunia](https://github.com/wojciechgunia)<br>
<img src="https://skillicons.dev/icons?i=github" height="25" alt="github logo"/> [Remigiusz Janicki](https://github.com/TheRemekk)<br>
<img src="https://skillicons.dev/icons?i=github" height="25" alt="github logo"/> [Filip Kempa](https://github.com/Pilif102)<br>
<img src="https://skillicons.dev/icons?i=github" height="25" alt="github logo"/> [Paweł Kołaciński](https://github.com/KolacinskiP)

## Spis treści

1. [Opis](#l1)
2. [Instalacja](#l2)
3. [Funkcje](#l3)
4. [Struktura projektu](#l4)

<a id="l1"></a>
## Opis

Aplikacja ta stworzona jest do formatowania lub filtrowania struktur danych zapisanych w formacie JSON a także do porównania struktur między sobą. 
JSON Tools pozwala zarówno na zminifikowanie niezminifikowanej reprezentacji JSON, a także na operację odwrotną, która reprezentuje pełną strukturę z dodaniem odstępów oraz nowych linii.

Projekt korzysta z REST API, czyli interfejsu programistycznego opartego na architekturze REST, który umożliwia komunikację pomiędzy klientem a serwerem za pomocą protokołu HTTP.

Projekt napisany w języku Java z użyciem [Spring Boot](https://github.com/spring-projects/spring-boot) na wersji 3.1.X.  

<a id="l2"></a>
## Instalacja

Aby uruchomić ten projekt lokalnie, należy wykonać poniższe polecenia w terminalu:

```bash
  cd <scieżka_w_której_chcesz_umieścić_projekt>
  git clone https://github.com/wojciechgunia/PUT_IO_Project_JSON_Tools
  ``` 

Zalecam korzystać ze środowiska IntelliJ przy interakcji z aplikacją.  
Port na którym działa aplikacja można zmienić w pliku .properties.
Dla serwera działającego lokalnie na porcie 8080, bazowy URL wygląda następująco:

```bash
http://localhost:8080/api/v1/jsontools/..
``` 

Program posiada graficzny interfejs w formie aplikacji internetowej utworzonej w środowisku Angular CLI, który można znaleźć w repozytorium [JSON Tools FE](https://github.com/wojciechgunia/PUT_IO_Project_JSON_Tools_FE).

Aby uzyskać dostęp do dostępnych endpointów i przetestować ich działanie, należy skorzystać z narzędzi do wysyłania żądań HTTP, takich jak:

* Postman
* cURL
* Inne dowolne narzędzie obsługujące REST API.

<a id="l3"></a>
## Funkcje

Dostępne endpointy, z których można skorzystać do przekształcania struktury JSON:  
* load-json - Wczytuje strukturę w formie tekstu
  * metoda typu POST 
  * przyjmuje treść w formacie Text, a następnie parametr, który będzie nazwą wprowadzonej struktury
* get-json - Pobiera strukturę w formie JSON 
  * metoda typu GET
  * przyjmuje jako parametr nazwę struktury
* get-original - Pobiera strukturę w formie tekstu
  * metoda typu GET
  * przyjmuje jako parametr nazwę struktury
* get-minimalize - Wyświetla zminifikowaną reprezentację JSON'a
  * metoda typu GET
  * przyjmuje jako parametr nazwę struktury
* get-full - Wyświetla rozszerzoną reprezentację JSON'a
  * metoda typu GET
  * przyjmuje jako parametr nazwę struktury
* get-filtered - Pokaże tylko klucze i wartości, o które poprosimy
  * metoda typu GET
  * jako pierwszy parametr przyjmuje nazwę struktury
  * jako drugi parametr przyjmuje klucze, które mają pozostac wraz z wartościami
* get-without - Usunie klucze wraz z wartościami, o które poprosimy
  * metoda typu GET
  * jako pierwszy parametr przyjmuje nazwę struktury
  * jako drugi parametr przyjmuje klucze, które mają zostać usunięte wraz z wartościami
* get-differences - Porówna ze sobą dwie wcześniej załadowane do aplikacji struktury
  * metoda typu GET
  * przyjmuje dwa parametry, które są nazwami wprowadzonych wcześniej struktur

<a id="l4"></a>
## Struktura projektu

Klasy znajdujące się w projekcie i ich funkcjonalności:
* JsonToolsController - Służy do obsługi endpointów utworzonych za pomocą REST API 
* JsonToolsService - Klasa służąca do realizacji funkcji, które mają zostać wywołane przez endpointy w celu oddzielenia logiki aplikacji od kontrolera
* Response - Klasa służąca do wyświetlania odpowiedzi dla użytkownika z informacjami takimi jak kod błędu, data i czas wykonania oraz wiadomość od serwera 
* Code - Klasa typu ENUM, w której przechowywane są wiadomości wysyłane do użytkownika w przypadku wywołania endpointu przez Response

