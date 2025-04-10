package com.example.maps2;

import java.util.Objects;

public class StreetsStatus {
    private StreetsPolygon streetsPolygon;
    private WaterLevelOnStreets waterLevelOnStreets;

    public StreetsStatus(StreetsPolygon streetsPolygon, WaterLevelOnStreets waterLevelOnStreets) {
        this.streetsPolygon = streetsPolygon;
        this.waterLevelOnStreets = waterLevelOnStreets;
    }

    public StreetsPolygon getStreetsPolygon() {
        return streetsPolygon;
    }

    public WaterLevelOnStreets getWaterLevelOnStreets() {
        return waterLevelOnStreets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StreetsStatus that = (StreetsStatus) o;
        return Objects.equals(streetsPolygon, that.streetsPolygon) && waterLevelOnStreets == that.waterLevelOnStreets;
    }

    @Override
    public int hashCode() {
        return Objects.hash(streetsPolygon, waterLevelOnStreets);
    }
}
