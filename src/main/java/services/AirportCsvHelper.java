package services;
import models.Airport;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class AirportCsvHelper implements AirportHelper {
    private String fileName;
    private final int indexedColumn;
    private BufferedReader bufferedReader;

    public AirportCsvHelper(String fileName, int indexedColumn) throws IOException {
        this.fileName = fileName;
        this.indexedColumn = indexedColumn;
        this.createBufferReader();
    }

    private void createBufferReader() throws IOException {
        this.bufferedReader = new BufferedReader(new FileReader(fileName));
        this.bufferedReader.mark(10000000);
    }

    public List<Airport> importAirports() throws Exception {
        List<Airport> airports = new ArrayList<>();
        int index = 0;
        String row;

        while ((row = this.bufferedReader.readLine()) != null) {
            airports.add(new Airport(index++, extractColumn(this.indexedColumn, row)));
        }

        this.bufferedReader.reset();
        return airports;
    }

    private static String extractColumn(int numColumn, String row) throws IllegalArgumentException {
        int delimeterCounter = 0;

        for (var i = 0; i < row.length() - 1; i++) {
            var symbol = row.charAt(i);
            if (symbol == ',') {
                delimeterCounter++;
                if (delimeterCounter == numColumn - 1) {
                    var indexOfNext = row.indexOf(',', i + 1);
                    if (indexOfNext == -1) {
                        return row.substring(i + 2, row.length() - 1);  // skip ," (+2)
                    } else {
                        return row.substring(i + 2, indexOfNext - 1);   // skip ," (+2)
                    }
                }
            }
        }

        throw new IllegalArgumentException("Колонки не существует");
    }

    public void printSearchableAirports(Collection<Airport> searchedAirports) throws IOException, IllegalArgumentException {
        if (searchedAirports == null) throw new IllegalArgumentException("searchedAirports не может быть null");

        if (searchedAirports.isEmpty()) {
            System.out.println("Аэропортов по заданной строке не найдено");
            return;
        }

        String[] resultData = new String[searchedAirports.size()];
        List<Integer> searchedAirportRowIndexes = searchedAirports.stream().map(Airport::getRowIndex).collect(Collectors.toList());

        String row;
        int currentRowIndex = 0;
        while ((row = this.bufferedReader.readLine()) != null) {
            int indexInSearchedData = searchedAirportRowIndexes.indexOf(currentRowIndex);
            if (indexInSearchedData != -1) {
                resultData[indexInSearchedData] = row;
            }
            currentRowIndex++;
        }

        for (String data: resultData) {
            System.out.println(data);
        }
    }
}
