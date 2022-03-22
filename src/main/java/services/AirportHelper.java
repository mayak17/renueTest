package services;

import models.Airport;

import java.util.Collection;

public interface AirportHelper {
    Collection<Airport> importAirports() throws Exception;
    void printSearchableAirports(Collection<Airport> searchedAirports) throws Exception;;
}
