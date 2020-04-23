package com.zapsoftco.relica.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.junit.jupiter.api.Test;

class ResourceManagerTest {



	@Test
	void testGetLocalResource() {
		URL url = ResourceManager.getLocalResource("test/RMTMock.txt");
		assertFalse(url == null);
	}

	@Test
	void testGetApplicationProperties() {
		Properties props = ResourceManager.getApplicationProperties();
		assertTrue(props.containsKey("relica.base_server_uri"));
	}

	@Test
	void testGetRemoteResourceRelativeToBaseUrl() {
		try {
			URL urlObj = ResourceManager.getRemoteResource("gujint", UriType.RELATIVE_TO_BASE);
			Properties props = ResourceManager.getApplicationProperties();

			String expectedUrl = props.getProperty("relica.base_server_uri") + "gujint";
			String actualUrl = urlObj.toString();

			assertEquals(expectedUrl, actualUrl);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	@Test
	void testGetRemoteResourceWithAbsoluteUrl() {
		try {
			URL url = ResourceManager.getRemoteResource("http://example.com", UriType.ABSOLUTE);
			assertEquals("http://example.com", url.toString());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

	}

	@Test
	void testGetResourceAsJson() {

		try {
			URL url = getResourceUrl();
			RMTMock actual = 
					ResourceManager.getResourceAsJson(url.toString(), UriType.ABSOLUTE, RMTMock.class);
			RMTMock expected = new RMTMock();

			expected.setType("Test");

			assertEquals(expected, actual);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

	}
	
	private URL getResourceUrl() {
		return ResourceManager.getLocalResource("test/RMTMock.txt");
	}

}
