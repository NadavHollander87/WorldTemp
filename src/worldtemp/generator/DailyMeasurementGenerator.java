package worldtemp.generator;

import model.DailyMeasurement;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DailyMeasurementGenerator {
    private static final int DAYS_IN_YEAR = 365;
    private static final int MIN_TEMP = -20;
    private static final int MAX_TEMP = 50;
    private static final int MAX_TEMP_CHANGE = 3;
    private static final int SEED = 555;
    private static final Random rn = new Random(SEED);

    private static DailyMeasurement generateDailyTemp(LocalDate date, double temp) {
        return new DailyMeasurement(date, temp);
    }

    public static List<DailyMeasurement> generateYearTemps(LocalDate startDate) {
        List<DailyMeasurement> yearTemps = new ArrayList<>(DAYS_IN_YEAR);
        DailyMeasurement firstMeasurement = new DailyMeasurement(startDate, rn.nextDouble());
        yearTemps.add(firstMeasurement);
        for (int i = 1; i < DAYS_IN_YEAR; i++) {
            DailyMeasurement yesterdayMeas = yearTemps.get(i - 1);
            yearTemps.add(generateDailyTemp(yesterdayMeas.getDate().plusDays(1), getTodayTemperature(yesterdayMeas.getTemperature())));
        }
        return yearTemps;
    }

    private static double getTodayTemperature(double yesterdayTemp) {
        int minChange = -MAX_TEMP_CHANGE;
        int maxChange = MAX_TEMP_CHANGE;
        double delta = minChange + rn.nextDouble() * (maxChange - minChange);
        return Math.max(Math.min(delta + yesterdayTemp, MAX_TEMP), MIN_TEMP);
    }
}
