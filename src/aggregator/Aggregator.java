package aggregator;

import worldtemp.DailyMeasurement;

import java.util.List;

public interface Aggregator {

    double aggregate(List<DailyMeasurement> dailyMeasurements);
}
