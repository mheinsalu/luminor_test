package ee.mrtnh.luminor_test.model.payment;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.util.UUID.randomUUID;
import static javax.persistence.GenerationType.IDENTITY;

@Slf4j
@ToString
@Getter
@NoArgsConstructor
@MappedSuperclass
//@Inheritance
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = PaymentType1.class),
        @JsonSubTypes.Type(value = PaymentType2.class),
        @JsonSubTypes.Type(value = PaymentType3.class),
})
public abstract class Payment {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;

    @Positive(message = "Payment amount must be positive")
    double amount;
    @NotNull(message = "Payment currency must be EUR or USD")
    String currency; // EUR or USD
    @NotNull(message = "Debtor IBAN field must not be null")
    String debtorIban;
    @NotNull(message = "Creditor IBAN field must not be null")
    String creditorIban;

    String creationDate;
    String uuid;
    @Setter
    Double cancellationFee;

    public Payment(double amount, String currency, String debtorIban, String creditorIban) {
        checkParameterConstraints(amount, currency, debtorIban, creditorIban);
        this.amount = amount;
        this.currency = currency;
        this.debtorIban = debtorIban;
        this.creditorIban = creditorIban;
    }

    private void checkParameterConstraints(double amount, String currency, String debtorIban, String creditorIban) {

        if (currency == null || debtorIban == null || creditorIban == null) { // amount is double. primitive can't be null
            String message = "Payment fields amount, currency, debtor IBAN and creditor IBAN must not be null";
            log.error(message);
            throw new IllegalArgumentException(message);
        }

        if (amount <= 0) {
            String message = "Payment amount must be positive";
            log.error(message);
            throw new IllegalArgumentException(message);
        }

        if (!(currency.equalsIgnoreCase("EUR") || currency.equalsIgnoreCase("USD"))) {
            String message = "Payment currency must be EUR or USD";
            log.error(message);
            throw new IllegalArgumentException(message);
        }
    }

    public void addTimeAndUuidToPayment() {
        log.info("Adding DateTime and uuid to payment");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        this.creationDate = LocalDateTime.now().format(dateTimeFormatter);
        this.uuid = randomUUID().toString();
        log.info("DateTime is " + creationDate + " and uuid is " + uuid);
    }

}
