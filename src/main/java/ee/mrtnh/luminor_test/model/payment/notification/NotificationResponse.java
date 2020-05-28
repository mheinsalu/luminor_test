package ee.mrtnh.luminor_test.model.payment.notification;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.IDENTITY;

@NoArgsConstructor
@Setter
@Entity
@ToString
public class NotificationResponse {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;

    String uuid;
    boolean notificationSuccess;

    public NotificationResponse(String uuid, boolean notificationSuccess) {
        this.uuid = uuid;
        this.notificationSuccess = notificationSuccess;
    }
}
