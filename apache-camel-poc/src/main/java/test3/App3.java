package test3;

import static org.apache.camel.builder.Builder.*;
import static org.apache.camel.builder.PredicateBuilder.*;

import org.apache.camel.*;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.DefaultExchange;
import test2.MessageModel;

public class App3 {

    public static void main(String[] args) throws Exception {

        Predicate isUser = header("username").isNotNull();
        Predicate isAdmin = and(isUser, header("admin").isEqualTo("true"));
        Predicate isGod = and(isAdmin, header("type").isEqualTo("god"), header("test.cpf()").isEqualTo("333"));

        Predicate hasCpfOfGodUser = header("cpf").isEqualTo("333.333.333-33");

        CamelContext camelContext = new DefaultCamelContext();
        camelContext.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("direct:start")
                        .choice()
                        .when(isGod).process(processGodUser()).to("direct:end")
                        .when(isAdmin).process(processAdminUser()).to("direct:end")
                        .when(isUser).process(processOrdinaryUser()).to("direct:end")
                        .otherwise().to("direct:end")
                        .end();

                from("direct:end")
                        .choice()
                        .when(hasCpfOfGodUser).process(endProcess())
                        .otherwise().process(printFail())
                        .to("log:input");
            }

            private Processor processGodUser() {
                return exchange -> {
                    System.out.println("Processing a GOD user...");
                    exchange.getIn().setHeader("cpf", "333.333.333-33");
                };
            }

            private Processor processAdminUser() {
                return exchange -> {
                    System.out.println("Processing a ADMIN user...");
                    exchange.getIn().setHeader("cpf", "111.111.111-11");
                };
            }

            private Processor processOrdinaryUser() {
                return exchange -> {
                    System.out.println("Processing a ORDINARY user...");
                    exchange.getIn().setHeader("cpf", "000.000.000-00");
                };
            }

            private Processor endProcess() {
                return exchange -> {
                    System.out.println("Everything is finished. Received data:");
                    System.out.println(exchange.getIn().getHeaders().toString());
                    System.out.println("Received Body:");
                    System.out.println(exchange.getIn().getBody());
                };
            }

            private Processor printFail() {
                return exchange -> {
                    System.out.println("FAIL FINISH. The owner of CPF is not a GOD!!!");
                    System.out.println("Received CPF: " + exchange.getIn().getHeader("cpf"));
                };
            }

        });

        MessageModel message = new MessageModel("333", "jacob");

        Exchange exchange = new DefaultExchange(camelContext);
        exchange.getIn().setHeader("username", "jacob.crepusculo");
        exchange.getIn().setHeader("admin", "true");
        exchange.getIn().setHeader("type", "god");
        exchange.getIn().setHeader("test", message);
        exchange.getIn().setBody("Oi :D");


        ProducerTemplate template = camelContext.createProducerTemplate();
        camelContext.start();
        template.setDefaultEndpointUri("direct:start");
        template.send(exchange);
    }

}
