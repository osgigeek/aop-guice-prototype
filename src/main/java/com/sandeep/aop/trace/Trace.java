package com.sandeep.aop.trace;

/**
 * <p>
 * A Utility class which can be used to trace
 * </p>
 * 
 * @author Sandeep Nayak
 *
 */
public final class Trace {
  public static final String PROPERTY_ID = "X-ASPHX-PROPERTY-ID";
  public static final String TRANSACTION_ID = "X-ASPHX-TRANSACTION-ID";
  public static final String POST_ID = "X-ASPHX-POST-ID";

  private Trace() {
  }

  // A thread local variable which tracks all the trace elements to be logged
  private static final ThreadLocal<TraceEntry> traceVals =
      new ThreadLocal<TraceEntry>();

  /**
   * <p>
   * Enabling a trace adds the attributes passed in, into the thread local variable
   * </p>
   * 
   * @param transactionId the transaction id on the thread
   * @param propertyId the property id, added for illustration
   * @param postId the post id, added again for illustration
   */
  public static void enableTrace(String transactionId, String propertyId, String postId) {
    traceVals.set(new TraceEntry(transactionId, propertyId, postId));
  }

  /**
   * <p>
   * Returns the trace entry tracked against the thread
   * </p>
   * 
   * @return TraceEntry
   */
  public static TraceEntry getTrace() {
    return traceVals.get();
  }

  /**
   * <p>
   * Clears the trace entry tracked against the thread
   * </p>
   */
  public static void resetTrace() {
    traceVals.remove();
  }
}
