package com.example.mobielebeleving.MQTT;

import info.mqtt.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttException;

public class TopicHandler {

    public static void subscribeToTopic(MqttAndroidClient client, final String topic) {
            // Try to subscribe to the topic
        IMqttToken token = client.subscribe(topic, Settings.qualityOfService);
        System.out.println("Succesvol");
    }

    public static void unsubscribeToTopic(MqttAndroidClient client, final String topic) {

        // Try to unsubscribe to the topic
        IMqttToken token = client.unsubscribe(topic);
    }
}
