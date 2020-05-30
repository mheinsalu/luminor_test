package ee.mrtnh.luminor_test.model.cancellation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class CancellationRequest {

    @NotNull(message = "Cancellation request must have uuid")
    String uuid;
}
