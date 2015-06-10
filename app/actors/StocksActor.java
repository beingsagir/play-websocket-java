package actors;

import akka.actor.ActorRef;
import akka.actor.Props;
import play.libs.Akka;

public class StocksActor {

    private static ActorRef ref = Akka.system().actorOf(Props.create(StockActor.class, "FB"));

    public static ActorRef getInstance() {
        return ref;
    }
}
