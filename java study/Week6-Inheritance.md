# 6주차 과제: 상속

## 1. 자바 상속의 특징

### 상속

super class가 가지고 있는 멤버(field, method, Nested Class)를 물려받아 sub class가 사용할 수 있게 합니다.
생성자는 멤버가 아니므로 sub class가 상속되지 않지만, sub class에서 super class의 생성자를 호출할 수 있습니다.
그리고 명시적으로 상속 받지 않은 class들은 전부 Object를 상속 받습니다.

**super class**
sub class가 파생되는 클래스를 super class라고 합니다. 

**sub class**
다른 클래스에서 파생된 클래스를 sub class라고 합니다.

```java
public class Bicycle {
        
    public int cadence;
    public int gear;
    public int speed;
        
    public Bicycle(int startCadence, int startSpeed, int startGear) {
        gear = startGear;
        cadence = startCadence;
        speed = startSpeed;
    }
        
    public void setCadence(int newValue) {
        cadence = newValue;
    }
        
    public void setGear(int newValue) {
        gear = newValue;
    }
        
    public void applyBrake(int decrement) {
        speed -= decrement;
    }
        
    public void speedUp(int increment) {
        speed += increment;
    }
        
}

public class MountainBike extends Bicycle {
        
    public int seatHeight;

    public MountainBike(int startHeight,
                        int startCadence,
                        int startSpeed,
                        int startGear) {
        super(startCadence, startSpeed, startGear);
        seatHeight = startHeight;
    }   
        
    public void setHeight(int newValue) {
        seatHeight = newValue;
    }   
}
```



### 특징

- 추상 클래스나 클래스는 다중 상속이 되지 않습니다.
    - sub class가 인스터스화 할때 서로 다른 super class가 동일한 field를 초기화 할 때   
        어떤 super class의 어떤 메서드나 생성자가 우선적으로 적용할 지 모호합니다.
    - 서로 다른 super class가 동일한 메서드 시그니처와 생성자를 가지고 있을 때  
        sub class가 해당 메서드나 생성자를 호출시 어느 super class의 메서드나 생성자를 호출할 지가 모호합니다.
- 부모의 생성자는 상속되지 않습니다.
- super class의 멤버(field, method, Nested Class)를 상속 받습니다.
    - private 접근 제어자의 멤버는 상속하지 않습니다.  
        그러나 super class의 private field를 접근할 수 있는 메서드가 있는 경우는 사용할 수 있습니다.
- sub class 내에서 super class의 동일한 이름을 가진 field는 있는 경우는 super class의 filed가 숨겨지며,  
    super 키워드를 사용해야 접근할 수 있습니다.
- sub class에서 super class 메서드를 오버라이딩(재정의) 할 수 있습니다.
- 명시적으로 상속 받지 않은 클래스는 Object 클래스를 상속 받습니다.
- final class와 final method는 상속되지 않습니다.



## 2. super 키워드

super class의 method, field, constructor를 접근할 수 있는 키워드입니다.

### 멤버

```java
public class Superclass {

    public void printMethod() {
        System.out.println("Printed in Superclass.");
    }
}

public class Subclass extends Superclass {

    public void printMethod() {
        super.printMethod();
        System.out.println("Printed in Subclass");
    }
    public static void main(String[] args) {
        Subclass s = new Subclass();
        s.printMethod();    
    }
}
```

super 키워드를 사용하여 super class의 field, mehtod,  Nested Class를 호출할 수 있습니다.



### 생성자

```java
public class Superclass {
  
    public Superclass() {
      ...
    }

    public void printMethod() {
        System.out.println("Printed in Superclass.");
    }
}

public class Subclass extends Superclass {
  
  	public Subclass() {
      super();
    }

    public void printMethod() {
        super.printMethod();
        System.out.println("Printed in Subclass");
    }
    public static void main(String[] args) {
        Subclass s = new Subclass();
        s.printMethod();    
    }
}
```

super(prameter list)를 통하여 super class의 생성자를 호출합니다.
super class의 생성자 호출은 sub class의 생성자의 첫 번째 줄에서만 호출 할 수 있습니다.
sub class에서 super class의 생성자를 명시적으로 호출하지 않는 경우 super class의 인자가 없는 생성자가 java 컴파일러에 의해서 호출하도록 추가합니다.
sub class가 생성이 될려면 super class가 생성이 되어야합니다.



## 3. 메서드 오버라이딩

