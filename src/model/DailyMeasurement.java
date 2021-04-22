package model;

import java.time.LocalDate;

public class DailyMeasurement {
    private final LocalDate date;
    private final double temperature;

    public DailyMeasurement(LocalDate date, double temperature) {
        this.date = date;
        this.temperature = temperature;
    }

    public LocalDate getDate() {
        return date;
    }

    public double getTemperature() {
        return temperature;
    }

}
