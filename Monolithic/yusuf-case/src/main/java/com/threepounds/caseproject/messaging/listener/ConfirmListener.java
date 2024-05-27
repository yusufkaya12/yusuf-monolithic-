package com.threepounds.caseproject.messaging.listener;

import com.threepounds.caseproject.messaging.model.Messages;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

public class ConfirmListener {
    @RabbitListener(queues = "spring-boot-queue")
    public void handleMessage(Messages messages){
        System.out.println(messages.toString());
        System.out.println("Confirm message received");

    }
}
