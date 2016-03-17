package com.sandeep.aop.guice;

import java.lang.reflect.Method;

import org.apache.http.client.HttpClient;

import com.google.inject.AbstractModule;
import com.google.inject.matcher.AbstractMatcher;
import com.google.inject.matcher.Matchers;
import com.google.inject.name.Names;
import com.sandeep.aop.annotations.Trace;
import com.sandeep.aop.trace.HttpRequestBaseInterceptor;
import com.sandeep.aop.trace.HttpRequestWrapperInterceptor;
import com.sandeep.aop.trace.KafkaTraceInterceptor;
import com.sandeep.aop.traced.KafkaSender;

/**
 * <p>
 * This is the Guice module which wires all the dependencies and aspects together
 * </p>
 * <p>
 * This class identifies the interceptor to call on invocation
 * </p>
 * 
 * @author Sandeep Nayak
 *
 */
public class TraceModule extends AbstractModule {

  @Override
  protected void configure() {
    // This interceptor intercepts all calls to HTTPRequestGenerator and invokes the HTTPTraceLogger
    // prior to those calls
    bind(HttpClient.class).to(MyHttpClient.class);
    bindInterceptor(Matchers.subclassesOf(HttpClient.class),
        new AbstractMatcher<Method>() {
          @Override
          public boolean matches(Method method) {
            if (method.getName().equals("execute")) {
              return true;
            }
            return false;
          }
        },
        new HttpRequestBaseInterceptor(), new HttpRequestWrapperInterceptor());
    bindConstant().annotatedWith(Names.named("kafka_topic")).to("test_topic");
    bindConstant().annotatedWith(Names.named("kafka_partition")).to("test_partition");
    bindConstant().annotatedWith(Names.named("kafka_broker_list"))
        .to("dev.ut1.omniture.com:9092,socvm13.dev.ut1.omniture.com:9092");

    // This interceptor intercepts all calls to KafkaSender and invokes the KafkaTraceLogger
    // prior to those calls
    bindInterceptor(Matchers.subclassesOf(KafkaSender.class), Matchers.annotatedWith(Trace.class),
        new KafkaTraceInterceptor());
  }


}
