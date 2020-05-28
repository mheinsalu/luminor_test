package ee.mrtnh.luminor_test.service;

import ee.mrtnh.luminor_test.model.cancellation.CancellationRequest;
import ee.mrtnh.luminor_test.model.cancellation.CancellationResponse;
import ee.mrtnh.luminor_test.model.payment.PaymentType1;
import ee.mrtnh.luminor_test.model.payment.PaymentType2;
import ee.mrtnh.luminor_test.model.payment.PaymentType3;
import ee.mrtnh.luminor_test.repository.PaymentType1Repository;
import ee.mrtnh.luminor_test.repository.PaymentType2Repository;
import ee.mrtnh.luminor_test.repository.PaymentType3Repository;
import ee.mrtnh.luminor_test.util.CancellationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Slf4j
public class CancellationService {

    final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");

    @Autowired
    PaymentType1Repository type1Repository;

    @Autowired
    PaymentType2Repository type2Repository;

    @Autowired
    PaymentType3Repository type3Repository;

    @Autowired
    CancellationUtil cancellationUtil;

    public CancellationResponse processCancellation(@Valid CancellationRequest cancellationRequest) {
        LocalDateTime cancellationTime = LocalDateTime.now();
        CancellationResponse cancellationResponse = new CancellationResponse();
        cancellationResponse.setUuid(cancellationRequest.getUuid());
        try {
            double fee = findPaymentAcrossTypeTablesAndCancel(cancellationRequest.getUuid(), cancellationTime);
            cancellationResponse.setCancellationFee(fee);
            cancellationResponse.setMessage("Payment cancelled");
        } catch (NullPointerException e) {
            cancellationResponse.setMessage(e.getMessage());
        }
        catch (Exception e) {
            log.error(e.getMessage());
            cancellationResponse.setMessage("Internal server error");
        }
        return cancellationResponse;
    }

    private double findPaymentAcrossTypeTablesAndCancel(String uuid, LocalDateTime cancellationTime) {
        PaymentType1 payment1 = type1Repository.findByUuid(uuid);
        String paymentDateTimeString = null;

        if (payment1 != null) {
            paymentDateTimeString = payment1.getCreationDate(); // TODO: check for null?
            LocalDateTime paymentDateTime = LocalDateTime.parse(paymentDateTimeString, dateTimeFormatter);
            if (cancellationUtil.isNowOnSameDayAsPayment(paymentDateTime, cancellationTime)) {
                double fee = cancellationUtil.calculateCancellationFee(paymentDateTime, cancellationTime, 1);
                payment1.setCancellationFee(fee);
                type1Repository.save(payment1);
                return fee;
            }
        }

        PaymentType2 payment2 = type2Repository.findByUuid(uuid);
        if (payment2 != null) {
            paymentDateTimeString = payment2.getCreationDate();
            LocalDateTime paymentDateTime = LocalDateTime.parse(paymentDateTimeString, dateTimeFormatter);
            if (cancellationUtil.isNowOnSameDayAsPayment(paymentDateTime, cancellationTime)) {
                double fee = cancellationUtil.calculateCancellationFee(paymentDateTime, cancellationTime, 2);
                payment2.setCancellationFee(fee);
                type2Repository.save(payment2);
                return fee;
            }
        }

        PaymentType3 payment3 = type3Repository.findByUuid(uuid);
        if (payment3 != null) {
            paymentDateTimeString = payment3.getCreationDate();
            LocalDateTime paymentDateTime = LocalDateTime.parse(paymentDateTimeString, dateTimeFormatter);
            if (cancellationUtil.isNowOnSameDayAsPayment(paymentDateTime, cancellationTime)) {
                double fee = cancellationUtil.calculateCancellationFee(paymentDateTime, cancellationTime, 3);
                payment3.setCancellationFee(fee);
                type3Repository.save(payment3);
                return fee;
            }
        }
        throw new NullPointerException("Could not find payment");
    }
}
