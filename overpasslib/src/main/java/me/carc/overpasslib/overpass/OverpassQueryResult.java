package me.carc.overpasslib.overpass;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Map tags to class
 */
public class OverpassQueryResult {
    @SerializedName("elements")
    public List<Element> elements = new ArrayList<>();

    public static class Element {
        @SerializedName("type")
        public String type;

        @SerializedName("id")
        public long id;

        @SerializedName("lat")
        public double lat;

        @SerializedName("lon")
        public double lon;

        @SerializedName("tags")
        public Tags tags = new Tags();

        public static class Tags {
            @SerializedName("type")
            public String type;

            @SerializedName("amenity")
            public String amenity;

            @SerializedName("tourism")
            public String tourism;

            @SerializedName("historic")
            public String historic;

            @SerializedName("memorial")
            public String memorial;

            @SerializedName("name")
            public String name;

            @SerializedName("phone")
            public String phone;

            @SerializedName("contact:phone")
            public String contactPhone;

            @SerializedName("contact:email")
            public String contactEmail;

            @SerializedName("website")
            public String website;

            @SerializedName("fax")
            public String fax;

            @SerializedName("contact:website")
            public String contactWebsite;

            @SerializedName("source")
            public String source;

            @SerializedName("source:name")
            public String sourceName;

            @SerializedName("source:ref")
            public String sourceRef;

            @SerializedName("url")
            public String url;

            @SerializedName("addr:city")
            public String addressCity;

            @SerializedName("addr:postcode")
            public String addressPostCode;

            @SerializedName("addr:suburb")
            public String addressSuburb;

            @SerializedName("addr:street")
            public String addressStreet;

            @SerializedName("addr:housenumber")
            public String addressHouseNumber;

            @SerializedName("wheelchair")
            public String wheelchair;

            @SerializedName("toilets:wheelchair")
            public String wheelchairToilets;

            @SerializedName("wheelchair:description")
            public String wheelchairDescription;

            @SerializedName("opening_hours")
            public String openingHours;

            @SerializedName("internet_access")
            public String internetAccess;

            @SerializedName("fee")
            public String fee;

            @SerializedName("operator")
            public String operator;

            @SerializedName("collection_times")
            public String collectionTimes;

            @SerializedName("takeaway")
            public String takeaway;

            @SerializedName("delivery")
            public String delivery;

            @SerializedName("outdoor_seating")
            public String outdoor_seating;

            @SerializedName("religion")
            public String religion;

            @SerializedName("denomination")
            public String denomination;

            @SerializedName("shop")
            public String shop;

            @SerializedName("image")
            public String image;

            @SerializedName("smoking")
            public String smoking;

            @SerializedName("note")
            public String note;

            @SerializedName("cuisine")
            public String cuisine;

            @SerializedName("wikipedia")
            public String wikipedia;

            @SerializedName("wikidata")
            public String wikidata;

            @SerializedName("nudism")
            public String nudism;

            @SerializedName("highway")
            public String highway;

            @SerializedName("cycleway")
            public String cycleway;

            @SerializedName("comment")
            public String comment;

            @SerializedName("waterway")
            public String waterway;

            @SerializedName("sport")
            public String sport;

            @SerializedName("route")
            public String route;

            @SerializedName("railway")
            public String railway;

            @SerializedName("public_transport")
            public String public_transport;

            @SerializedName("aerialway")
            public String aerialway;

            @SerializedName("aeroway")
            public String aeroway;

            @SerializedName("healthcare")
            public String healthcare;

            @SerializedName("barrier")
            public String barrier;

            @SerializedName("boundary")
            public String boundary;

            @SerializedName("building")
            public String building;

            @SerializedName("entrance")
            public String entrance;

            @SerializedName("height")
            public String height;

            @SerializedName("craft")
            public String craft;

            @SerializedName("emergency")
            public String emergency;

            @SerializedName("landuse")
            public String landuse;

            @SerializedName("man_made")
            public String man_made;

            @SerializedName("military")
            public String military;

            @SerializedName("leisure")
            public String leisure;

            @SerializedName("natural")
            public String natural;

            @SerializedName("place")
            public String place;

            @SerializedName("power")
            public String power;

            @SerializedName("office")
            public String office;
        }
    }
}

