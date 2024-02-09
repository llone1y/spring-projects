package project3;


import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class RestClient {
    public static void main(String[] args) {
        final String sensorName = "test_sensor";

        registrationForSensor(sensorName);

        Random random = new Random();

        double minTempereature = -99.0;
        double maxTemperature = 99.0;

        for(int i = 0; i < 100; i++) {
            System.out.println(i);
            sendMeasurement(random.nextDouble() * maxTemperature, random.nextBoolean(), sensorName);
        }

    }

    private static void registrationForSensor(String sensorName) {
        final String url = "http://localhost:8080/sensors/registration";

        Map<String, Object> json = new HashMap<>();
        json.put("name", sensorName);

        makePostRequestWithJSON(url, json);
    }

    private static void sendMeasurement(double value, boolean raining, String sensor) {
        final String url = "http://localhost:8080/measurements/add";

        Map<String, Object> json = new HashMap<>();
        json.put("value", value);
        json.put("raining", raining);
        json.put("sensor", Map.of("name", sensor));

        makePostRequestWithJSON(url, json);
    }

    private static void makePostRequestWithJSON(String url, Map<String, Object> json) {
        final RestTemplate restTemplate = new RestTemplate();

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> request = new HttpEntity<>(json, headers);

        try {
            restTemplate.postForObject(url, request, String.class);

            System.out.println("Измерение успешно отправлено на сервер");
        } catch (HttpClientErrorException e) {
            System.out.println("Error");
            System.out.println(e.getMessage());
        }
    }
}
