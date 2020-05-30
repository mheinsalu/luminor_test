package ee.mrtnh.luminor_test.service;

import ee.mrtnh.luminor_test.model.payment.Payment;
import ee.mrtnh.luminor_test.model.payment.PaymentType1;
import ee.mrtnh.luminor_test.model.payment.PaymentType2;
import ee.mrtnh.luminor_test.model.payment.PaymentType3;
import ee.mrtnh.luminor_test.model.query.QueryAllRequest;
import ee.mrtnh.luminor_test.model.query.QueryAllResponse;
import ee.mrtnh.luminor_test.model.query.QueryRequest;
import ee.mrtnh.luminor_test.model.query.QueryResponse;
import ee.mrtnh.luminor_test.repository.PaymentType1Repository;
import ee.mrtnh.luminor_test.repository.PaymentType2Repository;
import ee.mrtnh.luminor_test.repository.PaymentType3Repository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class QueryService {

    @Autowired
    PaymentType1Repository type1Repository;

    @Autowired
    PaymentType2Repository type2Repository;

    @Autowired
    PaymentType3Repository type3Repository;

    public QueryAllResponse queryAllPaymentUuids(QueryAllRequest query) {
        log.info("Processing query for all payments");
        QueryAllResponse response = new QueryAllResponse();

        log.info("Finding all payments in database");
        List<Payment> listOfPayments = new ArrayList<>();
        listOfPayments.addAll(type1Repository.findAll());
        listOfPayments.addAll(type2Repository.findAll());
        listOfPayments.addAll(type3Repository.findAll());
        log.info("Payments found");

        listOfPayments = filterListOfPaymentsByCancelled(listOfPayments);
        listOfPayments = filterListOfPaymentsByAmount(listOfPayments, query);

        List<String> listOfUuids = listOfPayments.stream().map(Payment::getUuid).collect(Collectors.toList());
        response.setListOfPaymentUuids(listOfUuids);

        return response;
    }

    private List<Payment> filterListOfPaymentsByCancelled(List<Payment> listOfPayments) {
        log.info("Filtering list of payments by not cancelled status");
        listOfPayments = listOfPayments.stream().filter(payment -> payment.getCancellationFee() == null).collect(Collectors.toList());
        return listOfPayments;
    }

    private List<Payment> filterListOfPaymentsByAmount(List<Payment> listOfPayments, QueryAllRequest queryAllRequest) {
        log.info("Filtering list of payments by amount");
        Integer largerThan = queryAllRequest.getLowerLimit();
        if (largerThan != null) {
            log.info("Filtering list of payments by amount: larger than " + largerThan);
            listOfPayments = listOfPayments.stream().filter(payment -> payment.getAmount() >= largerThan).collect(Collectors.toList());
        }
        Integer smallerThan = queryAllRequest.getUpperLimit();
        if (smallerThan != null) {
            log.info("Filtering list of payments by amount: smaller than " + smallerThan);
            listOfPayments = listOfPayments.stream().filter(payment -> payment.getAmount() <= smallerThan).collect(Collectors.toList());
        }
        return listOfPayments;
    }


    public QueryResponse queryByUuid(QueryRequest query) {
        String uuid = query.getUuid();
        log.info("Processing query with uuid " + uuid);
        QueryResponse returnMessage = new QueryResponse();
        returnMessage.setUuid(uuid);

        PaymentType1 payment1 = type1Repository.findByUuid(uuid);
        if (payment1 != null) {
            log.info("Found payment of type 1");
            returnMessage.setUuid(payment1.getUuid());
            returnMessage.setCancellationFee(payment1.getCancellationFee());
            return returnMessage;
        }

        PaymentType2 payment2 = type2Repository.findByUuid(uuid);
        if (payment2 != null) {
            log.info("Found payment of type 2");
            returnMessage.setUuid(payment2.getUuid());
            returnMessage.setCancellationFee(payment2.getCancellationFee());
            return returnMessage;
        }

        PaymentType3 payment3 = type3Repository.findByUuid(uuid);
        if (payment3 != null) {
            log.info("Found payment of type 3");
            returnMessage.setUuid(payment3.getUuid());
            returnMessage.setCancellationFee(payment3.getCancellationFee());
            return returnMessage;
        }
        String message = "Could not find payment with uuid " + uuid;
        log.info(message);
        returnMessage.setMessage(message);
        return returnMessage;
    }
}
