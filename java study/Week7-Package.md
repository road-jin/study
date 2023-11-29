# 7주차 과제: 패키지

## 1. package

class, interface, enum, acnnotation의 액세스 보호 및 네임스페이스(논리적 그룹 식별자)관리를 제공하는 그룹입니다.  
개념적으로 패키지는 컴퓨터의 폴더와 유사합니다.  
서로 관련된 유형을 그룹화 하면 효율적으로 관리할 수 있으며,  `.`으로 패키지의 계층화를 나타냅니다.  
패키지 문은 소스파일의 첫 번째 줄이어야하고, 각 소스 파일에는 하나의 패키지 문만 있어야만 합니다.  
모든 클래스에는 정의된 클래스 이름과 패키지 이름이 있어야 하며, FQCN(Fully Qualified Class Name)이라고 합니다.



### Naming Conventions 

- 패키지는 모두 소문자로 작성합니다.
- 역방향 인터넷 도메인 이름을 사용하여 패키지 이름을 시작합니다.
    - 도메인이 mypackage.example.com -> 패키지는 com.example.mypackage
- 되도록 자바 예약어, 숫자, 기타 문자를 사용하지 않아야 하며, 어쩔 수 없이 사용해야 할 경우  `_`을 추가하여 사용합니다



### Types of packages

**Built-in Packages**
jre의 lib 폴더에 있는 rt.jar에 있는 일반적으로 사용되는 자바의 내장 패키지입니다.

- **java.lang:** 언어 지원(primitive data types, math operations) 클래스들이 포함되어 있으며, 자동적으로 import 합니다.

- **java.io:** 입출력 작업을 지원하기 위한 클래스가 포함되어 있습니다.

- **java.util:** 날짜/시간 연산 및 Linked List 같은 데이터 자료구조를 구현하는 유틸리티 클래스가 포함되어 있습니다.

- **java.applet:** Applets 생성을 위한 클래스가 포함되어 있습니다.

- **java.awt:** 그래픽 사용자 인터페이스의 컴포넌트를 구현하기 위한 클래스를 포함되어 있습니다.

- **java.net:** 네트워킹 작업을 지원하기 위한 클래스를 포함되어 있습니다.



**user-defined packages**
사용자 정의한 패키지들입니다.



**unnamed package**
패키지가 지정되지 않은 클래스들은 이름이 지정되지 않은 특별한 패키지로 이동됩니다.



## 2. import

해당 패키지의 지정된 클래스 및 패키지 경로에 있는 모든 클래스를 가져오는데 사용합니다.
패키지 문 다음 줄에 선언합니다.



### Fully Qualified Class Name Reference

```java
class GFG {
    
    public static void main(String[] args) {
        // Fully Qualified Name Reference
        java.util.ArrayList<String> arrayList = new java.util.ArrayList<String>();
    }
}
```

import 문을 사용하지 않고 FQCN을 사용하여 유형을 가져올 수 있습니다.
자주 사용하지 않은 경우에만 권장하며, 반복적으로 사용되면 import를 하여 사용하는게 코드 읽기가 편합니다.



### Package  import

```java
package org.study.example;

import graphics.Rectangle;

public class Foo {
  
  public static void main(String[] args) {
    Rectangle myRectangle = new Rectangle();
  }
}
```

특정 유형을 가져와야 하는 경우 사용하며, package 문 뒤에 추가합니다.
특정 유형을 여러번 사용해야 할 경우 효과적이며, FQCN을 사용하지 않아 가독성이 좋습니다.
같은 패키지인 경우 import를 생략 할 수 도 있습니다.



### Entire Package import

```java
package org.study.example;

import graphics.*;

public class Foo {
  
  public static void main(String[] args) {
    Rectangle myRectangle = new Rectangle();
    Circle myCircle = new Circle();
  }
}
```

특정 패키지에 포함된 모든 유형을 가져오는데 사용하며, 와일드(*)카드 문자가 포함된 import 문을 사용합니다.


### Static import

```java
package org.study.example;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
// import static java.lang.Math.*;

public class Foo {
  
  public static void main(String[] args) {
    double r = cos(PI * theta);
  }
}
```

static final field와 static method를 자주 접근해야 하는 경우 static import를 사용하여 패키지 및 클래스 이름을 쓰지 않고 사용할 수 있습니다.



## 3. 접근제어자

외부로부터 접근을 제한하는 제어자입니다.  
클래스, 변수, 메서드에 사용이 가능하며, 클래스의 경우는 기본적으로는 public, default만 가능하지만 중첩 클래스의 경우에는 모두 사용가능합니다.



**종류**

| 접근 제어자 | 클래스 내부 | 동일 패키지 | 자손 클래스 | 그 외의 영역 |
| :---------: | :---------: | :---------: | :---------: | :----------: |
|   public    |      O      |      O      |      O      |      O       |
|  protected  |      O      |      O      |      O      |      X       |
|   default   |      O      |      O      |      X      |      X       |
|   private   |      O      |      X      |      X      |      X       |



## 4. 클래스패스

자바의 가상머신 (JVM)이 컴파일된 클래스 파일(.class)을 실행시킬 때, 클래스패스에 설정된 경로에서 해당 파일을 찾습니다.   
클래스 경로에는 세미콜론(Windows) 또는 콜론(UNIX)으로 구분된 여러 경로가 포함될 수 있습니다   
최근에는 운영체제 상의 환경변수로 클래스패스를 설정하는 것은 지양하고 IDE나 빌드도구를 통해서 클래스패스를 설정한다. 



## 5. CLASSPATH 환경변수

```shell
// CLASSPATH 화면에 표시
Windows: set CLASSPATH
UNIX: echo $CLASSPATH

//CLASSPATH 설정
Windows: set CLASSPATH=C:\users\george\java\classes
UNIX: CLASSPATH=/home/george/java/classes; export CLASSPATH


// CLASSPATH 삭제
Windows: set CLASSPATH=
UNIX: unset CLASSPATH; export CLASSPATH
```



## 6. -classpath 옵션

```shell
$ java –classpath c:\javaproject\classes 
```



참고

https://docs.oracle.com/javase/tutorial/java/package/createpkgs.html  

https://www.geeksforgeeks.org/packages-in-java/

https://www.geeksforgeeks.org/import-statement-in-java/?ref=header_search\

https://gintrie.tistory.com/67