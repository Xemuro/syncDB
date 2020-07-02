package pl.dreem.sync.controller;

import io.restassured.http.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import pl.dreem.sync.controller.request.NewAlertRequest;
import pl.dreem.sync.controller.request.UpdateAlertRequest;
import pl.dreem.sync.db.replication.ReplicationDbConnectionMock;
import pl.dreem.sync.db.source.SourceDBConnectionMock;
import pl.dreem.sync.domain.dto.NewAlertDto;
import pl.dreem.sync.domain.identifer.AlertId;
import pl.dreem.sync.service.SourceDBService;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNull.notNullValue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReplicateDBControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ReplicationDbConnectionMock replicationConnectionMock;

    @Autowired
    private SourceDBConnectionMock sourceConnectionMock;

    @Autowired
    private SourceDBService service;

    @Test
    public void newAlarmShouldBeReplicatedIn5sec() {
        final NewAlertRequest testRequest = getNewAlertRequest();

        final String alertId = given().port(port)
                                      .with()
                                      .contentType(ContentType.JSON)
                                      .body(testRequest)
                                      .post("/db/source/alerts")
                                      .then()
                                      .statusCode(200)
                                      .extract()
                                      .jsonPath()
                                      .getString("alertId");

        await().atMost(Duration.ofSeconds(5))
               .until(() -> replicationConnectionMock.getAlertById(AlertId.from(UUID.fromString(alertId))),
                      is(notNullValue()));
    }

    @Test
    public void updatedAlarmShouldBeReplicatedIn5sec() {
        final NewAlertRequest testRequest = getNewAlertRequest();

        final String alertId = given().port(port)
                                      .with()
                                      .contentType(ContentType.JSON)
                                      .body(testRequest)
                                      .post("/db/source/alerts")
                                      .then()
                                      .statusCode(200)
                                      .extract()
                                      .jsonPath()
                                      .getString("alertId");

        final UpdateAlertRequest expectedResult = UpdateAlertRequest.from("update", 987);

        given().port(port)
               .with()
               .contentType(ContentType.JSON)
               .body(expectedResult)
               .put("/db/source/alerts/" + alertId)
               .then()
               .statusCode(200);

        await().atMost(Duration.ofSeconds(5))
               .until(() -> replicationConnectionMock.getAlertById(AlertId.from(UUID.fromString(alertId))).isPresent(),
                      is(true));
        await().atMost(Duration.ofSeconds(5))
               .until(() -> replicationConnectionMock.getAlertById(AlertId.from(UUID.fromString(alertId)))
                                                     .get()
                                                     .getCode(),
                      is(expectedResult.getCode()));
    }

    @Test
    public void deletedAlarmShouldBeRemovedIn5sec() {
        final NewAlertRequest testRequest = getNewAlertRequest();

        final String alertId = given().port(port)
                                      .with()
                                      .contentType(ContentType.JSON)
                                      .body(testRequest)
                                      .post("/db/source/alerts")
                                      .then()
                                      .statusCode(200)
                                      .extract()
                                      .jsonPath()
                                      .getString("alertId");

        await().atMost(Duration.ofSeconds(5))
               .until(() -> sourceConnectionMock.getAlertById(AlertId.from(UUID.fromString(alertId))).isPresent(),
                      is(true));

        await().atMost(Duration.ofSeconds(5))
               .until(() -> replicationConnectionMock.getAlertById(AlertId.from(UUID.fromString(alertId))).isPresent(),
                      is(true));

        given().port(port)
               .with()
               .delete("/db/source/alerts/" + alertId)
               .then()
               .statusCode(204);

        await().atMost(Duration.ofSeconds(5))
               .until(() -> replicationConnectionMock.getAlertById(AlertId.from(UUID.fromString(alertId))).isPresent(),
                      is(false));

        await().atMost(Duration.ofSeconds(5))
               .until(() -> sourceConnectionMock.getAlertById(AlertId.from(UUID.fromString(alertId))).isPresent(),
                      is(false));
    }

    @Test
    public void newAlarmShouldBeReplicatedIn5secLoadTests() {
        int loadTestSize = 1000;

        List<NewAlertRequest> testData = new ArrayList<>();
        for (int i = 0; i < loadTestSize; i++) {
            testData.add(getNewAlertRequest());
        }

        testData.forEach(newAlarm -> {
            given().port(port)
                   .with()
                   .contentType(ContentType.JSON)
                   .body(newAlarm)
                   .post("/db/source/alerts")
                   .then()
                   .statusCode(200)
                   .extract()
                   .jsonPath()
                   .getString("alertId");
        });

        await().atMost(Duration.ofSeconds(5))
               .until(() -> replicationConnectionMock.getAllAlerts().size(),
                      is(loadTestSize));
    }

    @Test
    public void deletedAlarmShouldBeRemovedIn5secLoadTest() {
        int loadTestSize = 1000;

        List<NewAlertRequest> testData = new ArrayList<>();
        for (int i = 0; i < loadTestSize; i++) {
            testData.add(getNewAlertRequest());
        }

        final List<UUID> alarmsIds = new ArrayList<>();
        testData.forEach(newAlarm -> {
            String alertId = given().port(port)
                                    .with()
                                    .contentType(ContentType.JSON)
                                    .body(newAlarm)
                                    .post("/db/source/alerts")
                                    .then()
                                    .statusCode(200)
                                    .extract()
                                    .jsonPath()
                                    .getString("alertId");
            alarmsIds.add(UUID.fromString(alertId));
        });

        alarmsIds.forEach(alarmId -> {
            given().port(port)
                   .with()
                   .delete("/db/source/alerts/" + alarmId.toString())
                   .then()
                   .statusCode(204);
        });

        await().atMost(Duration.ofSeconds(5))
               .until(() -> replicationConnectionMock.getAllAlerts().size(),
                      is(0));
    }

    private NewAlertRequest getNewAlertRequest() {
        return new NewAlertRequest("testMessage", 123);
    }

}