```java
public class Animal {
   
    public void testInstanceMethod() {
        System.out.println("The instance method in Animal");
    }
}

public class Cat extends Animal {
    
    public void testInstanceMethod() {
        System.out.println("The instance method in Cat");
    }

    public static void main(String[] args) {
        Cat myCat = new Cat();
        Animal myAnimal = myCat;
        myAnimal.testInstanceMethod();
    }
}

// result
// The instance method in Cat
```

super class의 메서드와 동일한 메서드 시그니처 및 반환 타입을 갖는 sub class의 인스턴스 메서드로 대체합니다.
@Override 애노테이션을 사용하여 오버라이딩 할 때 super class의 메서드를 재정의 할 것임을 컴파일러에게 알려주어
컴파일 에러를 통하여 오버라이딩을 제대로 했는지 체크할 수 있습니다.



### 주의사항

- super class 메서드의 접근 제한자보다 좁은 범위를 선언할 수 없습니다.
    - super class : default => sub class : public, proteced, default 가능
    - super class : public => sub class: proteced 불가능
- super class 메서드의 예외 보다 상위 예외를 선언하거나 추가할 수 없습니다.
- super class 메서드 시그니처와 같아야 합니다.
- super class 메서드의 반환타입과 같아야 합니다.
    - super class 메서드의 반환타입으로 변환(sub class)이 가능하면 변경이 가능합니다.



## 4. 다이나믹 메서드 디스패치

### 메서드 디스패치

어떤 메서드를 호출할지 결정하여 실제로 실행시키는 과정



### 정적 메서드 디스패치

```java
public class Dispatch {

	static class Service {
		void run() {
			System.out.println("run()");
		}

		void run(String msg) {
			System.out.println("run(" + msg + ")");
		}
	}

	public static void main(String[] args) {
		new Service().run();
		new Service().run("Dispatch");
	}
}

public class org.example.Dispatch
  minor version: 0
  major version: 52
  flags: (0x0021) ACC_PUBLIC, ACC_SUPER
  this_class: #7                          // org/example/Dispatch
  super_class: #8                         // java/lang/Object
  interfaces: 0, fields: 0, methods: 2, attributes: 2
Constant pool:
   #1 = Methodref          #8.#24         // java/lang/Object."<init>":()V
   #2 = Class              #25            // org/example/Dispatch$Service
   #3 = Methodref          #2.#24         // org/example/Dispatch$Service."<init>":()V
   #4 = Methodref          #2.#26         // org/example/Dispatch$Service.run:()V
   #5 = String             #27            // Dispatch
   #6 = Methodref          #2.#28         // org/example/Dispatch$Service.run:(Ljava/lang/String;)V
   #7 = Class              #29            // org/example/Dispatch
   #8 = Class              #30            // java/lang/Object
   #9 = Utf8               Service
  #10 = Utf8               InnerClasses
  #11 = Utf8               <init>
  #12 = Utf8               ()V
  #13 = Utf8               Code
  #14 = Utf8               LineNumberTable
  #15 = Utf8               LocalVariableTable
  #16 = Utf8               this
  #17 = Utf8               Lorg/example/Dispatch;
  #18 = Utf8               main
  #19 = Utf8               ([Ljava/lang/String;)V
  #20 = Utf8               args
  #21 = Utf8               [Ljava/lang/String;
  #22 = Utf8               SourceFile
  #23 = Utf8               Dispatch.java
  #24 = NameAndType        #11:#12        // "<init>":()V
  #25 = Utf8               org/example/Dispatch$Service
  #26 = NameAndType        #31:#12        // run:()V
  #27 = Utf8               Dispatch
  #28 = NameAndType        #31:#32        // run:(Ljava/lang/String;)V
  #29 = Utf8               org/example/Dispatch
  #30 = Utf8               java/lang/Object
  #31 = Utf8               run
  #32 = Utf8               (Ljava/lang/String;)V
{
  public org.example.Dispatch();
    descriptor: ()V
    flags: (0x0001) ACC_PUBLIC
    Code:
      stack=1, locals=1, args_size=1
         0: aload_0
         1: invokespecial #1                  // Method java/lang/Object."<init>":()V
         4: return
      LineNumberTable:
        line 3: 0
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0       5     0  this   Lorg/example/Dispatch;

  public static void main(java.lang.String[]);
    descriptor: ([Ljava/lang/String;)V
    flags: (0x0009) ACC_PUBLIC, ACC_STATIC
    Code:
      stack=2, locals=1, args_size=1
         0: new           #2                  // class org/example/Dispatch$Service
         3: dup
         4: invokespecial #3                  // Method org/example/Dispatch$Service."<init>":()V
         7: invokevirtual #4                  // Method org/example/Dispatch$Service.run:()V
        10: new           #2                  // class org/example/Dispatch$Service
        13: dup
        14: invokespecial #3                  // Method org/example/Dispatch$Service."<init>":()V
        17: ldc           #5                  // String Dispatch
        19: invokevirtual #6                  // Method org/example/Dispatch$Service.run:(Ljava/lang/String;)V
        22: return
      LineNumberTable:
        line 16: 0
        line 17: 10
        line 18: 22
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0      23     0  args   [Ljava/lang/String;
}
```

