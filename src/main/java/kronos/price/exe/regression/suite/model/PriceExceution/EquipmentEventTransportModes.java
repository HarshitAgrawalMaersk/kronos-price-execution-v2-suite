package kronos.price.exe.regression.suite.model.PriceExceution;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)

public class EquipmentEventTransportModes {
    @JsonProperty("startEvent")
    private String startEvent;
    @JsonProperty("transportModeCode")
    private String transportModeCode;
}

