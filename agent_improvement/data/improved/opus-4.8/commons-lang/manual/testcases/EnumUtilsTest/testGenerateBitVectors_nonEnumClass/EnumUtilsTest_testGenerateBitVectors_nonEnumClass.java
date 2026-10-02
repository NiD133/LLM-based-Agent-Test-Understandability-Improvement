package org.apache.commons.lang3;

import static org.apache.commons.lang3.LangAssertions.assertIllegalArgumentException;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class EnumUtilsTest_testGenerateBitVectors_nonEnumClass extends AbstractLangTest {

    /**
     * {@link EnumUtils#generateBitVectors(Class, Iterable)} only accepts enum classes.
     * Passing a non-enum class (here {@code Object.class}) must be rejected with an
     * {@link IllegalArgumentException}, even when the supplied values are empty.
     *
     * <p>Raw types are used deliberately: the method's generic bound
     * {@code <E extends Enum<E>>} would otherwise reject {@code Object.class} at compile
     * time, preventing us from exercising the runtime validation.</p>
     */
    @SuppressWarnings("unchecked")
    @Test
    void testGenerateBitVectors_nonEnumClass() {
        @SuppressWarnings("rawtypes")
        final Class nonEnumClass = Object.class;
        @SuppressWarnings("rawtypes")
        final List values = new ArrayList();

        assertIllegalArgumentException(() -> EnumUtils.generateBitVectors(nonEnumClass, values));
    }
}
