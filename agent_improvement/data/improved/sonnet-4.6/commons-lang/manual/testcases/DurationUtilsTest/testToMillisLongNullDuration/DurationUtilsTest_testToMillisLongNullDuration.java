package org.apache.commons.lang3.time;

import static org.apache.commons.lang3.LangAssertions.assertNullPointerException;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DurationUtilsTest_testToMillisLongNullDuration extends AbstractLangTest {

    @Test
    @DisplayName("toMillisLong(null) throws NullPointerException")
    void testToMillisLongNullDuration() {
        assertNullPointerException(() -> DurationUtils.toMillisLong(null));
    }
}
