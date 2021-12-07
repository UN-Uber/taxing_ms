package unal;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import unal.resources.PricingResource;

public class taxingApplication extends Application<taxingConfiguration> {

    public static void main(final String[] args) throws Exception {
        new taxingApplication().run(args);
    }

    @Override
    public String getName() {
        return "taxing";
    }

    @Override
    public void initialize(final Bootstrap<taxingConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final taxingConfiguration configuration,
                    final Environment environment) {
        environment.jersey().register(new PricingResource());
    }

}
