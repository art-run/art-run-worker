package artrun.artrunworker;

import lombok.Getter;
import lombok.Setter;
import org.locationtech.jts.geom.LineString;

@Getter
@Setter
public class RouteMatchDto {

    private Long routeId;
    private LineString targetRoute;
    private Double lat;
    private Double lng;

    @Override
    public String toString() {
        return "RouteMatchDto{" +
                "routeId=" + routeId +
                ", targetRoute=" + targetRoute +
                ", lat=" + lat +
                ", lng=" + lng +
                '}';
    }
}
