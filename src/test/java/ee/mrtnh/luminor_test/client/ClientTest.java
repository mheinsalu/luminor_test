package ee.mrtnh.luminor_test.client;

import ee.mrtnh.luminor_test.model.payment.notification.NotificationRequest;
import org.junit.Test;

import static org.junit.Assert.*;

public class ClientTest {

    Client client = new Client();

    // TODO: client methods currently always return false. If situation changes then tests should be changed

    @Test
    public void sendNotificationOfPayment() {
        NotificationRequest notificationRequest = new NotificationRequest("testUuid", "PaymentType1");
        assertFalse(client.sendNotificationOfPayment(notificationRequest));
    }

    @Test
    public void sendIp() {
        String ip = "test";
        assertFalse(client.sendIp(ip));
    }
}