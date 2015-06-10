package actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import java.util.Optional;
import play.libs.Akka;

public class StocksActor extends AbstractActor {

    private static class LazyStocksActor {
        public static final ActorRef ref = Akka.system().actorOf(Props.create(StocksActor.class));
    }

    public static ActorRef stocksActor() {
        return LazyStocksActor.ref;
    }

    public StocksActor() {
        receive(ReceiveBuilder
            .match(Stock.Watch.class, watch -> {
                String symbol = watch.symbol;
                // get or create the StockActor for the symbol and forward this message
                Optional.ofNullable(getContext().getChild(symbol)).orElseGet(
                        () -> context().actorOf(Props.create(StockActor.class, symbol), symbol)
                ).forward(watch, context());
            }).build());
    }
}
