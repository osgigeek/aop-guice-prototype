package com.sandeep.aop.trace;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.http.client.methods.HttpRequestWrapper;
import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * The HTTP Request wrapper logger is triggered when HttpClient is used to invoke HttpRequestWrapper
 * type objects
 * </p>
 * 
 * @author Sandeep Nayak
 *
 */
public class HttpRequestWrapperInterceptor extends TracingInterceptor {
  private static final Logger logger = LoggerFactory.getLogger(HttpRequestWrapperInterceptor.class);

  @Override
  public Object invoke(MethodInvocation invocation) throws Throwable {
    logger.debug("Trace at {} transactionId = {} && PropertyId = {} && PostId {} @time {}",
        invocation.getMethod().getName(), Trace.getTrace().getTransactionId(),
        Trace.getTrace().getPropertyId(),
        Trace.getTrace().getPostId(),
        Trace.getTrace().getTraceCreateDate());
    // Object obj = invocation.proceed();
    Object[] args = invocation.getArguments();
    if (args.length > 0 && args[0] instanceof HttpRequestWrapper) {
      ((HttpRequestWrapper) invocation.getArguments()[0])
          .addHeader(new BasicHeader(Trace.TRANSACTION_ID, Trace.getTrace().getTransactionId()));
      ((HttpRequestWrapper) invocation.getArguments()[0])
          .addHeader(new BasicHeader(Trace.PROPERTY_ID, Trace.getTrace().getPropertyId()));
    }
    return invocation.proceed();
  }
}
