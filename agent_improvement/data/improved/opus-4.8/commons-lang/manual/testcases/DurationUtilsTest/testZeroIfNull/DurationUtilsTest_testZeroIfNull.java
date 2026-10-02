package org.apache.commons.lang3.time;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link DurationUtils#zeroIfNull(Duration)}.
 *
 * <p>The method is a null-safe accessor: it returns {@link Duration#ZERO} when
 * given {@code null}, and otherwise returns the supplied duration unchanged.</p>
 */
public class DurationUtilsTest_testZeroIfNull extends AbstractLangTest {

    @Test
    void testZeroIfNull() {
        // A null input is replaced with the zero duration.
        assertEquals(Duration.ZERO, DurationUtils.zeroIfNull(null));

        // A non-null input is returned as-is.
        final Duration oneDay = Duration.ofDays(1);
        assertEquals(oneDay, DurationUtils.zeroIfNull(oneDay));
    }
}
