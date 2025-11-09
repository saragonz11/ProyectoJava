package com.FactuaracionPrimeraEntregaGonzalez.Makeup.error;

import lombok.*;
import java.time.Instant;
import java.util.Map;

@Data @NoArgsConstructor @AllArgsConstructor
public class ErrorPayload {
    private boolean success = false;
    private String message;
    private String path;
    private Map<String,String> fieldErrors;
    private Instant timestamp = Instant.now();
}
