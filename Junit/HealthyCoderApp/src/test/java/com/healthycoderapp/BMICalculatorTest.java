package com.healthycoderapp;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

class BMICalculatorTest {
	
	private String environment = "dev";
	
	@BeforeAll //�ش� �׽�Ʈ Ŭ������ �ʱ�ȭ�� �� �� �ѹ� ����Ǵ� �޼���
	static void beforeAll() { //������ static
		System.out.println("Before all unit tests!");
	}
	
	@AfterAll //�ش� �׽�Ʈ Ŭ������ ���� �� �� �ѹ� ����Ǵ� �޼���
	static void AfterAll() {
		System.out.println("After all unit tests!");
	}
	
	@Nested // ���â�� �������� ���� ���̱� ���ؼ� Nested�� �޼ҵ庰�� �����ش�. ������ �ϸ� �޼�Ʈ �̸����� ������ ���â�� ���� ���ϰ� �� �� �ִ�
	class IsDietRecommendedTests{
		@ParameterizedTest(name = "weight={0}, height = {1}") // CsvSource�� ����� �̾����� ���â������ �������� �÷��ش�.
		@CsvFileSource(resources = "/diet-recommended-input-data.csv", numLinesToSkip = 1)//������ ��ġ�� �����ش�. ������ ���� ù ���� column name�� ���� �� �� �ִµ� �̰��� test �߿� ������ �ȵǴ� ���̹Ƿ� numLinesToSkip�� �Ἥ �Ѱ��ش�.
		void should_ReturnTrue_When_DietRecommended(Double coderWeight, Double coderHeight) {//test �޼ҵ带 �������� ���̱� ���� �������� �޼ҵ� �̸��� ǥ������.
			
			//given: input value
			double weight = coderWeight;
			double height = coderHeight;
			
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
	
	@Nested
	@DisplayName("The example of DisplayName!")//�����ϰ� �׳� ���â������ �̸��� �ٲ��ش�.
	class FindCoderWithWorstBMITests{
		@Test
		@Disabled // �� ������̼��� ���� �޼ҵ�� �׽�Ʈ�� ��/������ ������ �ʰ� �׳� skip�Ѵ�.
		void should_ReturnCoderWithWorstBMI_When_CoderListNotEmpty() {
			//given
			List<Coder> coders = new ArrayList<> ();
			coders.add(new Coder(1.80, 60.0));
			coders.add(new Coder(1.82, 98.0));//�갡 ���� ���� BMI�� �������� �Ŷ�� ����ȴ�.
			coders.add(new Coder(1.82, 64.7));
			//when
			Coder coderWorstBMI = BMICalculator.findCoderWithWorstBMI(coders);
			//then
			assertAll( //2. assertion�� �ϳ��� ����Ǵ� ���� �����ϱ� ���� assertAll�� ����Ѵ�. ������ ������ ����.
					() -> assertEquals(1.82, coderWorstBMI.getHeight()),
					() -> assertEquals(98.0, coderWorstBMI.getWeight())//1. ���� ù��° assertion���� false�� ���´ٸ�, �ι�° assertion�� �������� ���� �ʴ´�.
					); //�̷��� asserAll�� ��������� ù��° assertion�� ������ ������, �ι�° assertion�� Ȯ���� �Ѵ�.
		}
		
		@Test
		@DisabledOnOs(OS.WINDOWS) // Ư�� os������ �� �׽�Ʈ�� skip�Ǵ� ���̴�.
		void should_ReturnCoderWithWorstBMIIn1Ms_When_CoderListHas10000Elements() {
			
			assumeTrue(BMICalculatorTest.this.environment.equals("prod")); // PROD environment, assumeTrue: true��� �����ϹǷ� fail�ȶ��. assertTrue��� �ϸ� ��������.
			//@Nested�� ���� ������ class ������ ���Ա� ������ ������ this�� outer class�� ����Ű�� �ʴ´�. �׷��Ƿ� this �տ� outer class name�� ���� �ٿ�����.
			//given
			List<Coder> coders = new ArrayList<>();
			for (int i = 0; i < 10000; i++) {
				coders.add(new Coder(1.0 + i, 10.0 + i));
			}
			//when
			Executable executable = () -> BMICalculator.findCoderWithWorstBMI(coders);
			
			//then
			assertTimeout(Duration.ofMillis(500), executable);//assertTimeout�� ���� executable�� �ɸ��� �ð��� üũ�ϹǷ� list�� �����ϴ� �ð��� ������� �ʱ� ������ 1ms�δ� �����ϹǷ� ������ ���.
			//������ �����ϱ� ���ؼ��� list ���� �ð����� ����ؼ� Millis()�ȿ� 500������ �־�����.
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
	}
	
	@Nested
	class GetBMIScoresTests{
		@Test
		void should_ReturnCorrectBMIScoreArray_When_CoderListNotEmpty() {
			//given
			List<Coder> coders = new ArrayList<> ();
			coders.add(new Coder(1.80, 60.0));
			coders.add(new Coder(1.82, 98.0));//�갡 ���� ���� BMI�� �������� �Ŷ�� ����ȴ�.
			coders.add(new Coder(1.82, 64.7));
			double[] expected = {18.52, 29.59, 19.53};
			//when
			double[] bmiScores = BMICalculator.getBMIScores(coders);
			//then
			//assertEquals(expected, bmiScores); // expected�� real�� element�� ������, object�� �ٸ���. ([D@~~]�̷���)
			//assertEquals�� object���� ���ƾ� ������ ���� �ʴ´�.
			assertArrayEquals(expected, bmiScores); // element�� ������ Ȯ���� �� �ִ� assertion�� ����ϸ� ������ �ذ�ȴ�.
		}
	}
	


}
