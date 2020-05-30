package ee.mrtnh.luminor_test.model.query;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.lang.Nullable;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class QueryAllRequest {

    @Nullable
    Integer lowerLimit;
    @Nullable
    Integer upperLimit;
}
