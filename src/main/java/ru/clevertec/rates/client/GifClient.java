package ru.clevertec.rates.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import ru.clevertec.rates.entity.GifData;

@FeignClient(value = "gif", url = "${gif.url}")
public interface GifClient {

    @GetMapping(name = "rich", path = "${gif.rich}")
    GifData getRichGif();

    @GetMapping(name = "broke", path = "${gif.broke}")
    GifData getBrokeGif();
}
