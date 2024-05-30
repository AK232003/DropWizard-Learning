package org.example;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import org.example.Service.EmployeeService;
import org.example.configuration.DropwizardMySQLConfiguration;
import org.example.resource.DropwizardMySQLHealthCheckResource;
import org.example.resource.EmployeeResource;
import org.example.resource.PingResource;
import org.skife.jdbi.v2.DBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;

public class DropWizardMySQLApplication extends Application<DropwizardMySQLConfiguration> {
    private static final Logger logger = LoggerFactory.getLogger(DropWizardMySQLApplication.class);
    private static final String SQL = "sql";
    private static final String DROPWIZARD_MYSQL_SERVICE = "Dropwizard MySQL Service";

    public static void main(String[] args) throws Exception {
        new DropWizardMySQLApplication().run("server", args[0]);
    }

    @Override
    public void run(DropwizardMySQLConfiguration configuration, Environment environment) throws Exception {
        final DataSource dataSource = configuration.getDataSourceFactory().build(environment.metrics(), SQL);
        DBI dbi = new DBI(dataSource);

        DropwizardMySQLHealthCheckResource dropwizardMySQLHealthCheckResource = new DropwizardMySQLHealthCheckResource(dbi.onDemand(EmployeeService.class));
        environment.healthChecks().register(DROPWIZARD_MYSQL_SERVICE, dropwizardMySQLHealthCheckResource);
        logger.info("Registering Restful API Resources");
        environment.jersey().register(new PingResource());
        environment.jersey().register(new EmployeeResource(dbi.onDemand(EmployeeService.class)));
    }
}
