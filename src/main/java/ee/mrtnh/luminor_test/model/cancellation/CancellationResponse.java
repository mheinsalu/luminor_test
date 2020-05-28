package ee.mrtnh.luminor_test.model.cancellation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CancellationResponse {

    @NotNull(message = "Cancellation request must have uuid")
    String uuid;
    Double cancellationFee;
    String message;
}
