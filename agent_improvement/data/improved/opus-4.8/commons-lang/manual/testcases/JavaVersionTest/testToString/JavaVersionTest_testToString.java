package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link JavaVersion#toString()}.
 *
 * <p>{@code toString()} is overridden to return the constant's standard name
 * rather than its enum identifier. For example, {@code JAVA_1_2} (whose enum
 * name is {@code "JAVA_1_2"}) reports the standard Java version name
 * {@code "1.2"}.</p>
 */
public class JavaVersionTest_testToString extends AbstractLangTest {

    @Test
    void toStringReturnsStandardVersionName() {
        assertEquals("1.2", JavaVersion.JAVA_1_2.toString());
    }
}
