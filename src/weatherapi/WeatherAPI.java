package weatherapi;

import worldtemp.City;
import worldtemp.DailyMeasurement;

import java.util.List;
import java.util.Set;

public interface WeatherAPI {

    Set<City> getAllCitiesByIds(Set<String> ids);

    List<DailyMeasurement> getLastYearTemperature(String cityId);
}
