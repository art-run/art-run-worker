package artrun.artrunworker;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RouteMatchDto {

    private Long routeId;
    private String wktTargetRoute;
    private Double lat;
    private Double lng;

    public RouteMatchDto(Long routeId, String wktTargetRoute, Double lat, Double lng) {
        this.routeId = routeId;
        this.wktTargetRoute = wktTargetRoute;
        this.lat = lat;
        this.lng = lng;
    }

    @Override
    public String toString() {
        return "RouteMatchDto{" +
                "routeId=" + routeId +
                ", wktTargetRoute='" + wktTargetRoute + '\'' +
                ", lat=" + lat +
                ", lng=" + lng +
                '}';
    }
}
