/*
 * Units of Measurement Reference Implementation
 * Copyright (c) 2005-2019, Units of Measurement project.
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions
 *    and the following disclaimer in the documentation and/or other materials provided with the distribution.
 *
 * 3. Neither the name of JSR-385, Indriya nor the names of their contributors may be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED
 * AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package tech.units.indriya.unit;

import javax.measure.Unit;
import javax.measure.UnitConverter;
import javax.measure.quantity.Length;
import javax.measure.quantity.Time;

import org.junit.jupiter.api.Test;

import tech.units.indriya.unit.Units;

import static org.junit.jupiter.api.Assertions.*;

import static javax.measure.MetricPrefix.*;

/**
 *
 * @author Werner Keil
 */
public class MixedUnitTest {

  @Test
  public void testLength() {
    final Unit<Length> mixUnit =  Units.METRE.mix(CENTI(Units.METRE));
    assertEquals("m;cm", mixUnit.toString());
  }
  
  @Test
  public void testTime() {
    final Unit<Time> mixUnit =  Units.HOUR.
              mix(Units.MINUTE).mix(Units.SECOND);
    assertEquals("h;min;s", mixUnit.toString());
  }
  
  @Test
  public void testTimeOf() {
    final Unit<Time> mixTime =  MixedUnit.of(Units.DAY, Units.HOUR, Units.MINUTE);
    assertEquals("day;h;min", mixTime.toString());
  }
  
  /**
   * Test method for {@link javax.measure.Unit#getConverterTo}.
   */
  @Test
  public void testConverterToDay() {
    final Unit<Time> mixTime =  Units.HOUR.
              mix(Units.MINUTE).mix(Units.SECOND);
    assertTrue(mixTime instanceof MixedUnit);
    final MixedUnit<Time> actualMixTime = (MixedUnit<Time>)mixTime;
    assertEquals(Units.HOUR, actualMixTime.getReferenceUnit());
    UnitConverter converter = mixTime.getConverterTo(Units.DAY);
    UnitConverter converter2 = Units.HOUR.getConverterTo(Units.DAY);
    Double result = converter.convert(1d);
    Double result2 = converter2.convert(1d);
    UnitConverter converter3 = Units.DAY.mix(Units.HOUR).getConverterTo(Units.DAY);
    assertEquals(result, result2);
    assertTrue(Units.DAY.getConverterTo(Units.DAY).isIdentity());
    assertFalse(converter.isIdentity());
    assertFalse(converter2.isIdentity());
    assertTrue(converter3.isIdentity());
    //logger.log(Level.FINER(Units.DAY.getConverterTo(Units.DAY).isIdentity());
    //logger.log(Level.FINER, result.toString());
  }
}