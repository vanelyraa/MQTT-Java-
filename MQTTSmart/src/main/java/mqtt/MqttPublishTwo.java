package mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MqttPublishTwo {

	private static MqttClient Client ;


	public static void main(String[] args) {

		String topic = "/utility/power";
		String content = "Utilities off, waiting for requests";

		int qos = 2;

		String broker = "tcp://localhost:1883";
		String clientId = "PublisherTwo";

		MemoryPersistence persistence = new MemoryPersistence();

		try {

			Client = new MqttClient(broker, clientId, persistence);
			MqttConnectOptions connOpts = new MqttConnectOptions();

			connOpts.setCleanSession(true);

			connOpts.setKeepAliveInterval(180);
			
			
			System.out.println("Connecting to broker: " + broker);
			Client.connect(connOpts);
			System.out.println("Connected Two");


			publishMessage(topic, content, 2, false);

			publishMessage("/utility/devices/switch", "Devices are now on", 1, false);

			publishMessage("/utility/printer", "No visitor at the moment", 1, false);

			publishMessage("/utility/camera", "Cameras onn", 1, false);

		       		   
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

		MqttMessage message = new MqttMessage(payload.getBytes());
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
