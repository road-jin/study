# 2주차 과제: 자바 데이터 타입, 변수 그리고 배열

## 1. 프리미티브 타입 종류와 값의 범위 그리고 기본 값

|      Type       |          Size          |          Value Range          | Default Value |       Example        |      |
| :-------------: | :--------------------: | :---------------------------: | :-----------: | :------------------: | :--: |
|  byte(정수형)   |     1 byte(8 bit)      |     $-2^{7}$ ~ $2^{7} -1$     |       0       |    byte foo = 1;     |      |
|  short(정수형)  |     2 byte(16bit)      |    $-2^{15}$ ~ $2^{15} -1$    |       0       |    short foo = 1;    |      |
|   int(정수형)   |     4 byte(32bit)      |    $-2^{31}$ ~ $2^{31} -1$    |       0       |     int foo = 1;     |      |
|  long(정수형)   |     8 byte(64bit)      |    $-2^{63}$ ~ $2^{63} -1$    |       0       |    long foo = 1L;    |      |
|  float(실수형)  |     4 byte(32bit)      |  $-3.4e38$ ~ $3.4e38$ 근사값  |     0.0f      |   float foo = 1.0f   |      |
| double(실수형)  |     8 byte(64bit)      | $-1.7e308$ ~ $1.7e308$ 근사값 |      0.0      |   double foo = 1.0   |      |
| boolean(논리형) |      1 byte(8bit)      |          true, false          |     false     | boolean foo = false; |      |
|  char(정수형)   | unsigned 2 byte(16bit) |        0 ~ $2^{16}-1$         |   '\u0000'    |   char foo = '1';    |      |



### 정수형

