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
package net.paoding.rose.web.controllers.roseInfo;

import net.paoding.rose.web.Invocation;

/**
 * 
 * @author 王志亮 [qieqie.wang@gmail.com]
 * 
 */
public class WhichController {

    public String controller(Invocation inv) {
        if (!inv.getRequestPath().isForwardRequest()) {
            return Frame.wrap("不要直接敲地址");
        }
        return Frame.wrap("<div><span style=\"margin-right:5px;\"><strong>"
                + inv.getRequestPath().getUri()
                + "</strong></span>=<span style=\"margin-left:5px;\">"
                + inv.getControllerClass().getName() + "." + inv.getMethod().getName()
                + "</span></div>");
    }
}
