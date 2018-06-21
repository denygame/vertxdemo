package test.eventbus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.*;

public class WebVerticle extends AbstractVerticle {

	private static final Logger LOG = LoggerFactory.getLogger(WebVerticle.class);
	
	@Override
	public void start() throws Exception {
		vertx.createHttpServer().requestHandler(request -> {
			JsonObject messageBody = new JsonObject().put("web", "d");

			LOG.info("Sending: " + messageBody);
			vertx.eventBus().send("echo", messageBody, reply -> {
				JsonObject echoMess = (JsonObject) reply.result().body();
				LOG.info("Reply from Echo: " + echoMess);
				request.response().end(echoMess.encodePrettily());
			});

		}).listen(8080);
	}

}
