import lombok.Data;
import models.Airport;

import java.util.*;
import java.util.stream.Collectors;

@Data
public class AirportGuide {
    private List<Airport> airports;

    public AirportGuide(Collection<Airport> airports) {
        if (airports == null) throw new IllegalArgumentException("airports не может быть null");

        this.airports = airports.stream().collect(Collectors.toList());
        Collections.sort(this.airports);
    }

    public Collection<Airport> searchByPrefix(String prefix) throws IllegalArgumentException  {
        final Comparator<Airport> PREFIX_COMPARATOR = (airport, searchableAirport) -> {
            var searchablePrefix = searchableAirport.getSearchField();
            if (airport.getSearchField().length() < searchablePrefix.length()) return -1;
            return airport.getSearchField().substring(0, searchablePrefix.length()).compareTo(searchablePrefix);
        };

        if (prefix == null) throw new IllegalArgumentException("prefix не может быть null");

        if (prefix.isEmpty()) return this.airports;

        final int randomIndex = Collections.binarySearch(this.airports, new Airport(null, prefix), PREFIX_COMPARATOR);

        int rangeStarts = randomIndex, rangeEnds = randomIndex;

        if (randomIndex < 0) return Collections.emptyList();

        while (rangeStarts > -1 && this.airports.get(rangeStarts).startWithPrefix(prefix))
            rangeStarts--;

        while (rangeEnds < this.airports.size() && this.airports.get(rangeEnds).startWithPrefix(prefix))
            rangeEnds++;

        return this.airports.subList(rangeStarts + 1, rangeEnds);
    }
}