구현 클래스를 통해 컴파일 시점에서 컴파일러가 어떤 메서드를 호출할지 명확하게 알고 있는 경우를 말합니다.  
컴파일시 생성된 바이트코드에도 정보가 남아있으며, 애플리케이션 실행 전에 호출할 메서드를 결정합니다.
대표적으로 오버로딩은 컴파일시점에서 메서드 시그니처를 통하여 호출할 메서드를 알 수 있습니다.



### 동적 메서드 디스패치

```java
public class Dispatch {

	static abstract class Service {
		abstract void run();
	}

	static class MyService1 extends Service {
		@Override
		void run() {
			System.out.println("run1");
		}
	}

	static class MyService2 extends Service {
		@Override
		void run() {
			System.out.println("run2");
		}
	}



	public static void main(String[] args) {
		Service svc = new MyService1();
		svc.run();

		Service svc2 = new MyService2();
		svc2.run();
	}
}

public class org.example.Dispatch
  minor version: 0
  major version: 52
  flags: (0x0021) ACC_PUBLIC, ACC_SUPER
  this_class: #7                          // org/example/Dispatch
  super_class: #8                         // java/lang/Object
  interfaces: 0, fields: 0, methods: 2, attributes: 2
Constant pool:
   #1 = Methodref          #8.#30         // java/lang/Object."<init>":()V
   #2 = Class              #31            // org/example/Dispatch$MyService1
   #3 = Methodref          #2.#30         // org/example/Dispatch$MyService1."<init>":()V
   #4 = Methodref          #12.#32        // org/example/Dispatch$Service.run:()V
   #5 = Class              #33            // org/example/Dispatch$MyService2
   #6 = Methodref          #5.#30         // org/example/Dispatch$MyService2."<init>":()V
   #7 = Class              #34            // org/example/Dispatch
   #8 = Class              #35            // java/lang/Object
   #9 = Utf8               MyService2
  #10 = Utf8               InnerClasses
  #11 = Utf8               MyService1
  #12 = Class              #36            // org/example/Dispatch$Service
  #13 = Utf8               Service
  #14 = Utf8               <init>
  #15 = Utf8               ()V
  #16 = Utf8               Code
  #17 = Utf8               LineNumberTable
  #18 = Utf8               LocalVariableTable
  #19 = Utf8               this
  #20 = Utf8               Lorg/example/Dispatch;
  #21 = Utf8               main
  #22 = Utf8               ([Ljava/lang/String;)V
  #23 = Utf8               args
  #24 = Utf8               [Ljava/lang/String;
  #25 = Utf8               svc
  #26 = Utf8               Lorg/example/Dispatch$Service;
  #27 = Utf8               svc2
  #28 = Utf8               SourceFile
  #29 = Utf8               Dispatch.java
  #30 = NameAndType        #14:#15        // "<init>":()V
  #31 = Utf8               org/example/Dispatch$MyService1
  #32 = NameAndType        #37:#15        // run:()V
  #33 = Utf8               org/example/Dispatch$MyService2
  #34 = Utf8               org/example/Dispatch
  #35 = Utf8               java/lang/Object
  #36 = Utf8               org/example/Dispatch$Service
  #37 = Utf8               run
{
  public org.example.Dispatch();
    descriptor: ()V
    flags: (0x0001) ACC_PUBLIC
    Code:
      stack=1, locals=1, args_size=1
         0: aload_0
         1: invokespecial #1                  // Method java/lang/Object."<init>":()V
         4: return
      LineNumberTable:
        line 3: 0
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0       5     0  this   Lorg/example/Dispatch;

  public static void main(java.lang.String[]);
    descriptor: ([Ljava/lang/String;)V
    flags: (0x0009) ACC_PUBLIC, ACC_STATIC
    Code:
      stack=2, locals=3, args_size=1
         0: new           #2                  // class org/example/Dispatch$MyService1
         3: dup
         4: invokespecial #3                  // Method org/example/Dispatch$MyService1."<init>":()V
         7: astore_1
         8: aload_1
         9: invokevirtual #4                  // Method org/example/Dispatch$Service.run:()V
        12: new           #5                  // class org/example/Dispatch$MyService2
        15: dup
        16: invokespecial #6                  // Method org/example/Dispatch$MyService2."<init>":()V
        19: astore_2
        20: aload_2
        21: invokevirtual #4                  // Method org/example/Dispatch$Service.run:()V
        24: return
      LineNumberTable:
        line 26: 0
        line 27: 8
        line 29: 12
        line 30: 20
        line 31: 24
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0      25     0  args   [Ljava/lang/String;
            8      17     1   svc   Lorg/example/Dispatch$Service;
           20       5     2  svc2   Lorg/example/Dispatch$Service;
}

```

