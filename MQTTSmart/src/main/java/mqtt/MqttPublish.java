package mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MqttPublish {

	private static MqttClient Client ;


	public static void main(String[] args) {

		//topic and messages to be sent
		String topic = "/hvac/power";
		String content = "Technical problems with HVAC Service";

		int qos = 2; //type of communication: this one send message only once

		String broker = "tcp://localhost:1883"; //address where mosquitto works
		String clientId = "Publisher"; //client name, publisher and subscriber are clients

		MemoryPersistence persistence = new MemoryPersistence(); //somewhere to store data

		try {

			Client = new MqttClient(broker, clientId, persistence);
			MqttConnectOptions connOpts = new MqttConnectOptions();

			connOpts.setCleanSession(true); //if its true, is not durable connection, doesn't last if mqtt is down

			connOpts.setKeepAliveInterval(180); //time in seconds to sent a message sent to server (broker) saying the client is alive
			
			/*
			 * connect
			 */
			System.out.println("Connecting to broker: " + broker);
			Client.connect(connOpts);
			System.out.println("Connected one");


			/*
			 * sending messages
			 */

			publishMessage(topic, content, 2, false); //false does not hold last message

			publishMessage("/hvac/switch", "HVAC system on", 1, false);

			publishMessage("/hvac", "temperature 21 degrees", 1, false);

			publishMessage("/hvac/coLevel", "CO level is 39, extractors are off", 1, false);

			/*
			 * disconnect
			 */        		   
			Client.disconnect();

			System.out.println("Disconnected");
			
			Client.close();
	        
			System.exit(0);

		} catch (MqttException me) {
			System.out.println("reason " + me.getReasonCode());
			System.out.println("msg " + me.getMessage());
			System.out.println("loc-msg " + me.getLocalizedMessage());
			System.out.println("cause " + me.getCause());
			System.out.println("exception " + me);
			me.printStackTrace();
		}

	}

	private static void publishMessage(String topic, String payload, int qos, boolean retained) {

		System.out.println("Publishing message: " + payload + " on topic "+ topic );            

		MqttMessage message = new MqttMessage(payload.getBytes()); //create mqtt message, including payload, an array of bytes
		message.setRetained(retained);
		message.setQos(qos);     

		try {

			Client.publish(topic, message);

		} catch (MqttException me) {
			System.out.println("reason " + me.getReasonCode());
			System.out.println("msg " + me.getMessage());
			System.out.println("loc-msg " + me.getLocalizedMessage());
			System.out.println("cause " + me.getCause());
			System.out.println("exception " + me);
			me.printStackTrace();
		}

		System.out.println("Message published");


	}

}
