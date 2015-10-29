package com.wbertan.aula_12_exemplo02.modelo;

import java.io.Serializable;

public class MapLocation implements Serializable {
    private static final long serialVersionUID = 1L;

    private Double latitude;
    private Double longitude;

    private String fullLocation;
    private String countryCode;
    private String countryName;
    private String featureName;
    private String locality;
    private String postalCode;
    private String adminArea;
    private String subThoroughfare;

    private String[] address;

    public void MapLocation() {
    }

    public String getFullLocation() {
        return fullLocation;
    }

    public void setFullLocation(String fullLocation) {
        this.fullLocation = fullLocation;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getFeatureName() {
        return featureName;
    }

    public void setFeatureName(String featureName) {
        this.featureName = featureName;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getAdminArea() {
        return adminArea;
    }

    public void setAdminArea(String adminArea) {
        this.adminArea = adminArea;
    }

    public String getSubThoroughfare() {
        return subThoroughfare;
    }

    public void setSubThoroughfare(String subThoroughfare) {
        this.subThoroughfare = subThoroughfare;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String[] getAddress() {
        return address;
    }

    public String getAddress(int pos) {
        if(address == null || pos > address.length){
            return null;
        }
        return address[pos];
    }

    public void setAddress(String[] address) {
        this.address = address;
    }
}