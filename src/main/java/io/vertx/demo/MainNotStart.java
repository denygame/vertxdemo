package io.vertx.demo;

import io.vertx.core.Vertx;

public class MainNotStart {
	public static void main(String[] args) {
		Vertx.vertx().createHttpServer().requestHandler(req -> {
			req.response().end("aaa");
		}).listen(3000);
	}
}
