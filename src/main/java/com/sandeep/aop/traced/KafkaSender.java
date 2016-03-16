package com.sandeep.aop.traced;

import java.util.Properties;
import java.util.concurrent.Future;

import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.sandeep.aop.annotations.Trace;

@SuppressWarnings("unused")
/**
 * <p>
 * The class is a prototype for Kafka Sender to demonstrate that annotated sends of messages can be
 * intercepted and the message can be updated prior to send
 * </p>
 * 
 * @author Sandeep Nayak
 *
 */
public class KafkaSender {
  private final Properties kafkaProps = new Properties();
  private final String kafkaTopic;
  private final String kafkaPartition;
  private static final Logger logger = LoggerFactory.getLogger(KafkaSender.class);
  @Inject
  public KafkaSender(@Named("kafka_topic") String topic, @Named("kafka_partition") String partition,
      @Named("kafka_broker_list") String brokerList) {
    this.kafkaTopic = topic;
    this.kafkaPartition = partition;
    kafkaProps.put("bootstrap.servers", brokerList);
    kafkaProps.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    kafkaProps.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    kafkaProps.put("acks", 2);
    kafkaProps.put("retries", 2);
    kafkaProps.put("send.buffer.bytes", 1024);
  }

  @Trace
  public Future<RecordMetadata> sendMessage(String message) {
    logger.debug("Message to send is = {} ", message);
    return null;
  }

}
