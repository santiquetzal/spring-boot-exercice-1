package com.virtualcave.excercise.remote;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.virtualcave.excercise.remote.dto.CurrencyDto;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CurrencyService {

    @Autowired
    static Environment env;
    
    public CurrencyDto getCurrency(String currencyCode) {
        // TODO: IMPORTANTE ¡¡¡ ESTO ESTÁ MOCKEADO !!!
        return CurrencyDto.builder().symbol("€").code("EUR").decimals(2).build();
    }
//    public CurrencyDto getCurrency(String currencyCode) {
//        // TODO: Usar fichero CurrencyService ¿Pq no está cogiendo los valores de app.properties?
//        OkHttpClient client = new OkHttpClient();
//        Request request = new Request.Builder()
//                .url("http://localhost:7070" + "/v1/currencies/" + currencyCode)
//                .addHeader("Authorization", "Basic " + Credentials.getEncodedCredentials())
//                .build();
//
//        // Envia la petición y recibe la respuesta
//        try (Response response = client.newCall(request).execute()) {
//            ObjectMapper mapper = new ObjectMapper();
//            String json = response.body().string();
//            return mapper.readValue(json, CurrencyDto.class);
//        } catch (IOException e) {
//            // TODO: Manejar excepcion
//            throw new RuntimeException(e);
//        }
//    }
}
