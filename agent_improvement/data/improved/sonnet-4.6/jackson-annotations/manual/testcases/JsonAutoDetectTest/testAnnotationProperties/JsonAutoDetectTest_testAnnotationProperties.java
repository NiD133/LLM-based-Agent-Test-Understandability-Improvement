package com.fasterxml.jackson.annotation;

import java.lang.reflect.Member;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link JsonAutoDetect.Visibility#isVisible(Member)}, verifying that
 * each visibility level correctly accepts or rejects a public field.
 */
public class JsonAutoDetectTest_testAnnotationProperties extends AnnotationTestUtil {

    /** A simple class with a public field used as a reflection target. */
    static class Bogus {
        public String value;
    }

    @Test
    public void testAnnotationProperties() throws Exception {
        // Obtain a public field to use as the visibility probe.
        Member publicField = Bogus.class.getField("value");

        // Visibility levels that should recognise a public field as visible.
        assertTrue(Visibility.ANY.isVisible(publicField),
                "ANY should accept all access levels including public");
        assertTrue(Visibility.NON_PRIVATE.isVisible(publicField),
                "NON_PRIVATE should accept public members");
        assertTrue(Visibility.PUBLIC_ONLY.isVisible(publicField),
                "PUBLIC_ONLY should accept public members");
        assertTrue(Visibility.PROTECTED_AND_PUBLIC.isVisible(publicField),
                "PROTECTED_AND_PUBLIC should accept public members");

        // Visibility levels that should reject the field.
        assertFalse(Visibility.NONE.isVisible(publicField),
                "NONE should reject all members regardless of access level");

        // DEFAULT is a sentinel meaning "use the enclosing context's setting".
        // Its isVisible() implementation falls through to the default switch
        // branch and unconditionally returns false.
        assertFalse(Visibility.DEFAULT.isVisible(publicField),
                "DEFAULT is a delegation marker and always returns false from isVisible()");
    }
}
