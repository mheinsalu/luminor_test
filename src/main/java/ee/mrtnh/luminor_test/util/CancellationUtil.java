package ee.mrtnh.luminor_test.util;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CancellationUtil {

    public boolean isNowOnSameDayAsPayment(LocalDateTime paymentDateTime, LocalDateTime cancellationTime) {
        if (paymentDateTime.getYear() == cancellationTime.getYear()
                && paymentDateTime.getDayOfYear() == cancellationTime.getDayOfYear()) {
            return true;
        }
        return false;
    }

    public double calculateCancellationFee(LocalDateTime paymentDateTime, LocalDateTime cancellationTime, int paymentType) {
        int hoursInSystem = Math.abs(cancellationTime.getHour() - paymentDateTime.getHour());
        double coefficient;
        if (paymentType == 1) {
            coefficient = 0.05;
        } else if (paymentType == 2) {
            coefficient = 0.1;
        } else if (paymentType == 3) {
            coefficient = 0.15;
        } else {
            throw new IllegalArgumentException("Payment type must be 1, 2 or 3");
        }
        return hoursInSystem * coefficient;
    }

}
