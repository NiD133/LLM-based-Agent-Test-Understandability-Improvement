package org.apache.commons.lang3.time;

import static org.apache.commons.lang3.LangAssertions.assertNullPointerException;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

public class DurationUtilsTest_testToMillisIntNullDuration extends AbstractLangTest {

    /**
     * {@link DurationUtils#toMillisInt(java.time.Duration)} requires a non-null duration,
     * so passing {@code null} must raise a {@link NullPointerException}.
     */
    @Test
    void testToMillisIntNullDuration() {
        assertNullPointerException(() -> DurationUtils.toMillisInt(null));
    }
}
