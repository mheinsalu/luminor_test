package ee.mrtnh.luminor_test.util;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.*;

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
    public void calculateCancellationFee_type1_previousHour() {
        LocalDateTime cancellationTime = LocalDateTime.parse("2020-01-17_06-01-02", dateTimeFormatter);

        double fee = cancellationUtil.calculateCancellationFee(paymentDate, cancellationTime, 1);
        assertEquals(fee, 0.05, 0);
    }

    @Test
    public void calculateCancellationFee_type1_previousMinute() {
        LocalDateTime cancellationTime = LocalDateTime.parse("2020-01-17_07-01-01", dateTimeFormatter);

        double fee = cancellationUtil.calculateCancellationFee(paymentDate, cancellationTime, 1);
        assertEquals(fee, 0, 0);
    }

    @Test
    public void calculateCancellationFee_type2_previousHour() {
        LocalDateTime cancellationTime = LocalDateTime.parse("2020-01-17_06-01-02", dateTimeFormatter);

        double fee = cancellationUtil.calculateCancellationFee(paymentDate, cancellationTime, 2);
        assertEquals(fee, 0.1, 0);
    }

    @Test
    public void calculateCancellationFee_type3_previousHour() {
        LocalDateTime cancellationTime = LocalDateTime.parse("2020-01-17_06-01-02", dateTimeFormatter);

        double fee = cancellationUtil.calculateCancellationFee(paymentDate, cancellationTime, 3);
        assertEquals(fee, 0.15, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void calculateCancellationFee_typeInvalid_previousHour() {
        LocalDateTime cancellationTime = LocalDateTime.parse("2020-01-17_06-01-02", dateTimeFormatter);

        cancellationUtil.calculateCancellationFee(paymentDate, cancellationTime, 4);
    }


}