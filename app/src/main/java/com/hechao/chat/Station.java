package com.hechao.chat;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/3/30.
 */
public class Station {


    String name;
    String brandname;
    String distance;
    String address;
    double lat;
    double lon;

    double price;
    @Override
    public String toString() {
        return "Station{" +
                "name='" + name + '\'' +
                ", brandname='" + brandname + '\'' +
                ", distance='" + distance + '\'' +
                ", address='" + address + '\'' +
                ", lat=" + lat +
                ", lon=" + lon +
                ", price=" + price +
                '}';
    }

    public Station() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrandname() {
        return brandname;
    }

    public void setBrandname(String brandname) {
        this.brandname = brandname;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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


    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
