/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package log.parser;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

/**
 * Apache HTTP log parser using {@link StringTokenizer}. Standard Apache HTTP
 * log format. Refer to
 * https://httpd.apache.org/docs/current/mod/mod_log_config.html#formats
 * 
 * @author Ashish
 *
 */
public class ApacheLogParserUsingStringTokenizer {

	/**
	 * Fetches the list of all the log entries in the given log file
	 * 
	 * @param logFilePath
	 * @return {@code List<String>} List of all the log entries from the file
	 */
	private static List<String> getLogEntriesFromLogFile(final String logFilePath) {

		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		Path path = null;
		try {
			path = Paths.get(classloader.getResource(logFilePath).toURI());
		} catch (URISyntaxException e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
		try {
			return Files.readAllLines(path, StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
	}

	/**
	 * Fetches the distinct unique host IP addresses from all the log entries in the
	 * given log file
	 * 
	 * @param logFilePath
	 * @return {@code Set<String>} List of unique IP addresses
	 */
	public static Set<String> getUniqueIPAddresses(final String logFilePath) {

		final List<String> logEntries = getLogEntriesFromLogFile(logFilePath);
		if (logEntries == null || logEntries.isEmpty()) {
			return Collections.emptySet();
		}

		final Set<String> uniqueIPs = new HashSet<>();
		logEntries.forEach(log -> {
			String ipAddress = getIPAddress(log);
			if (ipAddress != null) {
				uniqueIPs.add(ipAddress);
			}
		});

		return uniqueIPs;
	}

	/**
	 * Fetches top 3 active host IP addresses from all the log entries in the given
	 * log file
	 * 
	 * @param logEntries
	 * @param topCount
	 * @return {@code List<String>} List of top 3 active IP addresses
	 */
	public static List<String> getTopActiveIPAddresses(final String logFilePath, final int topCount) {

		final List<String> logEntries = getLogEntriesFromLogFile(logFilePath);
		if (logEntries == null || logEntries.isEmpty()) {
			return Collections.emptyList();
		}

		final Map<String, Integer> ipHits = new HashMap<>();

		logEntries.forEach(log -> {
			String ipAddress = getIPAddress(log);
			if (ipAddress != null) {
				if (ipHits.containsKey(ipAddress)) {
					ipHits.put(ipAddress, ipHits.get(ipAddress) + 1);
				} else {
					ipHits.put(ipAddress, 1);
				}
			}
		});

		return ipHits.entrySet().parallelStream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
				.limit(topCount).map(Map.Entry::getKey).collect(Collectors.toList());
	}

	/**
	 * Fetches top 3 most visited request URLs from all the log entries in the given
	 * log file
	 * 
	 * @param logEntries
	 * @param topCount
	 * @return {@code List<String>} List of top 3 most visited URLs
	 */
	public static List<String> getTopVisitedURLs(final String logFilePath, final int topCount) {

		List<String> logEntries = getLogEntriesFromLogFile(logFilePath);
		if (logEntries == null || logEntries.isEmpty()) {
			return Collections.emptyList();
		}

		final Map<String, Integer> urlHits = new HashMap<>();

		logEntries.forEach(log -> {
			String requestURL = getRequestURL(log);
			if (requestURL != null) {
				if (urlHits.containsKey(requestURL)) {
					urlHits.put(requestURL, urlHits.get(requestURL) + 1);
				} else {
					urlHits.put(requestURL, 1);
				}
			}
		});

		return urlHits.entrySet().parallelStream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
				.limit(topCount).map(Map.Entry::getKey).collect(Collectors.toList());
	}

	/**
	 * Parse the log entry using {@link StringTokenizer} and \t as the delimiter and
	 * returns the IP address
	 * 
	 * @param log
	 * @return IP address from the log entry
	 */
	public static String getIPAddress(final String log) {

		if (log == null) {
			return null;
		}

		return new StringTokenizer(log, " \t").nextToken();
	}

	/**
	 * Parse the log entry using {@link StringTokenizer} and \t as the delimiter and
	 * returns the request URL. Read all the tokens until tokenizer returns the URL
	 * 
	 * @param log
	 * @return request URL from the log entry
	 */
	public static String getRequestURL(final String log) {

		if (log == null) {
			return null;
		}

		StringTokenizer tokenizer = new StringTokenizer(log, " \t");

		// Separator
		tokenizer.nextToken();
		// Separator
		tokenizer.nextToken("[");
		// Request Time
		tokenizer.nextToken(" \t");
		// Separator
		tokenizer.nextToken("\"");
		// HTTP Request
		String httpRequest = tokenizer.nextToken();
		// Separator
		tokenizer.nextToken(" \t");
		// HTTP Status
		tokenizer.nextToken(" \t");
		// Separator
		tokenizer.nextToken("\"");
		// Referrer
		tokenizer.nextToken();
		// Separator
		tokenizer.nextToken();
		// User Agent
		tokenizer.nextToken();

		// Use another String Tokenizer on the HTTP Request
		tokenizer = new StringTokenizer(httpRequest);
		// HTTP Method
		tokenizer.nextToken();
		// Request URL
		return tokenizer.nextToken();
	}

}
