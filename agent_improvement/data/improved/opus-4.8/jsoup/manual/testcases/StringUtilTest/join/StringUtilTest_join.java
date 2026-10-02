package org.jsoup.internal;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for {@link StringUtil#join(java.util.Collection, String)}, which concatenates the
 * elements of a collection into a single string, inserting a separator between consecutive elements.
 */
public class StringUtilTest_join {

    private static final String SPACE_SEPARATOR = " ";

    @Test
    public void joiningSingleEmptyStringYieldsEmptyString() {
        String joined = StringUtil.join(Collections.singletonList(""), SPACE_SEPARATOR);

        assertEquals("", joined);
    }

    @Test
    public void joiningSingleElementOmitsSeparator() {
        String joined = StringUtil.join(Collections.singletonList("one"), SPACE_SEPARATOR);

        assertEquals("one", joined);
    }

    @Test
    public void joiningMultipleElementsInsertsSeparatorBetweenThem() {
        String joined = StringUtil.join(Arrays.asList("one", "two", "three"), SPACE_SEPARATOR);

        assertEquals("one two three", joined);
    }
}
