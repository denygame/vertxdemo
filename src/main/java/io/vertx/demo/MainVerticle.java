package io.vertx.demo;

import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;
import io.vertx.run.Runner;

public class MainVerticle extends AbstractVerticle {
	
	public static void main(String[] args) {
		Runner.runExample(MainVerticle.class);
	}
	
    @Override
    public void start() throws Exception {
    	Router router = Router.router(vertx);

        router.route().handler(routingContext -> {
          routingContext.response().putHeader("content-type", "text/html").end("Hello World!");
        });

        vertx.createHttpServer().requestHandler(router::accept).listen(8080);
        System.out.println("HTTP server started on port 8080");
    }
}
