package ee.mrtnh.luminor_test.client;

import ee.mrtnh.luminor_test.model.payment.notification.NotificationRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.InputMismatchException;

@Component
@Slf4j
public class NotificationClient {

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
        String message = "Could not send notification. Incompatible payment type";
        log.error(message);
        throw new InputMismatchException(message);
    }

    private HttpHeaders createHeaders() {
        log.info("Creating Http headers for Notification Client");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        return headers;
    }
}
