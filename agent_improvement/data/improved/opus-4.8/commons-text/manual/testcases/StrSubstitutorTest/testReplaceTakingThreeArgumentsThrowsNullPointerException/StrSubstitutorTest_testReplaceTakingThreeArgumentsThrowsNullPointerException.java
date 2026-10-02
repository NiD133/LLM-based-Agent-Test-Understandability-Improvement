package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Properties;
import org.junit.jupiter.api.Test;

public class StrSubstitutorTest_testReplaceTakingThreeArgumentsThrowsNullPointerException {

    /**
     * The static {@link StrSubstitutor#replace(Object, Properties)} dereferences the source via
     * {@code source.toString()} when the supplied properties are {@code null}. A {@code null}
     * source therefore triggers a {@link NullPointerException}.
     */
    @Test
    void replaceWithNullSourceAndNullPropertiesThrowsNullPointerException() {
        assertThrows(NullPointerException.class,
                () -> StrSubstitutor.replace(null, (Properties) null));
    }
}
