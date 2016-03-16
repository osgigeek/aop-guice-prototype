package com.sandeep.aop.trace;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * This interceptor traps all calls to create HTTP GET requests, allows the calls to go through,
 * intercepts the response and alters the request to inject trace headers.
 * </p>
 * 
 * @author Sandeep Nayak
 *
 */
public class HttpTraceLogger extends TraceLogger {
  private static final Logger logger = LoggerFactory.getLogger(HttpTraceLogger.class);
  @Override
  public Object invoke(MethodInvocation invocation) throws Throwable {
    logger.debug("Trace at {} transactionId = {} && PropertyId = {} && PostId {} @time {}",
            invocation.getMethod().getName(), Trace.getTrace().getTransactionId(),
            Trace.getTrace().getPropertyId(),
        Trace.getTrace().getPostId(),
        Trace.getTrace().getTraceCreateDate());
    HttpGet get = (HttpGet) invocation.proceed();
    get.addHeader(new BasicHeader(Trace.TRANSACTION_ID, Trace.getTrace().getTransactionId()));
    get.addHeader(new BasicHeader(Trace.PROPERTY_ID, Trace.getTrace().getPropertyId()));
    return get;
  }
}
