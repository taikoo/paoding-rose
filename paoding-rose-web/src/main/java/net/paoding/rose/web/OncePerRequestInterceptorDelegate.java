/*
 * Copyright 2007-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.paoding.rose.web;

import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author 王志亮 [qieqie.wang@gmail.com]
 */
public class OncePerRequestInterceptorDelegate extends InterceptorDelegate {

    private static Log logger = LogFactory.getLog(OncePerRequestInterceptorDelegate.class);

    private final String filteredKey;

    public OncePerRequestInterceptorDelegate(ControllerInterceptor interceptor) {
        super(interceptor);
        this.filteredKey = "$$paoding-rose.interceptor.oncePerRequest."
                + getInterceptor().getClass().getName() + "-r"
                + Integer.toHexString(new Random().nextInt());
    }

    @Override
    public Object roundInvocation(Invocation inv, InvocationChain chain) throws Exception {
        Invocation temp = inv;
        boolean tobeIntercepted = false;
        while (true) {
            tobeIntercepted = (temp.getAttribute(filteredKey) == null);
            if (!tobeIntercepted) {
                break;
            }
            temp = temp.getPreInvocation();
            if (temp == null) {
                break;
            }
        }
        if (tobeIntercepted) {
            inv.setAttribute(filteredKey, true);
            if (logger.isDebugEnabled()) {
                logger.debug("do oncePerRequest interceptor.before: " + getName());
            }
            return super.roundInvocation(inv, chain);
        } else {
            if (logger.isDebugEnabled()) {
                logger.debug("skip oncePerRequest interceptor.before: " + getName());
            }
            return chain.doNext();
        }
    }

    @Override
    public final void afterCompletion(Invocation inv, Throwable ex) throws Exception {
        if (inv.getAttribute(filteredKey) != null) {
            if (logger.isDebugEnabled()) {
                logger.debug("do oncePerRequest interceptor.afterCompletion: " + getName());
            }
            super.afterCompletion(inv, ex);
        } else {
            if (logger.isDebugEnabled()) {
                logger.debug("skip oncePerRequest interceptor.afterCompletion: " + getName());
            }
        }
    }

    @Override
    public String toString() {
        return "oncePerRequest." + this.interceptor.toString();
    }

}