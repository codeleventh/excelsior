package ru.eleventh.gte.data;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class CsvRecord {
    private final Float priceClose, priceHigh, priceLow;
    private final String name;
    private final LocalDate date;
}
