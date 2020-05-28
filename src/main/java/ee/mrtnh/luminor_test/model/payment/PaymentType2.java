package ee.mrtnh.luminor_test.model.payment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;

import javax.persistence.Entity;

@Slf4j
@ToString(callSuper = true)
@Getter
@NoArgsConstructor
@Entity
public class PaymentType2 extends Payment {

    String details;

    public PaymentType2(double amount, String currency, String debtorIban, String creditorIban, @Nullable String details) {
        super(amount, currency, debtorIban, creditorIban);
        checkParameterConstraints(currency);
        this.details = details;
    }

    private void checkParameterConstraints(String currency) {
        if (!currency.equalsIgnoreCase("USD")) {
            String message = "Payment currency for payment TYPE2 must be USD";
            log.error(message);
            throw new IllegalArgumentException(message);
        }
    }
}
