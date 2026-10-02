package org.jsoup.internal;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StringUtilTest_join {

    @Test
    public void joinSingleEmptyStringReturnsEmptyString() {
        assertEquals("", StringUtil.join(Collections.singletonList(""), " "));
    }

    @Test
    public void joinSingleStringReturnsThatString() {
        assertEquals("one", StringUtil.join(Collections.singletonList("one"), " "));
    }

    @Test
    public void joinMultipleStringsSeparatesEachValue() {
        assertEquals("one two three", StringUtil.join(Arrays.asList("one", "two", "three"), " "));
    }
}
