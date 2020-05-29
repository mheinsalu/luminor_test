package ee.mrtnh.luminor_test.repository;

import ee.mrtnh.luminor_test.model.payment.notification.NotificationResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
class NotificationResponseRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private NotificationResponseRepository notificationResponseRepository;

    @Test
    void whenFindByUuid_thenReturnNotification() {
        // given
        NotificationResponse notificationResponse = new NotificationResponse("testUuid", true);
        entityManager.persist(notificationResponse);
        entityManager.flush();

        // when
        NotificationResponse found = notificationResponseRepository.findByUuid(notificationResponse.getUuid());

        // then
        assertThat(found.getUuid()).isEqualTo(notificationResponse.getUuid());
    }

    @Test
    void whenFindByFalseUuid_thenReturnNotification() {
        // given
        NotificationResponse notificationResponse = new NotificationResponse("testUuid", true);
        entityManager.persist(notificationResponse);
        entityManager.flush();

        // when
        NotificationResponse found = notificationResponseRepository.findByUuid("falseTestUuid");

        // then
        assertThat(found).isNull();
    }
}