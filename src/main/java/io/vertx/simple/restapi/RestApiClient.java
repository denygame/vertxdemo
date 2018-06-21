package io.vertx.simple.restapi;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.core.json.JsonArray;

public class RestApiClient {

	private HttpClient client;

	public static void main(String[] args) {
		Vertx vertx = Vertx.vertx();
		RestApiClient client = new RestApiClient(vertx);

		client.addName("Huy", ar -> {
			if (ar.succeeded()) {
				System.out.println("Names add success!" + ar.result());
			} else {
				System.out.println("Unable to retrieve the list of names: " + ar.cause().getMessage());
			}
		});

		client.getNames(ar -> {
			if (ar.succeeded()) {
				System.out.println("Names: " + ar.result().encode());
			} else {
				System.out.println("Unable to retrieve the list of names: " + ar.cause().getMessage());
			}
		});
	}

	public RestApiClient(Vertx vertx) {
		// Create the HTTP client and configure the host and post.
		client = vertx.createHttpClient(new HttpClientOptions().setDefaultHost("localhost").setDefaultPort(8000));
	}

	public void close() {
		// Don't forget to close the client when you are done.
		client.close();
	}

	public void getNames(Handler<AsyncResult<JsonArray>> handler) {
		// Emit a HTTP GET
		client.get("/names", response ->
		// Handler called when the response is received
		// We register a second handler to retrieve the body
		response.bodyHandler(body -> {
			// When the body is read, invoke the result handler
			handler.handle(Future.succeededFuture(body.toJsonArray()));
		})).exceptionHandler(t -> {
			// If something bad happen, report the failure to the passed handler
			handler.handle(Future.failedFuture(t));
		})
				// Call end to send the request
				.end();
	}

	public void addName(String name, Handler<AsyncResult<Void>> handler) {
		// Emit a HTTP POST
		client.post("/names", response -> {
			// Check the status code and act accordingly
			if (response.statusCode() == 200) {
				handler.handle(Future.succeededFuture());
			} else {
				handler.handle(Future.failedFuture(response.statusMessage()));
			}
		}).exceptionHandler(t -> handler.handle(Future.failedFuture(t)))
				// Pass the name we want to add
				.end(name);
	}
}
