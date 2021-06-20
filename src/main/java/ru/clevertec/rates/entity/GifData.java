package ru.clevertec.rates.entity;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
public class GifData implements Serializable {
    private Data data;

    @lombok.Data
    @JsonRootName(value = "data")
    public static class Data implements Serializable {
        private String type;
        private String id;
        private Images images;

        @lombok.Data
        @JsonRootName(value = "images")
        public static class Images implements Serializable {
            private Map<String, String> original;
        }
    }
}
