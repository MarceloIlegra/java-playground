package br.com.sample;

import org.apache.camel.CamelContext;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.SimpleRegistry;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Application {

    public static void main(String[] args) throws Exception {

        BasicDataSource basicDataSource =  new BasicDataSource();
        basicDataSource.setDriverClassName("org.h2.Driver");
        basicDataSource.setUrl("jdbc:h2:tcp://localhost/~/test");
        basicDataSource.setUsername("sa");
        basicDataSource.setPassword("");


        PreparedStatement preparedStatement = basicDataSource.getConnection().prepareStatement("select * from teste");
        ResultSet resultSet = preparedStatement.executeQuery();

        while(resultSet.next()){
            System.out.println(resultSet.getString("DESCRIPTION"));
        }
        
        SimpleRegistry reg = new SimpleRegistry() ;
        reg.put("myDataSource",basicDataSource);

        CamelContext camelContext = new DefaultCamelContext(reg);
        /**
         * <property name="driverClassName" value="org.apache.derby.jdbc.EmbeddedDriver"/>
         <property name="url" value="jdbc:derby:memory:orders;create=true"/>
         <property name="username" value=""/>
         <property name="password" value=""/>
         */

        //camelContext.addService(basicDataSource);
        camelContext.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("direct:start").process(startProcess()).to("direct:process");
                from("direct:process").process(executeProcess()).to("log:input");
            }

            private Processor startProcess() {
                return exchange -> {
                    System.out.println("START");
                    exchange.getIn().setBody("Cry", String.class);
                };
            }

            private Processor executeProcess() {
                return exchange -> {
                    System.out.println("PROCESSS");
                    System.out.println(exchange.getIn().getBody().toString());
                };
            }
        });

        ProducerTemplate template = camelContext.createProducerTemplate();
        camelContext.start();
        template.sendBody("direct:start", "TESTE");
    }

}

/**
 <bean id="dataSource" class="org.apache.commons.dbcp.BasicDataSource"
 destroy-method="close">
 <property name="driverClassName" value="com.mysql.jdbc.Driver" />
 <property name="url" value="jdbc:mysql://localhost/javavill_forum" />
 <property name="username" value="root" />
 <property name="password" value="" />
 </bean>


 <!-- configure the Camel SQL component to use the JDBC data source -->
 <bean id="sqlComponent" class="org.apache.camel.component.sql.SqlComponent">
 <property name="dataSource" ref="dataSource" />
 </bean>

 <bean id="topicBean" class="com.javavillage.camel.sql.NewTopicBean" />
 */
