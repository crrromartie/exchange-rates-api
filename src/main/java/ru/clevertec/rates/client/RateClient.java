package ru.clevertec.rates.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.clevertec.rates.entity.Rate;

@FeignClient(value = "rate", url = "${rate.url}")
public interface RateClient {

    @GetMapping(name = "latest", path = "${rate.latest}")
    Rate getLatestRate();

    @GetMapping(name = "yesterday", path = "${rate.yesterday}")
    Rate getYesterdayRate(@PathVariable String date);
}
