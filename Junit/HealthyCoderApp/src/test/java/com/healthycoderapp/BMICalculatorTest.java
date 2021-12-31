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
	
	@BeforeAll //해당 테스트 클래스를 초기화할 때 딱 한번 수행되는 메서드
	static void beforeAll() { //무조건 static
		System.out.println("Before all unit tests!");
	}
	
	@AfterAll //해당 테스트 클래스가 끝날 때 딱 한번 수행되는 메서드
	static void AfterAll() {
		System.out.println("After all unit tests!");
	}
	
	@Nested // 결과창의 가독성을 더욱 높이기 위해서 Nested로 메소드별로 묶어준다. 실행을 하면 메소트 이름별로 묶여서 결과창을 더욱 편하게 볼 수 있다
	class IsDietRecommendedTests{
		@ParameterizedTest(name = "weight={0}, height = {1}") // CsvSource의 값들과 이어져서 결과창에서의 가독성을 올려준다.
		@CsvFileSource(resources = "/diet-recommended-input-data.csv", numLinesToSkip = 1)//파일의 위치를 적어준다. 파일을 보면 첫 줄이 column name인 것을 알 수 있는데 이것은 test 중에 읽으면 안되는 것이므로 numLinesToSkip을 써서 넘겨준다.
		void should_ReturnTrue_When_DietRecommended(Double coderWeight, Double coderHeight) {//test 메소드를 가독성을 높이기 위해 문장으로 메소드 이름을 표현하자.
			
			//given: input value
			double weight = coderWeight;
			double height = coderHeight;
			
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
	
	@Nested
	@DisplayName("The example of DisplayName!")//간단하게 그냥 결과창에서의 이름을 바꿔준다.
	class FindCoderWithWorstBMITests{
		@Test
		@Disabled // 이 어노테이션이 붙은 메소드는 테스트가 참/거짓을 따지지 않고 그냥 skip한다.
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
		@DisabledOnOs(OS.WINDOWS) // 특정 os에서는 이 테스트가 skip되는 것이다.
		void should_ReturnCoderWithWorstBMIIn1Ms_When_CoderListHas10000Elements() {
			
			assumeTrue(BMICalculatorTest.this.environment.equals("prod")); // PROD environment, assumeTrue: true라고 가정하므로 fail안뜬다. assertTrue라고 하면 오류난다.
			//@Nested로 묶어 정리한 class 안으로 들어왔기 때문에 이제는 this가 outer class를 가리키지 않는다. 그러므로 this 앞에 outer class name을 갖다 붙여주자.
			//given
			List<Coder> coders = new ArrayList<>();
			for (int i = 0; i < 10000; i++) {
				coders.add(new Coder(1.0 + i, 10.0 + i));
			}
			//when
			Executable executable = () -> BMICalculator.findCoderWithWorstBMI(coders);
			
			//then
			assertTimeout(Duration.ofMillis(500), executable);//assertTimeout은 오직 executable에 걸리는 시간만 체크하므로 list에 대입하는 시간은 고려하지 않기 때문에 1ms로는 부족하므로 오류가 뜬다.
			//오류를 수정하기 위해서는 list 대입 시간까지 고려해서 Millis()안에 500정도는 넣어주자.
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
	


}
