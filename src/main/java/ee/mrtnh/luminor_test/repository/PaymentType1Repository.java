package ee.mrtnh.luminor_test.repository;

import ee.mrtnh.luminor_test.model.payment.PaymentType1;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface PaymentType1Repository extends PaymentBaseRepository<PaymentType1> {

}
