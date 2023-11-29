# 3주차 과제: 연산자

## 연산자

연산자는 연산을 수행하는 기호를 말합니다.

- 연산자(operator): 연산을 수행하는 기호(+,-,* 등)
- 피연산자(operand): 연산자의 작업 대상(변수, 상수, 리터럴, 수식)

### **산술 변환**

이항 연산자는 두 피연산자의 타입이 일치해야 연산이 가능하므로, 피연산자의 타입이 서로 다르다면 연산 전에 형변환을 해야합니다.  
모든 연산에서 산술 변환이 일어나지만, 쉬프트 연산자(<<, >>), 증감 연산자(++, --)는 예외입니다.

- 두 피연산자의 타입을 같게 일치( 큰 타입으로 일치)
    - long + int -> long + long 
    - float + int -> float + float
- 피연산자의 타입이 int보다 작은 타입이면 int로 변환
    (JVM의 피연산자 스택이 4byte 단위로 저장하기 때문입니다.)
    - byte + short -> int + int
    - char + short -> int + int



## 1. 산술 연산자

### 사칙 연산자

| Operator | Description                                 |
| -------- | ------------------------------------------- |
| `+`      | 더하기 연산자 (문자열 연결에도 사용됩니다.) |
| `-`      | 빼기 연산자                                 |
| `*`      | 곱하기 연산자                               |
| `/`      | 나누기 연산자                               |
| `%`      | 나머지 연산자                               |

덧셈, 뺄셈, 곱셈, 나눗셈, 나머지를 수행하는 연산자입니다.
boolean 타입을 제외한 모든 primitive Type에 사용이 가능합니다.

```java
byte a = 10;
byte b = 20;
byte c = a + b;						// 컴파일 에러
byte c = (byte) (a + b);  			// 30

long i = 1_000_000 * 1_000_000;		// -727,379,968
long j = 1_000_000 * 1_000_000L;	// 1,000,000,000,000
```

자바는 기본적으로 정수형은 int로 계산하기 때문에 위와 같이 `byte c = a + b`는 결과가 int형으로 반환되어 컴파일 에러가 발생합니다.  
`long i = 1_000_000 * 1_000_000`는 int * int으로  계산이 되고,   
int가 표현할 수 있는 범위가 초과하여 overflow가 발생 후에 long으로 형변환이 됩니다.  
`long j = 1_000_000 * 1_000_000L`는 long * long으로 계산이 되서 계산된 값이 long이 표현할 수 있는 범위이기 때문에 정상적으로 계산이 됩니다.



### 단항 연산자

| Operator | Description         |
| :------- | ------------------- |
| `+`      | 양수 부호 연산자    |
| `-`      | 음수 부호 연산자    |
| `++`     | 피연산자 값을 1증가 |
| `--`     | 피연산자 값을 1감소 |

부호를 변경하는 연산자와 값을 1 증감시켜주는 연산자가 있습니다.

```java
int a = -10;
a = +a; 								// -10

a = -10;
a = -a; 		  						// 10

System.out.println(++a);				// 10
System.out.println(a++);				// 12
```

위와 같이 - 부호 연산자로 부호를 반대로 변경합니다.
증감 연산자는 전위형과 후위형이 있습니다.

- 전위형: 값이 참조되기 전에 증가시킵니다. (++a)
- 후위형: 값이 참조된 후에 증가시킵니다. (a++)



## 2. 비트 연산자

| Operator | Description                                                  |
| -------- | ------------------------------------------------------------ |
| &        | 비트 AND 연산자 두 비트가 1인 경우 1, 아니면 0               |
| \|       | 비트 OR 연산자 두 비트 중 하나라도 1이면 1, 아니면 0         |
| ^        | 비트 XOR 연산자 두 비트가 다를 경우 1, 같은 경우 0           |
| ~        | 비트 보수 연산자 모든 비트를 반전                            |
| <<       | 왼쪽 피연산자의 2진수 자리를 왼쪽으로 이동하고 빈자리는 0    |
| >>       | 왼쪽 피연산자의 2진수 자리를 오른쪽으로 이동하고 빈자리는 부호 비트와 같은 값 |
| >>>      | 왼쪽 피연산자의 2진수 자리를 오른쪽으로 이동하고 빈자리는 0  |

