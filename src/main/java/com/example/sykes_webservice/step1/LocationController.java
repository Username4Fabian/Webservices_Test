package com.example.sykes_webservice.step1;


import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;



@RestController
public class LocationController {

    private List<Location> knownLocations;
    private SrtmFile srtmFile;

    @PostConstruct
    public void init() {
        System.out.println("Ich wurde erzeugt");
        knownLocations = new ArrayList<>();
        knownLocations.add(new Location("Leoben", 47.383333, 15.1));
        knownLocations.add(new Location("Bruck", 47.416667, 15.266667));
        knownLocations.add(new Location("Kapfenberg", 47.433333, 15.316667));
        knownLocations.add(new Location("Mariazell", 47.769722, 15.316667));
        knownLocations.add(new Location("Graz", 47.066667, 15.45));
        knownLocations.add(new Location("Vienna", 48.2082, 16.3738));
        knownLocations.add(new Location("Linz", 48.3064, 14.2858));
        knownLocations.add(new Location("Graz", 47.0707, 15.4395));
        knownLocations.add(new Location("Salzburg", 47.8095, 13.0550));
        knownLocations.add(new Location("Innsbruck", 47.2682, 11.3923));
        knownLocations.add(new Location("Klagenfurt", 46.6249, 14.3050));
        knownLocations.add(new Location("Villach", 46.6111, 13.8558));
        knownLocations.add(new Location("Wels", 48.1575, 14.0289));
        knownLocations.add(new Location("St. Pölten", 48.2047, 15.6256));
        knownLocations.add(new Location("Dornbirn", 47.4125, 9.7417));
        knownLocations.add(new Location("Wiener Neustadt", 47.8151, 16.2465));
        knownLocations.add(new Location("Bregenz", 47.5031, 9.7471));
        knownLocations.add(new Location("Eisenstadt", 47.8450, 16.5336));
        knownLocations.add(new Location("Leonding", 48.2606, 14.2406));
        knownLocations.add(new Location("Traun", 48.2203, 14.2333));
        knownLocations.add(new Location("Amstetten", 48.1219, 14.8747));
        knownLocations.add(new Location("Klosterneuburg", 48.3053, 16.3256));
        knownLocations.add(new Location("Schwechat", 48.1381, 16.4708));
        knownLocations.add(new Location("Ternitz", 47.7275, 16.0361));
        knownLocations.add(new Location("Baden bei Wien", 48.0069, 16.2308));

        srtmFile = new SrtmFile(new File("/Users/fabiansykes/Documents/GitHub/srtm_40_03.asc"));
    }


    @GetMapping("/location")
    public Location getLocation(@RequestParam(value = "name", defaultValue = "Leoben") String name) {
        return knownLocations.stream()
                .filter(location -> location.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new LocationNotFoundException("Die angeforderte Location wurde nicht gefunden"));
    }

    @GetMapping("/nearest")
    public Location findNearest(@RequestParam(value = "latitude", defaultValue = "47.383333") Double latitude, @RequestParam(value = "longitude", defaultValue = "15.1") Double longitude) {
        Location givenLocation = new Location("Gegebener Ort", latitude, longitude);

        return knownLocations.stream()
                .min(Comparator.comparing(location -> location.distanceTo(givenLocation)))
                .orElseThrow(() -> new LocationNotFoundException("Keine Locations gefunden"));
    }


    @ExceptionHandler(LocationNotFoundException.class)
    public ResponseEntity<String> handleLocationNotFoundException(LocationNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }


    @GetMapping("/altitude")
    public ResponseEntity<Double> getAltitude(@RequestParam("latitude") Double latitude, @RequestParam("longitude") Double longitude) {
        Location location = new Location("Given Location", latitude, longitude);
        Optional<Double> altitude = srtmFile.getAltitudeForLocation(location);
        if (altitude.isPresent()) {
            return ResponseEntity.ok(altitude.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/chart-altitude")
    public ResponseEntity<List<Double>> getChartAltitude(
            @RequestParam("startLat") Double startLat,
            @RequestParam("startLon") Double startLon,
            @RequestParam("endLat") Double endLat,
            @RequestParam("endLon") Double endLon,
            @RequestParam("numberOfPoints") Integer numberOfPoints) {
        Location startLocation = new Location("Start Location", startLat, startLon);
        Location endLocation = new Location("End Location", endLat, endLon);
        List<Location> intermediateLocations = startLocation.calculateIntermediateLocations(endLocation, numberOfPoints);

        List<Double> altitudeData = new ArrayList<>();
        for (Location location : intermediateLocations) {
            Optional<Double> altitude = srtmFile.getAltitudeForLocation(location);
            if (altitude.isPresent()) {
                altitudeData.add(altitude.get());
            }
        }

        return ResponseEntity.ok(altitudeData);
    }

    public static class LocationNotFoundException extends RuntimeException {
        public LocationNotFoundException(String message) {
            super(message);
        }
    }
}