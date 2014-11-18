package com.msevgi.facebookapiex;

import com.squareup.otto.Bus;

public final class BusProvider {

    private static Bus mBus = new Bus();

    private BusProvider() {
    }

    public static Bus getInstance() {
        return mBus;
    }
}