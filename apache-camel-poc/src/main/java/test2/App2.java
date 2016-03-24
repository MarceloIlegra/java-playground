package test2;

import org.apache.camel.CamelContext;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class App2 {

    public static void main(String[] args) throws Exception {

        CamelContext camelContext = new DefaultCamelContext();
        camelContext.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("direct:start").process(startProcess()).to("direct:process");
                from("direct:process").process(executeProcess()).to("log:input");
            }

            private Processor startProcess() {
                return exchange -> {
                    System.out.println("START");
                    MessageModel messageModel = new MessageModel("020.115.888-99", "Message my name");
                    exchange.getIn().setBody(messageModel, MessageModel.class);
                };
            }

            private Processor executeProcess() {
                return exchange -> {
                    System.out.println("PROCESSS");
                    System.out.println(exchange.getIn().getBody(MessageModel.class).getName());
                };
            }
        });

        ProducerTemplate template = camelContext.createProducerTemplate();
        camelContext.start();
        template.sendBody("direct:start", "TESTE");
    }

}


