package com.mserpa.resequencer.poc;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.DefaultExchange;

import java.util.Arrays;
import java.util.List;


public class Application {

    public static void main(String[] args) throws Exception {
        CamelContext camelContext = new DefaultCamelContext();
        camelContext.addRoutes(new Pipeline());
        ProducerTemplate template = camelContext.createProducerTemplate();
        camelContext.start();
        template.setDefaultEndpointUri("direct:start");

        List<Event> events = Arrays.asList(new Event(2, "A"), new Event(1, "B"), new Event(2, "C"), new Event(0, "D"), new Event(1, "E"));

        long start1 = System.nanoTime();
        events.forEach(event -> {
            long start = System.nanoTime();
            Exchange exchange = new DefaultExchange(camelContext);
            exchange.getIn().setBody(event);
            exchange.getIn().setHeader("priorityLevel", event.getPriority());
            template.send(exchange);


            try {
                Thread.sleep(300L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            long end = System.nanoTime();
            long elapsedTime = (end - start) / 1000000L;
            System.out.println(elapsedTime);

        });
        long end1 = System.nanoTime();
        long elapsedTime1 = (end1 - start1) / 1000000L;
        System.out.println("total time to process all messages: " + elapsedTime1);

    }

}
