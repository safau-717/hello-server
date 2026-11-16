package com.example;

import com.sun.net.httpserver.HttpServer;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HelloServerTest {

    @Test
    void greetWithoutName() {
        assertEquals("Hello, World!", HelloServer.greet(null));
    }

    @Test
    void serverAnswersOverHttp() throws Exception {
        HttpServer server = HelloServer.start(0); // port libre choisi par l'OS
        try {
            int port = server.getAddress().getPort();
            HttpRequest request = HttpRequest.newBuilder(
                    URI.create("http://localhost:" + port + "/hello?name=Maven")).build();
            HttpResponse<String> response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());

            assertEquals(200, response.statusCode());
            assertEquals("Hello, Maven!", response.body());
        } finally {
            server.stop(0);
        }
    }
}
