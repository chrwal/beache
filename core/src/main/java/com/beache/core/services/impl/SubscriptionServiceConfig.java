package com.beache.core.services.impl;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition()
public @interface SubscriptionServiceConfig {

    @AttributeDefinition(name = "Service URL")
    String endpoint_url() default "http://beache.com/subscribe";

}
