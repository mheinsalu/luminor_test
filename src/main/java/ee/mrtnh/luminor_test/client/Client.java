package ee.mrtnh.luminor_test.client;

import ee.mrtnh.luminor_test.model.payment.notification.NotificationRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.lang.Nullable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.InputMismatchException;

@Component
@Slf4j
public class Client {

    // TODO: client methods currently always return false

    @Value("${notification.url}")
    String notificationURL;

    public boolean sendNotificationOfPayment(NotificationRequest notificationRequest) {
        try {
            String uuid = notificationRequest.getUuid();
            String requestUrl = createRequestUrl(notificationRequest);
            log.info("Sending notification of payment " + notificationRequest + " to url " + notificationURL);

            HttpHeaders headers = createHeaders();
            // HttpEntity<String>: To get result as String.
            HttpEntity<String> entity = new HttpEntity<>(headers);

            RestTemplate restTemplate = new RestTemplate();

            // Send request with GET method and Headers.
            log.info("Sending notification of payment saved");
            ResponseEntity<String> response = restTemplate.exchange(requestUrl, HttpMethod.GET, entity, String.class, uuid);
            log.info("Notification sent");

            HttpStatus statusCode = response.getStatusCode();
            log.info("Response Status Code: " + statusCode);

            if (statusCode.is2xxSuccessful()) {
                return true;
            }
        } catch (Exception e) {
            log.error("Could not send notification for " + notificationRequest);
        }
        return false;
    }

    private String createRequestUrl(NotificationRequest notificationRequest) {
        log.info("Creating notification request URL based on payment type");
        if (notificationRequest.getPaymentType().equalsIgnoreCase("PaymentType1")) {
            return notificationURL + "PaymentType1/" + "{uuid}";
        } else if (notificationRequest.getPaymentType().equalsIgnoreCase("PaymentType2")) {
            return notificationURL + "PaymentType2/" + "{uuid}";
        }
        String message = "Could not send notification. Incompatible payment type (must be type 1 or 2)";
        log.error(message);
        throw new InputMismatchException(message);
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        return headers;
    }

    @Async
    public Boolean sendIp(String ip) {
        try {
            String requestUrl = "http://www.geoplugin.net/json.gp?ip=" + ip;

            HttpHeaders headers = createHeaders();
            // HttpEntity<String>: To get result as String.
            HttpEntity<String> entity = new HttpEntity<>(headers);
            RestTemplate restTemplate = new RestTemplate();
            // Send request with GET method and Headers.
            log.info("Sending IP to external geolocation service");
            ResponseEntity<String> response = restTemplate.exchange(requestUrl, HttpMethod.GET, entity, String.class);
            if (response.getBody() == null) {
                log.info("IP query response body is null");
                return false;
            }
            String countryName = getCountryNameFromIpResponse(response.getBody());
            log.info("IP sent. Country is " + countryName);
            return true;
        } catch (Exception e) {
            log.error("Could not send IP to location service");
        }
        return false;
    }

    @Nullable
    private String getCountryNameFromIpResponse(String response) {
        String[] splitLines = response.split(",");
        for (String line : splitLines) {
            if (line.contains("geoplugin_countryName")) {
                return getLineValueFromLine(line);
            }
        }
        log.info("Could not find country name in response to IP query");
        return null;
    }

    private String getLineValueFromLine(String line) {
        String[] splitLine = line.split(":");
        if (splitLine.length == 2) {
            return splitLine[1];
        }
        return null;
    }
}
