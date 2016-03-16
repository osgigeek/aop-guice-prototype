package com.sandeep.aop.trace;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * Trace logger is a method interceptor, this code is invoked each time the trace logger needs to be
 * called for methods annotated with @Trace
 * </p>
 * 
 * @author Sandeep Nayak
 *
 */
public class TraceLogger implements MethodInterceptor {
  private static final Logger logger = LoggerFactory.getLogger(TraceLogger.class);

  public TraceLogger() {}

  @Override
  public Object invoke(MethodInvocation invocation) throws Throwable {
    logger.debug("Trace at {} transactionId = {} && PropertyId = {} && PostId {}",
        invocation.getMethod().getName(), Trace.getTrace().getTransactionId(),
        Trace.getTrace().getPropertyId(),
        Trace.getTrace().getPostId());
    Object result = invocation.proceed();
    HttpRequestBase httpRequest = null;
    if (result != null && result instanceof HttpRequestBase) {
      httpRequest = (HttpRequestBase) result;
      httpRequest
          .addHeader(new BasicHeader(Trace.TRANSACTION_ID, Trace.getTrace().getTransactionId()));
    }
    return httpRequest;
  }
}
