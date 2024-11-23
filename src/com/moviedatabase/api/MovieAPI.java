package com.moviedatabase.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moviedatabase.main.Movie;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class MovieAPI {
    private static final String header = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI2OTNjZTc1Y2I4MTJjYzY1NTJmN2YwNTAxZWIyYzRkYyIsIm5iZiI6MTczMjA3MjQ4Ni43MTE0Njc3LCJzdWIiOiI2NzM5ZjNmMDYyNGE4NWI4ODk5ZWQzODMiLCJzY29wZXMiOlsiYXBpX3JlYWQiXSwidmVyc2lvbiI6MX0.YlL1L51iVV5GSbHeu3TAi_ZQXaBOZRTf00-81fSZAGU";

    private static final HttpClient client = HttpClient.newHttpClient();
    private static HttpRequest request;
    private static HttpResponse<String> response;

    public static Movie getMovie(String movieTitle) {
        request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.themoviedb.org/3/search/movie?query="+removeSpaces(movieTitle)+"&include_adult=false&language=en-US&page=1"))
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + header)
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());

            ObjectMapper mapper = new ObjectMapper();

            JsonNode movies = mapper.readTree(response.body()).get("results");

            return mapper.readValue(movies.get(0).toPrettyString(), Movie.class);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void loadMovieDetail(int movieID) {
        request = HttpRequest.newBuilder()
        .uri(URI.create("https://api.themoviedb.org/3/movie/"+movieID+"?language=en-US"))
        .header("accept", "application/json")
        .header("Authorization", "Bearer " + header)
        .method("GET", HttpRequest.BodyPublishers.noBody())
        .build();

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());

            ObjectMapper mapper = new ObjectMapper();

            JsonNode movie = mapper.readTree(response.body());
//                - Title
            System.out.println("Title: " + movie.get("title"));
//                - Show the summary/brief of the movie
            System.out.println("Overview: " + movie.get("overview"));
//                - genres of the movie
            System.out.println("Genre: " + movie.get("genres").findValues("name"));
//                - Popularity
            System.out.println("Popularity: " + movie.get("popularity"));
//                - Release date
            System.out.println("Release Date: " + movie.get("release_date"));
//                - Vote average
            System.out.println("Vote Average: " + movie.get("vote_average"));
//                - Vote counts
            System.out.println("Vote Count: " + movie.get("vote_count"));

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Movie> fetchSearchedMovies(String title) {
        request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.themoviedb.org/3/search/movie?query="+removeSpaces(title)+"&include_adult=false&language=en-US&page=1"))
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + header)
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());

            ObjectMapper mapper = new ObjectMapper();

            JsonNode movies = mapper.readTree(response.body()).get("results");

            List<Movie> searchedMovies = new ArrayList<>();

            for(JsonNode movieObj : movies) {
                searchedMovies.add(mapper.readValue(movieObj.toPrettyString(), Movie.class));
            }

            return searchedMovies;
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static String prettyPrint(String jsonString) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Object jsonObject = mapper.readValue(jsonString, Object.class);

            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static String removeSpaces(String title) {
        return title.trim().replaceAll(" ", "+");
    }
}