![](https://raw.githubusercontent.com/road-jin/imagebox/main/images/asd1.jpeg)

1 byte에서 부호(-, +) 표현하기 위해서 n-1의 비트를 이용하여 수를 표현합니다.  
그리고 양수 부호 비트에서 0을 포함하기 때문에 부호가 있는 정수형의 표현 범위는 $-2^n$ ~ $2^n-1$이 됩니다.



### 실수형

![](https://raw.githubusercontent.com/road-jin/imagebox/main/images/float.jpeg)

실수형은 부호, 지수, 가수로 나누어 저장됩니다.  
실수형은 저장할려고 하는 값과 실제 저장된 값이 오차가 날 수 있으며, 아래와 같습니다.

- float: 32bit IEEE 754 부동 소수점이며,  가수 부분이 23bit이며 정규화를 통해 24bit까지 표현이 가능하여  
    $10^7$ <  $2^{24}$ <$10^8$ 이므로 정밀도는 7이 됩니다.
- double: 64bit IEEE 754 부동 소수점이며, 가수 부분이 52bit이며 정규화를 통해 53bit까지 표현이 가능하여  
    $10^{10}$ <  $2^{53}$ <$10^{11}$ 이므로 정밀도는 10이 됩니다.



### 논리형

true, flase 두 가지 값만 사용할 수 있으며, 1 bit의 정보만 가지고 있지만 자바의 최소 단위가 byte라서 1 byte를 사용합니다.



### 문자형

16bit 유니코드 문자입니다. 최소값 `\u0000(또는 0)` 과 최대값 `\uffff(또는 65,535)`을 갖습니다.



## 2. 프리미티브 타입과 레퍼런스 타입

#### Primitive Type

**타입 종류** 

- NumbericType
    - IntegralType
        - byte
        - short
        - int
        - long
        - char
    - FloatingPointType
        - float
        - double
- boolean

기본 타입에는 null 할당할 수 없습니다.



#### Reference Type

**타입 종류**

- Class Types
- Interface Type
- Type Variable(Generic)
- Array Type

참조 타입에는 null을 할당할 수 있습니다.  
또한 참조 타입은 Heap 영역에 저장되며, 변수에 할당할 경우 Heap 영역에 저장된 주소를 참조합니다.  
많은 변수들은 해당 참조 타입의 개체를 참조할 수 있으며, 해당 참조 타입의 개체의 값을 변경할 경우   
참조한 모든 변수들의 값이 동일하게 변경됩니다.



## 3. 리터럴

리터럴은 고정된 값이며,  Primitive Type에 할당할 수 있습니다.

#### 정수 리터럴

정수 리터럴은 L 또는 l로 끝나는 경우 long 유형이고, 그렇지 않으면 int 유형입니다.  
보통의 경우는 소문자 l은 숫자 1과 구분하기 어렵기 때문에 대문자 L을 사용하는 것이 좋습니다.  
byte, short, int, long 타입은 int 리터럴로 할당할 수 있습니다.  
int 범위를 벗어나는 값은 long 리터럴로 생성됩니다.

```java
// 모두 같은 26을 나타냅니다.
int decVal = 26;
int hexVal = 0x1a;
int binVal = 0b11010;
```

위와 같이 10진수와 2진수로 나타낼 수도 있습니다.

```java
long creditCardNumber = 1234_5678_9012_3456L;
long socialSecurityNumber = 999_99_9999L;
float pi =  3.14_15F;
long hexBytes = 0xFF_EC_DE_5E;
long hexWords = 0xCAFE_BABE;
long maxLong = 0x7fff_ffff_ffff_ffffL;
byte nybbles = 0b0010_0101;
long bytes = 0b11010010_01101001_10010100_10010010;
```

자바 7 이상부터 추가된 기능으로 숫자 리터럴에 밑줄 문자를 사용하여 가독성을 높일 수가 있습니다.  
아래와 같은 위치에는 넣을 수 없습니다. 

- 숫자의 시작 또는 끝
- 부동 소수점 리터럴에서 소수점에 붙은경우
- 뒤에 붙는 F, L, D등의 문자의 앞 뒤에 붙은경우
- 문자열에서 문자가 올 것으로 예상되는 위치



#### 실수 리터럴

float의 경우 F 또는 f로 끝나며, double의 경우에는 D 또는 d로 끝나며 double의 경우는 생략할 수 도 있습니다.

```java
// 모두 닽은 123.4를 나타냅니다.
double d1 = 123.4;
double d2 = 1.234e2;
float f1 = 123.4f;
```

위와 값이 과학적 표기법(E 또는 e)를 사용하여 표기할 수 도 있습니다.



#### 문자 및 문자열 리터럴

char, String은 유니코드로 표현되어 직접 해당 문자를 입력할 수도 있고, 유니코드 이스케이프('\u0000')를 사용할 수도 있습니다.  
char의 리터럴인 경우 '작은따옴표'를 사용하고, String의 리터럴인 경우 "큰 따옴표"를 사용합니다.  
`\b`(백 스페이스), `\t`(탭), `\n`(라인 피드), `\f`(폼 피드), `\r`(캐리지 리턴), `\"`, `\'`, `\\` 와 같은 이스케이프 문자라고 특별한 문자를 가지고 있습니다.



#### 논리 리터럴

boolean 타입에 사용하며, true, false가 있습니다.



#### 특수 리터럴

```java
String str = null;
Class<String> str = String.class;
```

`null`, `Class` 리터널도 있습니다.



## 4. 변수 선언 및 초기화하는 방법

```java
public class Variables {
	
	static int classVariable;				// 클래스 변수 선언 (primitive type이라서 기본값 0)
	static int classVariable2 = 2;  // 클래스 변수 선언 및 초기화(명시적 초기화)
	static int classVariable3;			// 클래스 변수 선언 (primitive type이라서 기본값 0)
	
	static {
		classVariable3 = 3;						// 클래스 초기화 블록으로 클래스 변수를 초기화 합니다.
	}
	
	int instanceVariable;						// 인스턴스 변수 선언 (primitive type이라서 기본값 0)
	int instanceVariable2 = 2;			// 인스턴스 변수 선언 및 초기화(명시적 초기화)
	int instanceVariable3;					// 인스턴스 변수 선언 (primitive type이라서 기본값 0)
	int instanceVariable4;					// 인스턴스 변수 선언 (primitive type이라서 기본값 0)
	
	{
		instanceVariable3 = 3;				// 인스턴스 초기화 블록으로 인스턴스 변수를 초기화 합니다.
	}
	
	public Variables() {				
		instanceVariable4 = 4;				// 생성자로 인스턴스 변수를 초기화 합니다.
	}
	
	
	void foo(int num) {							// 매개 변수 선언
		int variableA;								// 지역 변수 선언
		variableA = 10;								// 지역 변수 초기화
		int variableB = variableA;		// 지역 변수 선언 및 초기화(명시적 초기)
	}
}
```

#### 변수 선언

```java
	int 		foo;
변수 타입  변수 이름
```

메모리의 빈공간에 데이터를 변수 타입에 맞는 크기의 공간을 준비합니다.

- 변수 타입: 변수에 저장될 값이 어떠한 Type 인지를 정합니다.
- 변수 이름: 변수가 값을 저장할 수 있는 메모리 주소를 나타내는 이름입니다.



#### 변수 초기화

```java
int foo = 1;
```

변수를 선언한 후 메모리에 저장공간이 준비가 되었을 경우 해당 공간에 값을 저장해주는 것을 초기화라고 합니다.



**명시적 초기화**

변수 선언과 동시에 초기화 하는 것을 명시적 초기화라고 합니다.   
클래스 및 지역변수 어디서든 사용가능하며 여러 초기화 방법중 권장합니다.



**초기화 블럭**

블럭 스코프 안에서 코드를 작성할 수 있어서 명시적 초기화만으로 초기화하기 어려울 경우 사용하면 효과적입니다. 

- 클래스 초기화 블럭: 클래스 변수들을 초기화하기 위해서 사용합니다.   
    인스턴스 초기화 블럭보다 먼저 실행됩니다.
- 인스턴스 초기화 블럭: 인스턴스 변수들을 초기화하기 위해서 사용합니다.      
    생성자보다 먼저 실행됩니다.



**생성자**

인스턴스를 생성시 사용되는 함수로 인스턴스 변수들을 초기화 할 수 있습니다.



#### 변수 종류

- 클래스 변수: 클래스 선언시 `static` 키워드와 함께 선언된 필드이며,  모든 인스턴스들이 공유합니다.   
    클래스 명으로 접근이 가능하고,  클래스 하나에 하나의 변수이기 때문에 클래스 변수라고 불립니다.
- 인스턴스 변수: 클래스 선언시 `static` 키워드 없이 선언된 필드이며, 인스턴스 별로 다른 값을 가질 수 있기 떄문에 인스턴스 변수라고 불립니다.
- 지역 변수: 메서드 블록 내에서 생성되는 변수로 다른 클래스나 메서드에서 접근할 수 없는 변수입니다.   
    지역 변수는 자동적으로 초기화가 안되어 무조건 초기화를 하고 사용해야합니다.
- 매개 변수: 매개 변수는 메서드의 인자로 전달되는 변수를 의미합니다.  
    매개변수는 초기화 할 수 없습니다.



## 5. 변수의 스코프와 라이프타임

| 변수의 종류   | 선언 위치   | 생성 시기                 | 종료 시기                                  |                      변수의 스코프                       |
| ------------- | ----------- | ------------------------- | ------------------------------------------ | :------------------------------------------------------: |
| 클래스 변수   | 클래스 영역 | 클래스 로더에서 링킹 시점 | JVM 종료 시점                              | 클래스 전역(해당 클래스로 생성된 각각의 인스턴스에 공유) |
| 인스턴스 변수 | 클래스 영역 | 인스턴스 생성 시점        | GC에 의해서 소멸 시점                      |                     각각의 인스턴스                      |
| 지역 변수     | 메서드 영역 | 변수 선언 시점            | 메서드 종료 시점<br />(스택에서 소멸 시점) |                      메서드 블록 안                      |



## 6. 타입 변환, 캐스팅 그리고 타입 프로모션

#### 타입 변환

타입 변환은 어떠한 값이나 변수의 타입을 다른 타입으로 변경합니다.



#### 타입 프로모션

```java
byte a = 10;
short b = a;
```

byte -> short -> int -> long -> float -> double   
		   char -> int  
위와 같이 작은 메모리 크기를 가지고 있는 타입에서 큰 메모리 크기 타입으로 저장될 때 자동적으로 발생합니다.  
long(8byte)에서 float(4byte) 자동 형변환이 가능한 이유는 float이 숫자의 범위가 더 크기 때문입니다.  
byte -> char로 타입 프로모션을 할 경우 char에 마이너스 값이 들어갈 수 있어서 에러가 발생합니다.



**오토 박싱**

```java
int a = 10;
Integer b = a;
```

오토 박싱은 java 컴파일러가 primitive type -> object wrapper type으로 자동 형변환하는 작업을 말합니다.

| Primitive type | Wrapper class |
| :------------- | :------------ |
| boolean        | Boolean       |
| byte           | Byte          |
| char           | Character     |
| float          | Float         |
| int            | Integer       |
| long           | Long          |
| short          | Short         |
| double         | Double        |

**언박싱**

```java
public static void main(String[] args) {
  Integer a = 10;
  unboxing(a);
}

public static void unboxing(int number) {
  System.out.println(number);
}
```

언박싱은 java 컴파일러가 object wrapper type -> primitive type으로 자동 형변환하는 작업을 말합니다.



#### 캐스팅

캐스팅은 명시적으로 해당 타입으로 변환하겠다고 나타냅니다.

```java
Long a = 10L;
int b = (int) a;

short c = 7410;
byte d = (byte) a; // 크기가 작기 때문에 데이터가 손실 됩니다.
```

![](https://raw.githubusercontent.com/road-jin/imagebox/main/images/casting-20231023214636481.jpeg)
큰 메모리 크기 타입에서 작은 메모리 크기 타입으로 강제로 캐스팅하는 경우 데이터 손실이 있을 수가 있습니다.  
위와 같이 작은 메모리 크기 타입 bit 수만큼 뒤에서 부터 채워지고 모두 채워지면 나머지는 전부 버립니다.  
그래서 결과가 -14가 됩니다.



## 7. 1차 및 2차 배열 선언하기

```java
// 1차원
// 선언
int[] arr;
int arr2[];

// 선언 및 초기화
int[] arr3 = new int[1];
int[] arr4 = new int[]{10};
int[] arr5 = {10};

// 2차원
// 선언
int[][] arr6;
int arr7[][];

// 선언 및 초기화
int[][] arr8 = new int[1][1];
int[][] arr9 = new int[][]{{10}, {10, 20}, {10, 20, 30}};
int[][] arr10 = {{10}, {10, 20}, {10, 20, 30}};
```

위와 같이 선언할 수 있습니다.
배열은 다차원 개념을 선택하여서 실제 메모리 주소는 일렬로 있지만, 개념적으로 다차원이라고 생각하시면 됩니다.



```java
int[][] a = new int[2][3]
```

![](https://raw.githubusercontent.com/road-jin/imagebox/main/images/img_c_array23.png)
위와 같이 세로 2줄, 가로 3줄인 6개의 int 타입의 메모리 크기를 가집니다.  
[] 에 있는 숫자들을 전부 곱하여 메모리 크기를 계산합니다. 



## 8. 타입 추론, var

```java
var channels = new HashMap<String, Integer>();
int channelNumber = channels.get("kbs");

var arr = new ArrayList<>(); // 컴파일 에러
```

자바 10에서 추가된 기능으로, 코드 작성 당시 타입이 정해지지 않았지만, 컴파일러가 해당 타입을 유추합니다.
컴파일러가 오른쪽의 대입되는 값을 통하여 타입을 유추합니다.
그래서 var과 같이 다이아몬드 연산자를 같이 사용하게 되면 컴파일 에러가 발생합니다.



출처

https://github.com/kksb0831/Practice_project/blob/master/Java_Study_02.md  
https://docs.oracle.com/javase/tutorial/java/nutsandbolts/datatypes.html  
https://docs.oracle.com/javase/specs/jls/se7/html/jls-4.html  
https://velog.io/@dion/%EB%B0%B1%EA%B8%B0%EC%84%A0%EB%8B%98-%EC%98%A8%EB%9D%BC%EC%9D%B8-%EC%8A%A4%ED%84%B0%EB%94%94-2%EC%A3%BC%EC%B0%A8-%EC%9E%90%EB%B0%94-%EB%8D%B0%EC%9D%B4%ED%84%B0-%ED%83%80%EC%9E%85-%EB%B3%80%EC%88%98-%EA%B7%B8%EB%A6%AC%EA%B3%A0-%EB%B0%B0%EC%97%B4  
https://docs.oracle.com/javase/tutorial/java/nutsandbolts/variables.html  
https://www.geeksforgeeks.org/automatic-type-promotion-in-overloading-in-java/  
https://docs.oracle.com/javase/tutorial/java/data/autoboxing.html   
https://b-programmer.tistory.com/225  
https://developer.oracle.com/learn/technical-articles/jdk-10-local-variable-type-inference   