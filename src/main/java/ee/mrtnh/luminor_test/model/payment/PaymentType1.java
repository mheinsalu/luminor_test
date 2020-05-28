package ee.mrtnh.luminor_test.model.payment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Slf4j
@ToString(callSuper = true)
@Getter
@NoArgsConstructor
@Entity
public class PaymentType1 extends Payment {

    @NotNull(message = "Details field must not be null")
    String details;

    public PaymentType1(double amount, String currency, String debtorIban, String creditorIban, String details) {
        super(amount, currency, debtorIban, creditorIban);
        checkParameterConstraints(currency, details);
        this.details = details;
    }

    private void checkParameterConstraints(String currency, String details) {
        if (!currency.equalsIgnoreCase("EUR")) {
            String message = "Payment currency for payment TYPE1 must be EUR";
            log.error(message);
            throw new IllegalArgumentException(message);
        }
        if (details == null) {
            String message = "Details field in payment TYPE1 must not be null";
            log.error(message);
            throw new IllegalArgumentException(message);
        }
    }
}
