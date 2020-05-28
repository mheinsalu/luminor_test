package ee.mrtnh.luminor_test.repository;

import ee.mrtnh.luminor_test.model.payment.PaymentType2;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface PaymentType2Repository extends PaymentBaseRepository<PaymentType2> {

}
