package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// [annotations#342] Verifies that JsonTypeInfo.Value.equals() and hashCode()
// correctly account for the writeTypeIdForDefaultImpl field.
public class JsonTypeInfoTest_testWriteTypeIdForDefaultImplEqualsAndHashCode extends AnnotationTestUtil {

    @Test
    public void testWriteTypeIdForDefaultImplEqualsAndHashCode() {
        // Two separate Value instances both configured with writeTypeIdForDefaultImpl=true
        JsonTypeInfo.Value trueFirst  = JsonTypeInfo.Value.EMPTY.withWriteTypeIdForDefaultImpl(Boolean.TRUE);
        JsonTypeInfo.Value trueSecond = JsonTypeInfo.Value.EMPTY.withWriteTypeIdForDefaultImpl(Boolean.TRUE);

        // A Value with writeTypeIdForDefaultImpl=false — distinct from the two above
        JsonTypeInfo.Value falseValue = JsonTypeInfo.Value.EMPTY.withWriteTypeIdForDefaultImpl(Boolean.FALSE);

        // A Value with writeTypeIdForDefaultImpl=null — distinct from both true and false
        JsonTypeInfo.Value nullValue  = JsonTypeInfo.Value.EMPTY.withWriteTypeIdForDefaultImpl(null);

        // Same flag value → must be equal and produce identical hash codes
        assertEquals(trueFirst, trueSecond);
        assertEquals(trueFirst.hashCode(), trueSecond.hashCode());

        // Different flag values → must not be equal
        assertNotEquals(trueFirst, falseValue);
        assertNotEquals(trueFirst, nullValue);
        assertNotEquals(falseValue, nullValue);
    }
}
