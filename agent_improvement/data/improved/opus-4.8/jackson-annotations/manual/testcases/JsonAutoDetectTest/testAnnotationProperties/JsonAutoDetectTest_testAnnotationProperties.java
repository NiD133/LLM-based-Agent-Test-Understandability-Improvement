package com.fasterxml.jackson.annotation;

import java.lang.reflect.Member;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Verifies how each {@link Visibility} level answers {@code isVisible(Member)}
 * when the member is a <b>public</b> field ({@code Bogus.value}, defined in
 * {@link AnnotationTestUtil}).
 */
public class JsonAutoDetectTest_testAnnotationProperties extends AnnotationTestUtil {

    @Test
    public void testAnnotationProperties() throws Exception {
        // The member under test is the public field "value" on the Bogus helper class.
        Member publicField = Bogus.class.getField("value");

        // ANY accepts every access level, so a public member is visible.
        assertTrue(Visibility.ANY.isVisible(publicField));

        // NONE disables detection entirely: nothing is ever visible.
        assertFalse(Visibility.NONE.isVisible(publicField));

        // A public member clears every "at least X" threshold below:
        assertTrue(Visibility.NON_PRIVATE.isVisible(publicField));         // anything but private
        assertTrue(Visibility.PUBLIC_ONLY.isVisible(publicField));         // public only
        assertTrue(Visibility.PROTECTED_AND_PUBLIC.isVisible(publicField)); // protected or public
        assertTrue(Visibility.NON_PRIVATE.isVisible(publicField));

        // DEFAULT defers to context-specific defaults and has no rule of its
        // own here, so isVisible() falls through to returning false.
        assertFalse(Visibility.DEFAULT.isVisible(publicField));
    }
}
