package ru.eleventh.excelsior.data;

import lombok.Data;

import java.time.LocalDate;

@Data
public class DayPrice implements Comparable<DayPrice> {
    private final Float price;
    private final LocalDate date;

    @Override
    public int compareTo(DayPrice o) {
        return this.getDate().compareTo(o.getDate());
    }
}
