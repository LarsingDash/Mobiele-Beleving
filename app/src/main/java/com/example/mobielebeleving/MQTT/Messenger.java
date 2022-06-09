package com.example.mobielebeleving.MQTT;

import android.util.Log;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.UnsupportedEncodingException;

public class Messenger {

    private void publishMessage(MqttAndroidClient client, String topic, String msg) {
        byte[] encodedPayload = new byte[0];
        try {
            // Convert the message to a UTF-8 encoded byte array
            encodedPayload = msg.getBytes("UTF-8");
            
            // Store it in an MqttMessage
            MqttMessage message = new MqttMessage(encodedPayload);

            // Set parameters for the message
            message.setQos(Settings.qualityOfService);
            message.setRetained(false);

            // Publish the message via the MQTT broker
            client.publish(topic, message);
        } catch (UnsupportedEncodingException | MqttException e) {
            e.printStackTrace();
        }
    }
}
