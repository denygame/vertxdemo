package test.http.call;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.MultiMap;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import io.vertx.run.Runner;

public class VertxWebClient extends AbstractVerticle {

	public static void main(String[] args) {
		Runner.runExample(VertxWebClient.class);
//		Vertx.vertx().deployVerticle(new VertxWebClient());
	}
	
	
	@Override
	public void start() throws Exception {		
		WebClient client = WebClient.create(vertx);

		client
		  .get(80, "localhost", "/mine/restful_ttgt/api/v1/places/suggest?token=c5524a995239b62515d0be602f3ce43c&key=ly thuong")
		  .send(ar -> {
		    if (ar.succeeded()) {
		      // Obtain response
		      HttpResponse<Buffer> response = ar.result();

		      System.out.println("Received response with status code " + response.statusCode());
		      JsonObject json = response.body().toJsonObject();
		      System.out.println(json);
		    } else {
		      System.out.println("Something went wrong " + ar.cause().getMessage());
		    }
		  });
		
		
		MultiMap form = MultiMap.caseInsensitiveMultiMap();
		form.set("username", "po");
		form.set("password", "123");
		
		client
			.post(80,"localhost","/mine/restful_ttgt/api/v1/auth/login")
			.sendForm(form, req->{
				if(req.succeeded()) {
					 HttpResponse<Buffer> response = req.result();
					 
					 JsonObject json = response.body().toJsonObject();
					 System.out.println(json);
				} else {
					System.out.println("Something went wrong " + req.cause().getMessage());	
				}
			});
	}

}
