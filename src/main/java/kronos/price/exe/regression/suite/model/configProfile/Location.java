package kronos.price.exe.regression.suite.model.configProfile;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;




    @Data
    @Builder
    public class Location{


            @JsonProperty("facilityCode")
            private String facilityCode;

    }

