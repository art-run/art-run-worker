package artrun.artrunworker;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.locationtech.jts.operation.overlay.snap.LineStringSnapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class MapMatchingConsumer {

    private final KafkaSender kafkaSender;

    /**
     * match.req topic에서 메시지를 받아서 맵매칭하여 match.req topic으로 반환
     * @param message
     */
    @KafkaListener(id="main-listener", topics = "match.req")
    public void receiveMapMatchRequest(String message) throws JsonProcessingException, ParseException {
        log.info("Kafka to Worker listen: " + message);
        RouteMatchDto routeMatchDto = new ObjectMapper().readValue(message, RouteMatchDto.class);
        RouteMatchDto matchedRouteMatchDto = snapToTargetRoute(routeMatchDto);
        String matchedMessage = new ObjectMapper().writeValueAsString(matchedRouteMatchDto);

        kafkaSender.send("match.res", matchedMessage);
    }

    private RouteMatchDto snapToTargetRoute(RouteMatchDto routeMatchDto) throws ParseException {
        Geometry targetRoute = new WKTReader().read(routeMatchDto.getWktTargetRoute());
        LineStringSnapper lineStringSnapper = new LineStringSnapper((LineString) targetRoute, 0.05);
        Coordinate coordinate = new Coordinate(routeMatchDto.getLat(), routeMatchDto.getLng());
        Coordinate snappedCoordinate = lineStringSnapper.snapTo(new Coordinate[]{coordinate})[0];

        routeMatchDto.setLat(snappedCoordinate.getX());
        routeMatchDto.setLng(snappedCoordinate.getY());

        return routeMatchDto;
    }
}
