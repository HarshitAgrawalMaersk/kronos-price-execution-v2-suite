package kronos.price.exe.regression.suite.model.PriceExceution;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Locations {
    @JsonProperty("locationFunction")
    private String locationFunction;

    @JsonProperty("cityCode")
    private String cityCode;

    @JsonProperty("countryCode")
    private String countryCode;

    @JsonProperty("facilityCode")
    private String facilityCode;

}
