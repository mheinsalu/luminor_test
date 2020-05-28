package ee.mrtnh.luminor_test.model.query;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.lang.Nullable;

@NoArgsConstructor
@Getter
@ToString
public class QueryAllRequest {

    @Nullable
    Integer largerThanAmountFilter;
    @Nullable
    Integer smallerThanAmountFilter;
}
