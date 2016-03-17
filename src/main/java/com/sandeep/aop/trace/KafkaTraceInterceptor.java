package com.sandeep.aop.trace;

import org.aopalliance.intercept.MethodInvocation;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * The Kafka tracer intercepts messages to be sent to Kafka, adds the necessary headers in the
 * message and also logs the message.
 * </p>
 * 
 * @author Sandeep Nayak
 *
 */
public class KafkaTraceInterceptor extends TracingInterceptor {
  private static final String MESSAGE_BODY = "body";
  private static final String MESSAGE_HEADER = "header";
  private static final Logger logger = LoggerFactory.getLogger(KafkaTraceInterceptor.class);
  @Override
  public Object invoke(MethodInvocation invocation) throws Throwable {
    logger.debug("Trace at {} transactionId = {} && PropertyId = {} && PostId {} @time {}",
        invocation.getMethod().getName(),
        Trace.getTrace().getTransactionId(),
            Trace.getTrace().getPropertyId(),
        Trace.getTrace().getPostId(),
        Trace.getTrace().getTraceCreateDate());
    Object args[] = invocation.getArguments();
    String updatedMessage = null;
    if (args.length > 0) {
      String message = (String) args[0];
      logger.debug("Message before sending is = {} ", message);
      ObjectMapper mapper = new ObjectMapper();
      ObjectNode root = mapper.createObjectNode();
      ObjectNode header = mapper.createObjectNode();
      header.put(Trace.TRANSACTION_ID, Trace.getTrace().getTransactionId());
      header.put(Trace.PROPERTY_ID, Trace.getTrace().getPropertyId());
      root.put(MESSAGE_HEADER, header);
      root.put(MESSAGE_BODY, "Hey this surely works!!");
      updatedMessage = mapper.writeValueAsString(root);
    }
    invocation.getArguments()[0] = updatedMessage;
    return invocation.proceed();
  }
}
