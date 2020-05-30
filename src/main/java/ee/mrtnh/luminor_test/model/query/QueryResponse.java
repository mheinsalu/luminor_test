package ee.mrtnh.luminor_test.model.query;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class QueryResponse {

    String uuid;
    Double cancellationFee;
    String message;
}
