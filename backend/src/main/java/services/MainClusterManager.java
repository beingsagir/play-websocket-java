package services;

import akka.actor.ActorSystem;
import akka.cluster.Cluster;
import journal.SharedJournalSetter;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainClusterManager {

    public static void main(String... args) throws IOException {

        //improper / lazy checking - export port and cluster - or use defaults if not available
        ActorSystem system = null;
        String role = "backend";
        if (args.length < 1) {
            system = startSystem("0", role);
        } else {
            String port = args[0];
            system = startSystem(port, role);
        }

        if (Cluster.get(system).getSelfRoles().stream().anyMatch(r -> r.startsWith("backend"))) {
            system.actorOf(StockManager.props(), "stockManager");
        }

        system.actorOf(SharedJournalSetter.props(), "shared-journal-setter");

        addShutdownHook(system);
    }

    public static ActorSystem startSystem(String port, String role) {

        int portNr = Integer.parseInt(port);

        // Override the port number configuration
        Config config = ConfigFactory.parseString("akka.remote.netty.tcp.port=" + portNr).
                withFallback(ConfigFactory.parseString("akka.cluster.roles=[" + role + "]")).
                withFallback(ConfigFactory.parseString("akka.loglevel=INFO")).
                withFallback(ConfigFactory.load());

        return ActorSystem.create("application", config);
    }

    public static void addShutdownHook(ActorSystem system) {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                System.out.print("Shutting down the actor system..");
                system.shutdown();
                system.awaitTermination();
            }
        });
    }
}
