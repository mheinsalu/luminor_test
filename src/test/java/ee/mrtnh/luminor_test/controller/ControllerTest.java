package ee.mrtnh.luminor_test.controller;

import ee.mrtnh.luminor_test.model.cancellation.CancellationRequest;
import ee.mrtnh.luminor_test.model.payment.PaymentType1;
import ee.mrtnh.luminor_test.model.query.QueryAllRequest;
import ee.mrtnh.luminor_test.model.query.QueryRequest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void paymentEndpoint_isUp() throws Exception {
        PaymentType1 paymentType1 = new PaymentType1(1, "EUR", "debtorIban", "creditorIban", "details");

        final String baseUrl = "http://localhost:" + port + "/createPayment/";
        URI uri = new URI(baseUrl);
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<PaymentType1> request = new HttpEntity<>(paymentType1, headers);
        ResponseEntity<String> result = this.restTemplate.postForEntity(uri, request, String.class);

        //Verify request succeed
        Assert.assertEquals(200, result.getStatusCodeValue());
    }

    @Test
    public void cancelEndpoint_isUp() throws Exception {
        CancellationRequest cancellationRequest = new CancellationRequest("testUuid");

        final String baseUrl = "http://localhost:" + port + "/cancelPayment/";
        URI uri = new URI(baseUrl);
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<CancellationRequest> request = new HttpEntity<>(cancellationRequest, headers);
        ResponseEntity<String> result = this.restTemplate.postForEntity(uri, request, String.class);

        //Verify request succeed
        Assert.assertEquals(200, result.getStatusCodeValue());
    }

    @Test
    public void queryAllEndpoint_isUp() throws Exception {
        QueryAllRequest queryAllRequest = new QueryAllRequest();

        final String baseUrl = "http://localhost:" + port + "/queryAllPayments/";
        URI uri = new URI(baseUrl);
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<QueryAllRequest> request = new HttpEntity<>(queryAllRequest, headers);
        ResponseEntity<String> result = this.restTemplate.postForEntity(uri, request, String.class);

        //Verify request succeed
        Assert.assertEquals(200, result.getStatusCodeValue());
    }

    @Test
    public void queryByUuidEndpoint_isUp() throws Exception {
        QueryRequest queryRequest = new QueryRequest("testUuid");

        final String baseUrl = "http://localhost:" + port + "/queryPaymentByUuid/";
        URI uri = new URI(baseUrl);
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<QueryRequest> request = new HttpEntity<>(queryRequest, headers);
        ResponseEntity<String> result = this.restTemplate.postForEntity(uri, request, String.class);

        //Verify request succeed
        Assert.assertEquals(200, result.getStatusCodeValue());
    }

}
