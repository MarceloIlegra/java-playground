package test4.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class AdminUserProcessor implements Processor{

    @Override
    public void process(Exchange exchange) throws Exception {
        System.out.println("Processing a ADMIN user...");
        exchange.getIn().setHeader("cpf", "111.111.111-11");
    }

}