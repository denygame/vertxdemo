package io.vertx.demo;

import java.net.UnknownHostException;

import com.mongodb.*;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.*;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.run.Runner;

public class MainRestMongo extends AbstractVerticle {

	public static void main(String[] args) {
		Runner.runExample(MainRestMongo.class);
	}

	private DBCollection collection = null;

	public DBCollection getCollection() {
		return collection;
	}

	public void setCollection(DBCollection collection) {
		this.collection = collection;
	}

	public MainRestMongo() throws UnknownHostException {
		MongoClient mongo = new MongoClient();
		DB db = mongo.getDB("mydb");
		DBCollection collection = db.getCollection("sinhvien");
		this.setCollection(collection);
	}

	@Override
	public void start() throws Exception {

		Router router = Router.router(vertx);

		// Enable the body parser to we can get the form data and json documents in out
		// context.
		router.route().handler(BodyHandler.create());

		router.get("/students").handler(ctx -> {
			DBCursor ls = this.getCollection().find();
			ctx.response().putHeader("content-type", "application/json").end(Json.encodePrettily(ls.toArray()));
		});

		router.post("/students").handler(this::handleInsertStudent);

		router.get("/test").handler(res -> {
			Test.func();
			res.response().end("asd");
		});

		vertx.createHttpServer().requestHandler(router::accept).listen(3000);
	}

	private void handleInsertStudent(RoutingContext rc) {
		String dept_id = rc.request().getParam("dept_id");
		String dept_no = rc.request().getParam("dept_no");
		String dept_name = rc.request().getParam("dept_name");
		String location = rc.request().getParam("location");

		if (checkNull(dept_id) && checkNull(dept_name) && checkNull(dept_no) && checkNull(location)) {

			BasicDBObject doc = new BasicDBObject("dept_id", dept_id).append("dept_no", dept_no)
					.append("dept_name", dept_name).append("location", location);
			this.getCollection().insert(doc);

			Object id = doc.get("_id");

			JsonObject response = new JsonObject().put("msg", "success").put("_id", Json.encodePrettily(id));

			rc.response().putHeader("content-type", "application/json").end(Json.encodePrettily(response));
		} else {
			rc.response().putHeader("content-type", "application/json")
					.end(Json.encodePrettily(new JsonObject().put("msg", "error")));
		}

	}

	private boolean checkNull(String str) {
		return (str != null && !str.isEmpty()) ? true : false;
	}

}