인터페이스나 추상 클래스로 정의한 변수에서 추상메서드를 호출 할 때,   
객체 생성은 런타임 시점에서 하기 때문에 호출되는 메서드는 컴파일 시점에서 알지 못합니다.  
실제 어떤 메서드를 호출 할 지는 런타임 시점에서 해당 변수에 할당되어 있는 객체가 무엇인지를 보고 호출할 메서드를 결정합니다.



### 더블 디스패치

```java
public class Dispatch {

	interface Post {
		void postOn(SNS sns);
	}

	static class Text implements Post {
		@Override
		public void postOn(SNS sns) {
			sns.post(this);
		}
	}

	static class Picture implements Post {
		@Override
		public void postOn(SNS sns) {
			sns.post(this);
		}
	}

	interface SNS {
		void post(Text post);
		void post(Picture post);
	}

	static class Facebook implements SNS {
		@Override
		public void post(Text post) {
			System.out.println("text-facebook");
		}

		@Override
		public void post(Picture post) {
			System.out.println("picture-facebook");
		}
	}

	static class Twitter implements SNS {
		@Override
		public void post(Text post) {
			System.out.println("text-twitter");
		}

		@Override
		public void post(Picture post) {
			System.out.println("picture-twitter");
		}
	}

	public static void main(String[] args) {
		List<Post> posts = Arrays.asList(new Text(), new Picture());
		List<SNS> sns = Arrays.asList(new Facebook(), new Twitter());

		posts.forEach(p -> sns.forEach(p::postOn));

	}
}
```

동적 디스패치 메서드가 두번 일어나는 현상입니다.  
해당 예제에서는 Post의 어떤 구현체의 postOn()을 호출할지, SNS의 어떤 구현체의 post()를 호출할지 총 두번의 동적 디스패치가 이루어집니다.  
더블 디스패치를 이용하면 SNS 구현체 추가 시 postOn() 메서드에서 SNS instanceof 분기처리 없이 확장가능한 로직을 만들 수 있습니다.



## 5. 추상 클래스

```java
abstract class GraphicObject {
    int x, y;
   
    void moveTo(int newX, int newY) {
        ...
    }
  
    abstract void draw();
    abstract void resize();
}

class Circle extends GraphicObject {
    void draw() {
        ...
    }
    void resize() {
        ...
    }
}
class Rectangle extends GraphicObject {
    void draw() {
        ...
    }
    void resize() {
        ...
    }
}
```

abstract 키워드가 선언된 클래스로 추상 메서드를 포함할 수 도 있고, 포함하지 않을 수도 있습니다.
추상 클래스는 인스턴스화 할 수 없지만,  추상클래스를 상속하여 sub class가 인스턴스화 할 수 있습니다.
추상 메서드를 선언하면 sub class에서 해당 추상 메서드를 구현하거나, sub class도 abstract 선언하여 추상화해야 합니다.



### 추상 클래스와 인터페이스 중 어는 것을 사용해야 하나요?

**추상 클래스**

- 밀접하게 관련된 여러 클래스 간에 필드나 메서드를 공유할 경우
- 많은 공통 메서드나 필드가 있거나 접근제어자 public 이외의 좁은 범위가 필요한 경우
- 인스턴스 변수나 final 인스턴스 변수를 사용해야 할 경우

**인터페이스**

- 여러 클래스간의 필드나 메서드를 공유하지 않을 경우
- 다중 상속을 활용하고 싶은 경우
- 특정 데이터 유형의 행동을 지정하고 싶지만, 해당 동작을 구현하는 사람은 신경 쓰지 않을 경우



