package com.fasterxml.jackson.annotation;

import java.lang.reflect.Member;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JsonAutoDetectTest_testAnnotationProperties extends AnnotationTestUtil {

    private static final JsonAutoDetect.Value NO_OVERRIDES = JsonAutoDetect.Value.noOverrides();

    private static final JsonAutoDetect.Value DEFAULTS = JsonAutoDetect.Value.defaultVisibility();

    private static final String PUBLIC_FIELD_NAME = "value";

    static class Bogus {
        public int value;
    }

    @Test
    public void testAnnotationProperties() throws Exception {
        Member publicField = Bogus.class.getField(PUBLIC_FIELD_NAME);

        assertVisible(publicField, Visibility.ANY);
        assertNotVisible(publicField, Visibility.NONE);
        assertVisible(publicField, Visibility.NON_PRIVATE);
        assertVisible(publicField, Visibility.PUBLIC_ONLY);
        assertVisible(publicField, Visibility.PROTECTED_AND_PUBLIC);
        assertVisible(publicField, Visibility.NON_PRIVATE);
        assertNotVisible(publicField, Visibility.DEFAULT);
    }

    private void assertVisible(Member member, Visibility visibility) {
        assertTrue(visibility.isVisible(member));
    }

    private void assertNotVisible(Member member, Visibility visibility) {
        assertFalse(visibility.isVisible(member));
    }
}
