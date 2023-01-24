package com.virtualcave.excercise.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.virtualcave.excercise.exception.CurrencyException;
import com.virtualcave.excercise.service.dto.CurrencyDto;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.logging.log4j.util.Base64Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Slf4j
@Service
public class CurrencyServiceImpl implements CurrencyService {

    @Autowired
    private Environment env;

    @Override
    public CurrencyDto getCurrency(String currencyCode) {
        File cacheDirectory = new File(env.getRequiredProperty("app.currency.cache.folder"));
        int cacheSize = Integer.parseInt(env.getRequiredProperty("app.currency.cache.size")) * 1024 * 1024; // MB

        final OkHttpClient client = new OkHttpClient.Builder()
                .cache(new Cache(cacheDirectory, cacheSize))
                .build();

        Request request = new Request.Builder()
                .url(env.getRequiredProperty("app.currency.server.url") +
                        env.getRequiredProperty("app.currency.server.path.currencies") + currencyCode)
                .addHeader("Authorization", "Basic " + getEncodedCredentials())
                .build();

        // Envía la petición y recibe la respuesta
        try (Response response = client.newCall(request).execute()) {

            assert response.body() != null;
            String json = response.body().string();
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(json, CurrencyDto.class);

        } catch (IOException e) {
            throw new CurrencyException("Error realizando la llamada al 'currency server'", e);
        } catch (AssertionError | NullPointerException e) {
            throw new CurrencyException("Error parseando el body de la response", e);
        }
    }

    public CharSequence getEncodedCredentials() {
        String username = env.getRequiredProperty("app.currency.server.credentials.authentication.username");
        String password = env.getRequiredProperty("app.currency.server.credentials.authentication.password");
        String credentials = username + ":" + password;
        return Base64Util.encode(credentials);
    }
}
