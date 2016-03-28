package test4;

import org.apache.camel.*;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.DefaultExchange;
import test2.MessageModel;
import test4.pipeline.SimplePipeline;

public class Application {

    public static void main(String[] args) throws Exception {

        CamelContext camelContext = new DefaultCamelContext();
        camelContext.addRoutes(new SimplePipeline());
        ProducerTemplate template = camelContext.createProducerTemplate();
        camelContext.start();
        template.setDefaultEndpointUri("direct:start");
        template.send(defineHeaders(camelContext));
    }

    private static Exchange defineHeaders(CamelContext camelContext) {
        MessageModel message = new MessageModel("333", "jacob");
        Exchange exchange = new DefaultExchange(camelContext);
        exchange.getIn().setHeader("username", "jacob.crepusculo");
        exchange.getIn().setHeader("admin", "true");
        exchange.getIn().setHeader("type", "god");
        exchange.getIn().setHeader("test", message);
        exchange.getIn().setBody("Oi :D");
        return exchange;
    }

}
