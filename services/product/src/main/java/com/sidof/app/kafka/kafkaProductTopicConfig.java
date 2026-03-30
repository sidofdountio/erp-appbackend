package com.sidof.app.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

/**
 * <blockquote><pre>
 * Author   : @Dountio
 * LinkedIn : @SidofDountio
 * GitHub   : @SidofDountio
 * Version  : V1.0
 * Email    : sidofdountio406@gmail.com
 * Since    : 2/19/26
 * </blockquote></pre>
 */

@Configuration
public class kafkaProductTopicConfig {

    @Bean
    public NewTopic productCreatedTopic() {
        return TopicBuilder
                .name("product.created.v1")
                .partitions(3)
                .replicas(1)
                .build();
    }


//    @Bean
//    public NewTopic productTopic(){
//        return TopicBuilder
//                .name("product.created.v1")
//                .build();
//    }
}
