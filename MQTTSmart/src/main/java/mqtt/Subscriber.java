package mqtt;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;


class Subscriber implements MqttCallback {

    public Subscriber() {
    }

    @Override
    public void connectionLost(Throwable thrwbl) {
    	System.out.println("connection is lost " + thrwbl.getStackTrace());
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        System.out.println(new String(message.getPayload()) + " arrived from topic " + topic + " with qos " + message.getQos() );
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
    	 System.out.println("delivery is complete " +  token.isComplete());
    }
    
}
