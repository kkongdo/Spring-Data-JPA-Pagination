## JPA와 Hibernate
### JPA
>
**JPA**(Java Persistence API)는 자바 진영의 ORM(Object-Relational Mapping) 기술 표준으로 채택된 인터페이스(Interface)의 모음이다.

여기서 우리는 'ORM'이라는 단어를 간단하게 살펴보면 객체 지향 언어에서 의미하는 ***객체(클래스)와 RDB의 테이블을 자동으로 매핑(Mapping)하는 방법***을 의미한다.

객체 지향 프로그래밍에서는 데이터를 객체 형태로 다루고, RDB는 데이터를 테이블 형태로 저장한다. 이 두 시스템 간에는 다음과 같은 불일치가 존재한다.

#### Object와 RDB의 존재하는 불일치
- **상속** : 객체 지향 언어에서는 상속을 통해 계층 구조를 만들 수 있지만, RDB에는 상속 개념이 없다.
- **연관 관계** : 객체 간의 연관 관계(1:1, 1,N 등)를 표현하는 방법이 데이터베이스의 외래 키와는 다르다.
- **식별자** : 객체는 주로 참조를 통해 식별되지만, 데이터베이스는 주로 기본 키를 통해 식별된다.
- **데이터 타입** : 객체와 데이터베이스의 데이터 타입이 다를 수 있다.

=> <u>즉. 이러한 둘 사이간의 불일치성을 해결하는 역할이 바로 **ORM**이다.</u>

JPA는 이러한 ORM을 구현하기 위한 표준 인터페이스를 제공하며, JPA를 구현한 대표적인 구현체로는 다음과 같이 세가지들이 있다.

#### JPA의 구현체의 종류
- EclipseLink
- ***Hibernate*** 
(가장 널리 사용되는 JPA 구현체로, 다양한 기능과 높은 호환성을 자랑한다.)
- DataNucleus


### Hibernate
Hibernate(하이버네이트)는 자바의 ORM 프레임워크로서, JPA가 정의하는 인터페이스를 구현하고 있는 JPA 구현체 중 하나이다.

