package ee.mrtnh.luminor_test.service;

import ee.mrtnh.luminor_test.model.cancellation.CancellationRequest;
import ee.mrtnh.luminor_test.model.cancellation.CancellationResponse;
import ee.mrtnh.luminor_test.model.payment.PaymentType1;
import ee.mrtnh.luminor_test.repository.PaymentType1Repository;
import ee.mrtnh.luminor_test.util.CancellationUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertNotNull;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
public class CancellationServiceTest {

    @Mock
    PaymentType1Repository type1Repository;

    @Mock
    CancellationUtil cancellationUtil;

    @InjectMocks
    CancellationService cancellationService;

    final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");

    @Test
    public void processCancellation_givesMessage() {
        PaymentType1 paymentType1 = new PaymentType1(1, "EUR", "debtorIban", "creditorIban", "details");
        paymentType1.setUuid("type1testUuid");
        LocalDateTime previousHour = LocalDateTime.now().minusHours(1);
        String previousHourString = previousHour.format(dateTimeFormatter);
        paymentType1.setCreationDate(previousHourString);

        LocalDateTime cancellationDateTime = LocalDateTime.parse("2020-01-17_07-01-02", dateTimeFormatter);

        given(type1Repository.findByUuid(paymentType1.getUuid())).willReturn(paymentType1);
        given(cancellationUtil.isNowOnSameDayAsPayment(LocalDateTime.parse(paymentType1.getCreationDate(), dateTimeFormatter), cancellationDateTime)).willReturn(true);

        CancellationRequest cancellationRequest = new CancellationRequest("type1testUuid");
        CancellationResponse cancellationResponse = cancellationService.processCancellation(cancellationRequest);

        assertNotNull(cancellationResponse.getMessage());
    }
}