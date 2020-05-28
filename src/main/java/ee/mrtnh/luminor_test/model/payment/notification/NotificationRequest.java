package ee.mrtnh.luminor_test.model.payment.notification;

import lombok.*;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class NotificationRequest {

    @NotNull
    String uuid;
    @NotNull
    String paymentType;

}
