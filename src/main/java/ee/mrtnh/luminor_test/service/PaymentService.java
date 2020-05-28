package ee.mrtnh.luminor_test.service;

import ee.mrtnh.luminor_test.client.NotificationClient;
import ee.mrtnh.luminor_test.model.payment.Payment;
import ee.mrtnh.luminor_test.model.payment.PaymentType1;
import ee.mrtnh.luminor_test.model.payment.PaymentType2;
import ee.mrtnh.luminor_test.model.payment.PaymentType3;
import ee.mrtnh.luminor_test.model.payment.notification.NotificationRequest;
import ee.mrtnh.luminor_test.model.payment.notification.NotificationResponse;
import ee.mrtnh.luminor_test.repository.NotificationRepository;
import ee.mrtnh.luminor_test.repository.PaymentType1Repository;
import ee.mrtnh.luminor_test.repository.PaymentType2Repository;
import ee.mrtnh.luminor_test.repository.PaymentType3Repository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.InputMismatchException;

@Service
@Slf4j
public class PaymentService {

    final String TYPE_1 = "PaymentType1";
    final String TYPE_2 = "PaymentType2";
    final String TYPE_3 = "PaymentType3";

    @Autowired
    PaymentType1Repository type1Repository;

    @Autowired
    PaymentType2Repository type2Repository;

    @Autowired
    PaymentType3Repository type3Repository;

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    NotificationClient notificationClient;

    public Payment processPayment(Payment payment) {
        payment.addTimeAndUuidToPayment();
        return savePaymentBasedOnTypeAndNotify(payment);
    }

    private Payment savePaymentBasedOnTypeAndNotify(Payment payment) {
        String uuid = payment.getUuid();
        log.info("Saving payment with uuid " + uuid + " to database");
        Payment savedPayment = null;
        boolean notificationSuccess = false;
//        if (payment.getClass().isInstance(PaymentType1.class)) { // TODO: doesn't work
        if (payment.toString().startsWith(TYPE_1)) {
            log.info("Payment " + uuid + " is " + TYPE_1);
            savedPayment = type1Repository.save((PaymentType1) payment);
            notificationSuccess = notificationClient.sendNotificationOfPayment(new NotificationRequest(uuid, TYPE_1));
        } else if (payment.toString().startsWith(TYPE_2)) {
            log.info("Payment " + uuid + " is " + TYPE_2);
            savedPayment = type2Repository.save((PaymentType2) payment);
            notificationSuccess = notificationClient.sendNotificationOfPayment(new NotificationRequest(uuid, TYPE_2));
        } else if (payment.toString().startsWith(TYPE_3)) {
            log.info("Payment " + uuid + " is " + TYPE_3);
            savedPayment = type3Repository.save((PaymentType3) payment);
            notificationSuccess = notificationClient.sendNotificationOfPayment(new NotificationRequest(uuid, TYPE_3));
        } else {
            String message = "Valid payment type not found for " + uuid;
            log.info(message);
            throw new InputMismatchException(message);
            // TODO: throw error? try-catch in controller and on catch send some message to client, how to send message that is not return type?
        }
        log.info("Saving notification result to database. Notification success is " + notificationSuccess);
        notificationRepository.save(new NotificationResponse(uuid, notificationSuccess));
        return savedPayment;
    }
}