## 6. final 키워드

### 변수

```java
private final int number = 10;
```

변수에서 final 키워드를 선언 시 해당 변수는 재할당이 불가능합니다.
재할당 할 경우 컴파일 에러가 발생합니다.



### 메서드

```java
public final int getNumber() {
   ... 
}
```

메서드에 final 키워드를 사용하면, 해당 메서드는 오버라이딩을 할 수 없는 메서드입니다.
sub class에서 재정의가 불가능합니다.
재정의 할 경우 컴파일 에러가 발생합니다.



### 클래스

```java
public final class Foo {
  ...
}
```

클래스에 final 키워드를 사용하면 해당 클래스는 상속을 할 수 없는 클래스입니다.
상속을 하는 경우 컴파일 에러가 발생합니다.



## 7. Object 클래스

Object 클래슨는 모든 클래스의 직간적접인 super class입니다.
모든 클래스는 Object의 인스턴스 메서드를 상속받아 사용할 수 있습니다.



### getClass()

```java
void printClassName(Object obj) {
    System.out.println("The object's" + " class is " +
        obj.getClass().getSimpleName());
}
```

객체의 이름(getSimpleName()), super class(getSuperclass()), 구현된 인터페이스(getInterfaces())등   
클래스에 대한 정보를 가지고 있는 Class 객체를 가져오는데 사용할 수 있는 메서드입니다.  
해당 메서드는 오버라이딩을 할 수 없습니다.



### equals()

```java
public class Book {
    String ISBN;
    
    public String getISBN() { 
        return ISBN;
    }
    
    public boolean equals(Object obj) {
        if (obj instanceof Book)
            return ISBN.equals((Book)obj.getISBN()); 
        else
            return false;
    }
}

public class Main {
		public static void main(String[] args) {
				Book firstBook  = new Book("0201914670");
        Book secondBook = new Book("0201914670");
      
        if (firstBook.equals(secondBook)) {
            System.out.println("objects are equal");
        } else {
            System.out.println("objects are not equal");
        }
		}
}

// reuslt
// objects are equal
```

동일성 연산자(==)를 사용하여 두 객체가 같은지 비교하여 같으면 true, 다르면 flase를 반환합니다.  
primitive type의 경우는 올바른 결과가 나타나지만, reference type의 경우 객체의 메모리주소 값으로 동일한지 판단하기 때문에  
동일한 정보를 포함하는지에 대해서 equals() 메서드를 재정의하여 해당 클래스의 동등성을 보장할 수 있습니다.

### hashCode()

해싱 알고리즘에 의해 생성된 정수 값을 반환합니다.  
두 객체가 서로 같으면 해시코드도 같아야합니다.

오버라이딩을 하지 않으면 기본적으로 인스턴스 주소 값으로 해싱 알고리즘을 통하여 반환하기 때문에  
equals() 메서드를 재정의하면 hashCode() 메서드도 재정의 하여야 두 객체가 동일성을 가지게 됩니다.  
HashSet, HashMap 과 같이 hashCode를 사용하여 동일성 확인하는 동작을 하기 때문에  
hashCode()를 재성의 하지 않으면 동일성을 보장할 수가 없습니다.



### clone()

Cloneable 인터페이스를 구현해야 사용할 수 있으며, 해당 객체와 동일한 객체(복사본)를 만듭니다.  
Cloneable 인터페이스는 구현 해야할 내용은 없지만 복제를 허용함을 명시하기 위해서 사용하고, 구현이 안되어 있는 경우 예외가 발생합니다.  
reference type의 경우 원본의 reference type을 참조하기 때문에 복사본에서 refernce type을 수정 시 원본 객체에서도 반영이 되기 때문에  
오버라이딩을 통하여 DeepCopy를 해줘야 복사본의 refernce type도 독립적으로 사용할 수 있습니다.



### toString()

객체를 문자열로 표현한 값을 반환합니다.  
디버깅 할 때 객체를 표현하는 매우 유용한 메서드이며, 기본적으로는 클래스이름@해시코드 입니다.



### finalize()

객체가 가비지컬렉션에 의하여 소멸 직전에 호출하는 콜백 메서드입니다.  
이 메서드를 통하여 리소스 해제와 같은 정리 작업을 수행하도록 재정의 할 수 있습니다.



참조

https://docs.oracle.com/javase/tutorial/java/IandI/index.html  
https://www.youtube.com/watch?v=s-tXAHub6vg