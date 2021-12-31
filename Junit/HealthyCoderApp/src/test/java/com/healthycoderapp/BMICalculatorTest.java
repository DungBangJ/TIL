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
		
		//when: �޼����� ����� �����ϴ� ����
		boolean recommended = BMICalculator.isDietRecommended(weight, height);
		
		//then: assertion - ����Ǵ��� �ƴ���
		assertTrue(recommended);
	}
	
	@Test
	void should_ReturnFalse_When_DietRecommended() {
		
		//given: input value
		double weight = 50.0;
		double height = 1.92;
		
		//when: �޼����� ����� �����ϴ� ����
		boolean recommended = BMICalculator.isDietRecommended(weight, height);
		
		//then: assertion - ����Ǵ��� �ƴ���
		assertFalse(recommended);
	}
	@Test
	void should_ThrowArithmeticException_When_DietRecommended() {
		
		//given: input value
		double weight = 50.0;
		double height = 0;
		
		//when: �޼����� ����� �����ϴ� ����
		Executable executable = () -> BMICalculator.isDietRecommended(weight, height);
		//lambda expression�� ����ؼ� ��� ����� ���� �ƴ�����, ��� executable�� assertThrows�� �Ѱ��༭
		//�̰��� ���ܸ� ���������� Ȯ���Ѵ�.
		//then: assertion - ����Ǵ��� �ƴ���
		assertThrows(ArithmeticException.class, executable);
	}

}
