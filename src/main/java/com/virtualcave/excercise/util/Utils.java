package com.virtualcave.excercise.util;

import com.virtualcave.excercise.model.Rate;
import com.virtualcave.excercise.model.dto.RateDto;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

public class Utils {

    static String serverName;
    static int port;

    @Value("${app.currency.server.url}")
    private static String wiremockUrl;

    @Value("${app.currency.server.port}")
    private static int wiremockPort;

    public Utils() throws IOException {
        InetAddress ip = InetAddress.getLocalHost();
        String serverName = ip.getHostAddress();
        ServerSocket serverSocket = new ServerSocket(0);
        int port = serverSocket.getLocalPort();
        serverSocket.close();
    }

    public static String getServerNameAndPort() {
        return serverName + ":" + port;
    }

    public static String getLocalServerName() {
        return serverName;
    }

    public static int getLocalServerPort() {
        return port;
    }

    public static String getCurrencyServerUrl() {
        return wiremockUrl;
    }
    public static int getCurrencyServerPort() {
        return wiremockPort;
    }

    public static String getCurrencyServerUrlAndPort() {
        return wiremockUrl + ":" + port;
    }

    public static Rate convertRateDtoToRate(RateDto rateDto) {
        Rate rate = new Rate();
        rate.setId(rateDto.getId());
        rate.setBrandId(rateDto.getBrandId());
        rate.setProductId(rateDto.getProductId());
        rate.setStartDate(rateDto.getStartDate());
        rate.setEndDate(rateDto.getEndDate());
        rate.setPrice(rateDto.getPrice());
        rate.setCurrencyCode(rateDto.getCurrencyCode());
        return rate;
    }

    public static RateDto convertRateToRateDto(Rate rate) {
        RateDto rateDto = new RateDto();
        rateDto.setId(rate.getId());
        rateDto.setBrandId(rate.getBrandId());
        rateDto.setProductId(rate.getProductId());
        rateDto.setStartDate(rate.getStartDate());
        rateDto.setEndDate(rate.getEndDate());
        rateDto.setPrice(rate.getPrice());
        rateDto.setCurrencyCode(rate.getCurrencyCode());
        return rateDto;
    }
}


