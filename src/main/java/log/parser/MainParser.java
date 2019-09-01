package log.parser;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * @author Ashish
 *
 */
public class MainParser {

	/**
	 * @param args
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public static void main(String[] args) throws IOException, URISyntaxException {

		final String logFilePath = "log/files/programming-task-example-data.log";

		System.out.println("Using Regex Pattern ====================== Start");
		System.out.println(
				"Number of unique IP addresses: " + ApacheLogParserUsingRegex.getUniqueIPAddresses(logFilePath).size());
		System.out.println(
				"Top 3 most active IP addresses: " + ApacheLogParserUsingRegex.getTopActiveIPAddresses(logFilePath, 3));
		System.out.println(
				"Top 3 most visited request URLs: " + ApacheLogParserUsingRegex.getTopVisitedURLs(logFilePath, 3));
		System.out.println("Using Regex Pattern ====================== End");

		System.out.println("Using StringTokenizer ====================== Start");
		System.out.println("Number of unique IP addresses: "
				+ ApacheLogParserUsingStringTokenizer.getUniqueIPAddresses(logFilePath).size());
		System.out.println("Top 3 most active IP addresses: "
				+ ApacheLogParserUsingStringTokenizer.getTopActiveIPAddresses(logFilePath, 3));
		System.out.println("Top 3 most visited request URLs: "
				+ ApacheLogParserUsingStringTokenizer.getTopVisitedURLs(logFilePath, 3));
		System.out.println("Using StringTokenizer ====================== End");

	}

}
