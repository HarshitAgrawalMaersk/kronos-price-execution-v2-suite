package kronos.price.exe.regression.suite.model.PriceExceution;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
//@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PricingParameters {

   @JsonProperty("pricingParameterName")
    private String pricingParameterName;

    @JsonProperty("pricingParameterValue")
    private String pricingParameterValue;


    public static List<PricingParameters> filterEmptyValues(List<PricingParameters> parameters) {


        return parameters.stream()
                .filter(param -> {
                    String name = param.getPricingParameterName();
                    String value = param.getPricingParameterValue();
                    return !(Objects.equals(name, "option1") || Objects.equals(name, "finalDestination"))
                            && value != null
                            && !value.isEmpty();
                })
                .collect(Collectors.toList());
    }

}
