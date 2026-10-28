package org.example.scheduler;

import org.example.service.PriceAlertService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class PriceAlertScheduler {

    private final PriceAlertService priceAlertService;

    public PriceAlertScheduler(PriceAlertService priceAlertService) {
        this.priceAlertService = priceAlertService;
    }

    @Scheduled(fixedDelayString = "${pretium.alerts.check-interval-ms:300000}")
    public void checkPriceAlerts() {
        priceAlertService.checkActiveAlerts();
    }
}

