/*
 *  Copyright 2015 Adobe Systems Incorporated
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.beache.core.servlets;

import com.beache.core.services.SubscriptionService;
import com.beache.core.services.UserData;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ResourceUtil;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.propertytypes.ServiceDescription;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Map;

/**
 * Send the userdata to the external rest service to subscribe the user for the event.
 */
@Component(service = Servlet.class)
@SlingServletResourceTypes(
        resourceTypes="beache/components/page",
        methods=HttpConstants.METHOD_POST,
        selectors = "subscribe",
        extensions = "json")
@ServiceDescription("Subscribtion Servlet")
public class SubscribeServlet extends SlingAllMethodsServlet {

    private static final long serialVersionUID = 1L;

    @Reference
    private SubscriptionService subscriptionService;

    @Reference
    private ResourceResolverFactory resourceResolverFactory;

    @Override
    protected void doPost(final SlingHttpServletRequest req,
            final SlingHttpServletResponse resp) throws ServletException, IOException {
        String name = req.getRequestParameter("name").getString(); //todo: check null
        String email = req.getRequestParameter("email").getString();

        UserData userData = UserData.builder().name(name).email(email).build();

        boolean success = subscriptionService.subscibe(userData);
        if (success) {
            try {
                persistData(req, userData);
            } catch (LoginException e) {
                e.printStackTrace(); //todo: logging
            }
        }
        resp.setStatus(success ? 200 : 500);
    }

    private void persistData(SlingHttpServletRequest req, UserData userData) throws LoginException, PersistenceException {
        Map<String, Object> auth = ImmutableMap.of(ResourceResolverFactory.SUBSERVICE, "subscriptionService"); //todo: create user mapping

        byte[] md5 = DigestUtils.md5(userData.getName() + userData.getEmail());

        try (ResourceResolver resolver = resourceResolverFactory.getServiceResourceResolver(auth)) {
            Resource base = resolver.getResource("/var/subscription/base");
            String newResourceName = ResourceUtil.createUniqueChildName(base, "subscription");
            ResourceUtil.getOrCreateResource(resolver, newResourceName, ImmutableMap.of("md5", md5), null, true);
        }

    }

}