
# Spring Security Login Page

```java
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

     @Override
     protected void configure(HttpSecurity http) throws Exception {
          http
                .csrf().disable()
                .authorizeRequests()
                    .antMatchers("/", "/account/register", "/css/**", "/api/**").permitAll()
                    .anyRequest().authenticated()
                    .and()
                .formLogin()
                    .loginPage("/account/login")
                    .defaultSuccessUrl("/home")
                    .permitAll()
                .logout()
                    .logoutSuccessUrl("/login")
                    
     }
}
```

- spring security로 로그인을 구현하는 코드이다.

`@EnableWebSecurity`
- 웹 보안을 활성화
- 필요한 사항
  - Spring Security가 WebSecurityConfigurer를 구현하거나 WebSecurityConfigurerAdapter를 확장한 빈으로 설정되어야 한다.
    - WebSecurityConfigurerAdapter를 확장하는 방법이 자주 쓰인다.

`configure(HttpSecurity http)`
- WebSecurityConfigurerAdapter의 configure() 메서드를 오버라이딩하고 동작을 설정하는 것으로 웹 보안을 설정할 수 있다.
- 인터셉터로 요청을 안전하게 보호하는 방법을 설정하기 위한 오버라이딩

`authorizeRequests()`
- 요청에 의한 보안 검사를 시작하는 메서드
- `antMatchers("/", "/account/register", "/css/**", "/api/**").permitAll().anyRequest().authenticated()`
  - 이 코드를 `authorizeRequests()` 뒤에 작성한다면 특정한 경로(`antMatchers()`)에 모든 사용자가 접근이 허용되고(`permitAll()`) 모든 요청에  보안 검사를 한다.(`anyRequest().authenticated()` )

`formLogin()`
- 보안 검증은 formLogin 방식으로 한다는 뜻이다.
  - 로그인 페이지(`loginPage("/account/login")`)에서 로그인을 실시하고 성공한다면 기본 URL(`defaultSuccessUrl("/home")`)로 이동시킨다.

특정 URL에 접근 권한을 다음과 같이 설정할 수 있다.
```java
http.authorizeRequests()
                .antMatchers("/guest/**").permitAll()
                .antMatchers("/manager/**").hasRole("MANAGER")
                .antMatchers("/admin/**").hasRole("ADMIN");
```
- `antMatchers("/guest/**").permitAll()`
  - 모든 접근 허용
- `antMatchers("/manager/**").hasRole("MANAGER")`
  - `MANAGER` role만 접근 허용
- `antMatchers("/admin/**").hasRole("ADMIN")`
  - `ADMIN` role만 접근 허용


```java
http.exceptionHandling().accessDeniedPage("/accessDenied");
```
- 경로에 접근하는데 예외가 발생다면 특정 경로로 이동시킨다.

`logout().logoutSuccessUrl("/login")`
- 로그아웃에 성공을 하면 특정 URL로 이동시킨다.