package com.threepounds.caseproject.messaging.listener;
import com.threepounds.caseproject.messaging.model.Messages;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;


@Service
public class RegisterListener {
    @RabbitListener(queues = "spring-boot-queue")
    public void handleMessage(Messages messages){
        System.out.println(messages.toString());
        System.out.println("Register message received");

    }
 }




