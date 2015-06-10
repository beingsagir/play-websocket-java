package actors;

import java.util.Deque;

public class Stock {
    public static final class Latest {
        public Latest() {}
    }

    public static final Latest latest = new Latest();

    public static final class History {
        public final String symbol;
        public final Deque<Double> history;

        public History(String symbol, Deque<Double> history) {
            this.symbol = symbol;
            this.history = history;
        }
    }

    public static final class Watch {
        public final String symbol;

        public Watch(String symbol) {
            this.symbol = symbol;
        }
    }
}
