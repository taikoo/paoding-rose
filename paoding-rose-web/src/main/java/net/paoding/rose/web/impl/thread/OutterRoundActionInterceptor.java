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
package net.paoding.rose.web.impl.thread;

import net.paoding.rose.web.AfterInterceptors;
import net.paoding.rose.web.BeforeInterceptors;
import net.paoding.rose.web.ControllerInterceptorAdapter;
import net.paoding.rose.web.Dispatcher;
import net.paoding.rose.web.Invocation;

/**
 * @author 王志亮 [qieqie.wang@gmail.com]
 */
public class OutterRoundActionInterceptor extends ControllerInterceptorAdapter {

    public OutterRoundActionInterceptor() {
        setPriority(Integer.MAX_VALUE);
    }

    @Override
    public boolean isSupportDispatcher(Dispatcher dispatcher) {
        return true;
    }

    @Override
    public Object before(Invocation inv) throws Exception {
        for (Object object : inv.getMethodParameters()) {
            if (object instanceof BeforeInterceptors) {
                ((BeforeInterceptors) object).doBeforeInterceptors(inv);
            }
        }
        return true;
    }

    @Override
    public Object after(Invocation inv, Object instruction) throws Exception {
        for (Object object : inv.getMethodParameters()) {
            if (object instanceof AfterInterceptors) {
                instruction = ((AfterInterceptors) object).doAfterInterceptors(inv, instruction);
            }
        }
        return instruction;
    }
}
