import actors.Stock;
import actors.StockActor;
import actors.StocksActor;
import akka.actor.ActorRef;
import akka.actor.Cancellable;
import play.Application;
import play.GlobalSettings;
import play.libs.Akka;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

public class Global extends GlobalSettings {

    @Override
    public void onStart(Application application) {
        super.onStart(application);

        scheduleTick();
    }

    private Cancellable scheduleTick() {
        return Akka.system().scheduler().schedule(
                Duration.Zero(),
                Duration.create(75, TimeUnit.MILLISECONDS),
                StocksActor.getInstance(),
                Stock.latest,
                Akka.system().dispatcher(),
                ActorRef.noSender());
    }
}
