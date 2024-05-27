package com.threepounds.caseproject.messaging.producer;
import com.threepounds.caseproject.messaging.model.Messages;
import jakarta.annotation.PostConstruct;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;


import org.springframework.stereotype.Service;


@Service
public class RegistrationMessageProducer {

    @Value("${spring.rabbitmq.template.routing-key}")
    private String routingName;
    @Value("${spring.rabbitmq.template.default-receive-queue}")
    private  String queueName;
    @Value("${spring.rabbitmq.template.exchange}")
    private  String exchange;
    private final RabbitTemplate rabbitTemplate;

    public RegistrationMessageProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendQueue(Messages messages) {
        System.out.println(messages.getName());
        rabbitTemplate.convertAndSend(exchange, routingName, messages);
    }


}
