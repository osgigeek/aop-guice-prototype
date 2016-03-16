package com.sandeep.aop.trace;

import java.time.Instant;

/**
 * <p>
 * Trace Entry is simply a POJO which carries the necessary trace information as a singular packet
 * </p>
 * 
 * @author Sandeep Nayak
 *
 */
public class TraceEntry {
  private final String transactionId;
  private final String propertyId;
  private final String postId;
  private final Instant createdDate;

  /**
   * <p>
   * A Trace entry contains transaction id, property id and post id
   * </p>
   * 
   * @param transactionId the transaction id unique for each call
   * @param propertyId the property id
   * @param postId the post id
   */
  public TraceEntry(String transactionId, String propertyId, String postId) {
    this.transactionId = transactionId;
    this.propertyId = propertyId;
    this.postId = postId;
    createdDate = Instant.now();
  }

  /**
   * <b>Returns the transaction id</b>
   * 
   * @return Transaction id
   */
  public String getTransactionId() {
    return transactionId;
  }

  /**
   * <b>Returns the property id</b>
   * 
   * @return Property id
   */
  public String getPropertyId() {
    return propertyId;
  }

  /**
   * <b>Returns the post id</b>
   * 
   * @return post id
   */
  public String getPostId() {
    return postId;
  }

  /**
   * <b>Returns the date-time the trace was created</b>
   * 
   * @return Instant
   */
  public Instant getTraceCreateDate() {
    return createdDate;
  }
}
