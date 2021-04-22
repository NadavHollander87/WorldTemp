package aggregator;

import worldtemp.DailyMeasurement;

import java.util.List;

public class AvgAggregator implements Aggregator {

    @Override
    public double aggregate(List<DailyMeasurement> dailyMeasurements) {
        double sum = 0;
        for (DailyMeasurement dt : dailyMeasurements) {
            sum += dt.getTemperature();
        }
        return sum / dailyMeasurements.size();
    }
}
