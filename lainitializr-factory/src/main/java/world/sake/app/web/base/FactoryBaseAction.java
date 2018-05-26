/*
 * Copyright 2015-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package world.sake.app.web.base;

import java.util.stream.Stream;

import javax.annotation.Resource;

import org.dbflute.optional.OptionalThing;
import world.sake.app.web.base.login.FactoryLoginAssist;
import world.sake.mylasta.action.FactoryMessages;
import world.sake.mylasta.action.FactoryUserBean;
import world.sake.mylasta.direction.FactoryConfig;
import org.lastaflute.core.exception.LaApplicationMessage;
import org.lastaflute.core.json.JsonManager;
import org.lastaflute.web.login.LoginManager;
import org.lastaflute.web.ruts.process.ActionRuntime;
import org.lastaflute.web.validation.ActionValidator;
import org.lastaflute.web.validation.LaValidatableApi;

/**
 * @author jflute
 * @author iwamatsu0430
 */
public abstract class FactoryBaseAction extends LainitializrBaseAction implements LaValidatableApi<FactoryMessages> {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    /** The application type for HanGaR, e.g. used by access context. */
    protected static final String APP_TYPE = "HGR"; // #change_it_first

    /** The user type for Member, e.g. used by access context. */
    protected static final String USER_TYPE = "M"; // #change_it_first (can delete if no login)

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Resource
    private JsonManager jsonManager;
    @Resource
    private FactoryConfig config;
    @Resource
    private FactoryLoginAssist loginAssist;

    // ===================================================================================
    //                                                                               Hook
    //                                                                              ======
    // #app_customize you can customize the action hook
    @Override
    protected Object[] filterApplicationExceptionMessageValues(ActionRuntime runtime, LaApplicationMessage msg) {
        return Stream.of(msg.getValues()).map(vl -> jsonManager.toJson(vl)).toArray(); // for client-managed message
    }

    @Override
    public void hookFinally(ActionRuntime runtimeMeta) {
        super.hookFinally(runtimeMeta);
    }

    // ===================================================================================
    //                                                                           User Info
    //                                                                           =========
    // -----------------------------------------------------
    //                                      Application Info
    //                                      ----------------
    @Override
    protected String myAppType() { // for framework
        return APP_TYPE;
    }

    // -----------------------------------------------------
    //                                            Login Info
    //                                            ----------
    // #app_customize return empty if login is unused
    @Override
    protected OptionalThing<FactoryUserBean> getUserBean() { // application may call, overriding for co-variant
        return loginAssist.getSavedUserBean();
    }

    @Override
    protected OptionalThing<String> myUserType() { // for framework
        return OptionalThing.of(USER_TYPE);
    }

    @Override
    protected OptionalThing<LoginManager> myLoginManager() { // for framework
        return OptionalThing.of(loginAssist);
    }

    // ===================================================================================
    //                                                                          Validation
    //                                                                          ==========
    @SuppressWarnings("unchecked")
    @Override
    public ActionValidator<FactoryMessages> createValidator() { // for co-variant
        return super.createValidator();
    }

    @Override
    public FactoryMessages createMessages() { // application may call
        return new FactoryMessages(); // overriding to change return type to concrete-class
    }
}
