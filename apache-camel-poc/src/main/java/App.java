import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.util.jndi.JndiContext;

public class App {

    public static void main(String args[]) throws Exception {
        JndiContext jndiContext = new JndiContext();
        jndiContext.bind("ruleA", new RuleA());
        jndiContext.bind("ruleB", new RuleB());
        jndiContext.bind("ruleC", new RuleC());
        CamelContext camelContext = new DefaultCamelContext(jndiContext);

        try {
            camelContext.addRoutes(new RouteBuilder() {
                public void configure() {

                    from("direct:start")
                            .to("direct:a");

                    from("direct:a")
                            .to("bean:ruleA?method=execute")
                            .setBody(simple("body: ${body}, thread: ${threadName}"))
                            .to("direct:b");

                    from("direct:b")
                            .to("bean:ruleB?method=execute")
                            .choice()
                            .when(body().contains("Marcelo")).to("direct:c")
                            .otherwise().to("stream:out");

                    from("direct:c")
                            .to("bean:ruleC?method=execute")
                            .setBody(simple("body: ${body}, thread: ${threadName}"))
                            .to("stream:out");

                }
            });
            ProducerTemplate template = camelContext.createProducerTemplate();
            camelContext.start();
            template.sendBody("direct:a", "Marcelo");

            //template.sendBody("direct:start", "Multicast");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }

    }

}