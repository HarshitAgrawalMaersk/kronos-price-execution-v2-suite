package kronos.price.exe.regression.suite.model.PriceExceution;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;



@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommodityClassification {

    @JsonProperty("commodityCode")
    private String commodityCode;


}
