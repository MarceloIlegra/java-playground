package test4.predicates;

import org.apache.camel.Exchange;
import org.apache.camel.Predicate;

public class IsUserPredicate implements Predicate{

    @Override
    public boolean matches(Exchange exchange) {
        return exchange.getIn().getHeader("username") != null;
    }

}
