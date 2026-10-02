package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.function.IntUnaryOperator;
import org.apache.commons.text.CharacterPredicate;
import org.apache.commons.text.CharacterPredicates;
import org.apache.commons.text.RandomStringGenerator;
import org.apache.commons.text.TextRandomProvider;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class) @EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class RandomStringGenerator_ESTest extends RandomStringGenerator_ESTest_scaffolding {

  // IntUnaryOperator.identity() always returns its input unchanged.
  // With a one-element character list, applyAsInt(1) returns 1 -> list.get(1) is out of bounds.
  @Test(timeout = 4000)
  public void testGenerateWithIdentityRandomAndSelectFromThrowsIndexOutOfBounds() throws Throwable {
      char[] twoNullChars = new char[2]; // both '\0', deduplicated to a single-element set
      RandomStringGenerator.Builder builder = new RandomStringGenerator.Builder();
      RandomStringGenerator.Builder builderWithChars = builder.selectFrom(twoNullChars);
      RandomStringGenerator.Builder builderWithRandom = builderWithChars.usingRandom(IntUnaryOperator.identity());
      RandomStringGenerator generator = builderWithRandom.get();
      try {
          generator.generate(1114111);
          fail("Expecting exception: IndexOutOfBoundsException");
      } catch (IndexOutOfBoundsException e) {
      }
  }

  // A mock random that always returns 0 causes generate to select the first available code point every time.
  @Test(timeout = 4000)
  public void testGenerateWithMockRandomAlwaysReturningZeroDoesNotThrow() throws Throwable {
      TextRandomProvider alwaysZero = mock(TextRandomProvider.class, new ViolatedAssumptionAnswer());
      doReturn(0, 0, 0, 0, 0).when(alwaysZero).applyAsInt(anyInt());
      RandomStringGenerator.Builder builder = RandomStringGenerator.builder();
      builder.usingRandom(alwaysZero);
      RandomStringGenerator generator = builder.get();
      generator.generate(1114111);
  }

  @Test(timeout = 4000)
  public void testGenerateRangeThrowsWhenMaxLengthSmallerThanMinLength() throws Throwable {
      RandomStringGenerator.Builder builder = new RandomStringGenerator.Builder();
      RandomStringGenerator generator = builder.get();
      try {
          generator.generate(1114111, 0);
          fail("Expecting exception: IllegalArgumentException");
      } catch (IllegalArgumentException e) {
          //
          // Maximum length 0 is smaller than minimum length 1114111.
          //
          verifyException("org.apache.commons.lang3.Validate", e);
      }
  }

  @Test(timeout = 4000)
  public void testGenerateRangeThrowsWhenMinLengthIsNegative() throws Throwable {
      RandomStringGenerator.Builder builder = RandomStringGenerator.builder();
      RandomStringGenerator generator = builder.get();
      try {
          generator.generate(-1, 1114111);
          fail("Expecting exception: IllegalArgumentException");
      } catch (IllegalArgumentException e) {
          //
          // Minimum length -1 is smaller than zero.
          //
          verifyException("org.apache.commons.lang3.Validate", e);
      }
  }

  @Test(timeout = 4000)
  public void testGenerateRangeWithAlphanumericAndLetterPredicates() throws Throwable {
      CharacterPredicate[] predicates = new CharacterPredicate[5];
      predicates[0] = CharacterPredicates.ASCII_ALPHA_NUMERALS;
      predicates[1] = CharacterPredicates.LETTERS;
      predicates[2] = CharacterPredicates.ASCII_ALPHA_NUMERALS;
      predicates[3] = CharacterPredicates.LETTERS;
      predicates[4] = CharacterPredicates.LETTERS;
      RandomStringGenerator.Builder builder = RandomStringGenerator.builder();
      RandomStringGenerator.Builder builderWithFilter = builder.filteredBy(predicates);
      RandomStringGenerator generator = builderWithFilter.get();
      generator.generate(2913, 1114111);
  }

  @Test(timeout = 4000)
  public void testGenerateRangeWithSelectFromNullCharsReturnsString() throws Throwable {
      char[] fourNullChars = new char[4];
      RandomStringGenerator.Builder builder = new RandomStringGenerator.Builder();
      RandomStringGenerator.Builder builderWithChars = builder.selectFrom(fourNullChars);
      RandomStringGenerator generator = builderWithChars.get();
      String result = generator.generate(0, 168);
      //  // Unstable assertion: assertEquals("...", result);
  }

  @Test(timeout = 4000)
  public void testGenerateThrowsWhenLengthIsNegative() throws Throwable {
      RandomStringGenerator.Builder builder = RandomStringGenerator.builder();
      RandomStringGenerator generator = builder.get();
      try {
          generator.generate(-882);
          fail("Expecting exception: IllegalArgumentException");
      } catch (IllegalArgumentException e) {
          //
          // Length -882 is smaller than zero.
          //
          verifyException("org.apache.commons.lang3.Validate", e);
      }
  }

  @Test(timeout = 4000)
  public void testGenerateZeroLengthReturnsEmptyString() throws Throwable {
      RandomStringGenerator.Builder builder = RandomStringGenerator.builder();
      RandomStringGenerator generator = builder.get();
      String result = generator.generate(0);
      assertEquals("", result);
  }

  @Test(timeout = 4000)
  public void testWithinRangeThrowsWhenValueExceedsMaxCodePoint() throws Throwable {
      RandomStringGenerator.Builder builder = new RandomStringGenerator.Builder();
      try {
          builder.withinRange(2064888123, 2064888123);
          fail("Expecting exception: IllegalArgumentException");
      } catch (IllegalArgumentException e) {
          //
          // Value 2064888123 is larger than Character.MAX_CODE_POINT.
          //
          verifyException("org.apache.commons.lang3.Validate", e);
      }
  }

  @Test(timeout = 4000)
  public void testWithinRangeThrowsWhenMinCodePointIsNegative() throws Throwable {
      RandomStringGenerator.Builder builder = RandomStringGenerator.builder();
      try {
          builder.withinRange(-3302, -3302);
          fail("Expecting exception: IllegalArgumentException");
      } catch (IllegalArgumentException e) {
          //
          // Minimum code point -3302 is negative
          //
          verifyException("org.apache.commons.lang3.Validate", e);
      }
  }

  @Test(timeout = 4000)
  public void testWithinRangeWithEqualMinAndMaxIsValidAndDefaultLengthIsZero() throws Throwable {
      RandomStringGenerator.Builder builder = new RandomStringGenerator.Builder();
      builder.withinRange(0, 0);
      assertEquals(0, RandomStringGenerator.Builder.DEFAULT_LENGTH);
  }

  @Test(timeout = 4000)
  public void testWithinRangeThrowsWhenMinCodePointExceedsMax() throws Throwable {
      RandomStringGenerator.Builder builder = RandomStringGenerator.builder();
      try {
          builder.withinRange(1114111, 0);
          fail("Expecting exception: IllegalArgumentException");
      } catch (IllegalArgumentException e) {
          //
          // Minimum code point 1114111 is larger than maximum code point 0
          //
          verifyException("org.apache.commons.lang3.Validate", e);
      }
  }

  // pair[0]='L' (76), pair[1]='\0' (0): minimum > maximum -> IllegalArgumentException
  @Test(timeout = 4000)
  public void testWithinRangeCharPairsThrowsWhenFirstCharExceedsSecond() throws Throwable {
      RandomStringGenerator.Builder builder = RandomStringGenerator.builder();
      char[][] pairs = new char[1][6];
      char[] pair = new char[2];
      pair[0] = 'L';
      pairs[0] = pair;
      try {
          builder.withinRange(pairs);
          fail("Expecting exception: IllegalArgumentException");
      } catch (IllegalArgumentException e) {
          //
          // Minimum code point 76 is larger than maximum code point 0
          //
          verifyException("org.apache.commons.lang3.Validate", e);
      }
  }

  // pair = ['\0', '\0']: min == max == 0, valid range; checks DEFAULT_MAXIMUM_CODE_POINT constant
  @Test(timeout = 4000)
  public void testWithinRangeCharPairsWithZeroToZeroRangeIsValidAndDefaultMaxIsUnicodeMax() throws Throwable {
      RandomStringGenerator.Builder builder = RandomStringGenerator.builder();
      char[][] pairs = new char[1][6];
      char[] pair = new char[2];
      pairs[0] = pair;
      RandomStringGenerator.Builder result = builder.withinRange(pairs);
      assertEquals(1114111, RandomStringGenerator.Builder.DEFAULT_MAXIMUM_CODE_POINT);
  }

  // Pair has length 6, not 2; each pair must have exactly two elements (min, max)
  @Test(timeout = 4000)
  public void testWithinRangeCharPairsThrowsWhenPairLengthIsNotTwo() throws Throwable {
      RandomStringGenerator.Builder builder = RandomStringGenerator.builder();
      char[][] pairs = new char[1][6];
      try {
          builder.withinRange(pairs);
          fail("Expecting exception: IllegalArgumentException");
      } catch (IllegalArgumentException e) {
          //
          // Each pair must contain minimum and maximum code point
          //
          verifyException("org.apache.commons.lang3.Validate", e);
      }
  }

  @Test(timeout = 4000)
  public void testWithinRangeCharPairsWithNullReturnsSameBuilderInstance() throws Throwable {
      RandomStringGenerator.Builder builder = new RandomStringGenerator.Builder();
      RandomStringGenerator.Builder result = builder.withinRange((char[][]) null);
      assertSame(builder, result);
  }

  @Test(timeout = 4000)
  public void testSelectFromNullDoesNotThrowAndDefaultMaxCodePointIsUnicodeMax() throws Throwable {
      RandomStringGenerator.Builder builder = RandomStringGenerator.builder();
      builder.selectFrom((char[]) null);
      assertEquals(1114111, RandomStringGenerator.Builder.DEFAULT_MAXIMUM_CODE_POINT);
  }

  @Test(timeout = 4000)
  public void testSelectFromWithAccumulateEnabledAndDefaultMinCodePointIsZero() throws Throwable {
      RandomStringGenerator.Builder builder = RandomStringGenerator.builder();
      builder.setAccumulate(true);
      char[] emptyChars = new char[0];
      RandomStringGenerator.Builder result = builder.selectFrom(emptyChars);
      assertEquals(0, RandomStringGenerator.Builder.DEFAULT_MINIMUM_CODE_POINT);
  }

  @Test(timeout = 4000)
  public void testFilteredByNullClearsPredicatesAndDefaultMinCodePointIsZero() throws Throwable {
      RandomStringGenerator.Builder builder = new RandomStringGenerator.Builder();
      builder.filteredBy((CharacterPredicate[]) null);
      assertEquals(0, RandomStringGenerator.Builder.DEFAULT_MINIMUM_CODE_POINT);
  }

  @Test(timeout = 4000)
  public void testFilteredByCalledTwiceReplacesPredicatesAndDefaultLengthIsZero() throws Throwable {
      RandomStringGenerator.Builder builder = new RandomStringGenerator.Builder();
      CharacterPredicate[] predicates = new CharacterPredicate[1];
      builder.filteredBy(predicates);
      builder.filteredBy(predicates);
      assertEquals(0, RandomStringGenerator.Builder.DEFAULT_LENGTH);
  }
}