Hibernate는 객체와 관계형 데이터베이스 간의 매핑을 관리하며, 데이터베이스와의 상호작용을 간소화한다. 또한, Query문들을 콘솔창에서 확인하고 싶으면, 다음과 같은 설정을 application.properties 파일에 추가하면 된다.
```java
// 쿼리 로그 Show를 true로 설정
// 실행되는 SQL 쿼리를 콘솔에 출력한다.
spring.jpa.show-sql=true 

// SQL문을 정렬하여 출력
// 즉, 출력되는 SQL 쿼리를 보기 쉽게 포맷팅한다.
spring.jpa.properties.hibernate.format_sql=true 


// 바인딩되는 파라미터 값을 출력
logging.level.org.hibernate.type.descriptor.sql=trace
```
![](https://velog.velcdn.com/images/kkong_do/post/bfa7b7df-cc60-4cf7-bfa9-18991e9d0168/image.png)

***

## Spring Data JPA

JPA를 편리하게 사용할 수 있도록 지원하는 스프링 하위 프로젝트 중 하나이다. CRUD 처리에 필요한 인터페이스를 제공하고 Hibernate의 엔티티 매니저를 직접 다루지 않고도 Repository를 정의해 사용함으써 ***스프링이 적합한 쿼리를 동적으로 생성하는 방식으로 DB를 조작한다.***
![](https://velog.velcdn.com/images/kkong_do/post/f29e1c0a-64d0-4304-aeea-7c1edd6eeb25/image.png)


### Spring Data JPA의 주요 기능

이번 글에서 다루는 Spring Data JPA 기능은 다음과 같다.
- JPQL(Java Persistence Query Language)
- Query Method
- 정렬과 페이징 처리
- @Query annotation
- QueryDSL

***

### JPQL(Java Persistence Query Language)
>
**JPA에서 사용할 수 있는 쿼리**를 의미한다.

JPQL의 문법이 SQL 문법과 매우 비슷하여 데이터베이스 쿼리에 익숙한 분들이 어렵지 않게 사용할  수 있다. JPQL은 엔티티 객체를 대상으로 수행하는 쿼리이므로 매핑된 엔티티의 이름과 필드의 이름을 사용한다.

```java
// JPQL Basic Example
SELECT P FROM PRODUCT P WHERE P.NUMBER = ?1;

// PRODUCT는 엔티티 타입, NUMBER는 엔티티 객체의 속성을 의미한다.
// 1은 첫 번째 파라미터 의미한다. (여기서는 매개변수로 받은 number의 값이 들어갈 것이다.)
```

### Query Method

>
리포지터리에서 기본으로 제공되는 메서드외 별도의 메서드를 정의해야 하는 경우 쿼리문을 작성하기 위해 사용되는 것이 바로 ***Query Method***이다.

우리가 사용하는 Repository 인터페이스는 기본적으로 JpaRepository를 구현받아서 다양한 CRUD 메서드를 제공한다. Query Method는 크게 동작을 결정하는 **Subject(주제) + Predicate(동작)**으로 구분한다. 

'findBy...'와 'getBy...' 등이 Subject(주제)를 나타내며, ***By는 Predicate(동작)의 시작을 나타내어 구분자의 역할***을 한다.

```java
// 리포지터리의 쿼리 메서드 생성 예
List<Person> findByLastNameAndEmail(String lastName, String Email);

// 메서드 명에 들어가있는 By 이후의 LastName과 Email이 메서드의 매개변수로 들어가 있다.
```

서술어에 들어갈 엔티티의 속성 식은 엔티티에서 관리하는 필드만 참조할 수 있다.

#### Query Method의 주제 키워드
>
동사와 By 사이의 도메인이 표현될 수 있지만 repository에서 이미 설정한 후이기 때문에 생략 가능하다.

1. **조회하는 기능**을 수행하는 키워드
```java
- findBy...
- readBy...
- getBy...
- queryBy...
- searchBy...
- streamBy...

```
- return type으로 Collection이나 Stream에 속한 하위 타입을 설정할 수 있다.

2. **특정 데이터 존재 유무**를 확인하는 키워드
```java
- existsBy...

// Example
boolean existsByNumber(Long number);
```
- return type으로 boolean을 사용한다.

3. **삭제 쿼리를 수행**하는 키워드
```java
- deleteBy...
- removeBy...

// Example
void deleteByNumber(Long number);
long removeByName(String name);
```
- return type으로 void나 정수형(삭제된 갯수)을 반환한다.

4. **쿼리를 통해 조회된 결과값의 개수를 제한**하는 키워드
```java
- findFirst숫자By...
- findTop숫자By...

// Example
List<Product> findFirst5ByName(String name);
List<Product> findTop10ByName(String name);
```
- 두 키워드는 동일한 동작을 수행한다.
- 주제와 By사이에 위치하며, 한 번의 동작으로 여러 건을 조회할 때 사용한다.
- 하나의 건으로 조회하는 것은 숫자를 생략하면 된다.


#### Query Method의 조건자 키워드
>
Spring Data JPA의 **메소드 이름 기반 쿼리 작성 방식에서 조건식을 뜻하는 단어를 포함해서 사용되는 키워드**를 의미한다.

- 책에서의 애매한 표현 : _'JPQL의 서술어 부분에서 사용할 수 있는 키워드'_라고 나와있는데 JPQL의 서술어 부분에서 사용할 수 있는 키워드는 JPQL 쿼리의 WHERE 절에서 사용할 수 있는 조건자를 의미한다.


1. **값의 일치를 조건으로 사용**하는 조건자 키워드
```java
- ..IS

// Example
Product findByNumberIS(Long number);
Product findByNumberEquals(Long number);
```

2. **값의 불일치를 조건으로 사용**하는 조건자 키워드
```java
- ..(Is)Not

// Example
Product findByNumberIsNot(Long number);
Product findByNumberNot(Long number);
```
- Is 생략하고 Not키워드만 사용할 수 있다.

3. **값이 Null인지 검사**하는 조건자 키워드
```java
- ..(Is)Null
- ..(Is)NotNull

// Example
List<Product> findByUpdatedAtNull();
List<Product> findByUpdatedAtIsNull();
List<Product> findByUpdatedAtNotNull();
List<Product> findByUpdatedAtIsNotNull();
```
4. **boolean타입으로 지정된 컬럼값을 확인**하는 키워드
```java
- ..(Is)True
- ..(Is)False

// Example
Product findByActiveTrue();
Product findByActiveIsTrue();
Product findByActiveFalse();
Product findByActiveIsFalse();
```

5. **여러 조건을 묶을 때 사용**하는 키워드
```java
- And
- Or

// Example
Product findByNumberAndName(Long number, String name);
Product findByNumberOrName(Long number, String name);
```

6. **숫자나 datetime 컬럼을 대상으로 하는 비교 연산 시 사용**하는 키워드
```java
- ..(Is)GreaterThan
- ..(Is)LessThan
- ..(Is)Between

// Example
List<Product> findByPriceGreaterThan(Long price);
List<Product> findByPriceLessThan(Long price);
List<Product> findByPriceBetween(Long startPrice, Long endPrice);List<Product> findByPriceLessThanEqual(Long startPrice, Long endPrice);
```
- `GreaterThan`과 `LessThan` 키워드는 비교 대상에 대한 초과/미만의 개념으로 비교 연산을 수행한다.
- 경계값까지 포함시키고 싶으면 `Equal` 키워드를 추가하면 된다.

7. **컬럼값에서 일부 일치 여부를 확인**하는 키워드
```java
- ..(Is)StartingWith
- ..(Is)EndingWith
- ..(Is)Containing
- ..(Is)Like

// Example
List<Customer> findByFirstNameStartingWith(String prefix);
List<Customer> findByLastNameEndingWith(String suffix);
List<Customer> findByFirstNameContaining(String infix);
List<Customer> findByLastNameLike(String pattern);

// Service단에서 사용되는 예
List<Customer> customers = customerRepository.findByFirstNameStartingWith("Jo");
// John, Johnny, Joanna
List<Customer> customers = customerRepository.findByLastNameEndingWith("son");
// Johnson, Jackson, Harrison
List<Customer> customers = customerRepository.findByFirstNameContaining("ann");
// Joanna, Annabelle, Hannah
List<Customer> customers = customerRepository.findByLastNameLike("%son%");
// Johnson, Jackson, Harrison
```
- SQL 쿼리문에서 값의 일부를 포함하는 값을 추출할 때 사용하는 '%'와 동일한 역할을 하는 키워드이다.
- 자동으로 생성되는 SQL문을 보면 StartingWith 키워드는 문자열 앞, EndingWith 키워드는 문자열 끝, Containing 키워드는 문자열의 양끝에 '%'가 자동으로 배치된다.
- 하지만! Like키워드는 명시적으로 '%'를 입력해야 한다.

***

### 정렬과 페이징 처리

#### Query Method를 통한 정렬 처리
```java
// 쿼리 메서드의 정렬 처리
// Asc : 오름차순, Desc : 내림차순
List<Product> findByNameOrderByNumberAsc(String name);
List<Product> findByNameOrderByNumberDesc(String name);
```
- 기본 쿼리 메서드(findByName)를 작성한 후 `OrderBy` 키워드를 삽입하여 정렬하고자 하는 컬럼(Number)과 오름차순/내림차순(Asc 혹은 Desc)을 설정하면 정렬이 수행된다.
- `List<Product> findByNameOrderByNumberAsc(String name);` 의 Query Method를 해석하면 **'상품 정보를 이름으로 검색한 후 상품 번호로 오름차순 정렬을 수행한다.'**는 의미이다.
- `List<Product> findByNameOrderByNumberDesc(String name);` 의 Query Method를 해석하면 **'상품 정보를 이름으로 검색한 후 상품 번호의 내림차순 정렬을 수행한다.'**는 의미이다.

```java
// 쿼리 메서드에서 여러 정렬 기준 사용
// And를 붙이지 않음
List<Prodcut> findByNameOrderByPriceAscStockDesc(String name);
List<Product> findByNameOrderByPrice(String name);
```
```java
// List<Prodcut> findByNameOrderByPriceAscStockDesc(String name)의 hibernate
Hibernate: 
    select
        product0_.id as id1_0_,
        product0_.name as name2_0_,
        product0_.price as price3_0_,
        product0_.stock as stock4_0_
    from
        product product0_
    where
        product0_.name=?
    order by
        product0_.price asc,
        product0_.stock desc

```
- 위처럼 정렬 키워드를 삽입해서 정렬을 수행하는 것도 가능하지만 메서드의 이름이 길어질수록 가독성이 떨어지는 문제가 발생할 수 있다.
- 그렇기 때문에 ** Sort 객체를 매개변수로 주어 가독성을 보완하는 방법** 또한 있다.

```java
List<Product> findByName(String name, Sort sort);
```
```java
// Service단에서 indByName을 호출하는 예시
productRepository.findByName("연필", Sort.by(Order.asc("price")));
productRepository.findByName("볼펜", Sort.by(Order.asc("price"), Order.desc("stock")));
```

- Sort 클래스는 내부 클래스로 정의되어 있는 Order 객체를 활용해 정렬 기준을 세우고, Order 객체 내 있는 `asc()` 메서드와 `desc()` 메서드를 활용하여 오름차순과 내림차순을 지정한다.
- 여러 정렬 기준을 사용할 경우에는 `,(콤마)`를 이용하여 구분한다.
- Sort 클래스를 호출한 위치에서 정렬 기준이 길어져 가독성이 떨어지는 문제점이 발생할 수 있다.
- 그렇기에 호출하는 부분에서 하나의 메서드로 분리해서 Query method를 호출하는 코드를 작성하는 방법 또한 있다.

```java
class ProductService{
...
productRepository.findByName("연필", getSort());
...
private Sort getSort(){ // 필요한 쿼리메서드를 하나의 메서드로 분리하여 코드를 재활용 츠견을 높였다.
	return Sort.by(
    	Order.Asc("price"),
        Order.Desc("stock")
    );
}
```

#### 페이징 처리
>
**페이징(Paging)**이란 데이터베이스의 레코드를 개수로 나눠 페이지를 구분하는 것을 의미한다. 이를 통해 많은 양의 데이터를 효율적으로 처리하고, 사용자에게 필요한 부분만을 보여줄 수 있다.

- JPA에서는 Page와 Pageable 인터페이스를 사용하여 페이징을 구현한다.
```java
// 페이징 처리를 위한 쿼리 메서드
Page<Product> findByName(String name, Pageable pageable);
```
- return type으로 Page를 설정하고, parameter에는 pageable 타입의 객체를 정의하였다.
- Page : 페이징된 결과를 포함하는 객체로, 조회된 데이터 리스트와 함께 페이지 정보(총 페이지 수, 현재 페이지 번호, 총 레코드 수 등)를 제공한다.
- Pageable : 페이징 정보를 담고 있는 객체로, 페이지 번호와 페이지 크기 등을 설정할 수 있다.
```java
// Service단에서 indByName을 호출하는 예시
Page<Product> productPage = produtRepository.findByName("연필, PageRequest.of(0, 2));
for(Product product : productPage.getContent()){
	System.out.println(product);
}

// 출력결과예시
// Product{id=1, name='연필', price=100.0, stock=50}
// Product{id=2, name='연필', price=120.0, stock=30}
```
```java
Hibernate: 
    select
        product0_.id as id1_0_,
        product0_.name as name2_0_,
        product0_.price as price3_0_,
        product0_.stock as stock4_0_
    from
        product product0_
    where
        product0_.name=? limit ?
Hibernate: 
    select
        count(product0_.id) as col_0_0_
    from
        product product0_
    where
        product0_.name=?
```
- PageRequest는 Pageable의 구현체이다. 다시 말해 Pageable 객체는 PageRequest를 사용하여 생성한다.
- limit 절은 결과로 반환될 행(row)의 수를 제한하는 데 사용된다.
-  Page 객체에서 제공하는 `getContent()` 메서드를 사용하여 엔티티의 리스트를 가져올 수 있다.

##### Pageable of() 메서드의 종류

|of 메서드|매개변수 설명|비고|
|------|---|---|
|of(int page, int size)|페이지 번호(0부터 시작), 페이지당 데이터 갯수|데이터 정렬X|
|of(int page, int size, Sort)|페이지 번호(0부터 시작), 페이지당 데이터 갯수, 정렬|sort에 의해 정렬|
|of(int page, int size, Direction, String --- 속성)|페이지 번호(0부터 시작), 페이지당 데이터 갯수, 정렬 방향, 속성(컬럼)|Sort.by(direction, properties)에 의해 정렬|

***

#### 여기까지 잘 따라왔으면 한가지 의문점이 들 것이다❓
>🙋🏻‍♂️ : "OK, 그럼 그냥 JPQL(혹은 native SQL)을 작성하지 않고 Query Method만 작성하면 되는거야??"
📢 : **"음.. 그렇게도 생각 할 수 있어 하지만 아냐!!"** 

- 우선 **Spring Data JPA는 메서드 이름을 분석하여 자동으로 쿼리를 생성하는 기능을 제공하며 이를 _<u>메서드 쿼리 생성(Method Query Creation)</u>_**이라고 한다!! 
- 이 기능을 통해 우리는 특정 패턴을 따르는 메서드 이름을 정의함으로써 복잡한 쿼리를 작성할 필요 없이 데이터베이스 작업을 간편하게 수행할 수 있다. -> ~~_<u>'쉽죠? 아 쉽죠는 어려운 말의 반대말입니다. 깔깔'</u>_~~


- **그러나!! BUT!! However!! Nevertheless!!** 
복잡한 조건을 가진 쿼리나 여러 테이블을 조인해야 하는 경우, 특정 데이터베이스의 고유한 함수나 기능을 사용해야 하는 경우, 여러 조건을 동적으로 추가해야 하는 경우 Query Method로 구현하기가 어렵다.
***=> @Query 어노테이션을 사용하여 직접 쿼리문을 작성해주면 된다.***
```java
// Example
package com.example.firstobject.repository;

import com.example.firstobject.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    // 특정 게시글의 모든 댓글 조회
  	@Query("SELECT p FROM Product p WHERE p.name = :name")
    List<Product> findByName(@Param("name") String name);
    // 특정 닉네임의 모든 댓글 조회
    List<Comment> findByNickname(String nickname);
}

```
- 이러한 간편한 기능 덕분에 메서드 이름만으로 간단한 쿼리를 생성할 수 있도 있고 필요에 따라 직접 쿼리를 작성하여 복잡한 요구 사항을 충족할 수 있다.

***

### @Query annotation
>
@Query 어노테이션은 JPQL 또는 네이티브 SQL을 직접 작성하여 튜닝된 쿼리를 사용할 때 사용하는 어노테이션이다.

- 기본적으로 Spring Data JPA는 메서드 이름을 기반으로 JPQL을 자동으로 생성한다. 하지만 개발자가 직접 작성한 JPQL 또는 네이티브 SQL을 사용하고 싶다면 `@Query` 어노테이션을 사용할 수 있다.

```java
public interface ProductRepository extends JpaRepository<Product, Long> {

    // @Query 어노테이션을 사용한 사용자 정의 JPQL 쿼리
    @Query("SELECT p FROM Product p WHERE p.name = :name")
    List<Product> findByNameCustom(@Param("name") String name);

    // @Query 어노테이션을 사용한 네이티브 SQL 쿼리
    @Query(value = "SELECT * FROM product WHERE name = :name", nativeQuery = true)
    List<Product> findByNameNative(@Param("name") String name);
}
```
#### native SQL VS JPQL

||native SQL |JPQL|
|--|----|---|
|**대상**|데이터베이스 테이블|엔티티 객체|
|**데이터베이스 독립성**|데이터베이스 종속적|데이터베이스 독립적|
|**구문**|SQL 구문 직접 사용|객체 지향적인 구문|
|**유연성 및 최적화**|데이터베이스의 고유 기능 사용|객체 지향적인 접근 방식|

- JPQL은 `FROM` 절 뒤에 엔티티 타입을 지정하고 별칭을 설정한다.
- `WHERE` 절을 통해 SQL과 마찬가지로 조건을 지정하는데 `?1`, `?2`와 같이 순번을 이용해서 인자를 받아올 수도 있다. 
```java
// 순번을 이용해서 인자를 받아 올 수 있는 Example
@Query("SELECT p FROM Product p WHERE p.name = ?1")
List<Product> findByName(String name);
```
- 하지만 파라미터의 순서가 바뀔 수 있기 때문에 `@Param`을 어노테이션을 사용하여 파라미터를 직접 바인딩하는 방식으로 메소드를 구현하면 오류 발생 확률을 줄이고 유지보수를 수월하게 할 수 있다.
- 마지막으로, `@Query` 어노테이션은 엔티티 타입이 아니라 원하는 컬럼의 값만 추출할 수도 있고 이때의 리턴 타입은 `List<Object[]>`형태로 지정할 수 있다.
  
***
#### 용어 정리🤷🏻‍♂️
- **스프링 하위 프로젝트** : Spring 프레임워크의 기능을 확장하고 특정 용도를 위해 더 쉽게 사용할 수 있도록 제공하는 추가 모듈 또는 라이브러리이다. 
- **엔티티 매니저** : JPA(Java Persistence 	API)에서 중요한 역할을 하는 인터페이스로, 애플리케이션이 데이터베이스와 상호 작용할 수 있도록 하는 주요 객체이다. 엔티티의 생명 주기를 관리하고, 데이터베이스 쿼리를 수행하며, 트랜잭션을 관리한다.

***
**Reference📚**
- 스프링부트 핵심 가이드 - 장정우 -
