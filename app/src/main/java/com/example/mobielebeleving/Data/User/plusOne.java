package com.example.mobielebeleving.Data.User;

public class plusOne implements Runnable{

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("kanker zooi");
        }
    }
}
