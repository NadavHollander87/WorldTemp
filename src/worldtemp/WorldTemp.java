package worldtemp;

import aggregator.Aggregator;
import aggregator.AvgAggregator;
import weatherapi.WeatherAPI;
import weatherapi.WeatherAPIRandom;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class WorldTemp {

    public static final int POPULATION_THRESHOLD = 50000;

    public static void main(String[] args) {
        WorldTemp wt = new WorldTemp();
        Set<String> cityIds = wt.generateCityIds();
        Aggregator avgAgg = new AvgAggregator();
        System.out.println(wt.getTopCities(cityIds, avgAgg, 30));
    }

    private Set<String> generateCityIds() {
        Set<String> cityIds = new TreeSet<>();
        for (int i = 0; i < 30; i++) {
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

    private List<CityWithAgg> sortCities(Set<City> cities, Aggregator agg) {
        List<CityWithAgg> topCitiesByAggValues = new ArrayList<>();
        for (City city : cities) {
            if (city.getPopulation() > POPULATION_THRESHOLD) {
                double cityVal = aggregateCityTemps(city, agg);
                topCitiesByAggValues.add(new CityWithAgg(city, cityVal));
            }
        }
        Collections.sort(topCitiesByAggValues);
        Collections.reverse(topCitiesByAggValues);
        return topCitiesByAggValues;
    }

    private double aggregateCityTemps(City city, Aggregator agg) {
        WeatherAPI api = new WeatherAPIRandom();
        return agg.aggregate(api.getLastYearTemperature(city.getId()));
    }
}
