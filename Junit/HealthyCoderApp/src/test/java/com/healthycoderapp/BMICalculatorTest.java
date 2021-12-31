package com.healthycoderapp;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class BMICalculatorTest {

	@Test
	void should_ReturnTrue_When_DietRecommended() {
		
		//given: input value
		double weight = 89.0;
		double height = 1.72;
		
		//when: 메서드의 결과를 대입하는 구문
		boolean recommended = BMICalculator.isDietRecommended(weight, height);
		
		//then: assertion - 실행되는지 아닌지
		assertTrue(recommended);
	}
	
	@Test
	void should_ReturnFalse_When_DietRecommended() {
		
		//given: input value
		double weight = 50.0;
		double height = 1.92;
		
		//when: 메서드의 결과를 대입하는 구문
		boolean recommended = BMICalculator.isDietRecommended(weight, height);
		
		//then: assertion - 실행되는지 아닌지
		assertFalse(recommended);
	}
	@Test
	void should_ThrowArithmeticException_When_DietRecommended() {
		
		//given: input value
		double weight = 50.0;
		double height = 0;
		
		//when: 메서드의 결과를 대입하는 구문
		Executable executable = () -> BMICalculator.isDietRecommended(weight, height);
		//lambda expression을 사용해서 즉시 사용할 것은 아니지만, 대신 executable을 assertThrows에 넘겨줘서
		//이것이 예외를 던지는지만 확인한다.
		//then: assertion - 실행되는지 아닌지
		assertThrows(ArithmeticException.class, executable);
	}

}
