package kronos.price.exe.regression.suite.model.PriceExceution;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;


@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EquipmentTypes {

    @JsonProperty("equipmentSizeTypeCode")
    private String equipmentSizeTypeCode;

    @JsonProperty("commodityClassification")
    private List<CommodityClassification> commodityClassification;

  }



