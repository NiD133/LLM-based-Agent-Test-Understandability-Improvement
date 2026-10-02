package com.fasterxml.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonFormat.Feature;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import org.junit.jupiter.api.Test;
import static com.fasterxml.jackson.annotation.JsonFormat.DEFAULT_RADIX;
import static org.junit.jupiter.api.Assertions.*;

public class JsonFormatTest_testWithTimeZonePreservesRadix extends AnnotationTestUtil {

    private final JsonFormat.Value EMPTY = JsonFormat.Value.empty();

    @Test
    void testWithTimeZonePreservesRadix() {
        int binaryRadix = 2;
        JsonFormat.Value v = JsonFormat.Value.forRadix(binaryRadix);
        JsonFormat.Value withTz = v.withTimeZone(java.util.TimeZone.getTimeZone("UTC"));
        assertEquals(binaryRadix, withTz.getRadix());
    }
}
