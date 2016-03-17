package com.sandeep.aop.guice;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

import com.sandeep.aop.annotations.Trace;

/**
 * <p>
 * Guice cannot intercept calls to objects which Guice does not create so we do need to allow Guice
 * to create the instances of HttpClient. We bind HttpClient.class to MyHttpClient in the Guice
 * module. So we wrapper the HttpClient and annotate the wrapper to intercept calls
 * </p>
 * 
 * @author Sandeep Nayak
 *
 */
public class MyHttpClient implements HttpClient {
  private HttpClient wrapped;
  public MyHttpClient() {
    wrapped = HttpClients.createDefault();
  }

  @Override
  public HttpParams getParams() {
    return wrapped.getParams();
  }

  @Override
  public ClientConnectionManager getConnectionManager() {
    return wrapped.getConnectionManager();
  }

  @Override
  @Trace
  public HttpResponse execute(HttpUriRequest request) throws IOException, ClientProtocolException {
    return wrapped.execute(request);
  }

  @Override
  @Trace
  public HttpResponse execute(HttpUriRequest request, HttpContext context)
      throws IOException, ClientProtocolException {
    return wrapped.execute(request, context);
  }

  @Override
  @Trace
  public HttpResponse execute(HttpHost target, HttpRequest request)
      throws IOException, ClientProtocolException {
    return wrapped.execute(target, request);
  }

  @Override
  @Trace
  public HttpResponse execute(HttpHost target, HttpRequest request, HttpContext context)
      throws IOException, ClientProtocolException {
    return wrapped.execute(target, request, context);
  }

  @Override
  @Trace
  public <T> T execute(HttpUriRequest request, ResponseHandler<? extends T> responseHandler)
      throws IOException, ClientProtocolException {
    return wrapped.execute(request, responseHandler);
  }

  @Override
  @Trace
  public <T> T execute(HttpUriRequest request, ResponseHandler<? extends T> responseHandler,
      HttpContext context) throws IOException, ClientProtocolException {
    return wrapped.execute(request, responseHandler, context);
  }

  @Override
  @Trace
  public <T> T execute(HttpHost target, HttpRequest request,
      ResponseHandler<? extends T> responseHandler) throws IOException, ClientProtocolException {
    return wrapped.execute(target, request, responseHandler);
  }

  @Override
  @Trace
  public <T> T execute(HttpHost target, HttpRequest request,
      ResponseHandler<? extends T> responseHandler, HttpContext context)
          throws IOException, ClientProtocolException {
    return wrapped.execute(target, request, responseHandler, context);
  }


}
