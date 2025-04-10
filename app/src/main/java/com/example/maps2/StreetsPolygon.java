package com.example.maps2;

import com.google.android.gms.maps.model.Polygon;

public class StreetsPolygon {
    public  double firstPointLat;
    public  double firstPointLng;
    public  double secondPointLat;
    public  double secondPointLng;
    public  double thirdPointLat;
    public  double thirdPointLng;
    public  double fourthPointLat;
    public  double fourthPointLng;
    public double waterLevel;

    public double getWaterLevel() {
        return waterLevel;
    }

    public void setWaterLevel(double waterLevel) {
        this.waterLevel = waterLevel;
    }

    public StreetsPolygon(double firstPointLat, double firstPointLng, double secondPointLat, double secondPointLng, double thirdPointLat, double thirdPointLng, double fourthPointLat, double fourthPointLng, double waterLevel
) {
        this.firstPointLat = firstPointLat;
        this.firstPointLng = firstPointLng;
        this.secondPointLat = secondPointLat;
        this.secondPointLng = secondPointLng;
        this.thirdPointLat = thirdPointLat;
        this.thirdPointLng = thirdPointLng;
        this.fourthPointLat = fourthPointLat;
        this.fourthPointLng = fourthPointLng;
        this.waterLevel = waterLevel;
    }

    public double getFirstPointLat() {
        return firstPointLat;
    }

    public void setFirstPointLat(double firstPointLat) {
        this.firstPointLat = firstPointLat;
    }

    public double getFirstPointLng() {
        return firstPointLng;
    }

    public void setFirstPointLng(double firstPointLng) {
        this.firstPointLng = firstPointLng;
    }

    public double getSecondPointLat() {
        return secondPointLat;
    }

    public void setSecondPointLat(double secondPointLat) {
        this.secondPointLat = secondPointLat;
    }

    public double getSecondPointLng() {
        return secondPointLng;
    }

    public void setSecondPointLng(double secondPointLng) {
        this.secondPointLng = secondPointLng;
    }

    public double getThirdPointLat() {
        return thirdPointLat;
    }

    public void setThirdPointLat(double thirdPointLat) {
        this.thirdPointLat = thirdPointLat;
    }

    public double getThirdPointLng() {
        return thirdPointLng;
    }

    public void setThirdPointLng(double thirdPointLng) {
        this.thirdPointLng = thirdPointLng;
    }

    public double getFourthPointLat() {
        return fourthPointLat;
    }

    public void setFourthPointLat(double fourthPointLat) {
        this.fourthPointLat = fourthPointLat;
    }

    public double getFourthPointLng() {
        return fourthPointLng;
    }

    public void setFourthPointLng(double fourthPointLng) {
        this.fourthPointLng = fourthPointLng;
    }
}
