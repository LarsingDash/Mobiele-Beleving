package com.example.mobielebeleving.MQTT;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

public class BrokerConnection {

    public static void connectToBroker(MqttAndroidClient client, String clientId) {
        // Set up connection options for the connection to the MQTT broker
        MqttConnectOptions options = new MqttConnectOptions();
//        options.setAutomaticReconnect(true);
        options.setCleanSession(false);
        options.setUserName(Settings.username);
        options.setPassword(Settings.password.toCharArray());

        try {
            // Try to connect to the MQTT broker
            IMqttToken token = client.connect(options);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public static void disconnectFromBroker(MqttAndroidClient client) {
        try {
            // Try to disconnect from the MQTT broker
            IMqttToken token = client.disconnect();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
