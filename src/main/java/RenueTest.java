import models.Airport;
import services.AirportCsvHelper;
import services.AirportHelper;
import services.ConfigImporter;

import java.io.IOException;
import java.util.*;

public class RenueTest {
    public static void main(String[] args) throws Exception {
        System.out.print("Введите строку: ");
        String prefix = new Scanner(System.in).next();

        try {
            ConfigImporter configImporter = new ConfigImporter(args);
            String fileName = configImporter.getFileName();
            int indexedColumn = configImporter.getIndexedColumn();

            AirportHelper airportCsvHelper = new AirportCsvHelper(fileName, indexedColumn);
            Collection<Airport> airports = airportCsvHelper.importAirports();
            AirportGuide airportGuide = new AirportGuide(airports);

            long startTime = System.currentTimeMillis();
            Collection<Airport> searchResult = airportGuide.searchByPrefix(prefix);
            long searchExecutionTime = System.currentTimeMillis() - startTime;

            airportCsvHelper.printSearchableAirports(searchResult);

            System.out.println(String.format("Время поиска: %d мс", searchExecutionTime));
            System.out.println(String.format("Количество найденных строк: %d", searchResult.size()));
        }
        catch (IOException ex) {
            System.out.println("Файл с данными не найден!");
        }
        catch (Exception ex) {
            System.out.println("Что-то пошло не так!");
        }
    }
}
