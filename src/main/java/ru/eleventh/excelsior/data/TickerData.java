package ru.eleventh.excelsior.data;

import lombok.Data;
import ru.eleventh.excelsior.LimitedTreeSet;

import static java.lang.Math.max;
import static java.lang.Math.min;

@Data
public class TickerData {

    public TickerData(final Integer limit) {
        lastPrices = new LimitedTreeSet<>(limit);
    }

    private Integer days = 0;
    private Float minPrice = Float.MAX_VALUE, maxPrice = Float.MIN_VALUE;
    private final LimitedTreeSet<DayPrice> lastPrices;

    public void update(final CsvRecord record) {
        days++;
        minPrice = min(minPrice, record.getPriceLow());
        maxPrice = max(maxPrice, record.getPriceHigh());
        lastPrices.add(new DayPrice(record.getPriceClose(), record.getDate()));
    }
}
