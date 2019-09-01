HTTP Apache Log Parser
======================

Possible Approaches
-------------------
HTTP Apache has standard defined log formats as given in [mod_log_config](https://httpd.apache.org/docs/current/mod/mod_log_config.html#formats), there are quite a possible ways to parse HTTP logs:

For the tasks in hand, following approaches are implemented and tested:
- Parse HTTP logs using StringTokenizer
- Parse HTTP logs using Regex Pattern

Below 3rd party library is also explored:
- [nielsbasjes](https://github.com/nielsbasjes/logparser) - This framework can take any standard log format, parse the log entry and can map its values to a defined POJO. While using this library, it highlighted that 2 entries of the provided log files are corrupted and don't match to standard HTTP format (`%h %l %u %t \"%r\" %>s %b \"%{Referer}i\" \"%{User-Agent}i\"`) - both contains additional entries after `User-Agent` which is not expected. The invalid log entries from the log file are mentioned below:

	72.44.32.10 - - [09/Jul/2018:15:48:07 +0200] "GET / HTTP/1.1" 200 3574 "-" "Mozilla/5.0 (compatible; MSIE 10.6; Windows NT 6.1; Trident/5.0; InfoPath.2; SLCC1; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729; .NET CLR 2.0.50727) 3gpp-gba UNTRUSTED/1.0" junk extra
	168.41.191.9 - - [09/Jul/2018:22:56:45 +0200] "GET /docs/ HTTP/1.1" 200 3574 "-" "Mozilla/5.0 (X11; Linux i686; rv:6.0) Gecko/20100101 Firefox/6.0" 456 789


For ongoing log parsing, aggregating, filtering and reporting, below widely popular solutions are suggested for the application monitoring, tracing and reporting:
- ELK and/or EFK (Elastic + Logstash/Fluentd + Kibana) stack
- Splunk or SumoLogic


# Gradle Commands

Following `gradle` commands can be executed to build, test & run the utility classes. Change the working directory to the root directory `\HTTPLogParser` project on the command line interface then execute resepctive commands.

### Build the Executable

```
$ gradlew build
```

### Run the Executable Jar

```
$ gradlew run
```
Output the results in the console.


### Run the Tests

```
$ gradlew test
```

Runs JUnit tests on your code (by default).  Outputs an HTML file with test results to `build/reports/tests/test/index.html`.

### Code Coverage Report

```
$ gradlew jacocoTestReport
```

Outputs a code coverage report to `build/reports/jacoco/test/html/index.html`.


