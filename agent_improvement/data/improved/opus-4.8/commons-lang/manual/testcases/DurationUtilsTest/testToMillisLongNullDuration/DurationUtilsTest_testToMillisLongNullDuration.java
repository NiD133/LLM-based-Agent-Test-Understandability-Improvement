package org.apache.commons.lang3.time;

import static org.apache.commons.lang3.LangAssertions.assertNullPointerException;

import java.time.Duration;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link DurationUtils#toMillisLong(Duration)} with a {@code null} argument.
 */
public class DurationUtilsTest_testToMillisLongNullDuration extends AbstractLangTest {

    /**
     * {@code toMillisLong} rejects a {@code null} duration by throwing a
     * {@link NullPointerException} (it calls {@code Objects.requireNonNull} first).
     */
    @Test
    void testToMillisLongNullDuration() {
        assertNullPointerException(() -> DurationUtils.toMillisLong(null));
    }
}
