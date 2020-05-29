package ee.mrtnh.luminor_test.model.payment;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PaymentType1Test {

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorConstraints_detailsNull_throwsIllegalArgumentException() {
        new PaymentType1(1, "EUR", "debtorIban", "creditorIban", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorConstraints_currencyUSD_throwsIllegalArgumentException() {
        new PaymentType1(1, "USD", "debtorIban", "creditorIban", "details");
    }

}