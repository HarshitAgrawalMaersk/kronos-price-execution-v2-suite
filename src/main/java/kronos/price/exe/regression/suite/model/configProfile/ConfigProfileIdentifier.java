package kronos.price.exe.regression.suite.model.configProfile;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Data
@Getter
@Setter
@Builder
public class ConfigProfileIdentifier {
    private String configurationProfileIdentifierName;
    private List<String> configurationProfileIdentifierValues;

}
