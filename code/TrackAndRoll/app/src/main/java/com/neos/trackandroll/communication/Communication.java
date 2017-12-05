/**
 * @file Communication.java
 * @brief Class of communication (create the postman and has all the proxys)
 *
 * @version 1.0
 * @date 14/04/2016
 * @author Guillaume Muret
 * @copyright
 *	Copyright (c) 2016, PRØVE
 * 	All rights reserved.
 * 	Redistribution and use in source and binary forms, with or without
 * 	modification, are permitted provided that the following conditions are met:
 *
 * 	* Redistributions of source code must retain the above copyright
 * 	  notice, this list of conditions and the following disclaimer.
 * 	* Redistributions in binary form must reproduce the above copyright
 * 	  notice, this list of conditions and the following disclaimer in the
 * 	  documentation and/or other materials provided with the distribution.
 * 	* Neither the name of PRØVE, Angers nor the
 * 	  names of its contributors may be used to endorse or promote products
 *   	derived from this software without specific prior written permission.
 *
 * 	THIS SOFTWARE IS PROVIDED BY PRØVE AND CONTRIBUTORS ``AS IS'' AND ANY
 * 	EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * 	WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * 	DISCLAIMED. IN NO EVENT SHALL PRØVE AND CONTRIBUTORS BE LIABLE FOR ANY
 * 	DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * 	(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * 	LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * 	ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * 	(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * 	SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 */

package com.neos.trackandroll.communication;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.neos.trackandroll.communication.protocole.ProtocoleVocabulary;
import com.neos.trackandroll.communication.proxy.ProxyClient;
import com.neos.trackandroll.utils.JsonUtils;
import com.neos.trackandroll.utils.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class Communication extends Thread {

	/**
	 * The client proxy
	 */
	private ProxyClient proxyClient;

	/**
	 * The postman who send the messages for the UI
	 */
	public Postman postman;

	/**
	 * The thread which ask to the communication to ask to the postman to read a message on the socket
	 */
	private ReadThread readThread;

	/**
	 * The Distributor of the different method call after a reception
	 */
	public Distributor distributor;

	/**
	 * letterbox of the binder man
	 */
	private Handler handlerService = new Handler() {
		@Override
		synchronized public void handleMessage(Message msg) {
			try {
				JSONObject mainObj = JsonUtils.strToJson(msg.getData().getString(ProtocoleVocabulary.COMMUNICATION_LETTER_BOX_KEY));
				JSONObject params = mainObj.getJSONObject(ProtocoleVocabulary.JSON_PARAMS);
				distributor.dispatch(mainObj.getString(ProtocoleVocabulary.JSON_PROCESS), params);
			} catch (JSONException e) {
				LogUtils.e(LogUtils.DEBUG_TAG, "Error handlerService => ", e);
			}
		}
	};

	/**
	 * Builder of the communication
	 */
	public Communication() {
		//The postman
		this.postman = new Postman();

		//All the proxy
		this.proxyClient = new ProxyClient(this.postman);

		// Create the thread of the reading
		readThread = new ReadThread();

		//The distributor
		this.distributor = new Distributor();
	}

	/**
	 * Process which ask to the postman to read a message
	 *
	 * @return the received message
	 */
	public String readComMessage() throws IOException {
		if (this.getPostman().getStateSocket() == Postman.CONNECTED) {
			return postman.readMessage();
		}
		return null;
	}

	/**
	 * Function called when the UI want to call a process on the raspberry
	 *
	 * @param processOut : the process call on the raspberry
	 * @param data         : the data to send
	 */
	public void processManager(String processOut, Bundle data) {
		this.proxyClient.encode(processOut, data);
	}

	/**
	 * Process called to close the socket
	 */
	public void closeSocket() {
		if (this.postman != null) {
			this.postman.closeSocket();
		}
	}

	/**
	 * Getter of the postman
	 *
	 * @return the postman
	 */
	public Postman getPostman() {
		return this.postman;
	}

	/**
	 * Reading thread class called to read a message
	 */
	private class ReadThread extends Thread {

		/**
		 * The received message from the socket
		 */
		String receivedMessage;

		/**
		 * Called to read a message and send this message to the binder man's letter box
		 *
		 * @throws IOException
		 * @throws NullPointerException
		 */
		private void manageReading() throws IOException, NullPointerException {

			// loop which read the buffer and block the task when nothing is received
			while ((receivedMessage = readComMessage()) == null) ;

			LogUtils.d(LogUtils.DEBUG_TAG,"MESSAGE RECU >" + receivedMessage);
			// Reset the timer -> we have a response from the raspberry ! All clear !
			// TODO resetTimer();
			// Send message to the BinderMan letter box
			Message m = new Message();
			Bundle b = new Bundle();
			b.putString(ProtocoleVocabulary.COMMUNICATION_LETTER_BOX_KEY, receivedMessage);
			m.setData(b);
			handlerService.sendMessage(m);
		}

		/**
		 * Process called when a "start" of the thread occur
		 */
		@Override
		public void run() {

			try {
				// infinite loop. If a network problem occur : the infinite loop die
				while (true) {
					// manage the message read
					manageReading();
				}
			} catch (NullPointerException npe) {
				LogUtils.e(LogUtils.DEBUG_TAG, "NO SOCKET ==> ", npe);
			} catch (IOException ioe) {
				LogUtils.e(LogUtils.DEBUG_TAG, "SOCKET FERMEE ==> ", ioe);
			}
		}
	}
}