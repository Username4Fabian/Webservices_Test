package com.example.sykes_webservice;

public class Location 
{
	String name;
	Double latitude;
	Double longitude;
	
	/**
	 * Erzeugt ein Ort mit dem angegebenen Namen ander entsprechenden Stelle
	 * 
	 * @param name
	 * @param lat
	 * @param lon
	 */
	public Location(String name, Double lat, Double lon) {
		super();
		this.name = name;
		this.latitude = lat;
		this.longitude = lon;
	}
	
	
	
	public String getName() {
		return name;
	}



	public Double getLatitude() {
		return latitude;
	}



	public Double getLongitude() {
		return longitude;
	}

	public Double distanceTo(Location other) 
	{
		double latitude1 = this.getLatitude();
		double latitude2 = other.getLatitude();
		double longitude1 = this.getLongitude();
		double longitude2 = other.getLongitude();

		// Exakte Berechnung
		// Dezimale Positionsangaben in BOgenmaß umrechnen
		latitude1 = Math.toRadians(latitude1);
		latitude2 = Math.toRadians(latitude2);
		longitude1 = Math.toRadians(longitude1);
		longitude2 = Math.toRadians(longitude2);
		
		return 6378.388 * Math.acos((Math.sin(latitude1) * Math.sin(latitude2)) + 
				(Math.cos(latitude1) * Math.cos(latitude2) * Math.cos(longitude2 - longitude1)));
		
	}
	
	
	 public Double directionTo(Location other) 
	 {
			double lat1 = this.getLatitude();
			double lat2 = other.getLatitude();
			double lon1 = this.getLongitude();
			double lon2 = other.getLongitude();

			// Exakte Berechnung
			// Dezimale Positionsangaben in BOgenmaß umrechnen
			lat1 = Math.toRadians(lat1);
			lat2 = Math.toRadians(lat2);
			lon1 = Math.toRadians(lon1);
			lon2 = Math.toRadians(lon2);
			
			double y = Math.sin(lon2 - lon1) * Math.cos(lat2);
	        double x = Math.cos(lat1) * Math.sin(lat2) - Math.sin(lat1) * Math.cos(lat2) * Math.cos(lon2 - lon1);

	        double azimuth = Math.toDegrees(Math.atan2(y, x));
	        azimuth = (azimuth + 360) % 360;
	        
	        return azimuth;
	}

	
	@Override
	public String toString()
	{
		return String.format("%s(%.02f|%.02f)", this.name, this.latitude, this.longitude);
	}
}
