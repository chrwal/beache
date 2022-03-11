package com.beache.core.services.impl;

import com.beache.core.services.SubscriptionService;
import com.beache.core.services.UserDate;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@Component(service = SubscriptionService.class, immediate = true)
@Designate(ocd = SubscriptionServiceConfig.class)
public class SubscriptionServiceImpl implements SubscriptionService {

    private String endpointUrl;

    @Activate
    private void init(SubscriptionServiceConfig cfg) {
        this.endpointUrl = cfg.endpoint_url();
    }

    @Override
    public boolean subscibe(UserDate data) {
        return false;
    }

}
