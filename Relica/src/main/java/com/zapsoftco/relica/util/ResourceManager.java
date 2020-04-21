package com.zapsoftco.relica.util;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public  class ResourceManager {
	private static final String resourceFolderPath = "../../../../";

	public static URL getLocalResource(String desiredResource) {
		URL url = ResourceManager.class.getResource(resourceFolderPath + desiredResource);
		return url;
	}

	public static Properties getApplicationProperties() {
		URL url = getLocalResource("relica.properties");
		Properties props = new Properties();
		try {
			//instantiate props via the url's stream
			props.load(url.openStream());
		} catch (IOException e) {
			e.printStackTrace();
		}

		return props;
	}

	public static URL getRemoteResource(String uri, UriType uriType) throws MalformedURLException {
		if(uriType == UriType.RELATIVE_TO_BASE) 
			return getResourceFromRelicaServer(uri);
		else if(uriType == UriType.ABSOLUTE)
			return new URL(uri);

		return null;
	}

	private static URL getResourceFromRelicaServer(String uri) {
		try {
			Properties props = getApplicationProperties();
			String serverURI = props.getProperty("relica.base_server_uri");
			URL url = new URL(serverURI);
			return url;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static <T> T getResourceAsJson(String uri, UriType uriType, Class<T> modelClass)
			throws MalformedURLException {
		try {
			URL url = getRemoteResource(uri, uriType);
			ObjectMapper mapper = new ObjectMapper();
			T model = mapper.readValue(url.openStream(), modelClass);
			return model;
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
