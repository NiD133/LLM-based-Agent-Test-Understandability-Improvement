package org.apache.commons.lang3;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link EnumUtils} exposes a public no-argument constructor.
 *
 * <p>Although {@code EnumUtils} is a utility class whose methods are all static,
 * its constructor is intentionally public so that tools requiring a JavaBean
 * instance can instantiate it. This test simply verifies that the constructor
 * is publicly accessible and completes without error.</p>
 */
public class EnumUtilsTest_testConstructable extends AbstractLangTest {

    @Test
    void testConstructable() {
        // The public constructor must be invocable; this call would fail to
        // compile or throw if the constructor were not publicly accessible.
        new EnumUtils();
    }
}
