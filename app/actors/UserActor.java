package actors;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.Logger;
import play.libs.Json;
import play.mvc.WebSocket;

/**
 * The broker between the WebSocket and the StockActor(s).  The UserActor holds the connection and sends serialized
 * JSON data to the client.
 */
public class UserActor extends AbstractActor {

    public UserActor(WebSocket.Out<JsonNode> out) {
        ObjectNode jsonMessage = Json.newObject().put("message", "Message from the server.");
        out.write(jsonMessage);

        receive(ReceiveBuilder
            .match(Object.class, any -> {
                Logger.debug("Received message: " + any.toString());
            }).build());
    }
}
