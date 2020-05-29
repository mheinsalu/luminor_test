package ee.mrtnh.luminor_test.model.payment;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

public class PaymentTest {

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorConstraints_paymentNegative_throwsIllegalArgumentException() {
        new PaymentType1(-1, "EUR", "debtorIban", "creditorIban", "details");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorConstraints_currencyNotEurOrUsd_throwsIllegalArgumentException() {
        new PaymentType1(1, "test", "debtorIban", "creditorIban", "details");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorConstraints_currencyNull_throwsIllegalArgumentException() {
        new PaymentType1(1, null, "debtorIban", "creditorIban", "details");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorConstraints_debtorNull_throwsIllegalArgumentException() {
        new PaymentType1(1, "EUR", null, "creditorIban", "details");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorConstraints_creditorNull_throwsIllegalArgumentException() {
        new PaymentType1(1, "EUR", "debtorIban", null, "details");
    }

    @Test
    public void addTimeAndUuidToPayment_test_addsFieldValues() {
        PaymentType1 paymentType1 = new PaymentType1(1, "EUR", "debtorIban", "creditorIban", "details");
        paymentType1.addTimeAndUuidToPayment();
        assertThat(paymentType1.getCreationDate()).isNotNull();
        assertThat(paymentType1.getUuid()).isNotNull();
    }
}