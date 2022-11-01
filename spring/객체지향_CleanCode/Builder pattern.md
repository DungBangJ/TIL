## 빌더 패턴

생성과 관련된 디자인 패턴, 동일한 프로세스를 거쳐 다양한 구성의 인스턴스를 만드는 방법

- 복잡한 객체를 생성하는 클래스와 표현하는 클래스를 분리하여, 동일한 절차에서도 서로 다른 표현을 생성하는 방법을 제공
- 생성해야 하는 객체가 Optional한 속성을 많이 가질 때 더 좋다.

## 빌더 패턴이 해결하고자 하는 문제

객체를 생성할 때 생성자만 사용할 시에 발생할 수 있는 문제를 개선하기 위해 고안되었다.

- 경우에 따라 필요 없는 파라미터들에 대해서 팩토리 클래스에 일일이 NULL 값을 넘겨줘야한다.

빌더 패턴은 필수 값에 대해서는 생성자를 통해, 선택적인 값들에 대해서는 메소드를 통해 입력받은 후 build() 메소드를 통해 최종적으로 하나의 인스턴스를 반환하는 방식이다.

## 빌더 패턴 예시

```java
/**
 * 여행 계획
 */
public class TourPlan {
    private String title; // 여행 제목
    private LocalDate startDate; // 출발 일
    private int nights; // 몇 박
    private int days; // 며칠
    private String whereToStay; // 어디서 머물지
    private List<DetailPlan> plans; // n일차 할 일
}
 
/**
 * n일차 할 일
 */
public class DetailPlan {
    private int day; // n일차
    private String plan; // 할 일
}
```

### 점층적 생성자 패턴

- 점층적 생성자 패턴을 적용하면 생성자 오버로딩을 통해 구현할 수 있다.

```java
/**
 * 기본 생성자 (필수)
 */
public TourPlan() {
}
 
/**
 * 일반적인 여행 계획 생성자
 *
 * @param title 여행 제목
 * @param startDate 출발 일
 * @param nights n박
 * @param days m일
 * @param whereToStay 머물 장소
 * @param plans n일차 할 일
 */
public TourPlan(String title, LocalDate startDate, int nights, int days,
    String whereToStay, List<DetailPlan> plans) {
    this.title = title;
    this.nights = nights;
    this.days = days;
    this.startDate = startDate;
    this.whereToStay = whereToStay;
    this.plans = plans;
}
 
/**
 * 당일치기 여행 계획 생성자
 *
 * @param title 여행 제목
 * @param startDate 출발 일
 * @param plans 1일차 할 일
 */
public TourPlan(String title, LocalDate startDate, List<DetailPlan> plans) {
    this.title = title;
    this.startDate = startDate;
    this.plans = plans;
}
```

- 위와 같이 점층적 생성자 패턴으로 구현하면 Optional한 인자에 따라 새로운 생성자를 만들거나, NULL 값으로 채워야 하는 이슈가 발생한다.

```java
// 순서를 파악이 어렵고, 가독성이 떨어진다.
new TourPlan("여행 계획", LocalDate.of(2021,12, 24), 3, 4, "호텔",
    Collections.singletonList(new DetailPlan(1, "체크인")));
    
// 생성자를 만들지 않고 당일치기 객체를 생성하면 불필요한 Null을 채워야한다.
new TourPlan("여행 계획", LocalDate.of(2021,12, 24), null, null, null,
    Collections.singletonList(new DetailPlan(1, "놀고 돌아오기")));
```

- 어떤 인자가 무엇을 의미하는지 파악이 얼려우므로, 가독성이 떨어진다.
- 당일치기 객체를 생성하면 불필요한 NULL을 채워야 한다.

### 자바 빈(Bean) 패턴

점층적 생성자 패턴의 문제점을 보완하기 위해, setter 메소드를 사용한 자바 빈 패턴이 고안됐다.

