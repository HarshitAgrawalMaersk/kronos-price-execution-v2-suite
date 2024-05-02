package kronos.price.exe.regression.suite.model.PriceExceution;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PriceExecutionRequest {

    @JsonProperty("priceCalculationBaseDatetime")
    private String priceCalculationBaseDatetime;
    @JsonProperty("brandCode")
    private String brandCode;
    @JsonProperty("transportActivity")
    private String transportActivity;
    @JsonProperty("agreementLineTypeCodes")
    private List<String> agreementLineTypeCodes;
    @JsonProperty("agreementType")
    private String agreementType;

    @JsonProperty("chargeTypes")
    private List<String> chargeTypes;

    @JsonProperty("locations")
    private List<Locations> locations;

    @JsonProperty("equipmentTypes")
    private List<EquipmentTypes> equipmentTypes;

    @JsonProperty("pricingParameters")
    private List<PricingParameters> pricingParameters;

    @JsonProperty("searchContext")
    private List<SearchContext> searchContexts;


    @JsonProperty("searchConfigurations")
    private List<SearchConfigurations> searchConfigurations;

    @JsonProperty("equipmentEventTransportModes")
    private List<EquipmentEventTransportModes> equipmentEventTransportModes;





}
