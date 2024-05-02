package kronos.price.exe.regression.suite.model.PriceExceution;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public class SearchContext {
    @JsonProperty("contextName")
    private String contextName;

    @JsonProperty("contextValue")
    private String contextValue;
}
