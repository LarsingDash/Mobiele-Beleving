package com.example.mobielebeleving.MQTT;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import info.mqtt.android.service.Ack;
import info.mqtt.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class ConnectionHandler extends AppCompatActivity {

    @Override
    protected void onDestroy() {
        // Disconnect from the MQTT broker when the activity is closed
        BrokerConnection.disconnectFromBroker(Settings.mqttAndroidClient);
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create the MQTT client, using the URL of the MQTT broker and the client ID
        Settings.mqttAndroidClient = new MqttAndroidClient(getApplicationContext(), Settings.brokerHostUrl, Settings.clientID, Ack.AUTO_ACK);
        // Set up callbacks to handle events from the MQTT broker
        Settings.mqttAndroidClient.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                // Check what topic the message is for and handle accordingly
                if (topic.equals(Settings.topicFabelwoudPoints)) {
                    System.out.println(message.toString());
                } else if (topic.equals("larstest/lars")) {
                    System.out.println(message.toString());
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
            }
        });

        // Connect the MQTT client to the MQTT broker
        BrokerConnection.connectToBroker(Settings.mqttAndroidClient, Settings.clientID);
    }
}
