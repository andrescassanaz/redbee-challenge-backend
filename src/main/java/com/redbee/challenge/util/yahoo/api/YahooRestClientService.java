package com.redbee.challenge.util.yahoo.api;

import java.io.IOException;
import java.net.URI;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.redbee.challenge.exception.CityNotFoundException;
import com.redbee.challenge.util.yahoo.api.data.YahooApiResponse;

/**
 * The Yahoo Rest Client Service.
 */
@Service
public class YahooRestClientService {

	/** The Constant URL of API. */
	private static final String URL = "http://query.yahooapis.com/v1/public/yql";

	/**
	 * Gets the weather from woeid.
	 *
	 * @param woeid the woeid
	 * @return the actual weather
	 * @throws CityNotFoundException
	 */
	public YahooApiResponse getWeatherFromWoeid(long woeid) throws CityNotFoundException {

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL)
				.queryParam("q", "select * from weather.forecast where woeid=" + woeid + " and u='c'")
				.queryParam("format", "json");

		HttpEntity<?> entity = new HttpEntity<>(headers);

		HttpEntity<String> response = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET, entity,
				String.class);

		ObjectMapper objectMapper = new ObjectMapper();
		YahooApiResponse yahooApiResponse = new YahooApiResponse();
		;
		try {
			yahooApiResponse = objectMapper.readValue(response.getBody(), YahooApiResponse.class);
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (yahooApiResponse.getQuery().getResults() == null
				|| yahooApiResponse.getQuery().getResults().getChannel().getLocation() == null) {
			throw new CityNotFoundException();
		}

		return yahooApiResponse;

	}

	/**
	 * Gets the woeid from city name.
	 *
	 * @param cityName the city name
	 * @return the woeid from city name
	 * @throws CityNotFoundException exception for city not found
	 */
	public Long getWoeidFromCityName(String cityName) throws CityNotFoundException {

		Long woeid = null;

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL)
				.queryParam("q", "select woeid from geo.places(1) where text='" + cityName + "'")
				.queryParam("format", "json");

		HttpEntity<?> entity = new HttpEntity<>(headers);

		URI uri = builder.build().encode().toUri();
		HttpEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);

		ObjectMapper objectMapper = new ObjectMapper();
		YahooApiResponse yahooApiResponse = new YahooApiResponse();

		try {
			yahooApiResponse = objectMapper.readValue(response.getBody(), YahooApiResponse.class);
			woeid = yahooApiResponse.getQuery().getResults().getPlace().getWoeid();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			throw new CityNotFoundException();
		}

		return woeid;

	}

}
