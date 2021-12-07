package unal.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import unal.api.PriceRequest;
import unal.api.PriceResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Produces("application/json")
@Path("/")
public class PricingResource {
    private static final Double PRICE_PER_M = 0.0017;
    @GET
    @Path("/getPrice")
    public Response getPrice(
            @QueryParam("startLatitude") double startLatitude,
            @QueryParam("startLongitude") double startLongitude,
            @QueryParam("endLatitude") double endLatitude,
            @QueryParam("endLongitude") double endLongitude
    ) throws JsonProcessingException {
        PriceRequest request = PriceRequest.builder().startLatitude(startLatitude).startLongitude(startLongitude).endLatitude(endLatitude).endLongitude(endLongitude).build();

        double dist = org.apache.lucene.util.SloppyMath.haversinMeters(startLatitude, startLongitude, endLatitude, endLongitude);

        BigDecimal price = new BigDecimal(PRICE_PER_M).multiply(new BigDecimal(dist));
        price = price.setScale(3, RoundingMode.CEILING);
        PriceResponse response = PriceResponse.builder().price(price.toString()).build();
        //PriceResponse response = PriceResponse.builder().price(price.toString().substring(0,6)).build();

        ObjectMapper mapper = new ObjectMapper();
        System.out.println(mapper.writeValueAsString(response));

        return Response.ok(response).build();
    }

}
