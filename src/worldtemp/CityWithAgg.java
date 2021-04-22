package worldtemp;

import model.City;

public class CityWithAgg implements Comparable<CityWithAgg>{

    private final City city;
    private final double aggValue;

    public CityWithAgg(City city, double aggValue) {
        this.city = city;
        this.aggValue = aggValue;
    }

    public City getCity() {
        return city;
    }

    public double getAggValue() {
        return aggValue;
    }

    @Override
    public int compareTo(CityWithAgg o) {
        return Double.compare(aggValue, o.getAggValue());
    }

    @Override
    public String toString() {
        return "city = " + city.getName() + ", aggregated Value = " + aggValue + "\n";
    }
}
