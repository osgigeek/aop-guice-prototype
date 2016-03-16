package com.sandeep.aop.traced;

import org.apache.http.client.methods.HttpGet;

import com.sandeep.aop.annotations.Trace;

/**
 * <p>
 * This is the object which we are tracing. Note, you will see that only public methods are traced
 * </p>
 * 
 * @author Sandeep Nayak
 *
 */
public class TracedEntity {

  @Trace
  public HttpGet someMethod(String paramA, String paramB) {
    System.out.println(String.format("Some method called with params {%s, %s}", paramA, paramB));
    somePrivateMethod();
    somePublicMethod();
    unTracedMethod();
    HttpGet get = new HttpGet("http://localhost:7991");
    return get;
  }

  @Trace
  public void somePublicMethod() {
    System.out.println("In some public method");
  }

  @Trace
  private void somePrivateMethod() {
    System.out.println("In some private method");
  }

  private void unTracedMethod() {
    System.out.println("Untraceable");
  }
}