```java
int bitAndOperator = 2 & 2;					// 2
int bitOrOperator = 2 | 2;					// 2
int bitXorOperator = 5 ^ 7;					// 2
int bitComplementOperator = ~-3;			// 2
int leftSiftOperator = 1 << 1;				// 2
int rightSiftOperator = 4 >> 1;				// 2
int unsignedRightSiftOperator = 4 >> 1;     // 2
```



## 3. 관계 연산자

| Operator | Description                                            |
| -------- | ------------------------------------------------------ |
| >        | 왼쪽 피연산자의 값이 크면 true, 아니면 false           |
| <        | 왼쪽 피연산자의 값이 작으면 true, 아니면 false         |
| >=       | 왼쪽 피연산자의 값이 같거나 크면 true, 아니면 false    |
| <=       | 왼쪽 피연산자의 값이 같거나 작으면 true, 아니면 false  |
| ==       | 왼쪽, 오른쪽 피연산자의 값이 같으면 true, 아니면 false |
| !=       | 왼쪽, 오른쪽 피연산자의 값이 다르면 true, 아니면 false |

왼쪽, 오른쪽 피연산자가 크거나, 작거나, 같거나 같지 않은지 여부를 결정합니다.  
모든 결과는 boolean 타입으로 결정되며, 신술변환 후 비교하게 됩니다.  
char 인 경우는 'A' -> 65 정수 값으로 변환 후 비교합니다.



## 4. 논리 연산자

| Operator | Description |
| -------- | ----------- |
| &&       | AND 연산자  |
| \|\|     | OR 연산자   |
| !        | NOT 연산자  |

```java
boolean andOperator = i == j;
boolean orOperator = true || false;
boolean notOperator = !false;

// true
System.out.println(andOperator);
// true
System.out.println(orOperator);
// true
System.out.println(notOperator);	

int i = 0;
int j = 0;

if (i++ == 0 || j++ == 0) {
  // 1
  System.out.println(i);
  // 0
  System.out.println(j);			
}
```

- AND 연산자
    - 피연산자 모두가 true일 경우만 true
- OR 연산자
    - 피연산자 중 하나만 true여도 true
    - 둘 다 false 경우는 false
    - `if (i++ == 0 || j++ == 0)` 일 때 왼쪽 피연산자가 true이기 때문에 오른쪽 피연산자는 계산하지 않습니다.
- NOT 연산자
    - true일 때 false
    - false일 때 true



## 5. instanceof

```java
class InstanceofDemo {
    public static void main(String[] args) {

        Parent obj1 = new Parent();
        Parent obj2 = new Child();
        
		// true
        System.out.println("obj1 instanceof Parent: " + (obj1 instanceof Parent));
        // false
        System.out.println("obj1 instanceof Child: " + (obj1 instanceof Child));
        // false
        System.out.println("obj1 instanceof MyInterface: " + (obj1 instanceof MyInterface));
        // true
		System.out.println("obj2 instanceof Parent: " + (obj2 instanceof Parent));
        // true
        System.out.println("obj2 instanceof Child: " + (obj2 instanceof Child));
        // true
        System.out.println("obj2 instanceof MyInterface: " + (obj2 instanceof MyInterface));	
    }
}

class Parent {}
class Child extends Parent implements MyInterface {}
interface MyInterface {}
```

instanceof 연산자는 객체를 지정된 유형과 비교하여 boolean 타입으로 결과를 반환합니다.  
이를 사용하여 객체가 클래스의 인스턴인지, 하위 클래스의 인스턴스인지, 특정 인터페이스를 구현하는 클래스의 인스턴스인지 테스트 할 수 있습니다.



