package com.example.mobielebeleving.MQTT;

import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.UnsupportedEncodingException;

import info.mqtt.android.service.MqttAndroidClient;

public class Messenger {

    public static void publishMessage(MqttAndroidClient client, String topic, String msg) {
        byte[] encodedPayload = new byte[0];
        try {
            // Convert the message to a UTF-8 encoded byte array
            encodedPayload = msg.getBytes("UTF-8");

            // Store it in an MqttMessage
            MqttMessage message = new MqttMessage(encodedPayload);

            // Set parameters for the message
            message.setQos(Settings.qualityOfService);
            message.setRetained(true);

            // Publish the message via the MQTT broker
            client.publish(topic, message);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static void publishRetainingMessage(MqttAndroidClient client, String topic, String msg) {
        byte[] encodedPayload = new byte[0];
        try {
            // Convert the message to a UTF-8 encoded byte array
            encodedPayload = msg.getBytes("UTF-8");

            // Store it in an MqttMessage
            MqttMessage message = new MqttMessage(encodedPayload);

            // Set parameters for the message
            message.setQos(2);
            message.setRetained(true);

            // Publish the message via the MQTT broker
            client.publish(topic, message);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
