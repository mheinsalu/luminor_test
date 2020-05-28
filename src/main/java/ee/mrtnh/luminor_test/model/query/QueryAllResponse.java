package ee.mrtnh.luminor_test.model.query;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class QueryAllResponse {

    List<String> listOfPaymentUuids;
}
