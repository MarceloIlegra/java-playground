package test4.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class GodUserProcessor implements Processor{

    @Override
    public void process(Exchange exchange) throws Exception {
        System.out.println("Processing a GOD user...");
        exchange.getIn().setHeader("cpf", "333.333.333-33");
    }

}
