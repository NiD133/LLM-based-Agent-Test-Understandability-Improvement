package org.apache.commons.lang3.time;

import static org.apache.commons.lang3.LangAssertions.assertNullPointerException;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DurationUtilsTest_testToMillisIntNullDuration extends AbstractLangTest {

    @Test
    @DisplayName("toMillisInt throws NullPointerException when duration argument is null")
    void testToMillisIntNullDuration() {
        assertNullPointerException(() -> DurationUtils.toMillisInt(null));
    }
}
