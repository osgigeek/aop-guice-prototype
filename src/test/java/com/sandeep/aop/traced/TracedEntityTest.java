package com.sandeep.aop.traced;

import static org.junit.Assert.fail;

import java.io.IOException;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.sandeep.aop.guice.TraceModule;
import com.sandeep.aop.trace.Trace;

public class TracedEntityTest {
  @Inject
  TracedEntity entity;

  @Inject
  HTTPRequestGenerator requestor;

  @Inject
  KafkaSender sender;

  @Inject
  HttpClient client;

  private static final Logger logger = LoggerFactory.getLogger(TracedEntityTest.class);
  @Before
  public void setup() {
    Injector injector = Guice.createInjector(new TraceModule());
    injector.injectMembers(this);
    Trace.enableTrace("Transaction-1", "property-1", "post-1");
  }

  @After
  public void tearDown() {
    Trace.resetTrace();
  }

  @Test
  public void testHttp() {
    HttpGet get = null;
    try {
      get = requestor.createGETRequest("localhost", 7991);
      try{
        client.execute(get);
      } catch (IOException ex) {
        logger.warn("The server the test is connecting to is not running. {}", ex.getMessage());
      }
    } catch (Exception e) {
      e.printStackTrace();
      fail(e.getMessage());
    }
  }

  @Test
  public void traceKafka() {
    sender.sendMessage("Hey will this work?");
  }

  @Test
  public void traceSeparateThreads() {
    Runnable r = new Runnable(){
      @Override
      public void run() {
        Trace.enableTrace(String.format("Transaction-{}", Thread.currentThread().getId()),
            String.format("Property-{}", Thread.currentThread().getId()),
            String.format("Post-{}", Thread.currentThread().getId()));
        entity.someMethod(String.format("test-Param-", Thread.currentThread().getId()),
            String.format("test-Param-", Thread.currentThread().getId()));
      }
    };
    Thread t1 = new Thread(r);
    t1.start();

    Thread t2 = new Thread(r);
    t2.start();

    Thread t3 = new Thread(r);
    t3.start();

  }
}