```java
TourPlan tourPlan = new TourPlan();
tourPlan.setTitle("칸쿤 여행");
tourPlan.setNights(2);
tourPlan.setDays(3);
tourPlan.setStartDate(LocalDate.of(2021, 12, 24));
tourPlan.setWhereToStay("리조트");
tourPlan.addPlan(1, "체크인 이후 짐풀기");
tourPlan.addPlan(1, "저녁 식사");
tourPlan.addPlan(2, "조식 부페에서 식사");
tourPlan.addPlan(2, "해변가 산책");
tourPlan.addPlan(2, "점심은 수영장 근처 음식점에서 먹기");
tourPlan.addPlan(2, "리조트 수영장에서 놀기");
tourPlan.addPlan(2, "저녁은 BBQ 식당에서 스테이크");
tourPlan.addPlan(3, "조식 부페에서 식사");
tourPlan.addPlan(3, "체크아웃");
```

- 순서가 자유롭기 때문에 에러 발생 가능성이 줄어들었다.
  - 개인적으로 가독성은 훨씬 좋지 못하다고 생각한다.

해결하지 못한 문제점

- 함수 호출이 인자의 수만큼 이루어지고, 객체 호출 한 번에 생성할 수 없다.
- immutable 객체를 생성할 수 없다.(setter로 값을 변경하기 때문에)

### 빌더 패턴

필요한 객체를 직접 생성하는 대신, 먼저 필수 인자들을 생성자에 전부 전달하여 빌더 객체를 만든다.

- 선택 인자는 가독성이 좋은 코드로 인자를 넘길 수 있다.
- setter가 없으므로 객체 일관성을 유지하여 불변 객체로 생성할 수 있다.

먼저 TourPlanBuilder Interface를 만들어준다.

```java
public interface TourPlanBuilder {
 
    TourPlanBuilder nightsAndDays(int nights, int days);
 
    TourPlanBuilder title(String title);
 
    TourPlanBuilder startDate(LocalDate localDate);
 
    TourPlanBuilder whereToStay(String whereToStay);
 
    TourPlanBuilder addPlan(int day, String plan);
 
    TourPlan getPlan();
 
}
```

이를 구현하는 ConcreteBuilder 클래스를 만들어준다.

```java
public class DefaultTourBuilder implements TourPlanBuilder {
 
    private String title;
 
    private int nights;
 
    private int days;
 
    private LocalDate startDate;
 
    private String whereToStay;
 
    private List<DetailPlan> plans;
 
    @Override
    public TourPlanBuilder nightsAndDays(int nights, int days) {
        this.nights = nights;
        this.days = days;
        return this;
    }
 
    @Override
    public TourPlanBuilder title(String title) {
        this.title = title;
        return this;
    }
 
    @Override
    public TourPlanBuilder startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }
 
    @Override
    public TourPlanBuilder whereToStay(String whereToStay) {
        this.whereToStay = whereToStay;
        return this;
    }
 
    @Override
    public TourPlanBuilder addPlan(int day, String plan) {
        if (this.plans == null) {
            this.plans = new ArrayList<>();
        }
 
        this.plans.add(new DetailPlan(day, plan));
        return this;
    }
 
    @Override
    public TourPlan getPlan() {
        return new TourPlan(title, startDate, days, nights, whereToStay, plans);
    }
}
```

이를 토대로 TourPlan 객체를 생성한다.

```java
return tourPlanBuilder.title("칸쿤 여행")
        .nightsAndDays(2, 3)
        .startDate(LocalDate.of(2020, 12, 9))
        .whereToStay("리조트")
        .addPlan(0, "체크인하고 짐 풀기")
        .addPlan(0, "저녁 식사")
        .getPlan();
```

문제점이 모두 보완되었다.

main

```java
public class TourDirector {
 
    private TourPlanBuilder tourPlanBuilder;
 
    public TourDirector(TourPlanBuilder tourPlanBuilder) {
        this.tourPlanBuilder = tourPlanBuilder;
    }
 
    public TourPlan cancunTrip() {
        return tourPlanBuilder.title("칸쿤 여행")
                .nightsAndDays(2, 3)
                .startDate(LocalDate.of(2020, 12, 9))
                .whereToStay("리조트")
                .addPlan(0, "체크인하고 짐 풀기")
                .addPlan(0, "저녁 식사")
                .getPlan();
    }
 
    public TourPlan longBeachTrip() {
        return tourPlanBuilder.title("롱비치")
                .startDate(LocalDate.of(2021, 7, 15))
                .getPlan();
    }

    public static void main(String[] args) {
    TourDirector director = new TourDirector(new DefaultTourBuilder());
    TourPlan tourPlan = director.cancunTrip();
}
}
```


## 참고

https://dev-youngjun.tistory.com/197