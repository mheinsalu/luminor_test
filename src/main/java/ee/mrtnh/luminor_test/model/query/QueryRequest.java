package ee.mrtnh.luminor_test.model.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class QueryRequest {

    @NotNull(message = "Query request must have uuid")
    String uuid;
}
