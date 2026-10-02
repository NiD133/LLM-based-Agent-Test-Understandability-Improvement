package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;

import org.junit.jupiter.api.Test;

public class TestTaiInstant_factory_of_Instant {

    @Test
    public void factory_of_Instant() {
        TaiInstant test = TaiInstant.of(Instant.ofEpochSecond(0, 2));

        assertEquals((40587L - 36204) * 24 * 60 * 60 + 10, test.getTaiSeconds());
        assertEquals(2, test.getNano());
    }
}
