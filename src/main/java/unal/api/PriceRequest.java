package unal.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PriceRequest {
    @JsonProperty
    double startLatitude;
    @JsonProperty
    double startLongitude;
    @JsonProperty
    double endLatitude;
    @JsonProperty
    double endLongitude;
    @JsonProperty
    Integer uberType;
}
