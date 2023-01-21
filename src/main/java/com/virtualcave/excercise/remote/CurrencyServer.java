package com.virtualcave.excercise.remote;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.virtualcave.excercise.model.currency.Currency;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CurrencyServer {

    @Autowired
    static Environment env;
    
    public Currency getCurrency(String currencyCode) {
        // TODO: Usar fichero CUrrencyServer ¿Pq no está cogiendo los valores de app.properties?
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://currencies.wiremockapi.cloud"
                        + "/v1/currencies/" + currencyCode)
                .addHeader("Authorization", "Basic " + Credentials.getEncodedCredentials())
                .build();

        // Envia la petición y recibe la respuesta
        try (Response response = client.newCall(request).execute()) {
            ObjectMapper mapper = new ObjectMapper();
            String json = response.body().string();
            return mapper.readValue(json, Currency.class);
        } catch (IOException e) {
            // TODO: Manejar excepcion
            throw new RuntimeException(e);
        }
    }
}
