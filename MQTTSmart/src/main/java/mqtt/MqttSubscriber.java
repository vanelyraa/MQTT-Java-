package mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MqttSubscriber {

    public static void main(String[] args) {

        String broker = "tcp://localhost:1883";  //localhost and port
        String clientId = "Subscriber";
        
        
        MemoryPersistence persistence = new MemoryPersistence(); //where to store data
        
        
        try {
            MqttClient Client = new MqttClient(broker, clientId, persistence); //create client            
            MqttConnectOptions connOpts = new MqttConnectOptions(); //connection option
            
                     
            connOpts.setCleanSession(true); //deliveries for the client removed            
                       
            Client.setCallback(new Subscriber());//create a callback, listener            
            
            System.out.println("Connecting to broker: " + broker);
            Client.connect(connOpts);
           
            System.out.println("Connected subscriber");
            
            //subscribe to topic
            Client.subscribe("/hvac/#");
            Client.subscribe("/utility/#");
            
            
        } catch (MqttException me) {
            System.out.println("reason " + me.getReasonCode());
            System.out.println("msg " + me.getMessage());
            System.out.println("loc " + me.getLocalizedMessage());
            System.out.println("cause " + me.getCause());
            System.out.println("excep " + me);
            me.printStackTrace();
        }
    }
}
