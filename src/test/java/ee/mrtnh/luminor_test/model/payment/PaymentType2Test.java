package ee.mrtnh.luminor_test.model.payment;

import org.junit.Test;

public class PaymentType2Test {

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorConstraints_currencyEUR_throwsIllegalArgumentException() {
        new PaymentType2(1, "EUR", "debtorIban", "creditorIban", null);
    }
}