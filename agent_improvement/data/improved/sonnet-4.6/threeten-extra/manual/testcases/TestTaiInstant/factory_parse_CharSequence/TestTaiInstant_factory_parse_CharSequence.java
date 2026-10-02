package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestTaiInstant_factory_parse_CharSequence {

    // TAI instants are formatted as "{seconds}.{9-digit-nanos}s(TAI)", e.g. "42.900000000s(TAI)"
    @Test
    public void factory_parse_CharSequence() {
        for (int taiSeconds = -1000; taiSeconds < 1000; taiSeconds++) {
            for (int nanoOfSecond = 900000000; nanoOfSecond < 990000000; nanoOfSecond += 10000000) {
                String taiString = taiSeconds + "." + nanoOfSecond + "s(TAI)";
                TaiInstant parsed = TaiInstant.parse(taiString);
                assertEquals(taiSeconds, parsed.getTaiSeconds());
                assertEquals(nanoOfSecond, parsed.getNano());
            }
        }
    }
}
