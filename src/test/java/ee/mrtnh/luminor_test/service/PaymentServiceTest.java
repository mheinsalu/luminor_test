package ee.mrtnh.luminor_test.service;

import ee.mrtnh.luminor_test.repository.NotificationResponseRepository;
import ee.mrtnh.luminor_test.repository.PaymentType1Repository;
import ee.mrtnh.luminor_test.repository.PaymentType2Repository;
import ee.mrtnh.luminor_test.repository.PaymentType3Repository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
class PaymentServiceTest {

    @Autowired
    private PaymentService paymentService;

    @MockBean
    PaymentType1Repository type1Repository;

    @MockBean
    PaymentType2Repository type2Repository;

    @MockBean
    PaymentType3Repository type3Repository;

    @MockBean
    NotificationResponseRepository notificationResponseRepository;


    @Test
    void processPayment1() {
    }

    @Test
    void processPayment2() {
    }

    @Test
    void processPayment3() {
    }
}