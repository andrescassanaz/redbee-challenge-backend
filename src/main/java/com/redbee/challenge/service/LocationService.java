package com.redbee.challenge.service;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.redbee.challenge.exception.BoardNotFoundException;
import com.redbee.challenge.exception.CityNotFoundException;
import com.redbee.challenge.exception.LocationNotFoundException;
import com.redbee.challenge.model.Location;
import com.redbee.challenge.util.RestResponse;

public interface LocationService {

	/**
	 * Save.
	 *
	 * @param location the location
	 * @return the saved location
	 */
	public Location save(Location location);

	/**
	 * Delete.
	 *
	 * @param location the location
	 */
	public void delete(Location location);

	/**
	 * Find by id.
	 *
	 * @param id the id
	 * @return the location
	 */
	public Location findById(long id);

	/**
	 * Adds the location.
	 *
	 * @param locationJson the location on json format
	 * @return restResonse
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 * @throws CityNotFoundException 
	 * @throws ParseException 
	 */ 
	public RestResponse addLocation(String locationJson) throws JsonParseException, JsonMappingException, IOException, CityNotFoundException, ParseException;

	/**
	 * Delete location.
	 *
	 * @param locationJson the location on json format
	 * @return restResonse
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	public RestResponse deleteLocation(String locationJson)
			throws JsonParseException, JsonMappingException, IOException;

	public Location deleteLocationOfBoard(String boardJson) throws JsonParseException, JsonMappingException, IOException, BoardNotFoundException, LocationNotFoundException;
	
	public List<Location> findAll();
	

}