## 6. assignment(=)  연산자

| Operators | Description                                                  |
| --------- | ------------------------------------------------------------ |
| =         | 변수에 오른쪽의 피연자의 값을 대입합니다.                    |
| +=        | 변수에 변수값과  오른쪽 피연산자의 값을 더한 후 대입합니다.  |
| -=        | 변수에 변수값과  오른쪽 피연산자의 값을 뺀 후 대입합니다.    |
| *=        | 변수에 변수값과  오른쪽 피연산자의 값을 곱한 후 대입합니다.  |
| /=        | 변수에 변수값과  오른쪽 피연산자의 값을 나눈 몫을 대입합니다. |
| %=        | 변수에 변수값과  오른쪽 피연산자의 값을 나눈 나머지를 대입합니다. |
| &=        | 변수에 변수값과  오른쪽 피연산자의 값을 & 연산 후 대입합니다. |
| ^=        | 변수에 변수값과  오른쪽 피연산자의 값을 ^ 연산 후 대입합니다. |
| \|=       | 변수에 변수값과  오른쪽 피연산자의 값을 \| 연산 후 대입합니다. |
| <<=       | 변수에 변수값과  오른쪽 피연산자의 값을 << 연산 후 대입합니다. |
| >>=       | 변수에 변수값과  오른쪽 피연산자의 값을 >> 연산 후 대입합니다. |
| >>>=      | 변수에 변수값과  오른쪽 피연산자의 값을 >>> 연산 후 대입합니다. |

대입 연산자는 오른쪽 피연산자의 값을 왼쪽 피연산자의 값에 대입을 합니다.  
연산 진행 방식이 오른쪽에서 왼쪽으로 진행되며, 연산자들 중에서 제일 낮은 우선순위를 가지고 있습니다.



## 7. 화살표(->) 연산자

### 함수형 인터페이스

```java
@FunctionalInterface
public interface RunSomething {
  int doIt(int number);
}
```

추상 메소드를 단 하나만 가지고 있는 SAM (Single Abstract Method) 인터페이스입니다  
default method 또는 static method는 여러개 존재해도 상관 없습니다.  
@FuncationInterface 애노테이션이 없어도 람다 표현식이 가능하지만, 해당 인터페이스가 함수형인 체크를 해주기 때문에  
인터페이스 검증과 유지보수를 위해 붙여주는게 좋습니다.



### 람다 표현식(Lambda Expressions)

함수형 인터페이스의 인스턴스를 만드는 방법으로 쓰이고  메소드 매개변수, 리턴 타입, 변수로 만들어 사용할 수 있습니다.

```java
@FunctionalInterface
public interface RunSomething {
  int doIt(int number);
}

public calss Main {
  public static void main(String[] args) {
    
    // 람다 표현식 미사용
		RunSomething runSomething = new RunSomething() {
    		@Override
        public int doIt(int number) {
            return number + 10;
        }
    };

    // 람다 표현식 사용
    RunSomething rambda = number -> number + 10;
  }
}
```

**표현식 : ( 인자 리스트 ) -> { 바디 }**

**인자 리스트** 

- 인자가 없을 때 : ()
- 인자가 한 개 일 때 : (one) 또는 one
- 인자가 여러개 일 때 : (one, two) 
- 인자의 타입은 생략 가능하고 명시할 수도 있습니다. : (Integer one, Integer two)

**바디** 

- 화살표 오른쪽에 함수 본문을 정의합니다.
- 여러 줄인 경우에 {}를 사용해서 묶습니다.
- 한 줄인 경우에 생략 가능하며, return도 생략이 가능합니다.

**지역 변수 캡처**

