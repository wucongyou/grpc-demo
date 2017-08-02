package com.echo.grpcdemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;

/**
 * @author congyou.wu
 * @since 2017-08-02 下午3:52
 */
public class HelloClient {

    private static final Logger log = LoggerFactory.getLogger(HelloClient.class);

    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost",18081)
            .usePlaintext(true)
            .build();
        HelloGrpc.HelloBlockingStub blockingStub = HelloGrpc.newBlockingStub(channel);
        String name = "foo";
        log.info("Will try to greet " + name + " ...");
        HelloRequest request = HelloRequest.newBuilder().setName(name).build();
        HelloReply response;
        try {
            response = blockingStub.sayHello(request);
        } catch (StatusRuntimeException e) {
            log.error("RPC failed: {0}", e);
            return;
        }
        log.info("Greeting: " + response.getMessage());
    }
}
