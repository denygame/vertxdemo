package test.eventbus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;

public class VertxStart extends AbstractVerticle {
	
	public static void main(String[] args) {
		Vertx vertx = Vertx.vertx();
		System.out.println("main");
		vertx.deployVerticle(new EchoServiceVerticle());
//		vertx.deployVerticle(new RemoveServiceVerticle());
		vertx.deployVerticle(new WebVerticle());
	}
	
	@Override
	public void start() throws Exception {
		System.out.println("start");
		vertx.deployVerticle(new EchoServiceVerticle());
		vertx.deployVerticle(new WebVerticle());
	}
	
}
