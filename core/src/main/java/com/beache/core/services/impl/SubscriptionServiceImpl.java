package com.beache.core.services.impl;

import com.beache.core.services.SubscriptionService;
import com.beache.core.services.UserData;
import org.apache.commons.lang3.StringUtils;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.Designate;

@Component(service = SubscriptionService.class, immediate = true)
@Designate(ocd = SubscriptionServiceConfig.class)
public class SubscriptionServiceImpl implements SubscriptionService {

    private String endpointUrl;

    @Activate
    private void init(SubscriptionServiceConfig cfg) {
        this.endpointUrl = cfg.endpoint_url();
    }

    @Override
    public boolean subscibe(UserData data) {
        //todo: rest client
        return StringUtils.isNotBlank(data.getName()) && StringUtils.isNotBlank(data.getEmail());
    }

}
