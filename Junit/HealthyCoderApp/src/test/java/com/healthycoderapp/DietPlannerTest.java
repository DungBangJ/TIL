package com.healthycoderapp;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DietPlannerTest {
	
	private DietPlanner dietPlanner;
	
	@BeforeEach //BeforeEach가 붙은 메서드는 테스트 메서드 실행 이전에 수행
	void setup() {
		this.dietPlanner = new DietPlanner(20, 30, 50);
	}
	
	@AfterEach //AfterEach가 붙은 메서드는 테스트 메서드 실행 이후에 수행
	void afterEach() {
		System.out.println("A unit test was finished!");
	}
	

	@Test
	void test() {
		//given
		Coder coder = new Coder(1.82, 75.0, 26, Gender.MALE);
		DietPlan expected = new DietPlan(2202, 110, 73, 275);
		
		//when
		DietPlan actual = dietPlanner.calculateDiet(coder);
		
		//then
		//assertEquals(expected, actual); // 아예 같은 객체여야 성립된다.
		assertAll(
				() -> assertEquals(expected.getCalories(), actual.getCalories()),
				() -> assertEquals(expected.getProtein(), actual.getProtein()),
				() -> assertEquals(expected.getFat(), actual.getFat()),
				() -> assertEquals(expected.getCarbohydrate(), actual.getCarbohydrate())
			);
	}

}
