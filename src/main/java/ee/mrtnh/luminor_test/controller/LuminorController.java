package ee.mrtnh.luminor_test.controller;

import ee.mrtnh.luminor_test.model.cancellation.CancellationRequest;
import ee.mrtnh.luminor_test.model.cancellation.CancellationResponse;
import ee.mrtnh.luminor_test.model.payment.Payment;
import ee.mrtnh.luminor_test.model.query.QueryAllRequest;
import ee.mrtnh.luminor_test.model.query.QueryAllResponse;
import ee.mrtnh.luminor_test.model.query.QueryRequest;
import ee.mrtnh.luminor_test.model.query.QueryResponse;
import ee.mrtnh.luminor_test.service.CancellationService;
import ee.mrtnh.luminor_test.service.PaymentService;
import ee.mrtnh.luminor_test.service.QueryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@Slf4j
public class LuminorController {

    @Autowired
    PaymentService paymentService;

    @Autowired
    CancellationService cancellationService;

    @Autowired
    QueryService queryService;

    @PostMapping("/createPayment")
    public Payment createPayment(@RequestBody @Valid Payment payment, BindingResult bindingResult, HttpServletRequest request) {
        log.info("Call for payment creation");
        log.info(payment.toString());

        if (bindingResult.hasErrors()) {
            log.error("Constraint violation"); // TODO: send readable error message
            return null;
        }
        paymentService.logClientCountry(request);
        Payment response = paymentService.processPayment(payment);
        log.info("Sending response: " + response);
        return response;
    }

    @PostMapping("/cancelPayment")
    public CancellationResponse cancelPayment(@RequestBody @Valid CancellationRequest cancellationRequest, BindingResult bindingResult) {
        log.info("Call for payment cancellation");
        log.info(cancellationRequest.toString());

        CancellationResponse response = cancellationService.processCancellation(cancellationRequest);
        log.info("Sending response: " + response);
        return response;
    }

    @PostMapping("/queryAllPayments")
    public QueryAllResponse queryAllPayments(@RequestBody @Valid QueryAllRequest query, BindingResult bindingResult) {
        log.info("Call for all payments query");
        log.info(query.toString());

        QueryAllResponse response = queryService.queryAllPaymentUuids(query);
        log.info("Sending response: " + response);
        return response;
    }

    @PostMapping("/queryPaymentByUuid")
    public QueryResponse queryPaymentByUuid(@RequestBody @Valid QueryRequest query, BindingResult bindingResult) {
        log.info("Call for payment query");
        log.info(query.toString());

        QueryResponse response = queryService.queryByUuid(query);
        log.info("Sending response: " + response);
        return response;
    }


}
