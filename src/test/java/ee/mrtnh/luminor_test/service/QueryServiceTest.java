package ee.mrtnh.luminor_test.service;

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
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
public class QueryServiceTest {

    @Mock
    PaymentType1Repository type1Repository;

    @Mock
    PaymentType2Repository type2Repository;

    @Mock
    PaymentType3Repository type3Repository;

    @InjectMocks
    QueryService queryService;

    @BeforeAll
    public static void init() {
        // TODO: couldn't initialize listOfPayments1 here. tests failed
    }

    @Test
    public void queryAllPaymentUuids_withNoLimits_noneCancelled() {
        List<PaymentType1> listOfPayments1 = new ArrayList<>();
        listOfPayments1.add(generatePaymentType1WithAmount(1));
        listOfPayments1.add(generatePaymentType1WithAmount(2));
        listOfPayments1.add(generatePaymentType1WithAmount(3));
        listOfPayments1.add(generatePaymentType1WithAmount(4));

        given(type1Repository.findAll()).willReturn(listOfPayments1);
        given(type2Repository.findAll()).willReturn(new ArrayList<PaymentType2>());
        given(type3Repository.findAll()).willReturn(new ArrayList<PaymentType3>());

        QueryAllRequest query = new QueryAllRequest();
        QueryAllResponse queryAllResponse = queryService.queryAllPaymentUuids(query);
        assertEquals(4, queryAllResponse.getListOfPaymentUuids().size());
    }

    @Test
    public void queryAllPaymentUuids_withNoLimits_oneCancelled() {
        List<PaymentType1> listOfPayments1 = new ArrayList<>();
        PaymentType1 paymentType1 = generatePaymentType1WithAmount(1);
        paymentType1.setCancellationFee(0D);
        listOfPayments1.add(paymentType1);
        listOfPayments1.add(generatePaymentType1WithAmount(2));
        listOfPayments1.add(generatePaymentType1WithAmount(3));
        listOfPayments1.add(generatePaymentType1WithAmount(4));

        given(type1Repository.findAll()).willReturn(listOfPayments1);
        given(type2Repository.findAll()).willReturn(new ArrayList<PaymentType2>());
        given(type3Repository.findAll()).willReturn(new ArrayList<PaymentType3>());

        QueryAllRequest query = new QueryAllRequest();
        QueryAllResponse queryAllResponse = queryService.queryAllPaymentUuids(query);
        assertEquals(3, queryAllResponse.getListOfPaymentUuids().size());
    }

    public void queryAllPaymentUuids_withUpperLimit() {
        List<PaymentType1> listOfPayments1 = new ArrayList<>();
        listOfPayments1.add(generatePaymentType1WithAmount(1));
        listOfPayments1.add(generatePaymentType1WithAmount(2));
        listOfPayments1.add(generatePaymentType1WithAmount(3));
        listOfPayments1.add(generatePaymentType1WithAmount(4));

        given(type1Repository.findAll()).willReturn(listOfPayments1);
        given(type2Repository.findAll()).willReturn(new ArrayList<PaymentType2>());
        given(type3Repository.findAll()).willReturn(new ArrayList<PaymentType3>());

        QueryAllRequest query = new QueryAllRequest();
        query.setUpperLimit(3);
        QueryAllResponse queryAllResponse = queryService.queryAllPaymentUuids(query);
        assertEquals(3, queryAllResponse.getListOfPaymentUuids().size());
    }

    public void queryAllPaymentUuids_withLowerLimit() {
        List<PaymentType1> listOfPayments1 = new ArrayList<>();
        listOfPayments1.add(generatePaymentType1WithAmount(1));
        listOfPayments1.add(generatePaymentType1WithAmount(2));
        listOfPayments1.add(generatePaymentType1WithAmount(3));
        listOfPayments1.add(generatePaymentType1WithAmount(4));

        given(type1Repository.findAll()).willReturn(listOfPayments1);
        given(type2Repository.findAll()).willReturn(new ArrayList<PaymentType2>());
        given(type3Repository.findAll()).willReturn(new ArrayList<PaymentType3>());

        QueryAllRequest query = new QueryAllRequest();
        query.setLowerLimit(2);
        QueryAllResponse queryAllResponse = queryService.queryAllPaymentUuids(query);
        assertEquals(2, queryAllResponse.getListOfPaymentUuids().size());
    }

    public void queryAllPaymentUuids_withBothLimits() {
        List<PaymentType1> listOfPayments1 = new ArrayList<>();
        listOfPayments1.add(generatePaymentType1WithAmount(1));
        listOfPayments1.add(generatePaymentType1WithAmount(2));
        listOfPayments1.add(generatePaymentType1WithAmount(3));
        listOfPayments1.add(generatePaymentType1WithAmount(4));

        given(type1Repository.findAll()).willReturn(listOfPayments1);
        given(type2Repository.findAll()).willReturn(new ArrayList<PaymentType2>());
        given(type3Repository.findAll()).willReturn(new ArrayList<PaymentType3>());

        QueryAllRequest query = new QueryAllRequest();
        query.setUpperLimit(3);
        query.setLowerLimit(2);
        QueryAllResponse queryAllResponse = queryService.queryAllPaymentUuids(query);
        assertEquals(2, queryAllResponse.getListOfPaymentUuids().size());
    }

    @Test
    public void queryByUuid_found() {
        PaymentType1 paymentType1 = generatePaymentType1WithAmount(1);

        given(type1Repository.findByUuid(paymentType1.getUuid())).willReturn(paymentType1);

        QueryRequest query = new QueryRequest(paymentType1.getUuid());
        QueryResponse queryResponse = queryService.queryByUuid(query);

        assertNull(queryResponse.getCancellationFee());
    }

    @Test
    public void queryByUuid_notFound() {
        PaymentType1 paymentType1 = generatePaymentType1WithAmount(1);

        given(type1Repository.findByUuid(paymentType1.getUuid())).willReturn(null);

        QueryRequest query = new QueryRequest(paymentType1.getUuid());
        QueryResponse queryResponse = queryService.queryByUuid(query);

        assertNotNull(queryResponse.getMessage());
    }

    private PaymentType1 generatePaymentType1WithAmount(double amount) {
        PaymentType1 paymentType1 = new PaymentType1(amount, "EUR", "debtorIban", "creditorIban", "details");
        paymentType1.setUuid("type1testUuid_" + Math.random());
        return paymentType1;
    }
}