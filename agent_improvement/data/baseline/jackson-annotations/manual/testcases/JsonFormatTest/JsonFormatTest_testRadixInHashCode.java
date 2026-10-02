package com.fasterxml.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonFormat.Feature;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import org.junit.jupiter.api.Test;
import static com.fasterxml.jackson.annotation.JsonFormat.DEFAULT_RADIX;
import static org.junit.jupiter.api.Assertions.*;

public class JsonFormatTest_testRadixInHashCode extends AnnotationTestUtil {

    private final JsonFormat.Value EMPTY = JsonFormat.Value.empty();

    @Test
    void testRadixInHashCode() {
        JsonFormat.Value v1 = JsonFormat.Value.forRadix(2);
        JsonFormat.Value v2 = JsonFormat.Value.forRadix(16);
        // Not equal, so hashCodes should (very likely) differ
        assertNotEquals(v1, v2);
        assertNotEquals(v1.hashCode(), v2.hashCode());
    }
}
