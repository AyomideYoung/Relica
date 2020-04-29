package com.zapsoftco.relica.util;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This class provides methods to load resources needed by the application
 *
 */
public  class ResourceManager {
	/**
	 * The path relative to this class where the application's 
	 * resources are stored.
	 * This is the classpath for the app
	 */
	private static final String resourceFolderPath = "../../../../";

	/**
	 * Gets the {@code URL} of a resource bundled with the application.
	 * @param desiredResource
	 * the path to the desired resource relative to the resources folder
	 * @return
	 * the URL of the desired resource
	 */
	public static URL getLocalResource(String desiredResource) {
		URL url = ResourceManager.class.getResource(resourceFolderPath + desiredResource);
		return url;
	}

	/**
	 * Gets the properties specified in relica.properties file
	 * @return
	 * a new {@code Properties} object
	 */
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

	/**
	 * Gets a resource from a remote source.
	 * The {@link UriType} enum specifies if the resource should be loaded
	 * relative to the relica.base_server_uri as specified in relica.properties,
	 *  or if it 
	 * should be loaded from an external server in which case, the 
	 * {@code UriType.ABSOLUTE} must be used.
	 * <p>
	 * If the {@code UriType.RELATIVE_TO_BASE} is used, then the programmer
	 * should not bother specifying the absolute uri of the base server.
	 * This should be specified in the relica.properties file
	 * @param uri
	 * the path to the desired resource. This should be a fully valid uri unless
	 * the uriType is {@code UriType.RELATIVE_TO_BASE}
	 * @param uriType
	 * the {@code UriType} to use for this operation
	 * @return
	 * the Url of the remote resource
	 * @throws MalformedURLException
	 * if the specified {@code uri} is not valid
	 */
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
			String resourceURI = props.getProperty("relica.base_server_uri") + uri;
			URL url = new URL(resourceURI);
			return url;
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * Returns a resource as an object
	 * @param <T>
	 * the type of object to represent the resource
	 * @param uri
	 * the path to the desired resource
	 * @param uriType
	 * the {@code UriType} to use
	 * @param modelClass
	 * the Class of the object that will be used to represent the resource
	 * @return
	 * the object representing the requested resource
	 * @throws MalformedURLException
	 * if the url is malformed i.e. unacceptable
	 */
	public static <T> T getResourceAsJson(String uri, UriType uriType, Class<T> modelClass)
			throws MalformedURLException {
		try {
			URL url = getRemoteResource(uri, uriType);
			ObjectMapper mapper = new ObjectMapper();
			
			URLConnection connection = url.openConnection();
			connection.setConnectTimeout(10000);
			connection.connect();
			
			T model = mapper.readValue(connection.getInputStream(), modelClass);
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
