package controllers;

import play.mvc.Controller;
import play.mvc.Result;

/**
 * The main web controller that handles returning the index page, setting up a WebSocket, and watching a stock.
 */
public class Application extends Controller {

    public Result index() {
        return ok(views.html.index.render());
    }
}
