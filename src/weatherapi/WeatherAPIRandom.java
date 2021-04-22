package weatherapi;

import model.City;
import model.DailyMeasurement;
import worldtemp.generator.CityGenerator;
import worldtemp.generator.DailyMeasurementGenerator;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class WeatherAPIRandom implements WeatherAPI{

    @Override
    public Set<City> getAllCitiesByIds(Set<String> ids) {
        return CityGenerator.generateCities(ids);
    }

    @Override
    public List<DailyMeasurement> getLastYearTemperature(String cityId) {
        LocalDate dayOneYearAgo = LocalDate.now().minusYears(1);
        return DailyMeasurementGenerator.generateYearTemps(dayOneYearAgo);
    }


}
