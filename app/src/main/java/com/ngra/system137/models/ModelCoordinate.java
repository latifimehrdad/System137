package com.ngra.system137.models;

public class ModelCoordinate {

    String format;
    double lat;
    double lon;
    Integer zoom;
    Integer addressdetails;

    public ModelCoordinate(String format, double lat, double lon, Integer zoom, Integer addressdetails) {
        this.format = format;
        this.lat = lat;
        this.lon = lon;
        this.zoom = zoom;
        this.addressdetails = addressdetails;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public Integer getZoom() {
        return zoom;
    }

    public void setZoom(Integer zoom) {
        this.zoom = zoom;
    }

    public Integer getAddressdetails() {
        return addressdetails;
    }

    public void setAddressdetails(Integer addressdetails) {
        this.addressdetails = addressdetails;
    }
}
