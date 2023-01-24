package com.virtualcave.excercise.wiremock;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.virtualcave.excercise.service.dto.CurrencyDto;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import java.io.IOException;

@SpringBootTest
class WiremockServerTests {

    @Autowired
    Environment env;

    @Test
    void givenActiveWiremock_whenAskForCurrencyCode_thenReturnCurrency() throws RuntimeException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(env.getRequiredProperty("app.currency.server.url") +
                        env.getRequiredProperty("app.currency.server.path.currencies") + "EUR")
                .build();

        // Envía la petición y recibe la respuesta
        try (Response response = client.newCall(request).execute()) {

            assert response.body() != null;
            String json = response.body().string();
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.readValue(json, CurrencyDto.class);

            Assertions.assertEquals(200, response.code());
            Assertions.assertEquals("{\"code\":\"EUR\",\"symbol\":\"€\",\"decimals\":2}", json);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
