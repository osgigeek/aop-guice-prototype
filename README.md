# AOP Guice Prototype

## Objective
The purpose of this prototype is to demonstrate the ability to use AOP as a potential solution to implement cross-cutting concerns like Tracing.

## Setup
The git repo contains code which uses Guice to illustrate how Guice can be used to accomplish AOP. The code contains the following pieces

### Java Code Packages

* `com.sandeep.aop.annotations`: Contains all annotations which trigger cross cutting concerns
* `com.sandeep.aop.guice`: Contains all code which is related to google guice. This is extensions to guice modules which allow injecting behavior.
* `com.sandeep.aop.trace`: Contains code which illustrates how tracing is enabled using Guice for HTTP and Kafka classes
* `com.sandeep.aop.traced`: Contains code which is being traced
* `com.sandeep.aop.annotations`: Contains all annotations which trigger cross cutting concerns

### Test Code

The test code illustrates the tracing behavior. The code is in /test/java in package com.sandeep.aop.traced.

## Guice Limitations
* Guice cannot create proxies or intercept calls for objects which are not created using Guice. The objects have to be created using Guice and _injected_ into the dependents.


## Prototype Details
The demo contains two items:-

* Trace HTTP client invocations : see `testHttp`
	* The idea here is to intercept the HTTP requests before they are sent out and add the trace information as HTTP headers
* Trace Kafka sender sendMessage : see `traceKafka`
	* The idea here is to intercept the Kafka message send and create a message which carries the trace information as headers and the message as a body. The reason we need to do this is to ensure that a request when it flows from synchronous to asynchronous points we can still carry the tracing information on that request.

So what happens when the test is run:-

1. The test setup is called which loads the Guice injector and setups all the dependency injections and method interceptions.
	* The setup is done in the `TraceModule`
	* Trace module ensures that any call to HttpClient results in an instance of MyHTTPClient being created
	* It adds a method interceptor for HttpClient to ensure that all calls to `execute` on `HttpClient` are going into the two interceptors i.e. `HttpRequestBaseInterceptor` and `HttpRequestWrapperInterceptor`. The reason there are two is because the request has two forms in the `HttpRequest` hierarchy and to inject headers we need to know both types. Rather than have if/else conditions in one interceptor, we setup a chain of interceptors.
	* It binds constants to `Named` parameters which is used to create the Kafka Client. 
	* It adds a method interceptor for `KafkaSender` so that all calls to send a message are intercepted.
2. The following entities are injected in the test
	*  `TracedEntity`
	*  `HTTPRequestGenerator`
	*  `KafkaSender`
	*  `HttpClient`

3. When the test `testHttp` runs the following things happen
   * The setup has already injected transaction-id, property-id and post-id
	* The injected `HttpClient` is created and a GET request is executed
	* The instrumentation in `TraceModule` for the method interceptor will trigger and cause the `HttpRequestBaseInterceptor` to be invoked
	* The `HttpRequestBaseInterceptor` will then log the trace information from the thread local and also inject the relevant headers into the request. The request can be altered on the invocation just before allowing the invocation to proceed.
	* The invocation can be executed using `invocation.proceed()`

## Summary
In summary AOP with guice is simple and can be used to implement cross cutting concerns like tracing. It already integrates with `DropWizard` as well as other frameworks and is quite popular. Its lightweight and thus is a promising candidate as against AspectJ.