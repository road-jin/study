# 5주차 과제: 클래스

## 1. Class

클래스는 **객체의 상태와 행동**들을 **정의**하여 **객체가 생성되는 프로토타입**입니다.

```java
class Foo {
	// class의 member variable = field
  // instance field
  private int number;
  
  // class field
  private static int number2;  
  
  // static 초기화 블럭
  static {
    number2 = 10;
  }
  
  // 초기화 블럭
  {
    number = 20;
  }
  
  // 생성자
  public Foo() {
		number = 100;
  }
  
  // 메서드
  public int getNumber() {
    return number;
  }
  
  // 메서드
  public void setNumber(int number) {
    this.number = number;
  }
}
```

### 변수

클래스와 메서드들의 상태를 나타냅니다.

**선언**

```java
[Access Modifier] [type] [name]
    private         int     foo				
```



**종류**

- 클래스의 변수를 field라고 합니다.
    - static 이 선언된 class field
    - static 이 선언되지 않은 instace field
- method 또는 code bloack 안에 있는 변수를 local variable(지역 변수)라고 합니다.
- method 선언에 있는 변수를 parameter(매개변수)라고 합니다.



**변수 생명 주기**

| 변수의 종류   | 선언 위치   | 생성 시기                 | 종료 시기                                  |                      변수의 스코프                       |
| ------------- | ----------- | ------------------------- | ------------------------------------------ | :------------------------------------------------------: |
| 클래스 변수   | 클래스 영역 | 클래스 로더에서 링킹 시점 | JVM 종료 시점                              | 클래스 전역(해당 클래스로 생성된 각각의 인스턴스에 공유) |
| 인스턴스 변수 | 클래스 영역 | 인스턴스 생성 시점        | GC에 의해서 소멸 시점                      |                     각각의 인스턴스                      |
| 지역 변수     | 메서드 영역 | 변수 선언 시점            | 메서드 종료 시점<br />(스택에서 소멸 시점) |                      메서드 블록 안                      |

### 접근 제어자

외부에서 접근을 제한하는 제어자입니다.  
클래스, 변수, 메서드에 사용이 가능합니다.  
클래스의 경우는 기본적으로는 public, default만 가능하지만, 중첩 클래스의 경우에는 모두 사용가능합니다.



**종류**

| 접근 제어자 | 클래스 내부 | 동일 패키지 | 자손 클래스 | 그 외의 영역 |
| :---------: | :---------: | :---------: | :---------: | :----------: |
|   public    |      O      |      O      |      O      |      O       |
|  protected  |      O      |      O      |      O      |      X       |
|   default   |      O      |      O      |      X      |      X       |
|   private   |      O      |      X      |      X      |      X       |



### Field 초기화

**생성자**
생성자로 field를 초기화 할 수 있으며 아래 4.Constructors에서 설명

**초기화 블럭**
블럭 스코프 안에서 코드를 작성할 수 있어서 명시적 초기화만으로 초기화하기 어려울 경우 사용하면 효과적입니다. 

```java
class Foo {
	// class의 member variable = field
  // instance field
  private int number;
  
  // class field
  private static int number2;  
  
  // static 초기화 블럭
  static {
    number2 = 10;
  }
  
  // instance 초기화 블럭
  {
    number = 20;
  }
}
```

- **static 초기화 블럭**
    - class field들을 초기화하기 위해서 사용합니다. 
    - 초기화 블럭보다 먼저 실행됩니다.
    - 생성자보다 먼저 실행됩니다.
- **instance 초기화 블럭**
    - instance field들을 초기화하기 위해서 사용합니다.
    - 생성자보다 먼저 실행됩니다.



### 중첩 클래스

클래스 내부에 또 다른 클래스를 선언하는 것을 말합니다.  
중첩 클래스는 컴파일시 중첩 클래스마다 파일이 따로 생성됩니다.  
중첩 클래스는 static과 non-static 클래스로 카테고리로 나눠집니다. 

**사용하는 이유**

- 해당 클래스에만 사용하는 클래스를 논리적으로 그룹화
    - 해당 클래스를 사용하는 클래스가 하나만 존재할 경우, 사용하는 클래스에 해당 클래스가 포함하고 같이 유지(helper class)하면  
        패키지가 더욱 간소화됩니다.
- 캡슐화 증가
    - 외부에 공개하고 싶지 않은 클래스를 private 중첩 클래스로 사용하여 공개하지 않도록 할 수 있습니다.
- 코드가 읽기 쉬우며, 유지보수 증가
    - 한 클래스에 모든 코드가 있기 때문에 코드가 읽기 쉽습니다.



**Static Nested Class**

```java
public class OuterClass {
  public static class StaticNestedClass {
  }
}

public class Main {
  public static void main(String args[]) {
    StaticNestedClass staticNetedClass = new StaticNestedClass();
    StaticNestedClass staticNetedClass = new OuterClass.StaticNestedClass();
  }
}
```

