package com.virtualcave.excercise.wiremock;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.virtualcave.excercise.exception.CurrencyException;
import com.virtualcave.excercise.service.dto.CurrencyDto;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import java.io.IOException;

@SpringBootTest
@Slf4j
class WiremockTests {

    @Autowired
    Environment env;

    private static final String HOST = "localhost";
    private static final int PORT = 8080;
    public static WireMockServer server = new WireMockServer(PORT);


    static String message = "{\"code\":\"EUR\",\"symbol\":\"€\",\"decimals\":2}";

    @BeforeAll
    static void lista() {
        server.start();
        WireMock.configureFor(HOST, PORT);


        ResponseDefinitionBuilder mockResponse = new ResponseDefinitionBuilder();
        mockResponse.withStatus(200);
        mockResponse.withHeader("Content-Type", "application/json");
        mockResponse.withBody(message);

        WireMock.givenThat(WireMock.get("/v1/currencies/EUR").willReturn(mockResponse));
    }

    @Test
    void testCurrencyDetailsApi() throws IOException {
        String testApi = "http://" + HOST + ":" + PORT + "/v1/currencies/EUR";

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(testApi)
                .build();

        // Envía la petición y recibe la respuesta
        try (Response response = client.newCall(request).execute()) {

            assert response.body() != null;
            String json = response.body().string();

            Assertions.assertEquals(200, response.code());
            Assertions.assertEquals(message, json);

        } catch (Exception e) {
            throw e;
        }
    }

    @AfterAll
    static void closeServer() {
        if (server.isRunning() && server != null) {
            server.shutdown();
        }
    }
}
