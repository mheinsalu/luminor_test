package ee.mrtnh.luminor_test.util;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CancellationUtilTest {

    CancellationUtil cancellationUtil = new CancellationUtil();

    final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");

    LocalDateTime paymentDate = LocalDateTime.parse("2020-01-17_07-01-02", dateTimeFormatter);

    @Test
    public void isNowOnSameDayAsPayment_true() {
        LocalDateTime cancellationTime = LocalDateTime.parse("2020-01-17_07-01-02", dateTimeFormatter);
        assertTrue(cancellationUtil.isNowOnSameDayAsPayment(paymentDate, cancellationTime));
    }

    @Test
    public void isNowOnSameDayAsPayment_false() {
        LocalDateTime cancellationTime = LocalDateTime.parse("2020-01-18_07-01-02", dateTimeFormatter);
        assertFalse(cancellationUtil.isNowOnSameDayAsPayment(paymentDate, cancellationTime));
    }

    @Test
    public void calculateCancellationFee() {
    }
}