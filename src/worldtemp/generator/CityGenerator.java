package worldtemp.generator;

import model.City;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class CityGenerator {

    public static final int RANGE = 50000;
    public static final int MIN_POPULATION = 30000;
    private static final Random rn = new Random();

    public static City generate(String id) {
        return new City(id, id, getRandInt());
    }

    public static Set<City> generateCities(Set<String> ids) {
        Set<City> cities = new HashSet<>();
        for (String id : ids) {
            cities.add(generate(id));
        }
        return cities;
    }

    private static int getRandInt() {
        return rn.nextInt(CityGenerator.RANGE) + CityGenerator.MIN_POPULATION;
    }
}
