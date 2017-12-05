/**
 * @file ProxyClient.java
 * @brief proxy of the ProxyClient.
 * @version 1.0
 * @date 14/04/2016
 * @author Guillaume Muret
 * @copyright Copyright (c) 2016, PRØVE
 * All rights reserved.
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * * Neither the name of PRØVE, Angers nor the
 * names of its contributors may be used to endorse or promote products
 * derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY PRØVE AND CONTRIBUTORS ``AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL PRØVE AND CONTRIBUTORS BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.neos.trackandroll.communication.proxy;

import android.os.Bundle;

import com.neos.trackandroll.communication.Postman;
import com.neos.trackandroll.communication.protocole.ProtocoleVocabulary;
import com.neos.trackandroll.utils.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class ProxyClient {

    /**
     * postman use to send and receive the message
     */
    public Postman postman;

    /**
     * Constructor of all the proxys
     * @param postman : the postman
     */
    public ProxyClient(Postman postman) {
        this.postman = postman;
    }

    /**
     * transform the params and the process into JSONObject and call the postman to send the message
     * @param processOut : the process to call
     */
    public void encode(String processOut, Bundle data) {
        LogUtils.d(LogUtils.DEBUG_TAG,"Encodage");

        // Encyption
        String jsonString=this.toJSONString(processOut,data);

        // Tell handler to Send the Message
        this.postman.writeMessage(jsonString);
    }

    /**
     * encryption of the owner, function, params to create a Json String.
     * @param processOut : the process to call
     * @param data : the data
     * @return the json object convert into string
     */
    protected String toJSONString(String processOut, Bundle data) {
        JSONObject mainObj = new JSONObject();
        JSONObject params = new JSONObject();

        try {
            mainObj.put(ProtocoleVocabulary.JSON_PROCESS, processOut);
            mainObj.put(ProtocoleVocabulary.JSON_PARAMS, params);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mainObj.toString();
    }

    /**
     * Getter of the postman
     * @return the postman
     */
    public Postman getPostman() {
        return postman;
    }
}