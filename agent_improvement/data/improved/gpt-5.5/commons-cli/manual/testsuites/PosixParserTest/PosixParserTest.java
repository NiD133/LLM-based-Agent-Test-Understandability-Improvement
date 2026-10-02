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
 * Test case for {@link PosixParser}.
 *
 * <p>The overridden test methods below document inherited parser scenarios that
 * this parser intentionally does not support.</p>
 */
class PosixParserTest extends AbstractParserTestCase {

    private static final String NOT_SUPPORTED = "not supported by the PosixParser";
    private static final String NEGATIVE_OPTION_NOT_SUPPORTED = "not supported by the PosixParser (CLI-184)";

    @Override
    @SuppressWarnings("deprecation")
    @BeforeEach
    public void setUp() {
        super.setUp();
        parser = new PosixParser();
    }

    @Override
    @Test
    @Disabled(NOT_SUPPORTED)
    void testAmbiguousLongWithoutEqualSingleDash() throws Exception {
    }

    @Override
    @Test
    @Disabled(NOT_SUPPORTED)
    void testAmbiguousLongWithoutEqualSingleDash2() throws Exception {
    }

    @Override
    @Test
    @Disabled(NOT_SUPPORTED)
    void testAmbiguousPartialLongOption4() throws Exception {
    }

    @Override
    @Test
    @Disabled(NOT_SUPPORTED)
    void testDoubleDash2() throws Exception {
    }

    @Override
    @Test
    @Disabled(NOT_SUPPORTED)
    void testLongWithEqualSingleDash() throws Exception {
    }

    @Override
    @Test
    @Disabled(NOT_SUPPORTED)
    void testLongWithoutEqualSingleDash() throws Exception {
    }

    @Override
    @Test
    @Disabled(NOT_SUPPORTED)
    void testLongWithUnexpectedArgument1() throws Exception {
    }

    @Override
    @Test
    @Disabled(NEGATIVE_OPTION_NOT_SUPPORTED)
    void testNegativeOption() throws Exception {
    }

    @Override
    @Test
    @Disabled(NOT_SUPPORTED)
    void testShortWithEqual() throws Exception {
    }

    @Override
    @Test
    @Disabled(NOT_SUPPORTED)
    void testUnambiguousPartialLongOption4() throws Exception {
    }
}
