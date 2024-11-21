package com.moviedatabase.api;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class MovieAPI {

    public static void loadMovieDetail(int movieID) {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.themoviedb.org/3/movie/"+movieID+"?language=en-US"))
                .header("accept", "application/json")
                .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI2OTNjZTc1Y2I4MTJjYzY1NTJmN2YwNTAxZWIyYzRkYyIsIm5iZiI6MTczMjA3MjQ4Ni43MTE0Njc3LCJzdWIiOiI2NzM5ZjNmMDYyNGE4NWI4ODk5ZWQzODMiLCJzY29wZXMiOlsiYXBpX3JlYWQiXSwidmVyc2lvbiI6MX0.YlL1L51iVV5GSbHeu3TAi_ZQXaBOZRTf00-81fSZAGU")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        try {
            HttpResponse<String> response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println(prettyPrint(response.body()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
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
}
