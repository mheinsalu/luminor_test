package ee.mrtnh.luminor_test.repository;

import ee.mrtnh.luminor_test.model.payment.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface PaymentBaseRepository<T extends Payment> extends JpaRepository<T, Long> {

    public T findByUuid(String uuid);
}
