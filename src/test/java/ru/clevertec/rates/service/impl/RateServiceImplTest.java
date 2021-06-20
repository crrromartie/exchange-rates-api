package ru.clevertec.rates.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import ru.clevertec.rates.client.GifClient;
import ru.clevertec.rates.client.RateClient;
import ru.clevertec.rates.entity.GifData;
import ru.clevertec.rates.entity.Rate;
import ru.clevertec.rates.service.RateService;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@DirtiesContext
@SpringBootTest(classes = RateServiceImpl.class)
public class RateServiceImplTest {
    @Autowired
    private RateService rateService;

    @MockBean
    private RateClient rateClient;

    @MockBean
    private GifClient gifClient;

    @Test
    public void checkCourseTest() {
        Map<String, BigDecimal> latestRates = new HashMap<>();
        latestRates.put("BYN", new BigDecimal("2.5"));
        latestRates.put("RUB", new BigDecimal("65"));
        Rate latestRate = Rate.builder()
                .base("USD")
                .rates(latestRates)
                .build();

        Map<String, BigDecimal> yesterdayRates = new HashMap<>();
        yesterdayRates.put("BYN", new BigDecimal("2.4"));
        yesterdayRates.put("RUB", new BigDecimal("66"));
        Rate yesterdayRate = Rate.builder()
                .base("USD")
                .rates(yesterdayRates)
                .build();

        GifData brokeGif = new GifData();
        GifData.Data gifBrokeData = new GifData.Data();
        gifBrokeData.setId("broke");
        brokeGif.setData(gifBrokeData);

        GifData richGif = new GifData();
        GifData.Data gifRichData = new GifData.Data();
        gifRichData.setId("rich");
        richGif.setData(gifRichData);

        when(rateClient.getLatestRate()).thenReturn(latestRate);
        when(rateClient.getYesterdayRate(anyString())).thenReturn(yesterdayRate);
        when(gifClient.getRichGif()).thenReturn(brokeGif);
        when(gifClient.getBrokeGif()).thenReturn(richGif);

        GifData bynGif = rateService.checkCourse("BYN");
        GifData rubGif = rateService.checkCourse("RUB");

        assertThat(bynGif.getData().getId(), is("rich"));
        assertThat(rubGif.getData().getId(), is("broke"));
    }
}
