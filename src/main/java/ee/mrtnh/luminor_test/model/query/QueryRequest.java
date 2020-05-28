package ee.mrtnh.luminor_test.model.query;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
public class QueryRequest {

    @NotNull(message = "Query request must have uuid")
    String uuid;
}
