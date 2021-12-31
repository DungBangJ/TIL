package com.healthycoderapp;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class BMICalculatorTest {

	@Test
	void should_ReturnTrue_When_DietRecommended() {//test 메소드를 가독성을 높이기 위해 문장으로 메소드 이름을 표현하자.
		
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
	
	@Test
	void should_ReturnCoderWithWorstBMI_When_CoderListNotEmpty() {
		//given
		List<Coder> coders = new ArrayList<> ();
		coders.add(new Coder(1.80, 60.0));
		coders.add(new Coder(1.82, 98.0));//얘가 가장 나쁜 BMI를 갖고있을 거라고 예상된다.
		coders.add(new Coder(1.82, 64.7));
		//when
		Coder coderWorstBMI = BMICalculator.findCoderWithWorstBMI(coders);
		//then
		assertAll( //2. assertion이 하나만 실행되는 것을 방지하기 위해 assertAll을 사용한다. 형식은 다음과 같다.
				() -> assertEquals(1.82, coderWorstBMI.getHeight()),
				() -> assertEquals(98.0, coderWorstBMI.getWeight())//1. 만약 첫번째 assertion에서 false가 나온다면, 두번째 assertion은 실행조자 하지 않는다.
				); //이렇게 asserAll로 묶어놓으면 첫번째 assertion이 오류가 나더라도, 두번째 assertion도 확인을 한다.
	}
	
	@Test
	void should_ReturnNullWorstBMICoder_When_CoderListEmpty() {
		//give
		List<Coder> coders = new ArrayList<>();
		//when
		Coder coderWorstBMI = BMICalculator.findCoderWithWorstBMI(coders);
		//then
		assertNull(coderWorstBMI);
	}
	
	@Test
	void should_ReturnCorrectBMIScoreArray_When_CoderListNotEmpty() {
		//given
		List<Coder> coders = new ArrayList<> ();
		coders.add(new Coder(1.80, 60.0));
		coders.add(new Coder(1.82, 98.0));//얘가 가장 나쁜 BMI를 갖고있을 거라고 예상된다.
		coders.add(new Coder(1.82, 64.7));
		double[] expected = {18.52, 29.59, 19.53};
		//when
		double[] bmiScores = BMICalculator.getBMIScores(coders);
		//then
		//assertEquals(expected, bmiScores); // expected와 real의 element가 같지만, object가 다르다. ([D@~~]이런거)
		//assertEquals는 object까지 같아야 오류가 나지 않는다.
		assertArrayEquals(expected, bmiScores); // element를 나눠서 확인할 수 있는 assertion을 사용하면 문제가 해결된다.
	}

}
