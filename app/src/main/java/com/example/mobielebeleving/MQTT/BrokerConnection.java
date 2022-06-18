package com.example.mobielebeleving.MQTT;

import info.mqtt.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

public class BrokerConnection {

    public static void connectToBroker(MqttAndroidClient client, String clientId) {
        // Set up connection options for the connection to the MQTT broker
        MqttConnectOptions options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        options.setCleanSession(false);
        options.setUserName(Settings.username);
        options.setPassword(Settings.password.toCharArray());

        // Try to connect to the MQTT broker
        IMqttToken token = client.connect(options);
    }

    public static void disconnectFromBroker(MqttAndroidClient client) {
        // Try to disconnect from the MQTT broker
        IMqttToken token = client.disconnect();
    }
}
