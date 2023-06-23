package com.example.demo;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.security.auth.callback.Callback;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Printed;

import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/api")
@RequestScoped
public class KafkaStreamsController {
  private static final Logger LOGGER = Logger.getLogger(KafkaStreamsController.class.getName());

  @GET
  @Path("/send")

  public Response sayHello() {

    LOGGER.log(Level.WARNING, "Hello World...");

    escribeMensaje();

    return Response.status(Status.OK).entity(new String("Hello World")).build();
  }

  @GET
  @Path("/read")

  public Response readMessage() {

    LOGGER.log(Level.WARNING, "Hello World...");

    leeMensaje();

    return Response.status(Status.OK).entity(new String("Hello World")).build();
  }

  @GET
  @Path("/stream")

  public Response streamMessage() {

    LOGGER.log(Level.WARNING, "Hello World...");

    Properties props = new Properties();
    props.put(StreamsConfig.APPLICATION_ID_CONFIG, "wordcount-application");
    props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
    props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
    props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
    props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

    StreamsBuilder builder = new StreamsBuilder();

    KStream<String, String> textLines = builder.stream("alta_eventos");

    textLines.foreach((k, v) -> System.out.println("Key = " + k + " Value = " + v));

    Topology topology = builder.build();

    System.out.println(topology.describe());

    KafkaStreams streams = new KafkaStreams(topology, props);

    streams.start();

    return Response.status(Status.OK).entity(new String("Stream Hello World")).build();
  }

  private void leeMensaje() {
    String bootstrapServers = "127.0.0.1:9092";
    String groupId = "console-consumer-51678";
    String topic = "alta_eventos";

    // create consumer configs
    Properties properties = new Properties();
    properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
    properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
    properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
    properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

    // create consumer
    KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);

    // subscribe consumer to our topic(s)
    consumer.subscribe(Arrays.asList(topic));

    // while (true) {
    ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(10000));
    System.out.println("Total de mensajes: " + records.count());
    for (ConsumerRecord<String, String> record : records) {
      LOGGER.log(Level.WARNING, "Key: " + record.key() + ", Value: " + record.value());
      LOGGER.log(Level.WARNING, "Partition: " + record.partition() + ", Offset:" + record.offset());
    }
    // }

  }

  private void escribeMensaje() {
    LOGGER.log(Level.WARNING, "I am a Kafka Producer");

    String bootstrapServers = "127.0.0.1:9092";

    // create Producer properties
    Properties properties = new Properties();
    properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
    properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

    KafkaProducer<String, String> producer = new KafkaProducer<>(properties);
    ProducerRecord<String, String> producerRecord = new ProducerRecord<>("alta_eventos",
        "hello world desde el microprofile... desde el microservicio... no mames...");
    // send data - asynchronous

    producer.send(producerRecord, new org.apache.kafka.clients.producer.Callback() {

      @Override
      public void onCompletion(RecordMetadata recordMetadata, Exception e) {
        // TODO Auto-generated method stub

        // throw new UnsupportedOperationException("Unimplemented method
        // 'onCompletion'");
        if (e == null) {
          // the record was successfully sent
          LOGGER.log(Level.INFO, "Received new metadata. \n" +
              "Topic:" + recordMetadata.topic() + "\n" +
              "Partition: " + recordMetadata.partition() + "\n" +
              "Offset: " + recordMetadata.offset() + "\n" +
              "Timestamp: " + recordMetadata.timestamp());
        } else {
          LOGGER.log(Level.WARNING, "Error while producing", e);
        }

      }

    });

    // flush data - synchronous
    producer.flush();

    // flush and close producer
    producer.close();
  }

}
