/*
 *  Copyright 2018 Adobe Systems Incorporated
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
import com.beache.core.services.impl.SubscriptionServiceImpl;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(AemContextExtension.class)
class SubscribeServiceTest {

    private SubscriptionService subscriptionService = new SubscriptionServiceImpl();

    @Test
    void testSubscribtionSuccess() {
        assertTrue(subscriptionService.subscibe(
                UserData.builder().name("Some Body").email("user@host.org").build()
        ));
    }

    @Test
    void testSubscriptionError() {
        assertFalse(subscriptionService.subscibe(
                UserData.builder().name(null).build()
        ));
    }

}
