# 11주차 과제: Enum



## 1. enum 정의하는 방법

### Enum

열거형은 변수가 미리 정의된 상수 집합인 특별한 데이터 유형입니다.  
변수는 미리 정의된 값 중 하나와 같아야 합니다.  
열거형 유형의 필드 이름은 상수이기 때문에 대문자로 표시됩니다. 

### 특징

- Class를 사용하여 내부적으로 구현됩니다.

- enum의 상수는 enum 유형의 객체를 나타냅니다.

- switch 문에 인수로 전달될 수 있습니다.

- enum의 상수는 암시적으로 public static final입니다.

    

### 상속

- enum은 암시적으로 java.lang.Enum 클래스를 확장하여 Extends를 추가적으로 할 수 없습니다.

- enum은 많은 인터페이스를 구현할 수 있습니다.

- toString() 메서드는 enum의 상수 이름을 반환하며, 재정의 할 수 있습니다.

    

### 생성자

- 생성자를 포함할 수 있으며, enum 로딩 시 각 상수에 대해 별도로 실행됩니다.
- enum 객체를 new keyword를 통하여 인스턴스를 생성할 수 없으며, 생성자를 직접 호출할 수 없습니다.



### 메서드

- 구체적인 메서드 및 추상 메서드가 모두 포함될 수 있으며, 추상 메서드가 있는 경우 enum의 상수에서 이를 구현해야 합니다.



### 정의

```java
public class Main {

	public static void main(String[] args) {
		Day monday = Day.MONDAY;
		Month july = Month.JULY;
		Foo bar = Foo.BAR;

		System.out.println(monday);
		System.out.println(july.getNumber());
		System.out.println(bar.isFoo());
	}

	public enum Day {
		SUNDAY,
		MONDAY,
		TUESDAY,
		WEDNESDAY,
		THURSDAY,
		FRIDAY,
		SATURDAY;
	}

	public enum Month {
		JANUARY(1),
		FEBRUARY(2),
		MARCH(3),
		APRIL(4),
		MAY(5),
		JUNE(6),
		JULY(7),
		AUGUST(8),
		SEPTEMBER(9),
		OCTOBER(10),
		NOVEMBER(11),
		DECEMBER(12);

		private final int number;

		Month(int number) {
			this.number = number;
		}

		public int getNumber() {
			return number;
		}
	}
	
	public enum Foo {
		BAR(1) {
			@Override
			boolean isFoo() {
				return true;
			}
		};
		
		private final int number;

		Foo(int number) {
			this.number = number;
		}
		
		public int getNumber() {
			return number;
		}
		
		abstract boolean isFoo();
	}
}

/*
reuslt
MONDAY
7
true
*/
```

```
enum 열거형 이름 { 상수들 } 
```

enum은 위와 같이 정의할 수 있습니다.   
또한 enum은 필드를 가질 수 있습니다.



## 2. enum이 제공하는 메소드 (values()와 valueOf())

### values()

- enum의 모든 상수들을 배열 형태로 반환합니다.

```java
public enum Day {
		SUNDAY,
		MONDAY,
		TUESDAY,
		WEDNESDAY,
		THURSDAY,
		FRIDAY,
		SATURDAY;

		public static void main(String[] args) {
			Day[] values = Day.values();
		}
}
```



### valueOf(String name)

- 해당 enum에서 상수 이름이 같은 경우에 해당 상수를 반환합니다.

```java
public enum Day {
		SUNDAY,
		MONDAY,
		TUESDAY,
		WEDNESDAY,
		THURSDAY,
		FRIDAY,
		SATURDAY;

		public static void main(String[] args) {
			Day sunday = Day.valueOf("SUNDAY");
			System.out.println(sunday);
		}
}
/*
reuslt
SUNDAY
*/
```



## 3. java.lang.Enum

모든 enum들이 상속받고 있는 추상화 클래스입니다.  
toString()을 제외한 모든 메서드는 final이 있어 오버라이딩이 불가합니다.

### 생성자

- protected enum(String name, int ordinal)
    - 유일한 생성자이며,  프로그래머는 이 생성자를 호출할 수 없습니다.



### 주요 메서드

- public final String name()
    - enum 상수의 이름을 반환합니다.
