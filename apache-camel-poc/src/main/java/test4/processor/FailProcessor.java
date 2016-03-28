package test4.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class FailProcessor implements Processor{

    @Override
    public void process(Exchange exchange) throws Exception {
        System.out.println("FAIL FINISH. The owner of CPF is not a GOD!!!");
        System.out.println("Received CPF: " + exchange.getIn().getHeader("cpf"));
    }

}
