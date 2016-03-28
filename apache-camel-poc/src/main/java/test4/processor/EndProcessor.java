package test4.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 * Created by ilegra000034 on 28/03/2016.
 */
public class EndProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        System.out.println("Everything is finished. Received data:");
        System.out.println(exchange.getIn().getHeaders().toString());
        System.out.println("Received Body:");
        System.out.println(exchange.getIn().getBody());
    }

}
