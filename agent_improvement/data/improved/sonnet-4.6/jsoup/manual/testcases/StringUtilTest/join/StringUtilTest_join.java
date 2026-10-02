package org.jsoup.internal;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Collections;
import static org.junit.jupiter.api.Assertions.*;

public class StringUtilTest_join {

    @Test
    public void joinSingleEmptyStringReturnsEmpty() {
        assertEquals("", StringUtil.join(Collections.singletonList(""), " "));
    }

    @Test
    public void joinSingleElementReturnsThatElement() {
        assertEquals("one", StringUtil.join(Collections.singletonList("one"), " "));
    }

    @Test
    public void joinMultipleElementsSeparatesThemWithGivenSeparator() {
        assertEquals("one two three", StringUtil.join(Arrays.asList("one", "two", "three"), " "));
    }
}
