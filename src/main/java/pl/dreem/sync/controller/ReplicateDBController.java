package pl.dreem.sync.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.dreem.sync.controller.response.AlertResponse;
import pl.dreem.sync.db.DbConnectionFacade;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/db/replicate/alerts")
public class ReplicateDBController {

    private final DbConnectionFacade replicateFacade;

    public ReplicateDBController(@Qualifier("replicateDBFacade") final DbConnectionFacade replicateFacade) {
        this.replicateFacade = Objects.requireNonNull(replicateFacade);
    }

    @GetMapping
    public ResponseEntity<Set<AlertResponse>> getAlerts() {
        final Set<AlertResponse> response = replicateFacade.getAllAlerts()
                                                           .stream()
                                                           .map(AlertResponse::from)
                                                           .collect(Collectors.toUnmodifiableSet());
        return ResponseEntity.ok(response);
    }

}
