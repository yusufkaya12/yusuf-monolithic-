package com.threepounds.caseproject.messaging.rabbitMqConfiguration;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class RabbitMqConfiguration {
    @Value("${spring.rabbitmq.template.default-receive-queue}")
    private String queueName;
    @Value("${spring.rabbitmq.template.exchange}")
    private String exchange ;
    @Value("${spring.rabbitmq.template.routing-key}")
    private String routing;



    @Bean
    Queue queue(){
        return new Queue(queueName,false);

    }
    @Bean
    DirectExchange directExchange(){
        return new DirectExchange(exchange);
    }
    @Bean
    Binding binding(final Queue queue,final DirectExchange directExchange){
        return BindingBuilder.bind(queue).to(directExchange).with(routing);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }


}



