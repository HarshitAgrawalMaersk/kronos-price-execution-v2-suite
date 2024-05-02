package kronos.price.exe.regression.suite.requestMapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import kronos.price.exe.regression.suite.model.PriceExceution.PricingParameters;

import java.util.ArrayList;
import java.util.List;

public class PricingParametersMapper {

    public static  List<PricingParameters>  mapAndFilterParameters(String serviceContract, String routeCode, String finalDestination,
                                               String shipper, String consignee, String IMO, String cargoType,
                                               String haulage, String service, String option1) {


        serviceContract = serviceContract.equals("NA") ? "" : serviceContract;
        routeCode = routeCode.equals("NA") ? "" : routeCode;
        finalDestination = finalDestination.equals("null") ? null : finalDestination;
        service = service.equals("NA") ? "" : service;
        option1 = option1.equals("NA") ? "" : option1;
        shipper = shipper.equals("NA") ? "" : shipper;
        consignee = consignee.equals("NA") ? "" : consignee;
        IMO = IMO.equals("NA") ? "" : IMO;
        haulage = haulage.equals("NA") ? "" : haulage;
        cargoType = cargoType.equals("NA") ? "" : cargoType;
        List<PricingParameters> pricingParametersList = List.of(


                PricingParameters.builder().pricingParameterName("serviceContract").pricingParameterValue(serviceContract).build(),
                PricingParameters.builder().pricingParameterName("routeCode").pricingParameterValue(routeCode).build(),
                PricingParameters.builder().pricingParameterName("finalDestination").pricingParameterValue(finalDestination).build(),
                PricingParameters.builder().pricingParameterName("shipper").pricingParameterValue(shipper).build(),
                PricingParameters.builder().pricingParameterName("consignee").pricingParameterValue(consignee).build(),
                PricingParameters.builder().pricingParameterName("imo").pricingParameterValue(IMO).build(),

                PricingParameters.builder().pricingParameterName("cargoType").pricingParameterValue(cargoType).build(),
                PricingParameters.builder().pricingParameterName("haulage").pricingParameterValue(haulage).build()
        );


        pricingParametersList = PricingParameters.filterEmptyValues(pricingParametersList);
        pricingParametersList.add(PricingParameters.builder().pricingParameterName("service").pricingParameterValue(service).build());

        pricingParametersList.add(PricingParameters.builder().pricingParameterName("option1").pricingParameterValue(option1).build());

        pricingParametersList.add(PricingParameters.builder().pricingParameterName("finalDestination").pricingParameterValue(finalDestination).build());

        return pricingParametersList;
    }
}