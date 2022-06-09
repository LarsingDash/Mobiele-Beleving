package com.example.mobielebeleving.MQTT;

import android.util.Log;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttException;

public class TopicHandler {

    public static void subscribeToTopic(MqttAndroidClient client, final String topic) {
            // Try to subscribe to the topic
        try {
            IMqttToken token = client.subscribe(topic, Settings.qualityOfService);
            System.out.println("Succesfully Subscribed");
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public static void unsubscribeToTopic(MqttAndroidClient client, final String topic) {

        // Try to unsubscribe to the topic
        try {
            IMqttToken token = client.unsubscribe(topic);
            System.out.println("Succesfully Unsubscribed");
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
