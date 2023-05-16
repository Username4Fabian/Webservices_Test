package com.example.sykes_webservice.step1;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

class SrtmFile
{
	List<String> lines;
	int columns;
	int rows;
	double west;
	double south;
	double north;
	double east;
	double cellsize;
	int noDataValue;
	Path dataFile;
		
	public SrtmFile(File file)
	{
		if(file.exists() == false || file.canRead() == false)
		{
			throw new IllegalArgumentException("File can not be read or does not exist: " + file.toString());
		}
		this.dataFile = file.toPath();
		
		try 
		{
			List<String> metadata = Files.lines(dataFile).limit(6).collect(Collectors.toList());
			
			// Read the metadata
			columns = Integer.parseInt(metadata.get(0).substring(14));
			rows = Integer.parseInt(metadata.get(1).substring(14));
			west = Double.parseDouble(metadata.get(2).substring(14));
			south = Double.parseDouble(metadata.get(3).substring(14));
			cellsize = Double.parseDouble(metadata.get(4).substring(14));
			noDataValue = Integer.parseInt(metadata.get(5).substring(14));

			north = south + (rows * cellsize);
			east = west + (columns * cellsize);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * Returns the altitude for a given Location
	 * @param loc
	 * @return the value or null if there is no data present
	 */
	public Optional<Double> getAltitudeForLocation(Location loc)
	{
		
		// Calculate the row
		int row = (int) (rows - ((loc.getLatitude() - south) / (north - south) * rows));
		int col = (int) (columns - ((east - loc.getLongitude()) / (east - west)) * columns);
		
		if(col > columns || row > rows)
		{
			return Optional.empty();
		}
		
		try
		{
			
			String line = Files.lines(this.dataFile)
					.skip(6) // Skip the header
					.skip(row)  // Row
					.findFirst().get();
			
			String val = line.split(" ")[col]; //SLOW !!!
			
			if(val.equalsIgnoreCase(String.valueOf(noDataValue)))
			{
				return Optional.empty();
			}
			
			return Optional.of(Double.parseDouble(val));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
	}

	
}