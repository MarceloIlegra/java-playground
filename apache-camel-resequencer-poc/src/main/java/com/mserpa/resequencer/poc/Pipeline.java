package com.mserpa.resequencer.poc;


import org.apache.camel.builder.RouteBuilder;

public class Pipeline extends RouteBuilder{

    @Override
    public void configure() throws Exception {
        from("direct:start")
            .resequence().header("priorityLevel").allowDuplicates()
            .process(exchange -> {
                Event event = exchange.getIn().getBody(Event.class);
                System.out.println(event.getMessage() + " priority: " + event.getPriority());
            })
            .end();

    }
}