바깥 클래스와 동일한 방식으로 정적 중첩 클래스만으로도 인스턴스화를 할 수 있습니다.  
바깥 클래스(Outer Class)의 instance field나 method를 사용하지 못합니다.



**Inner Class**

```java
public class OuterClass {
  private int number = 10;
  
	public class InnerClass {
    // private static int innerClassNumber = 1;
		private static final int INNER_CLASS_NUMBER = 2;
    private int number = 100;
    
    public int getOuterClassNumber() {
      return OuterClass.this.number;
    }
    
    public int getInnerClassNumber() {
      
      return number;
    }
    
  }
}

public class Main {
  public static void main(String[] args) {
		OuterClass outerClass = new OuterClass();
		InnerClass innerClass = outerClass.new InnerClass();
	}
}
```

바깥 클래스(Outer Class)의 인스턴스와 연결되기 때문에 연결된 클래스가 인스턴스화 한 후에 중첩 클래스를 인스턴스화 할 수 있습니다.  
바깥 클래스의 filed와 메서드들을 사용할 수 있습니다. 

**쉐도잉 현상 발생 시 바깥 클래스 field 사용 방법**  
바깥 클래스의 field와 내부 클래스의 field명이 같을 경우 쉐도잉 현상으로 내부 클래스의 field만 가르키기 때문에
바깥 클래스의 field를 사용할려면 `바깥클래스.this.필드명`으로 사용할 수 있습니다.

**static 키워드 field 주의사항**   
 Inner class는 static 키워드만 쓴 field를 사용하지 못합니다  
// 16부터 사용 가능 확인 해봐야함.



**Local Class**

```java
public class Main {
	public static void main(String[] args) {
		System.out.println(foo());
	}

	public static int foo() {
		class LocalClass {
			public int getNumber() {
				return 10;
			}
		}

		LocalClass localClass = new LocalClass();
		return localClass.getNumber();
	}
}

// result
// 10
```

코드 블럭안에 정의된 클래스입니다.  
모든 블록 내에 Local Class를 정의할 수 있습니다.  
Inner Class와 마찬가지로 static 키워드만 쓴 field를 선언하거나 정의 할 수 없습니다.



**Anonymous Class**

```java
public class Main {
	public static void main(String[] args) {
		AnonymousClass anonymousClass = new AnonymousClass() {
			@Override
			public int getNumber() {
				return 10;
			}
		};

		System.out.println(anonymousClass.getNumber());
	}

	interface AnonymousClass {
		int getNumber();
	}
}

// result
// 10
```

Anonymous Class를 사용하면 코드를 간결하게 만들 수 있습니다.  
이를 통해 클래스를 선언하고 동시에 인스턴스화 할 수 있습니다.    
이름  없다는 점을 제외하면 로컬 클래스와 같습니다.

**표현식**

- new 연산자
- 구현하고자 하는 추상 클래스 또는 인터페이스 이름
- 구현하고자 하는 추상 클래스의 생성자 또는 인터페이스인 경우 빈괄호

**제약사항**

- final 혹은 effectively final이 아닌 지역 변수에 접근할 수 없습니다.
- 바깥 클래스의 변수와 동일한 이름으로 선언시 바깥 클래스의 변수를 가리게 됩니다.
- static 초기화 블럭  및 interface를 선언 할 수 없습니다.
- static 키워드만 쓴 field를 선언 하거나 정의 할 수 없습니다.
- 생성자를 정의할 수 없습니다.

**허용**

- 바깥 클래스의 멤버에 접근할 수 있습니다.
- 필드, 추가 메서드, instance 초기화 블럭, Local class를 선언할 수 있습니다.



## 2. Object

상태와 행동들을 가지고 있는 추상적이거나 물리적 존재를 의미하며, 객체들간의 메서드를 호출하여 상호작용을 합니다.

```java
public class Foo {
  public int number;
  
  public Foo() {
    this.number = 1;
  }
  
  public int getNumber() {
    return number;
  }
}


public class Main {
  public static void main(String args[]) {
    Foo foo = new Foo();
    foo.number = 100;
    System.out.println(foo.getNumber());
  }
}

// result
// 100
```

객체를 사용하기 위해서는 객체를 선언하고, 인스턴스화를 해야합니다.  
`Foo foo;` 이렇게 변수로 선언을 하고  
`Foo foo = new Foo();` new 연산자로 통해서 객채를 생성하게 되는데 이를 인스턴스화라고 합니다.

**new 연산자**
새 객체에 메모리를 할당하고 해당 메모리에 대한 잠조를 반환하여 클래스를 인스턴스화 합니다. 
new 연산자는 생성자를 호출하기 때문에 연산뒤에 항상 생성자가 있어야합니다.

