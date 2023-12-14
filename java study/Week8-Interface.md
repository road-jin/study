# 8주차 과제: 인터페이스



## 1. 인터페이스 정의하는 방법

인터페이스는 클래스와 유사한 referce type으로 상수, 메서드 시그니처, default method, static method, nested type만 포함할 수 있습니다.  
인터페이스는 인스턴스화할 수 없으며, 클래스에 의해서만 구횐되거나 다른 인터페이스로 확장할 수 있습니다.  

```java
public interface GroupedInterface extends Interface1, Interface2, Interface3 {

    // constant declarations
    
    // base of natural logarithms
    double E = 2.718282;
 
    // method signatures
    void doSomething (int i, double x);
    int doSomethingElse(String s);
}
```

인터페이스는 접근 제어자, interface 키워드, 인터페이스 명,  쉼표로 구분된 상위 인터페이스 목록(있는 경우) 및 인터페이스 본문으로 구성됩니다.

### 본문 구성

- 필드는 public static final을 가진 필드만 가질수 있으며, 생략할 수 있습니다.
- 메서드는 abstract method, default method, static method, private method, private static method를 가질 수 있습니다.
    - absract methnod는 public abstract로 선언되며, 생략할 수 있습니다.
    - default method는 자바 8에 추가 되었으며, 메서드를 정의할 수 있습니다.
    - static method는 자바 8에 추가 되었으며, 정적 메서드를 정의 할 수 있습니다.
    - private method는 자바 9에 추가 되었으며, private 접근 제어의자의 메서드를 정의 할 수 있습니다.
    - private static method는 자바 9에 추가 되었으며, private 접근 제어의자의 정적 메서드를 정의 할 수 있습니다.



## 2. 인터페이스 구현하는 방법

```java
public interface Phone {
  
  String information();
}

public class IPhone14Pro implements Phone {
  
  @Override
  public String information() {
    return "IPhone 14 Pro";
  }
}
```

인터페이스를 구현하는 클래스에 implements를 선언하고 해당 인터페이스 명을 포함시킵니다.  
두개 이상의 인터페이스를 구현할 수 있으며, 쉼표로 구분합니다.



## 3. 인터페이스 레퍼런스를 통해 구현체를 사용하는 방법

```java
public interface Phone {
  
  String information();
}

public class IPhone14Pro implements Phone {
  
  @Override
  public String information() {
    return "IPhone 14 Pro";
  }
}

public class GalaxyZFlip5 implements Phone {
  
  @Override
  public String information() {
    return "Galaxy Z Flip 5";
  }
}

public class Main {
  
  public static void main(String[] agrs) {
    Phone iphone = new IPhone14Pro();
    Phone galaxy = new GalaxyZFlip5();
    
    System.out.println(iphone.information());
    System.out.println(galaxy.information());
  }
}

// result
// IPhone 14 Pro
// Galaxy Z Flip 5
```

super class의 기능을 sub class가 대체할 수 있습니다.  
즉, 해당 인터페이스 타입의 referce type 변수의 인스턴스는 해당 인터페이스를 구현한 클래스로 대체할 수 있습니다.



## 4. 인터페이스 상속

```java
public interface Print {
  
  void print();
}

public interface Scan {
  
  void scan();
}

public interface Fax {
  
  void recive();
  
  void send();
}

public class MultiFunctionPrinter implements Print, Scan, Fax {
  
  @Override
  public void print() {
    ...
  }
  
  @Override
  public void scan() {
    ...
  }
  
  @Override
  public void recive() {
    ...
  }
  
  @Override
  public void send() {
    ...
  }
}


```

 추상 클래스나, 클래스와 다르게 인터페이스는 다중 상속을 지원합니다.  
또한 인터페이스는 인터페이스만 상속 받을 수 있습니다.



## 5. 인터페이스의 기본 메소드 (Default Method), 자바 8

```java
public interface Print {
  
  void print();
  
  default void testPrint() {
  	System.out.println("test");
  }
}

public class Pinter implements Print {
  
  @Override
  public void print() {
    ...
  }
}


public class Main {
  
  public static void main(String[] agrs) {
    Print printer = new Printer();
    printer.testPrint();
  }
}
```

자바 8 부터 default method가 추가 되었으며, 인터페이스에서 메서드 정의 할 수 있습니다.  
구현체에서 오버라이딩을 통해 메서드 재정의도 가능합니다.



## 6. 인터페이스의 static 메소드, 자바 8

```java
public interface Print {
  
  void print();
  
  default void testPrint() {
  	System.out.println("test");
  }
  
  static void clean() {
    System.out.println("clean");
  }
}

public class Foo {
  
  public static void main(String[] args) {
    Print.clean();
  }
}

// result
// clean
```

자바 8 부터 static method가 추가 되었으며, 인터페이스에서 정적 메서드 정의를 할 수 있습니다.  
default method와 다르게 재정의가 불가능합니다.  
인터페이스.정적 메서드명() 으로만 호출해야 합니다.



## 7. 인터페이스의 private 메소드, 자바 9

```java
public interface Print {
 
  private void test() {
    ...
  }
}
```

자바 9 부터 private method와 private static method를 사용할 수 있습니다.  
default method와 absract method에는 private 접근자를 사용할 수 없습니다.  
이로 인해 외부에 공개를 하지 않아도 될 메서드를 숨길 수 있어 캡슐화가 가능하게 되고, 중복 코드를 줄일 수 있게 되었습니다.



참조

https://docs.oracle.com/javase/tutorial/java/IandI/usinginterface.html