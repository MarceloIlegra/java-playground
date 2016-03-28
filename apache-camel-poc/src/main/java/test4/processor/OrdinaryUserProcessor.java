package test4.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class OrdinaryUserProcessor implements Processor{

    @Override
    public void process(Exchange exchange) throws Exception {
        System.out.println("Processing a ORDINARY user...");
        exchange.getIn().setHeader("cpf", "000.000.000-00");
    }

}
