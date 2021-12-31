package com.healthycoderapp;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BMICalculatorTest {

	@Test
	void should_ReturnTrue_When_DietRecommended() {
		assertTrue(BMICalculator.isDietRecommended(89.0, 1.72));
	}

}