- public final int ordinal()
    - enum 상수의 순서를 반환합니다.
    - 순서는 0부터 시작합니다.
- public String toString()
    - enum 상수의 이름을 반환하며, 메서드를 재정의 할 수 있습니다.
- public final boolean equals(Object other)
    - other 객체가 해당 enum 상수와 같을 경우 true를 반환하고 아니면 false 반환합니다.
- public final int hashcode()
    - enum 상수에 대한 해시 코드를 반환합니다.
- public final int comapreTo(E o)
    - 해당 enum 상수에 대해서만 ordinal을 통해 비교합니다.
    - 작으면 음의 정수 /같으면 0 /크면 양의 정수를 반환합니다.
- public final Class<E> getDeclaringClass()
    - enum 상수의 enum 유형에 해당하는 Class 객체를 반환합니다.
- public static T valueOf(Class<T> enumType, String name)
    - 해당 enum 유형과 enum 상수 이름을 통하여 enum 상수를 반환합니다.



## 4. EnumSet

enum과 함께 사용하기 위한 Set 인터페이스의 특수 구현 중 하나입니다.

### 특징

- AbstractSet 클래스를 확장하고 Set 인터페이스를 구현합니다.
- Java Collection Framework 구성원이며, 동기화되지 않습니다.
- HashSet 보다 훨씬 빠릅니다.
- null 을 허용하지 않습니다.
- ordinal 값의 순서대로 저장됩니다.
- 추가, 삭제, 조회의 시간복잡도는 O(1)입니다.
- enum 상수의 값만 가질 수 있습니다.



### 주요 메서드

- allOf(Class**<E>** elementType)
    - 매개변수로 받은 enum의 모든 상수를 추가하여 EnumSet을 만듭니다.
- complementOf(EnumSet**<E>** s)
    - 매개변수로 받은 EnumSet에 포함되어 있지 않은 enum의 상수들을 포함하는 EnumSet을 만듭니다.
- of()
    - 매개변수로 받은 enum 상수들을 포함하는 EnumSet을 만듭니다.
- range(E from, E to)
    - from과 to의 범위를 포함하는 enum 상수들을 추가하여 EnumSet을 만듭니다.
- noneOf(Class**<E>** elementType)
    - 매개변수로 받은 enum 유형의 비어있는 EnumSet을 만듭니다.
- copyOf()
    - 매개변수로 받은 EnumSet이나 Collection**<E>** 를 복사하여 EnumSet을 만듭니다.

```java
public enum Day {
		SUNDAY,
		MONDAY,
		TUESDAY,
		WEDNESDAY,
		THURSDAY,
		FRIDAY,
		SATURDAY;

		public static void main(String[] args) {
			EnumSet<Day> allOfDays = EnumSet.allOf(Day.class);
			EnumSet<Day> ofDays = EnumSet.of(SUNDAY, MONDAY, TUESDAY);
			EnumSet<Day> complementOfDays = EnumSet.complementOf(ofDays);
			EnumSet<Day> rangeDays = EnumSet.range(THURSDAY, SATURDAY);
			EnumSet<Day> noneOfDays = EnumSet.noneOf(Day.class);
			EnumSet<Day> copyOfDays = EnumSet.copyOf(allOfDays);

			System.out.println("allOf() : " + allOfDays);
			System.out.println("of() : " + ofDays);
			System.out.println("complementOf() : " + complementOfDays);
			System.out.println("rangeOf() : " + rangeDays);
			System.out.println("noneOf() : " + noneOfDays);
			System.out.println("copyOf() : " + copyOfDays);

		}
}
/*
result
allOf() : [SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY]
of() : [SUNDAY, MONDAY, TUESDAY]
complementOf() : [WEDNESDAY, THURSDAY, FRIDAY, SATURDAY]
rangeOf() : [THURSDAY, FRIDAY, SATURDAY]
noneOf() : []
copyOf() : [SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY]
*/
```



참고

https://www.geeksforgeeks.org/enum-in-java/?ref=header_search

https://docs.oracle.com/javase/tutorial/java/javaOO/enum.html

https://docs.oracle.com/javase/8/docs/api/

https://www.geeksforgeeks.org/enumset-class-java/?ref=header_search

https://parkadd.tistory.com/50



