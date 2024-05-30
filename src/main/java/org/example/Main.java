package org.example;

import com.codahale.metrics.health.HealthCheck;
import io.dropwizard.Application;
import io.dropwizard.configuration.ResourceConfigurationSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.example.configuration.ApplicationHealthCheck;
import org.example.configuration.BasicConfiguration;
import org.example.domain.Brand;
import org.example.repository.BrandRepository;
import org.example.resource.BrandResource;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application<BasicConfiguration> {

    public static void main(String[] args) throws Exception {
        new Main().run("server", "config.yml");
    }

    @Override
    public void run(BasicConfiguration basicConfiguration, Environment environment) {

        int defaultSize = basicConfiguration.getDefaultSize();
        BrandRepository brandRepository = new BrandRepository(initBrands());
        BrandResource brandResource = new BrandResource(defaultSize, brandRepository);
        ApplicationHealthCheck applicationHealthCheck = new ApplicationHealthCheck();

        environment.jersey().register(brandResource);
        environment.healthChecks().register("application", applicationHealthCheck);
    }

    @Override
    public void initialize(Bootstrap<BasicConfiguration> bootstrap) {
        bootstrap.setConfigurationSourceProvider(new ResourceConfigurationSourceProvider());
        super.initialize(bootstrap);
    }

    private List<Brand> initBrands() {
        final List<Brand> brands = new ArrayList<>();
        brands.add(new Brand(1L, "Brand1"));
        brands.add(new Brand(2L, "Brand2"));
        brands.add(new Brand(3L, "Brand3"));

        return brands;
    }
}