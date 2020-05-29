package ee.mrtnh.luminor_test.model.payment;

import org.junit.Test;

public class PaymentType3Test {

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorConstraints_BicNull_throwsIllegalArgumentException() {
        new PaymentType3(1, "USD", "debtorIban", "creditorIban", null);
    }

}