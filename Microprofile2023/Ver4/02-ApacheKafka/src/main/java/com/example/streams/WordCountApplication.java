package com.example.streams;

import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Printed;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.state.KeyValueStore;

public class WordCountApplication {
    public static void main(final String[] args) throws Exception {
       Properties props = new Properties();
       props.put(StreamsConfig.APPLICATION_ID_CONFIG, "wordcount-application");
       props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
       props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
       props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

       StreamsBuilder builder = new StreamsBuilder();
       KStream<String, String> textLines = builder.stream("alta_eventos");

       textLines.print(Printed.toSysOut());
       /* 
    
       KTable<String, Long> wordCounts = textLines
           .flatMapValues(textLine -> Arrays.asList(textLine.toLowerCase()))
           .groupBy((key, word) -> word)
           .count(Materialized.<String, Long, KeyValueStore<Bytes, byte[]>>as("counts-store"));
       wordCounts.toStream().to("WordsWithCountsTopic", Produced.with(Serdes.String(), Serdes.Long()));

       KafkaStreams streams = new KafkaStreams(builder.build(), props);
       streams.start();
       */
   }

}
