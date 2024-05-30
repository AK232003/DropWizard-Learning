package org.example.configuration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;

import javax.validation.constraints.NotNull;


public class BasicConfiguration extends Configuration {
    // this file is used for accessing the values in the config file mentioned in the resources
        @NotNull
        private final int defaultSize;

        @JsonCreator
        public BasicConfiguration(@JsonProperty("defaultSize") int defaultSize) {
            this.defaultSize = defaultSize;
        }

        public int getDefaultSize() {
            return defaultSize;
        }
    }
