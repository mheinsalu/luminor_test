package ee.mrtnh.luminor_test.service;

import ee.mrtnh.luminor_test.client.Client;
import ee.mrtnh.luminor_test.model.payment.Payment;
import ee.mrtnh.luminor_test.model.payment.PaymentType1;
import ee.mrtnh.luminor_test.model.payment.PaymentType2;
import ee.mrtnh.luminor_test.model.payment.PaymentType3;
import ee.mrtnh.luminor_test.model.payment.notification.NotificationRequest;
import ee.mrtnh.luminor_test.model.payment.notification.NotificationResponse;
import ee.mrtnh.luminor_test.repository.NotificationResponseRepository;
import ee.mrtnh.luminor_test.repository.PaymentType1Repository;
import ee.mrtnh.luminor_test.repository.PaymentType2Repository;
import ee.mrtnh.luminor_test.repository.PaymentType3Repository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
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
    NotificationResponseRepository notificationResponseRepository;

    @Autowired
    Client client;

    public Payment processPayment(Payment payment, HttpServletRequest request) {
        payment.addTimeAndUuidToPayment();
        logClientCountry(request);
        return determinePaymentTypeAndThenProcess(payment);
    }

    private Payment determinePaymentTypeAndThenProcess(Payment payment) {
        String uuid = payment.getUuid();
        log.info("Determining payment type");
        // if (payment.getClass().isInstance(PaymentType1.class)) { // TODO: doesn't work. why?
        if (payment.toString().startsWith(TYPE_1)) {
            log.info("Payment " + uuid + " is " + TYPE_1);
            return processType1(payment);
        } else if (payment.toString().startsWith(TYPE_2)) {
            log.info("Payment " + uuid + " is " + TYPE_2);
            return processType2(payment);
        } else if (payment.toString().startsWith(TYPE_3)) {
            log.info("Payment " + uuid + " is " + TYPE_3);
            return processType3(payment);
        } else {
            String message = "Valid payment type not found for " + uuid;
            log.info(message);
            throw new InputMismatchException(message);
        }
    }

    private Payment processType1(Payment payment) {
        String uuid = payment.getUuid();
        Payment savedPayment = type1Repository.save((PaymentType1) payment);
        boolean notificationSuccess = client.sendNotificationOfPayment(new NotificationRequest(uuid, TYPE_1));
        log.info("Saving notification result to database. Notification success is " + notificationSuccess);
        notificationResponseRepository.save(new NotificationResponse(uuid, notificationSuccess));
        return savedPayment;
    }

    private Payment processType2(Payment payment) {
        String uuid = payment.getUuid();
        Payment savedPayment = type2Repository.save((PaymentType2) payment);
        boolean notificationSuccess = client.sendNotificationOfPayment(new NotificationRequest(uuid, TYPE_2));
        log.info("Saving notification result to database. Notification success is " + notificationSuccess);
        notificationResponseRepository.save(new NotificationResponse(uuid, notificationSuccess));
        return savedPayment;
    }

    private Payment processType3(Payment payment) {
        return type3Repository.save((PaymentType3) payment);
    }

    private void logClientCountry(HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        log.info("Client's ip is " + ip);
        client.sendIp(ip);
    }
}
