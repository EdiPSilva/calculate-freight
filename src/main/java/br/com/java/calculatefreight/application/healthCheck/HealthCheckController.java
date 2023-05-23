package br.com.java.calculatefreight.application.healthCheck;

import br.com.java.calculatefreight.utils.DefaultResponse;
import br.com.java.calculatefreight.utils.StatusMessageEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;

@Slf4j
@RestController
@RequestMapping(value = "/health-check", produces = MediaType.APPLICATION_JSON_VALUE)
public class HealthCheckController {

    @Autowired
    private EntityManager entityManager;

    @GetMapping()
    public ResponseEntity<DefaultResponse> healthCheck() {
        if (performHealthCheck()) {
            return ResponseEntity.ok(DefaultResponse.builder()
                    .status(StatusMessageEnum.OK.getValue())
                    .build());
        }
        return ResponseEntity.internalServerError().build();
    }

    private boolean performHealthCheck() {
        try {
            entityManager.createNativeQuery("SELECT 1").getSingleResult();
        } catch (Exception exception) {
            log.error(exception.getMessage());
            return false;
        }
        return true;
    }
}
