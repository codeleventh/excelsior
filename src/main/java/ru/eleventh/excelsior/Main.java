package ru.eleventh.excelsior;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.val;
import ru.eleventh.excelsior.data.CsvRecord;
import ru.eleventh.excelsior.data.DayPrice;
import ru.eleventh.excelsior.data.TickerData;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.max;

@RequiredArgsConstructor
public class Main {

    private final int averageSpan, averageAmount;

    @Getter
    private final Map<String, TickerData> map = new HashMap<>();

    private void printAverage(Collection<DayPrice> stats) {
        var n = 0;
        var acc = 0f;
        val list = new ArrayList<>(stats);

        // Some dodgy optimization to make our average literally moving
        val firstPrintIndex = max(0, list.size() - averageAmount);
        for (int i = 0; i < list.size(); i++) {
            acc += list.get(i).getPrice();
            if (n < averageSpan) n++;

            if (i >= firstPrintIndex) {
                System.out.printf("%s: %.2f\n", list.get(i).getDate(), acc / n);
                if (n >= averageSpan)
                    acc -= list.get(i - (n - 1)).getPrice();
            }
        }
    }

    @SneakyThrows
    public void parseStonks(BufferedReader reader) {
        val statsSize = averageSpan + averageAmount - 1;

        String line;
        reader.readLine(); // Skipping CSV header
        while ((line = reader.readLine()) != null) {
            val segments = line.split(",");
            val record = new CsvRecord(
                    // The file structure is: price_close, price_high, price_low, price_open, ticker, date
                    Float.parseFloat(segments[0]),
                    Float.parseFloat(segments[1]),
                    Float.parseFloat(segments[2]),
                    segments[4],
                    LocalDate.parse(segments[5], DateTimeFormatter.ofPattern("M/d/yyyy"))
            );

            map.computeIfAbsent(record.getName(), k -> new TickerData(statsSize));
            map.get(record.getName()).update(record);
        }
    }

    public void printStonks() {
        map.forEach((key, value) -> {
            System.out.printf("%s: %.2f %.2f (%d records)\n", key, value.getMinPrice(), value.getMaxPrice(), value.getDays());
            printAverage(value.getLastPrices());
        });
    }

    @SneakyThrows
    public static void main(String[] args) {
        int N = 0, M = 0;
        val filePath = args[0];

        try {
            N = Integer.parseInt(args[2]); // N points for MA calculation
            M = Integer.parseInt(args[1]); // M days for calculating MA
            if (M < 1 || N < 1)
                throw new RuntimeException("M and N should be more than 0.");
            if (!Files.isReadable(Paths.get(filePath)))
                throw new RuntimeException(String.format("Cannot open '%s' file.", filePath));
        } catch (Exception e) {
            System.out.println("Hey, there is some invalid args.");
            System.out.println(e.getMessage());
            System.exit(-1);
        }

        val reader = new BufferedReader(new FileReader(filePath));
        val stonks = new Main(N, M);

        stonks.parseStonks(reader);
        stonks.printStonks();
    }
}
