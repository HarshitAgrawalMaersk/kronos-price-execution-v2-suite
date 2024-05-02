package kronos.price.exe.regression.suite.model.PriceExceution;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class PlaceOfDelivery {
    String locationFunction;
    String cityCode;
    String countryCode;
    String facilityCode;
}
