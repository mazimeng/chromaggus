package com.workasintended.chromaggus.order;

/**
 * Created by mazimeng on 8/1/15.
 */
public class Order {
    private Runnable onStop = new Runnable() {
        @Override
        public void run() {

        }
    };
    private boolean stop = false;

    public void update(float delta) {

    }

    public void start() {

    }

    public Runnable getOnStop() {
        return onStop;
    }

    public void setOnStop(Runnable onStop) {
        this.onStop = onStop;
    }

    public void stop() {
        this.stop = true;
        this.getOnStop().run();
    }

    public void setStop(boolean stopped) {
        this.stop = stopped;
    }

    public boolean getStop() {
        return this.stop;
    }
}
