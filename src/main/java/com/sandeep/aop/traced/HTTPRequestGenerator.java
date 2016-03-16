package com.sandeep.aop.traced;

import java.net.URISyntaxException;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sandeep.aop.annotations.Trace;

/**
 * <p>
 * The request generator generates requests for HTTP
 * </p>
 * 
 * @author Sandeep Nayak
 *
 */
public class HTTPRequestGenerator {
  private static final Logger logger = LoggerFactory.getLogger(HTTPRequestGenerator.class);
  @Trace
  /**
   * <p>
   * Create a get request to the identified host and port
   * </p>
   * 
   * @param host
   * @param port
   * @return
   * @throws URISyntaxException
   */
  public HttpGet createGETRequest(String host, int port) throws URISyntaxException {
    logger.debug("Some method called with params {{}, {}}", host, port);
    URIBuilder uriBuilder = new URIBuilder().setHost(host).setPort(port);
    return new HttpGet(uriBuilder.build().toString());
  }
}
