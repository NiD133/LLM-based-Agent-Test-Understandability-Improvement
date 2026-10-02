/*
  Licensed to the Apache Software Foundation (ASF) under one or more
  contributor license agreements.  See the NOTICE file distributed with
  this work for additional information regarding copyright ownership.
  The ASF licenses this file to You under the Apache License, Version 2.0
  (the "License"); you may not use this file except in compliance with
  the License.  You may obtain a copy of the License at

      https://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
 */

package org.apache.commons.cli;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link PosixParser} against the shared parser contract defined in {@link AbstractParserTestCase}.
 *
 * <p>
 * The bulk of the behavior is inherited: every test in the superclass runs unchanged against a
 * {@link PosixParser} instance supplied by {@link #setUp()}. This class only needs to do two things:
 * </p>
 * <ol>
 * <li>tell the shared suite which parser to exercise, and</li>
 * <li>opt out of the handful of scenarios the POSIX parser intentionally does not support
 * (see the "Unsupported scenarios" section below). Each opt-out re-declares the inherited test as an
 * empty, {@link Disabled} method so the suite skips it rather than failing.</li>
 * </ol>
 *
 * TODO Needs a rework using JUnit parameterized tests.
 */
class PosixParserTest extends AbstractParserTestCase {

    /**
     * Runs the shared setup, then points the inherited test suite at a {@link PosixParser}.
     */
    @Override
    @SuppressWarnings("deprecation")
    @BeforeEach
    public void setUp() {
        super.setUp();
        parser = new PosixParser();
    }

    // ------------------------------------------------------------------------
    // Unsupported scenarios.
    //
    // The PosixParser does not implement the following syntaxes, so the
    // corresponding inherited tests are disabled here. Each method is
    // intentionally empty; the @Disabled reason documents why it is skipped.
    // ------------------------------------------------------------------------

    @Override
    @Test
    @Disabled("not supported by the PosixParser")
    void testAmbiguousLongWithoutEqualSingleDash() throws Exception {
        // intentionally empty: scenario unsupported by the PosixParser
    }

    @Override
    @Test
    @Disabled("not supported by the PosixParser")
    void testAmbiguousLongWithoutEqualSingleDash2() throws Exception {
        // intentionally empty: scenario unsupported by the PosixParser
    }

    @Override
    @Test
    @Disabled("not supported by the PosixParser")
    void testAmbiguousPartialLongOption4() throws Exception {
        // intentionally empty: scenario unsupported by the PosixParser
    }

    @Override
    @Test
    @Disabled("not supported by the PosixParser")
    void testDoubleDash2() throws Exception {
        // intentionally empty: scenario unsupported by the PosixParser
    }

    @Override
    @Test
    @Disabled("not supported by the PosixParser")
    void testLongWithEqualSingleDash() throws Exception {
        // intentionally empty: scenario unsupported by the PosixParser
    }

    @Override
    @Test
    @Disabled("not supported by the PosixParser")
    void testLongWithoutEqualSingleDash() throws Exception {
        // intentionally empty: scenario unsupported by the PosixParser
    }

    @Override
    @Test
    @Disabled("not supported by the PosixParser")
    void testLongWithUnexpectedArgument1() throws Exception {
        // intentionally empty: scenario unsupported by the PosixParser
    }

    @Override
    @Test
    @Disabled("not supported by the PosixParser (CLI-184)")
    void testNegativeOption() throws Exception {
        // intentionally empty: scenario unsupported by the PosixParser (CLI-184)
    }

    @Override
    @Test
    @Disabled("not supported by the PosixParser")
    void testShortWithEqual() throws Exception {
        // intentionally empty: scenario unsupported by the PosixParser
    }

    @Override
    @Test
    @Disabled("not supported by the PosixParser")
    void testUnambiguousPartialLongOption4() throws Exception {
        // intentionally empty: scenario unsupported by the PosixParser
    }
}
