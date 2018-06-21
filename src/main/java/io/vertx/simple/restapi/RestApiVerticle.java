package io.vertx.simple.restapi;

import java.util.ArrayList;
import java.util.List;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

public class RestApiVerticle extends AbstractVerticle {

	private List<String> names = new ArrayList<>();
	
	
//	public static void main(String[] args) {
//		Vertx.vertx().deployVerticle(new RestApiVerticle());
//	}
	
	
	@Override
	public void start() throws Exception {
		Router router = Router.router(vertx);

		router.get("/").handler(req -> {
			req.response().end("Welcome");
		});

		router.get("/names")
				.handler(rc -> rc.response().putHeader("content-type", "application/json").end(Json.encode(names)));

		// to read the request body
		router.route().handler(BodyHandler.create());

		router.post("/names").handler(rc -> {
			// Read the body
			String name = rc.getBody().toString();
			if (name.isEmpty()) {
				// Invalid body -> Bad request
				rc.response().setStatusCode(400).end();
			} else if (names.contains(name)) {
				// Already included name -> Conflict
				rc.response().setStatusCode(409).end();
			} else {
				// Add the name to the list -> Created
				names.add(name);
				rc.response().setStatusCode(201).end(name);
			}
		});
		
		
		vertx.createHttpServer().requestHandler(router::accept).listen(8000);
		
		
	}

}
