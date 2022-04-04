package artrun.artrunworker;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.locationtech.jts.operation.overlay.snap.LineStringSnapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MapMatchingConsumerTest {

    @Test
    @DisplayName("스냅 테스트")
    void snapToTargetRoute() throws ParseException {
        // given
        RouteMatchDto routeMatchDto = new RouteMatchDto(1L, "LINESTRING(127.2676797373322 37.004810894968806,127.26727204156194 37.005033659711664,127.26705746484075 37.005376373426046,127.266563938382 37.00518788107435,127.26600603890691 37.004656672841335,127.26669268441472 37.004228276401264,127.26701454949651 37.00373133350641,127.26759390664373 37.00361138128649,127.26821617913518 37.00325152349115,127.26855950188909 37.00403978120286,127.26890282464299 37.00489657379332,127.26907448601995 37.005890441100696,127.26849512887273 37.005907576629994,127.26832346749578 37.00523928812571,127.26791577172551 37.004879438036134)", 128.1D, 38.02D);

        // when
        Geometry targetRoute = new WKTReader().read(routeMatchDto.getWktTargetRoute());
        LineStringSnapper lineStringSnapper = new LineStringSnapper((LineString) targetRoute, 0.05);
        Coordinate coordinate = new Coordinate(routeMatchDto.getLat(), routeMatchDto.getLng());
        Coordinate snappedCoordinate = lineStringSnapper.snapTo(new Coordinate[]{coordinate})[0];

        routeMatchDto.setLng(snappedCoordinate.getY());
        routeMatchDto.setLat(snappedCoordinate.getX());


        // then
        assertThat(routeMatchDto.getLat()).isBetween(126D, 128D);
        assertThat(routeMatchDto.getLng()).isBetween(36D, 38D);

    }
}