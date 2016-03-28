package test4.pipeline;

import org.apache.camel.Predicate;
import org.apache.camel.builder.RouteBuilder;
import test4.predicates.IsUserPredicate;
import test4.processor.*;

import static org.apache.camel.builder.PredicateBuilder.and;

public class SimplePipeline extends RouteBuilder{

    Predicate isAdmin = and(new IsUserPredicate(), header("admin").isEqualTo("true"));
    Predicate isGod = and(isAdmin, header("type").isEqualTo("god"), header("test.cpf()").isEqualTo("333"));
    Predicate hasCpfOfGodUser = header("cpf").isEqualTo("333.333.333-33");

    @Override
    public void configure() throws Exception {
        from("direct:start")
                .choice()
                .when(isGod).process(new GodUserProcessor()).to("direct:end")
                .when(isAdmin).process(new AdminUserProcessor()).to("direct:end")
                .when(new IsUserPredicate()).process(new OrdinaryUserProcessor()).to("direct:end")
                .otherwise().to("direct:end")
                .end();

        from("direct:end")
                .choice()
                .when(hasCpfOfGodUser).process(new EndProcessor())
                .otherwise().process(new FailProcessor())
                .to("log:input");
    }

}
