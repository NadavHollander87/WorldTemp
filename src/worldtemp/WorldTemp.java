package worldtemp;

import aggregator.Aggregator;
import aggregator.AvgAggregator;
import model.City;
import weatherapi.WeatherAPI;
import weatherapi.WeatherAPIRandom;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class WorldTemp {

    public static final int POPULATION_THRESHOLD = 50000;
    public static final int NUM_OF_CITIES = 500;

    public static void main(String[] args) {
        WorldTemp wt = new WorldTemp();
        Set<String> cityIds = wt.generateCityIds();
        Aggregator avgAgg = new AvgAggregator();
        System.out.println(wt.getTopCities(cityIds, avgAgg, 10));
    }

    private Set<String> generateCityIds() {
        Set<String> cityIds = new TreeSet<>();
        for (int i = 0; i < NUM_OF_CITIES; i++) {
            cityIds.add(Integer.toString(i));
        }
        return cityIds;
    }

    public List<CityWithAgg> getTopCities(Set<String> cityIds, Aggregator agg, int resultSize) {
        WeatherAPIRandom apiImpl = new WeatherAPIRandom();
        Set<City> cities = apiImpl.getAllCitiesByIds(cityIds);
        List<CityWithAgg> topCitiesByAggValues = sortCities(cities, agg);
        return topCitiesByAggValues.subList(0, Math.min(resultSize, topCitiesByAggValues.size()));
    }

    private List<CityWithAgg> sortCities(Set<City> cities, Aggregator aggtor) {
        ConcurrentAggregator concurAggtor = new ConcurrentAggregator();
        for (City city : cities) {
            if (city.getPopulation() > POPULATION_THRESHOLD) {
                concurAggtor.addCityAggregation(() -> new CityWithAgg(city, aggregateCityTemps(city, aggtor)));
            }
        }
        List<CityWithAgg> topCitiesByAggValues = concurAggtor.getCityWithAggList();
        Collections.sort(topCitiesByAggValues);
        Collections.reverse(topCitiesByAggValues);
        return topCitiesByAggValues;
    }

    private double aggregateCityTemps(City city, Aggregator agg) {
        WeatherAPI api = new WeatherAPIRandom();
        return agg.aggregate(api.getLastYearTemperature(city.getId()));
    }
}
