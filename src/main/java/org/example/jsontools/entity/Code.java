package org.example.jsontools.entity;

public enum Code
{
    SUCCESS("Operacja zakończona sukcesem"),
    BR1("Nie udało się dodać JSONa"),
    BR2("JSON o takiej nazwie już istnieje"),
    BR3("Nie znaleziono JSONa o podanej nazwie"),
    BR4("Wystąpił błąd podczas minifikacji JSONa");

    public final String label;
    Code(String label)
    {
        this.label = label;
    }
}
