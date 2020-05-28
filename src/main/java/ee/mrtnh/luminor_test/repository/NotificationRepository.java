package ee.mrtnh.luminor_test.repository;

import ee.mrtnh.luminor_test.model.payment.notification.NotificationResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationResponse, Long> {

    public NotificationResponse findByUuid(String uuid);
}
