package io.vertx.demo;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;
import io.vertx.run.Runner;

public class MainMysql extends AbstractVerticle {

	public static void main(String[] args) {
		Runner.runExample(MainMysql.class);
	}

	@Override
	public void start() throws Exception {
//		JsonObject mySQLClientConfig = new JsonObject().put("localhost", "kids");
		System.out.println("sd");
	}

}
