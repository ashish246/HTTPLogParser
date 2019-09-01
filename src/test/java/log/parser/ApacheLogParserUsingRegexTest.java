package log.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

/**
 * Class to test method for {@link ApacheLogParserUsingRegex} class
 * 
 * @author Ashish
 *
 */
public class ApacheLogParserUsingRegexTest {

	@Test
	public void testGetRequestURL() {

		final String logEntry = "177.71.128.21 - - [10/Jul/2018:22:21:28 +0200] \"GET /intranet-analytics/ HTTP/1.1\" 200 3574 \"-\" \"Mozilla/5.0 (X11; U; Linux x86_64; fr-FR) AppleWebKit/534.7 (KHTML, like Gecko) Epiphany/2.30.6 Safari/534.7\"";
		assertEquals("/intranet-analytics/", ApacheLogParserUsingRegex.getRequestURL(logEntry));
	}

	@Test
	public void testGetIPAddress() {

		final String logEntry = "177.71.128.21 - - [10/Jul/2018:22:21:28 +0200] \"GET /intranet-analytics/ HTTP/1.1\" 200 3574 \"-\" \"Mozilla/5.0 (X11; U; Linux x86_64; fr-FR) AppleWebKit/534.7 (KHTML, like Gecko) Epiphany/2.30.6 Safari/534.7\"";
		assertEquals("177.71.128.21", ApacheLogParserUsingRegex.getIPAddress(logEntry));
	}

	@Test
	public void testGetUniqueIPAddresses() {
		assertNotEquals(15, ApacheLogParserUsingRegex.getUniqueIPAddresses("log/files/duplicate-ips.log").size());
	}

	@Test
	public void testGetTopActiveIPAddresses() {
		assertEquals("168.41.191.40",
				ApacheLogParserUsingRegex.getTopActiveIPAddresses("log/files/duplicate-ips.log", 1).get(0));
	}

	@Test
	public void testGetTopVisitedURLs() {
		assertEquals("/docs/manage-websites/",
				ApacheLogParserUsingRegex.getTopVisitedURLs("log/files/duplicate-urls.log", 1).get(0));
	}

}
