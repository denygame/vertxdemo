package test.eventbus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;

public class RemoveServiceVerticle extends AbstractVerticle {

	@Override
	public void start() throws Exception {
		vertx.eventBus().consumer("remove",message->{
			message.reply(new JsonObject().put("remove", true));
		});
	}
	
}
