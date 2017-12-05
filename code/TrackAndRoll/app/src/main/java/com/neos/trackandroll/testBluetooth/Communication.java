package com.neos.trackandroll.testBluetooth;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.neos.trackandroll.communication.protocole.ProtocoleVocabulary;
import com.neos.trackandroll.utils.LogUtils;

import java.io.IOException;

public class Communication {

    private TestBluetoothActivity testBluetoothActivity;

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
            // JSONObject mainObj = JsonUtils.strToJson(msg.getData().getString(ProtocoleVocabulary.COMMUNICATION_LETTER_BOX_KEY));
            // JSONObject params = mainObj.getJSONObject(ProtocoleVocabulary.JSON_PARAMS);
            // distributor.dispatch(mainObj.getString(ProtocoleVocabulary.JSON_PROCESS), params);
            testBluetoothActivity.appendTextReceived(msg.getData().getString(ProtocoleVocabulary.COMMUNICATION_LETTER_BOX_KEY));
		}
	};

	/**
	 * Builder of the communication
	 */
	private Communication(TestBluetoothActivity activity, String address) {
        this.testBluetoothActivity = activity;

		//The postman
		this.postman = new Postman(this, address);

		//All the proxy
		//this.proxyClient = new ProxyClient(this.postman);

		// Create the thread of the reading
		readThread = new ReadThread();

		//The distributor
		this.distributor = new Distributor();
	}

	public static Communication getNewCommunication(TestBluetoothActivity activity, String address){
		return new Communication(activity, address);
	}

	public void launchReadThread(){
		this.readThread.start();
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
	 */
	public void sendMessage(String message) {
		this.postman.writeMessage(message);
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