package com.echo.grpcdemo;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.echo.grpcdemo.HelloGrpc.HelloImplBase;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

/**
 * @author congyou.wu
 * @since 2017-08-02 下午3:17
 */
public class HelloServer {

    private static final Logger log = LoggerFactory.getLogger(HelloServer.class);

    static class HelloImpl extends HelloImplBase {

        @Override
        public void sayHello(HelloRequest req, StreamObserver<HelloReply> responseObserver) {
            HelloReply reply = HelloReply.newBuilder().setMessage("Hello " + req.getName()).build();
            responseObserver.onNext(reply);
            responseObserver.onCompleted();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        int port = 18081;
        Server server = ServerBuilder.forPort(port)
            .addService(new HelloImpl())
            .build()
            .start();
        log.info("Server started, listening on " + port);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            // Use stderr here since the log may have been reset by its JVM shutdown hook.
            System.err.println("*** shutting down gRPC server since JVM is shutting down");
            server.shutdown();
            System.err.println("*** server shut down");
        }));
        server.awaitTermination();
    }
}
