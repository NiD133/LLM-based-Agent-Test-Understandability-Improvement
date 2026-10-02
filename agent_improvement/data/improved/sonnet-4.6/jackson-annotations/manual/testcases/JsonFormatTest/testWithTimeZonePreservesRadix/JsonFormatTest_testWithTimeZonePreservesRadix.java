package com.fasterxml.jackson.annotation;

import java.util.TimeZone;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// Verifies that calling withTimeZone() on a JsonFormat.Value preserves the previously configured radix.
public class JsonFormatTest_testWithTimeZonePreservesRadix extends AnnotationTestUtil {

    @Test
    void testWithTimeZonePreservesRadix() {
        int binaryRadix = 2;
        JsonFormat.Value valueWithRadix = JsonFormat.Value.forRadix(binaryRadix);
        JsonFormat.Value valueWithTimeZone = valueWithRadix.withTimeZone(TimeZone.getTimeZone("UTC"));
        assertEquals(binaryRadix, valueWithTimeZone.getRadix());
    }
}
