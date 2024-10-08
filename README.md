# Palindrome Checker App

Helidon MP project with a single endpoint to check if a word is a palindrome.

## Build and run
This is an offline project, so the maven repository is included and should use the local repository for all the maven commands and should also specify the offline flag, so maven won't try to download dependencies.

With JDK11+
```bash
mvn package -o -Dmaven.repo.local=<LOCATION>/cme/repository
java -jar target/palindrome-checker.jar
```

Replace `<LOCATION>` above with the full path to where this project has been unzipped.

## Exercise the application

```
curl -X GET http://localhost:8080/check?username=bob&value=kayak
{"result":true, "message":"Nice! You found a palindrome!"}

```

## See the health and metrics

```
curl -s -X GET http://localhost:8080/health
{"outcome":"UP",...
. . .

# Prometheus Format
curl -s -X GET http://localhost:8080/metrics
# TYPE base:gc_g1_young_generation_count gauge
. . .

# JSON Format
curl -H 'Accept: application/json' -X GET http://localhost:8080/metrics
{"base":...
. . .

```