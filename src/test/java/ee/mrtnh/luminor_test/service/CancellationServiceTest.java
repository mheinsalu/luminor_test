package ee.mrtnh.luminor_test.service;

import ee.mrtnh.luminor_test.model.payment.PaymentType1;
import ee.mrtnh.luminor_test.repository.PaymentType1Repository;
import ee.mrtnh.luminor_test.repository.PaymentType2Repository;
import ee.mrtnh.luminor_test.repository.PaymentType3Repository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class CancellationServiceTest {

    @Mock
    PaymentType1Repository type1Repository;

    @Mock
    PaymentType2Repository type2Repository;

    @Mock
    PaymentType3Repository type3Repository;

    @Before
    public void setUp() {
        PaymentType1 paymentType1 = new PaymentType1(1, "EUR", "debtorIban", "creditorIban", "details");
        paymentType1.setUuid("type1testUuid");

        Mockito.when(type1Repository.findByUuid(paymentType1.getUuid()))
                .thenReturn(paymentType1);
    }

    @Test
    public void processCancellation() {
    }
}