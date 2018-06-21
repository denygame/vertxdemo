package test.eventbus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

public class EchoServiceVerticle extends AbstractVerticle {

	private static final Logger LOG = LoggerFactory.getLogger(EchoServiceVerticle.class);

	@Override
	public void start() throws Exception {
		vertx.eventBus().consumer("echo", message -> {
			JsonObject messageBody = (JsonObject) message.body();
			LOG.info("Received: " + messageBody);
			messageBody.put("asd", "d");
			LOG.info("Send: " + messageBody);

			message.reply(messageBody);
		});
	}

}
