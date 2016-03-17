package com.sandeep.aop.trace;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * This interceptor traps all calls HttpClient which receives HttpRequest. The interceptor injects
 * http request headers
 * </p>
 * 
 * @author Sandeep Nayak
 *
 */
public class HttpRequestBaseInterceptor extends TracingInterceptor {
  private static final Logger logger = LoggerFactory.getLogger(HttpRequestBaseInterceptor.class);
  @Override
  public Object invoke(MethodInvocation invocation) throws Throwable {
    logger.debug("Trace at {} transactionId = {} && PropertyId = {} && PostId {} @time {}",
            invocation.getMethod().getName(), Trace.getTrace().getTransactionId(),
            Trace.getTrace().getPropertyId(),
        Trace.getTrace().getPostId(),
        Trace.getTrace().getTraceCreateDate());
    // Object obj = invocation.proceed();
    Object[] args = invocation.getArguments();
    if (args.length > 0 && args[0] instanceof HttpRequestBase) {
      ((HttpRequestBase) invocation.getArguments()[0])
          .addHeader(new BasicHeader(Trace.TRANSACTION_ID, Trace.getTrace().getTransactionId()));
      ((HttpRequestBase) invocation.getArguments()[0])
          .addHeader(new BasicHeader(Trace.PROPERTY_ID, Trace.getTrace().getPropertyId()));
    }
    return invocation.proceed();
  }
}
