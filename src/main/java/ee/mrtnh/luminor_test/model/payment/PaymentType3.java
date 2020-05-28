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
public class PaymentType3 extends Payment {

    @NotNull
    String creditorBankBIC;

    public PaymentType3(double amount, String currency, String debtorIban, String creditorIban, String creditorBankBIC) {
        super(amount, currency, debtorIban, creditorIban);
        checkParameterConstraints(creditorBankBIC);
        this.creditorBankBIC = creditorBankBIC;
    }

    private void checkParameterConstraints(String creditorBankBIC) {
        if (creditorBankBIC == null) {
            String message = "Creditor bank BIC field in payment TYPE3 must not be null";
            log.error(message);
            throw new IllegalArgumentException(message);
        }
    }
}
