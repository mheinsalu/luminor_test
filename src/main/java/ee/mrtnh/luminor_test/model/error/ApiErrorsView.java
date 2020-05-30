package ee.mrtnh.luminor_test.model.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ApiErrorsView {
    private List<ApiFieldError> fieldErrors;
    private List<ApiGlobalError> globalErrors;

}
