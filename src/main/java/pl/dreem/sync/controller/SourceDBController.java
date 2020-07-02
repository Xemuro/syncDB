package pl.dreem.sync.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.dreem.sync.controller.request.NewAlertRequest;
import pl.dreem.sync.controller.request.UpdateAlertRequest;
import pl.dreem.sync.controller.response.AlertResponse;
import pl.dreem.sync.domain.dto.NewAlertDto;
import pl.dreem.sync.domain.dto.UpdateAlertDto;
import pl.dreem.sync.domain.identifer.AlertId;
import pl.dreem.sync.service.SourceDBService;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/db/source/alerts")
public class SourceDBController {

    private final SourceDBService service;

    public SourceDBController(final SourceDBService service) {
        this.service = Objects.requireNonNull(service);
    }

    @GetMapping
    public ResponseEntity<Set<AlertResponse>> getAlerts() {
        final Set<AlertResponse> response = service.findAllAlerts()
                                                   .stream()
                                                   .map(AlertResponse::from)
                                                   .collect(Collectors.toUnmodifiableSet());
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<AlertId> newAlert(@RequestBody final NewAlertRequest request) {
        final AlertId alertId = service.saveAlert(NewAlertDto.from(request));
        return ResponseEntity.ok(alertId);
    }

    @PutMapping("/{alertId}")
    public ResponseEntity updateAlert(@PathVariable("alertId") final UUID alertId,
                                      @RequestBody final UpdateAlertRequest request) {
        service.updateAlert(UpdateAlertDto.from(AlertId.from(alertId), request));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{alertId}")
    public ResponseEntity deleteAlert(@PathVariable("alertId") final UUID alertId) {
        service.deleteAlert(AlertId.from(alertId));
        return ResponseEntity.noContent().build();
    }

}