객체가 인스턴스화 하였으면 사용할 준비가 되었습니다.
객체 사용은 `객체 변수명.메서드` 또는 `객체 변수명.변수`로 사용이 가능하며 접근제어자 때문에 접근이 불가할 수도 있습니다.



## 3. Method

class의 행동을 나타냅니다.

### 선언

```java
                                        메소드 시그니처 
[접근 제어자] [반환 타입] [메서드 명]	[매개변수들]         {예외들}														 
private	    int	        foo     (int number)    throw Exception
  {
        
  }
```

- 접근 제어자
    - 해당 메서드의 외부 접근을 어디까지 제한하는지를 나타냅니다.
- 반환 타입
    - 해당 메서드의 반환 타입을 나타냅니다.
    - void인 경우에는 반환하지 않습니다.
- 메소드 시그니처
    - 메서드 명
        - 해당 메서드의 이름을 나타냅니다.
    - 매개변수들
        - 외부에서 받아온 값들을 해당 메서드에서 사용할 수 있습니다.
        - 파라미터가 없는 경우는 ()으로 나타냅니다.
- 예외들
    - 해당 메서드를 호출 시 발생할 수 있는 예외들을 나타냅니다.
    - 생략 가능합니다.



### 매개변수

메서드나 생성자에 전달될 인자의 타입, 인자 명을 선언합니다.  
여러개의 인자를 선언할 수 있습니다.

**기본 타입 인자 전달**

```java
public class PassPrimitiveByValue {

    public static void main(String[] args) {
        int x = 3;
        passMethod(x);
        System.out.println("After invoking passMethod, x = " + x);
           
    }
        
    public static void passMethod(int p) {
        p = 10;
    }
}

// result 
// After invoking passMethod, x = 3
```

파라미터 값에 대한 모든 변경사항이 메서드 범위 내에서만 존재하며,  
메서드가 반환되거나 종료 시, 매개 변수는 사라지고 해당 매개 변수에 대한 모든 변경 사항이 손실됩니다.



**참조 타입 인자 전달**

```java
public class Student {
  private int age;
  private String name;
  
  public Student(int age, String name) {
    this.age = age;
    this.name = name;
  }
  
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
}

public class Main {
	public static void main(String args[]) {
    Student student = new Student(10, "만두");
    changeStudentName(student, "군만두");
    System.out.println(student.getName);
  }
  
  public static void changeStudentName(Student student, String changeName) {
    student.setName(changeName);
  }
}

// result
// 군만두
```

참조 타입의 파라미터도 값으로 메소드에 전달됩니다.  
전달된 참조는 메서드가 종료되고 나서도 같은 객체를 참조하기 때문에 필드값을 변경할 경우, 변경된 사항이 적용됩니다.



### 메서드 오버로딩

```java
public class DataArtist {
    ...
    public void draw(String s) {
        ...
    }
    public void draw(int i) {
        ...
    }
    public void draw(double f) {
        ...
    }
    public void draw(int i, double f) {
        ...
    }
}
```

자바는 메서드 시그니처를 통해서 다른 메서드를 구분할 수 있습니다.  
즉, 클래스 내의 메서드는 매개변수들이 다르고, 메서드 명이 돌일한 메서드를 가질 수 있습니다.  
반환 타입이나 접근제어자가 다르더라도 메서드 시그니처가 같으면 해당 메서드를 선언할 수 없습니다.



## 4. Constructors

```java
class Foo {
  // class의 member variable = field
  // instance field
  private int number;
  
  // 생성자
  public Foo() {
		number = 100;
  }
}
```

클래스에는 객체를 생성하기 위해 호출되는 생성자가 포함되어 있습니다.  
생성자 선언은 클래스 이름을 사용하고 반환 타입이 없다는 점을 제외하면 메서드 선언과 유사합니다.   
클래스에 생성자가 없을 경우 컴파일러가 기본 생성자(파라미터가 없는)를 자동 생성합니다.



## 5. this

```java
public class Rectangle {
    private int x, y;
    private int width, height;
        
    public Rectangle() {
        this(0, 0, 1, 1);
    }
    public Rectangle(int width, int height) {
        this(0, 0, width, height);
    }
    public Rectangle(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    ...
}
```

instance 메서드나 생성자 내에서 사용할 수 있으며, 현재 메서드나 생성자가 호출되는 해당 객체를 나타냅니다.  

**this와 field**
this와 field를 사용하여 메서드에서 매개변수나, 지역변수의 이름이 같을 경우 쉐도잉 현상이 일어나 field가 가려지기 때문에  
this 키워드를 사용하여 field를 사용할 수 있습니다.

**this와 constructor**
this와 constructor를 사용하여 해당 클래스의 다른 생성자를 호출할 수 있습니다.



참조

https://docs.oracle.com/javase/tutorial/java/javaOO/index.html
