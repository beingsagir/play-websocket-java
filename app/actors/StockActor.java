package actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Cancellable;
import akka.japi.pf.ReceiveBuilder;
import java.util.concurrent.TimeUnit;
import java.util.Deque;
import java.util.HashSet;

import play.Logger;
import scala.concurrent.duration.Duration;
import utils.FakeStockQuote;
import utils.StockQuote;

/**
 * There is one StockActor per stock symbol.  The StockActor maintains a list of users watching the stock and the stock
 * values.  Each StockActor updates a rolling dataset of randomly generated stock values.
 */
public class StockActor extends AbstractActor {

    final Deque<Double> stockHistory = FakeStockQuote.history(50);

    public StockActor(String symbol) {
        this(symbol, new FakeStockQuote());
    }

    public StockActor(String symbol, StockQuote stockQuote) {
        receive(ReceiveBuilder
            .match(Stock.Latest.class, latest -> {
                // Generate new price
                Double newPrice = stockQuote.newPrice(stockHistory.peekLast());
                stockHistory.add(newPrice);
                stockHistory.remove();

                // Log new stock price
                Logger.debug("New price: " + newPrice);
            }).build());
    }
}