```java
void lambdaCapturing_localVarMustBeFinal() { 
  int localVariable = 1000; 
  Runnable r = () -> System.out.println(localVariable);
  
  // 컴파일 에러 발생! 
  localVariable = 1002; 												
}



// 지역변수를 캡처하여 사용하는 람다를 외부로 반환하는 메서드
private Supplier<Integer> getLambda() {
  // 지역변수 localVariable
  int localVariable = 1000;
  // 자유변수 localVariable
  return () -> localVariable + 5; 										 
} 

void useLambda() {
  // localVariable에 담긴 1000에 5를 더한 값을 반환하여 actual에 저장 
  Supplier<Integer> lambda = getLambda(); 								
  int actual = lambda.get();
  // 1005 
  System.out.println(actual); 											
}
```

파라미터로 넘겨진 변수말고, 외부에서 정의된 변수(자유변수)가 람다식 내부에 저장하고 사용하는 동작입니다.  
static 변수와 instance 변수는 사용할 때 아무런 제약이 없지만,   
지역 변수를 사용할 경우는 람다 표현식 이후에 해당 지역 변수의 값을 변경할 경우 컴파일 에러가 발생합니다.  
`local variables referenced from a lambda expression must be final or effectively final`   
해당 에러가 발생하는 이유가 지역 변수는 stack에 위치하여 해당 메서드가 종료되면 지역 변수는 메모리에서 제거 되어  
람다 내부에서 해당 변수를 참조 할 수 없기 때문에 자유번수에 원본 지역변수를 값을 복제하여 사용하게 됩니다.  
따라서 복사본의 값이 바뀌지 않아야 하므로 지역 변수에는 한 번만 값을 할당해야 한다는 제약이 생깁니다. 



## 8. 3항 연산자

![](https://media.geeksforgeeks.org/wp-content/uploads/20191122171059/Conditional-or-Ternary-Operator-__-in-Java.jpg)

```java
int i = 10;
boolean foo = i < 100 ? true : false;
// true
System.out.println(foo); 												

foo = i < 100 ? i % 2 == 0 ? true : false : false;
// true
System.out.println(foo); 												
```

3개의 피연산자를 사용하는 조건 연산자입니다.  
if-then-else 문을 한 줄로 대체하며, 중첩 삼항 연산자도 가능합니다.  
처음 foo 변수에 대입되는 값은 i 값이 100보다 작으면 true이고, 같거나 크면 false인데 100보다 작으므로 true 입니다.  
두번째 foo 변수에 대입되는 값은 i 값이 100보다 작고, 짝수이면 true 그외 (홀수 및 100보다 같거나 크면) false 입니다.



## 9. 연산자 우선 순위

상위 일수록 우선순위가 높은 연산자입니다.

| Operators            | Precedence                               |
| -------------------- | ---------------------------------------- |
| postfix              | `expr++ expr--`                          |
| unary                | `++expr --expr +expr -expr ~ !`          |
| multiplicative       | `* / %`                                  |
| additive             | `+ -`                                    |
| shift                | `<< >> >>>`                              |
| relational           | `< > <= >= instanceof`                   |
| equality             | `== !=`                                  |
| bitwise AND          | `&`                                      |
| bitwise exclusive OR | `^`                                      |
| bitwise inclusive OR | `|`                                      |
| logical AND          | `&&`                                     |
| logical OR           | `||`                                     |
| ternary              | `? :`                                    |
| assignment           | `= += -= *= /= %= &= ^= |= <<= >>= >>>=` |



출처

https://docs.oracle.com/javase/tutorial/java/nutsandbolts/operators.html  
자바의 정석  
https://github.com/yeGenieee/java-live-study/blob/main/%5B3%5DJava%20Live%20Study.md  
https://www.geeksforgeeks.org/bitwise-operators-in-java  
https://www.geeksforgeeks.org/java-ternary-operator-with-examples  
https://bcp0109.tistory.com/313  
https://bugoverdose.github.io/development/lambda-capturing-and-free-variable  
모던 자바 인 액션
