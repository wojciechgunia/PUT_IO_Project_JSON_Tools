package org.example.jsontools.entity;

public enum Code
{
    SUCCESS("Operacja zakończona sukcesem"),
    BR1("Nie udało się dodać JSONa"),
    BR2("JSON o takiej nazwie już istnieje");

    public final String label;
    Code(String label)
    {
        this.label = label;
    }
}
