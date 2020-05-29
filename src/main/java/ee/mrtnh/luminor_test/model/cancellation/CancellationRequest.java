package ee.mrtnh.luminor_test.model.cancellation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CancellationRequest {

    @NotNull(message = "Cancellation request must have uuid")
    String uuid;
}
