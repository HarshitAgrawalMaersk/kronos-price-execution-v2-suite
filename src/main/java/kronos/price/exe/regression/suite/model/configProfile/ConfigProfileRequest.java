package kronos.price.exe.regression.suite.model.configProfile;


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
public class ConfigProfileRequest {

    @JsonProperty("configurationProfileType")
    private String configurationProfileType;

     @JsonProperty("location")
     private Location location;

     @JsonProperty("transportActivity")
     private String transportActivity;


     @JsonProperty("configurationProfileIdentifiers")
    private List<ConfigProfileIdentifier> configurationProfileIdentifiers;

}
