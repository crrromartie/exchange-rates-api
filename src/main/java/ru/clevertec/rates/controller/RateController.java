package ru.clevertec.rates.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.rates.entity.GifData;
import ru.clevertec.rates.service.RateService;

import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@RestController
@RequestMapping("/rate")
public class RateController {
    private static final String GIF_URL = "url";

    private final RateService rateService;

    @GetMapping("/rest-check")
    public ResponseEntity<GifData> restCheckCourse(@RequestParam String currency) {
        return new ResponseEntity<>(rateService.checkCourse(currency), HttpStatus.OK);
    }

    @SneakyThrows
    @GetMapping("/check")
    public void checkCourse(@RequestParam String currency, HttpServletResponse response) {
        response.sendRedirect(rateService.checkCourse(currency).getData().getImages().getOriginal().get(GIF_URL));
    }
}
