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
