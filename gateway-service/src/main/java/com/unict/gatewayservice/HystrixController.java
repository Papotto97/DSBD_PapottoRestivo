package com.unict.gatewayservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fallback")
public class HystrixController {

    @GetMapping("/auction-manager")
    public String firstServiceFallback(){
        return "This is a fallback for auction-manager.";
    }

    @GetMapping("/sagaorchestration")
    public String sagaorchestrationFallback(){
        return "This is a fallback for sagaorchestration.";
    }

    @GetMapping("/second")
    public String secondServiceFallback(){
        return "Second Server overloaded! Please try after some time.";
    }
}
