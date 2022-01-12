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
    private static Double PRICE_PER_M;

    private static BigDecimal price;

    private static BigDecimal calculatePrice(double PRICE_PER_M, double dist, BigDecimal minPrice) {
        price = new BigDecimal(PRICE_PER_M).multiply(new BigDecimal(dist));
        price = price.setScale(3, RoundingMode.CEILING);
        if (price.compareTo(minPrice) < 0)
            price = minPrice.setScale(3, RoundingMode.CEILING);
        return price;
    }

    @GET
    @Path("/getPrice")
    public Response getPrice(
            @QueryParam("startLatitude") double startLatitude,
            @QueryParam("startLongitude") double startLongitude,
            @QueryParam("endLatitude") double endLatitude,
            @QueryParam("endLongitude") double endLongitude,
            @QueryParam("uberType") Integer uberType
    ) throws JsonProcessingException {
        PriceRequest request = PriceRequest.builder().startLatitude(startLatitude).startLongitude(startLongitude).endLatitude(endLatitude).endLongitude(endLongitude).uberType(uberType).build();

        double dist = org.apache.lucene.util.SloppyMath.haversinMeters(startLatitude, startLongitude, endLatitude, endLongitude);

        switch (uberType) {
            case 1:
                PRICE_PER_M = 0.0018;
                price = calculatePrice(PRICE_PER_M, dist, (new BigDecimal(5.200)));
                break;
            case 2:
                PRICE_PER_M = 0.0017;
                price = calculatePrice(PRICE_PER_M, dist, (new BigDecimal(5.000)));
                break;
            case 3:
                PRICE_PER_M = 0.0025;
                price = calculatePrice(PRICE_PER_M, dist, (new BigDecimal(8.200)));
                break;
            case 4:
                PRICE_PER_M = 0.0032;
                price = calculatePrice(PRICE_PER_M, dist, (new BigDecimal(10.100)));
                break;
        }

        PriceResponse response = PriceResponse.builder().price(price.toString()).uberType(Integer.toString(uberType)).build();

        ObjectMapper mapper = new ObjectMapper();
        System.out.println(mapper.writeValueAsString(response));

        return Response.ok(response).build();
    }
}
