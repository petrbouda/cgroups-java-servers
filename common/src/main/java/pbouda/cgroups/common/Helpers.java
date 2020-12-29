package pbouda.cgroups.common;

import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class Helpers {

    private static final int MAX_ROWS = 1000;

    public static Collection<Long> generateIds(int count) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        return Stream.generate(() -> random.nextLong(Helpers.MAX_ROWS))
                .limit(count)
                .collect(Collectors.toUnmodifiableList());
    }

    public static Long generateId() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        return random.nextLong(Helpers.MAX_ROWS);
    }
}
