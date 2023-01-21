package com.virtualcave.excercise.remote;

import org.apache.logging.log4j.util.Base64Util;
import org.springframework.beans.factory.annotation.Value;

public class Credentials {

    @Value("${app.currency.credentials.authentication.username}")
    private static String username;

    @Value("${app.currency.credentials.authentication.username}")
    private static String password;

    public static CharSequence getEncodedCredentials() {
        String credentials = username + ":" + password;
        return Base64Util.encode(credentials);
    }
}
