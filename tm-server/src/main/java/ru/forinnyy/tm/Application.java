package ru.forinnyy.tm;

import lombok.NonNull;
import ru.forinnyy.tm.component.Bootstrap;

public final class Application {

    public static void main(final String... args) {
        @NonNull final Bootstrap bootstrap = new Bootstrap();
        bootstrap.start();
    }

}