package ru.clevertec.rates.controller;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@DirtiesContext
@SpringBootTest
public class RateControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8089);

    @Test
    public void restCheckCourseBYNTest() throws Exception {
        initLatestRateMock();
        initYesterdayRateMock();
        initRichGifMock();
        initBrokeGifMock();
        mockMvc.perform(get("/rate/rest-check?currency=byn"))
                .andExpect(jsonPath("$.data.id", is("qw7j9oHhS0wWguOhTK")))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void restCheckCourseAEDTest() throws Exception {
        initLatestRateMock();
        initYesterdayRateMock();
        initRichGifMock();
        initBrokeGifMock();
        mockMvc.perform(get("/rate/rest-check?currency=aed"))
                .andExpect(jsonPath("$.data.id", is("JPmzN81Iptc1xrxPDE")))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void checkCourseTest() throws Exception {
        initLatestRateMock();
        initYesterdayRateMock();
        initRichGifMock();
        initBrokeGifMock();
        mockMvc.perform(get("/rate/check?currency=byn"))
                .andExpect(status().is3xxRedirection());
    }

    private void initLatestRateMock() {
        WireMock.stubFor(
                WireMock.any(urlMatching("/api/latest"))
                        .willReturn(WireMock.aResponse()
                                .withHeader("Content-Type", "application/json;charset=UTF-8")
                                .withBodyFile("response/rates-latest.json")));
    }

    private void initYesterdayRateMock() {
        WireMock.stubFor(
                WireMock.any(urlMatching("/api/historical"))
                        .willReturn(WireMock.aResponse()
                                .withHeader("Content-Type", "application/json;charset=UTF-8")
                                .withBodyFile("response/rates-yesterday.json")));
    }

    private void initRichGifMock() {
        WireMock.stubFor(
                WireMock.any(urlMatching("/random/rich"))
                        .willReturn(WireMock.aResponse()
                                .withHeader("Content-Type", "application/json;charset=UTF-8")
                                .withBodyFile("response/gif-rich.json")));
    }

    private void initBrokeGifMock() {
        WireMock.stubFor(
                WireMock.any(urlMatching("/random/broke"))
                        .willReturn(WireMock.aResponse()
                                .withHeader("Content-Type", "application/json;charset=UTF-8")
                                .withBodyFile("response/gif-broke.json")));
    }
}
