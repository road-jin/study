# 1주차 과제: JVM은 무엇이며 자바 코드는 어떻게 실행하는 것인가

## 1. JVM이란 무엇인가

Java Virtial Machine의 약자로 Java Bytecode(.class)를 OS(운영체재)가 실행할 수 있는  
네이티브 코드로 변환(Interpreter, JIT Compiler) 하여 실행 합니다.   
해당 운영체제에 맞는 네이티브 코드로 바꿔서 실행해야 하므로,   
JVM은 특정 플랫폼에 종속적이며,  
JVM은 자바(JAVA)와 운영체제 사이에서 중개하는 역할을 수행하기 때문에  
자바가 운영체제에 신경 쓸 필요 없이 재사용이 가능하도록 해줍니다.

## 2. 컴파일 하는 방법

.java -> .class(bytecode)로 변환하는 과정을 컴파일이라고 합니다.

```shell
$ sudo vi Hello.java
public class Hello {
  public static void main(String args[]) {
    System.out.println("Hello JAVA");
  }
}

# javac [options] [sourcefiles-or-classnames]
$ javac Hello.java

$ ls -al
total 8
drwxr-xr-x    2 user  staff  102  8 31  2022 .
drwxr-xr-x    3 user  staff  102  8 31  2022 ..
-rw-r--r--    1 user  staff  414 10 15 16:36 Hello.class
-rw-r--r--    1 user  staff  108 10 15 16:36 Hello.java


-------------------------------------------------------

# ~/cs03 디렉토리에 여러개의 java 파일들이 있으며, 한번에 컴파일을 할려고 합니다.
# ~/test 디렉토리에 컴파일 된 파일들을 생성할려고 합니다.

$ java --version                                                                          
java 17.0.5 2022-10-18 LTS
Java(TM) SE Runtime Environment (build 17.0.5+9-LTS-191)
Java HotSpot(TM) 64-Bit Server VM (build 17.0.5+9-LTS-191, mixed mode, sharing)

$ mkdir test

$ ls -al                                                                                  
total 2
drwxr-xr-x  23 user  staff   736 10 15 20:29 .
drwxr-xr-x@  7 user  staff   224  3 20  2023 ..
drwxr-xr-x  15 user  staff   480  1 12  2023 cs03
drwxr-xr-x   2 user  staff    64 10 15 20:29 test

$ ls -al cs03                                                             
total 80
drwxr-xr-x  15 user  staff   480  1 12  2023 .
drwxr-xr-x  23 user  staff   736 10 15 20:17 ..
drwxr-xr-x  14 user  staff   448 10 15 20:04 .git
drwxr-xr-x  12 user  staff   384 10 15 20:21 .idea
-rw-r--r--   1 user  staff  2338  1 11  2023 ArithmeticLogicUnit.java
-rw-r--r--   1 user  staff  1658  1 11  2023 CommandDecoder.java
-rw-r--r--   1 user  staff  2830  1 11  2023 ControlUnit.java
-rw-r--r--   1 user  staff   954  1 11  2023 Cpu.java
-rw-r--r--   1 user  staff   932  1 11  2023 Main.java
-rw-r--r--   1 user  staff   883  1 11  2023 Memory.java
-rw-r--r--   1 user  staff   578  1 12  2023 OpCode.java
-rw-r--r--@  1 user  staff  2939  1 11  2023 README.md
-rw-r--r--   1 user  staff  1003  1 11  2023 Register.java
-rw-r--r--   1 user  staff   555  1 10  2023 Word.java

$ javac -source 11 -target 11 -d test -cp cs03 cs03/Main.java

$ ls -al test
total 80
drwxr-xr-x  12 user  staff   384 10 15 20:32 .
drwxr-xr-x  23 user  staff   736 10 15 20:29 ..
-rw-r--r--   1 user  staff   989 10 15 20:34 ArithmeticLogicUnit.class
-rw-r--r--   1 user  staff  1407 10 15 20:34 CommandDecoder.class
-rw-r--r--   1 user  staff   982 10 15 20:34 ControlUnit$1.class
-rw-r--r--   1 user  staff  1979 10 15 20:34 ControlUnit.class
-rw-r--r--   1 user  staff  1310 10 15 20:34 Cpu.class
-rw-r--r--   1 user  staff  1155 10 15 20:34 Main.class
-rw-r--r--   1 user  staff  1220 10 15 20:34 Memory.class
-rw-r--r--   1 user  staff  2710 10 15 20:34 OpCode.class
-rw-r--r--   1 user  staff  1760 10 15 20:34 Register.class
-rw-r--r--   1 user  staff   792 10 15 20:34 Word.class


```

### javac option

- -d : 클래스 파일을 생성할 루트 디렉터리를 지정합니다.
    기본적으로 컴파일러는 -d 옵션을 주지 않으면,  각 클래스 파일이 생성된 소스 파일과 동일한 디렉토리에 배치됩니다.

- -cp : 컴파일러가 컴파일 하기 위해서 필요로 하는 참조할 클래스 파일 경로를 지정해줍니다.

- -source : 지정된 자바 버전에 대한 자바 프로그래밍 언어의 규칙에 따라 소스 코드를 컴파일합니다.

- -target :  지정된 자바 버전에 적합한 파일을 생성합니다.
    지정된 자바 버전은 -source에서 지정된 자바 버전과 같거나 이상이어야 합니다.

- javac는 jdk에 들어있습니다.

- 그 외 옵션은 [오라클 javac command 참고](https://docs.oracle.com/en/java/javase/17/docs/specs/man/javac.html) 

    

## 3. 실행하는 방법

.class(bytecode)를 운영체제 실행합니다.

```shell
# To launch a class file:
# java [options] mainclass [args ...]
# To launch the main class in a JAR file:
# java [options] -jar jarfile [args ...]

$ java Hello
Hello JAVA

-------------------------------------------------------

$ jenv shell 11.0

$ java --version                                                                
java 11.0.13 2021-10-19 LTS
Java(TM) SE Runtime Environment 18.9 (build 11.0.13+10-LTS-370)
Java HotSpot(TM) 64-Bit Server VM 18.9 (build 11.0.13+10-LTS-370, mixed mode)

$ java -cp test Main                                                             
[0, 0, 0, 160, 0, 0, 0, 1]
[0, 0, 0, 160, 2, 0, 0, 2]
[4, 0, 0, 160, 2, 0, 0, 3]
[4, 1, 0, 160, 2, 0, 0, 4]
[4, 1, 1, 160, 2, 0, 0, 5]
[4, 1, 4, 160, 2, 0, 0, 6]
```

- -cp : 클래스 경로를 지정합니다.
- 그 외 옵션은 [오라클 java command 참고](https://docs.oracle.com/en/java/javase/17/docs/specs/man/java.html)



## 4. 바이트코드란 무엇인가

.class 파일에 있는 내용이 바이트코드입니다.  
JVM이 이해할 수 있는 바이너리(2진수) 코드이며, 명령어의 크기가 1byte라서 바이트코드라고 불립니다.   
바이트코드로 JVM이 여러 OS에서 실핼할 수 있게 해줍니다.

```shell
# javap 명령어를 이용하여 클래스 파일을 분해해서 OP code로 보여줍니다.

$ javap -v Hello.class                                                   
Classfile /Users/minkyujin/Desktop/Hello.class
  Last modified 2023. 10. 15.; size 414 bytes
  MD5 checksum b9c7402a4010f913625f0a87e43ab762
  Compiled from "Hello.java"
public class Hello
  minor version: 0
  major version: 55
  flags: (0x0021) ACC_PUBLIC, ACC_SUPER
  this_class: #5                          // Hello
  super_class: #6                         // java/lang/Object
  interfaces: 0, fields: 0, methods: 2, attributes: 1
Constant pool:
   #1 = Methodref          #6.#15         // java/lang/Object."<init>":()V
   #2 = Fieldref           #16.#17        // java/lang/System.out:Ljava/io/PrintStream;
   #3 = String             #18            // Hello JAVA
   #4 = Methodref          #19.#20        // java/io/PrintStream.println:(Ljava/lang/String;)V
   #5 = Class              #21            // Hello
   #6 = Class              #22            // java/lang/Object
   #7 = Utf8               <init>         // 생성자
   #8 = Utf8               ()V
   #9 = Utf8               Code
  #10 = Utf8               LineNumberTable
  #11 = Utf8               main
  #12 = Utf8               ([Ljava/lang/String;)V
  #13 = Utf8               SourceFile
  #14 = Utf8               Hello.java
  #15 = NameAndType        #7:#8          // "<init>":()V
  #16 = Class              #23            // java/lang/System
  #17 = NameAndType        #24:#25        // out:Ljava/io/PrintStream;
  #18 = Utf8               Hello JAVA
  #19 = Class              #26            // java/io/PrintStream
  #20 = NameAndType        #27:#28        // println:(Ljava/lang/String;)V
  #21 = Utf8               Hello
  #22 = Utf8               java/lang/Object
  #23 = Utf8               java/lang/System
  #24 = Utf8               out
  #25 = Utf8               Ljava/io/PrintStream;
  #26 = Utf8               java/io/PrintStream
  #27 = Utf8               println
  #28 = Utf8               (Ljava/lang/String;)V
{
  public Hello();
    descriptor: ()V
    flags: (0x0001) ACC_PUBLIC
    Code:
      stack=1, locals=1, args_size=1
         0: aload_0
         1: invokespecial #1                  // Method java/lang/Object."<init>":()V
         4: return
      LineNumberTable:
        line 1: 0

  public static void main(java.lang.String[]);
    descriptor: ([Ljava/lang/String;)V
    flags: (0x0009) ACC_PUBLIC, ACC_STATIC
    Code:
      stack=2, locals=1, args_size=1
         0: getstatic     #2                  // Field java/lang/System.out:Ljava/io/PrintStream;
         3: ldc           #3                  // String Hello JAVA
         5: invokevirtual #4                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
         8: return
      LineNumberTable:
        line 3: 0
        line 4: 8
}
SourceFile: "Hello.java"
```

- 옵션은 [오라클 javap command 참고](https://docs.oracle.com/en/java/javase/17/docs/specs/man/javap.html)



## 5. JIT 컴파일란 무엇이며 어떻게 동작하는가

![jit](https://media.geeksforgeeks.org/wp-content/uploads/CommonArticleDesign20-min-1.png)

자바 실행 속도를 향상시키기 위해서 HopSpot JVM 출시할 때 나온 컴파일 기법입니다.  
실행 시점에서 인터프리트 방식으로 기계어 코드를 생성하면서 그 코드를 캐싱하여, 같은 함수가 여러 번 불릴 때 매번 기계어 코드를 생성하는 것을 방지합니다.

#### 인터프리터(Interpreter)

바이트코드를 한 줄씩 네이티브 코드(기계어)로 변환하여 실행합니다.  
여기서 단점은 하나의 메서드를 여러 번 호출할 때마다 해석이 필요하다는 점입니다.



## 6. JVM 구성 요소

![img](https://blog.kakaocdn.net/dn/vfoxo/btrxwYbrr2y/LOdTzAPekKVYz0J9DSgPfk/img.png)

위 사진은 JVM의 전체적인 구조입니다.



### 클래스 로더(Class Loader)

------

![img](https://blog.kakaocdn.net/dn/OrcrI/btrxu8TaGtW/Igerfq3KD2rnFQE9dlCnTK/img.png)

자바 바이트 코드를 읽어서 JVM의 실행 엔진이 사용할 수 있도록 메모리의 메소드 영역에 적재하는 역할을 합니다.  
로딩 -> 링크 -> 초기화 순으로 진행됩니다.



#### 1. 로딩

클래스 로더가 .class 파일을 읽고 그 내용에 따라 적절한 바이너리 데이터를 만들고, 메소드 영역에 저장됩니다.  
이때 메소드 영역에 저장하는 데이터는 다음과 같습니다.

- FQCN(Fully-Quailified Class Name) : 클래스 로더, 클래스 패키지 경로, 패키지 이름, 클래스 이름을 모두 포함한 값
- Class, Interface, Enum
- 메소드와 상수

로딩이 끝나면 해당 클래스 타입의 Class 객체를 생성하여 힙 영역에 저장하게 됩니다.

![img](https://blog.kakaocdn.net/dn/txjvt/btrxu8eAmrd/1v23XrO7LRJVHr9USpUe50/img.png)

위 사진은 Internal.class가 로딩하는 과정의 예시입니다.  
Application -> Extension -> Bootstrap 클래스 로더의 순서대로 로딩 요청을 위임합니다.  
이후 기본 라이브러리인 rt.jar -> 외부 라이브러리인 ext -> JVM이 프로그램을 실행할 때 클래스를 찾기 위한 기준이 되는 경로인   
classpath 순으로 클래스 로딩을 시도하고, 없다면 ClassNotFoundException이 발생하게 됩니다.



#### 2. 링크

Verify, Prepare, Reolve(optional) 세 단계로 나눠져 있습니다.

- 검증(Verify) : .class 파일 형식이 유효한지 체크합니다.
- 준비(Prepare) : 클래스 변수(static 변수)와 메서드, 기본값에 필요한 메모리를 준비합니다.
- 분석(Resolve) : 심볼릭 메모리 레퍼런스를 메소드 영역에 있는 실제 레퍼런스로 교체합니다. 
    이 과정이 Optional인 이유는 이때 교체될 수도 있고, 나중에 실제 해당 레퍼런스를 사용할 때, 교체될 수도 있습니다.

#### 3. 초기화

Static 변수를 초기화하고 값을 할당하는 과정입니다.  
(static 블록이 있다면 이때 실행하게 됩니다.)   
클래스 로더는 계층 구조로 이루어져 있으며 기본적으로 세 가지 클래스 로더가 제공됩니다.

- 부트 스트랩 클래스 로더 : JAVA_HOME\lib에 있는 코어 자바 API를 제공합니다.  
    최상위 우선순위를 가진 클래스 로더
- 플랫폼(Extension) 클래스 로더 : JAVA_HOME\lib\ext 폴더 또는 java.ext.dirs 시스템 변수에  
    해당하는 위치에 있는 클래스를 읽습니다.
- 애플리케이션 클래스로더 : 애플리케이션 클래스 패스(애플리케이션 실행할 때 주는  
    -classpath 옵션 또는 java.class.path 환경 변수의 값에 해당하는 위치)에서 클래스를 읽습니다.



### 메모리(Memory)

------

#### 1. 메소드(Method) 

메서드 영역에는 메타데이터(런타임 상수 풀, 필드 및 메서드 데이터, 클래스, 인스턴스,   
인터페이스 초기화에 사용되는 생성자 코드와 같은 클래스별 구조)가 저장됩니다.
자바 8 이전에는 Permanent Generation  메모리 영역에 저장이 되었는데, 제한된 크기를 가지고 있어서
동적으로 클래스를 만드는 경우  OutOfMemory 발생할 수 있습니다.
이러한 이유로 자바 8 부터는 Permanent Generation 없어지고, Metaspace 메모리 영역으로 대체되었습니다.

**Permanent Generation**

- Heap에 속하며, 특수한 메모리 영역
- 클래스 메타데이터를 저장됩니다.
- 자바 8 부터 없어졌습니다.
- defalut Max size 
    - 32bit jvm 64MB
    - 64bit jvm 82MB

**Metaspace**

- 클래스 메타데이터를 저장됩니다. (Method area가 실제 저장되는 공간)
- Heap 영역이 아니라, Native 메모리 영역입니다.
- 기본값으로 제한된 크기를 가지고 있지 않으며 필요한 만큼 계속 늘어납니다.



#### 2. 힙(Heap)

new 키워드로 생성된 객체(Instance)를 저장 및 공유하는 영역입니다.  
메소드 영역에 로드된 클래스만 생성이 가능합니다.   
**힙이나 메소드는 모든 영역에 공유되는 자원입니다.**
힙 메모리는 세 부분으로 구성됩니다.

- Eden Space – Young Generation 공간의 일부입니다. 객체를 생성하면 JVM은 이 공간에서 메모리를 할당합니다.
- 생존자 공간(Survivor Space) – 이는 또한 Young Generation 공간의 일부이기도 합니다. 생존 공간에는 GC의 마이너 GC 단계에서 살아남은 기존 객체가 포함됩니다.
- Tenured Space – 이는 Old Generation 공간으로도 알려져 있습니다. 오래 살아남은 개체를 보유합니다. 기본적으로 Young Generation 객체에는 임계값이 설정되며, 이 임계값이 충족되면 이러한 객체는 Tenured 공간으로 이동됩니다.



#### string constant pool

![](https://www.baeldung.com/wp-content/uploads/2021/02/stringpool.png)

```java
String constantString1 = "Baeldung";
String constantString2 = "Baeldung";
        
assertThat(constantString1).isSameAs(constantString2); // true
```

Java에서 문자열은 불변성을 가지고 있기 때문에 JVM은 문자열의 복사본 하나만 저장하여 할당된 메모리 양을 최적화 할 수 있습니다.  
이과정을 interning이라고 합니다.  
문자열 변수를 생성하고 리터널 값을 할당하면 JVM은 poll에서 동일한 값의 문자열을 찾고, 있으면 메모리 주소를 참조하고 없으면 pool에 추가 메모리를 할당합니다.

  

```java
String constantString = "Baeldung";
String newString = new String("Baeldung");
 
assertThat(constantString).isNotSameAs(newString); // true

String fifth = "Baeldung";
String sixth = new String("Baeldung");
System.out.println(fifth == sixth); // false

String third = new String("Baeldung");
String fourth = new String("Baeldung"); 
System.out.println(third == fourth); // false
```

new 연산자를 이용하여 문자열을 생성하면 새 객체로 인식하고 JVM의 Heap 영역에 저장됩니다.    
이와 같이 생성된 모든 문자열은 자체 주소를 가지고, 기존 문자열 리터널이 저장되어 있는 String constant pool 영역과 다른 메모리 영역입니다.  
성능을 위해서 가능하면 문자열 리터럴 표기법으로 사용해야 컴파일러가 코드를 최적할 할 수 있습니다.

java 8이전에는 Permanent Generation 영역에 String constant pool을 배치하였는데, 이는 런타임 시 확장할 수 없고 GC에 수집 대상이 아니였습니다.  
그래서 String을 interning을 많이 하면 OutOfMemory 오류가 발생하는 경우가 생겨서 java 8 부터는 GC가 수집할 수 있는 Heap 영역에 저장됩니다.  
gc가 사용하지(참조 하지) 않는 문자열은 pool 제거하여 OutOfMemory 오류의 위엄이 줄어들었습니다.  
`XX:StringTableSize={size}`  로 String constant pool 크기를 지정할 수 있습니다.




#### 3. 스택(Stack)

Stack 영역에는 스레드마다 생성이 되는 영역이며, 메서드가 호출 될 때 마다 프레임이 생성됩니다. 각 프레임에는 세 부분이 포함됩니다.

- 지역 변수 배열 – 메소드의 모든 지역 변수와 매개변수를 포함합니다.
- 피연산자 스택 – 중간 계산 결과를 저장하기 위한 작업 공간으로 사용됩니다.
- 프레임 데이터 – 부분 결과, 메서드의 반환 값, 예외 발생 시 해당 catch 블록 정보를 제공하는 *예외 테이블에 대한 참조를 저장하는 데 사용됩니다.*

![img](https://blog.kakaocdn.net/dn/5MCVy/btrxjnwSWpE/Zw9rtmthnyW3nChaDEbwd0/img.png)
 예로 들어 에러가 발생할 경우 위 사진처럼 메소드 호출 스택이 쌓여 보여 줍니다.

#### 4. PC 레지스터(Program Counter Register)

스레드마다 생성이 되는 영역이고,  스레드가 현재 어떠한 명령 및 주소(스택 프레임)를 실행할 것인지를 가록 합니다.  
참조 : https://javapapers.com/core-java/java-jvm-run-time-data-areas/#Program_Counter_PC_Register

#### 5. 네이티브 메소드 스택(Native Method Stack)

스레드마다 생성이 되는 영역이고, 자바 외 언어로 작성된 네이티브 코드(주로 C, C++)를 수행하기 위한 스택(JNI) 영역입니다.  
**스택, PC레지스터, 네이티브 메소드 스택은 스레드마다 생성이 되고, 다른 스레드끼리 서로 공유되지는 않습니다.**



### 실행 엔진(Execution Engine)

------

#### 1. 인터프리터(Interpreter)

바이트코드를 한 줄씩 네이티브 코드로 변환하여 실행합니다.

#### 2. JIT 컴파일러(JIT Compiler)

인터프리터 효율을 높이기 위해, 인터프리터가 반복되는 코드를 발견하면  
JIT 컴파일로 반복되는 코드를 모두 네이티브 코드로 바꿔 둡니다.  
그다음부터 인터프리터는 네이티브 코드로 컴파일된 코드를 바로 사용하게 됩니다.

#### 3. 가비지 컬렉터(Garbage Collector) 추가적으로 공부

가비지 컬렉터는 공통적으로 크게 2가지 작업을 합니다.

1. Heap 내의 객체 중에서 garbage를 찾아냅니다.
2. 찾아낸 garbage를 처리해서 Heap memory를 회수합니다.

garbage를 판별하기 위해서 reacheabilty라는 개념을 사용하는데, 어떤 객체에 유효한 참조가 있으면 `reachable`
없으면 `unreachable` 로 구별하고,  `unreacheable` 객체를 garbage로 간주하여 가비지 컬렉터를 수행합니다.

이어서 더 해야함.





### JNI(Java Native Interface)

------

자바 애플리케이션에서 C, C++, 어셈블리로 작성된 함수를 사용할 수 있는 방법을 제공합니다.  
Native 키워드를 사용한 메소드를 호출합니다.

![img](https://blog.kakaocdn.net/dn/1r6XE/btrxiGKhBZo/fgtlbR07ZLLIMAfxKfcZ30/img.png)

스레드에서 대기시간을 설정하는 Thread.sleep() 메소드도 native 키워드를 사용하는 메소드입니다.  
참조 : https://medium.com/@bschlining/a-simple-java-native-interface-jni-example-in-java-and-scala-68fdafe76f5f

 

### 네이티브 메소드 라이브러리(Native Method Library)

------

C, C++로 작성된 라이브러리입니다.

## 7. JDK와 JRE의 차이

![](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FbzEcBB%2Fbtrxr4CFDPt%2FQj1DxUDGF5mWGGrhOsAk01%2Fimg.png)

![](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2Fl8v4A%2Fbtrxu8FrYHl%2F2zhkAIPekqU1JdR1vtUg3K%2Fimg.webp)

### JRE (Java Runtime Environment)

------

JRE의 배포판은 목적은 자바 애플리케이션을 실행할 수 있도록 구성된 배포판이며,  
JVM과 핵심 라이브러리 및 자바 런타임 환경에서 사용하는 프로퍼티 세팅이나 리소스 파일을 가지고 있습니다.(JVM + 라이브러리)  
개발자의 경우에는 JRE만 받아서 쓰는 경우는 거의 없습니다.  
왜냐하면, 자바를 개발하는 데에 필요한 도구들은 포함되지 않습니다. (개발 관련 도구는 JDK에서 제공됩니다.)

 

### JDK (Java Development Kit)

------

JDK는 Java를 사용하기 위해 필요한 모든 기능(JRE + 개발 관련 도구들)을 갖춘 배포판입니다.  
오라클은 자바 11부터는 JDK만 제공하고 있습니다.  
모듈 시스템을 사용할 수 있어서 사용자가 JRE를 만들 수 있기 때문에 JRE를 따로 제공하지 않습니다.



출처
https://docs.oracle.com/en/java/javase/17/docs/specs/man/javac.html  
https://docs.oracle.com/en/java/javase/17/docs/specs/man/java.htm  
https://docs.oracle.com/en/java/javase/17/docs/specs/man/javap.html  
https://www.inflearn.com/course/the-java-java8   
https://www.inflearn.com/course/the-java-code-manipulation   
https://www.baeldung.com/java-string-pool  
https://www.baeldung.com/java-string-constant-pool-heap-stack  
https://docs.oracle.com/en/middleware/soa-suite/soa/12.2.1.4/administer/configuring-reference-configuration-domain.html#GUID-18F3C6CA-E0B5-4B41-BFF7-5138221F1007   
https://dzone.com/articles/permgen-and-metaspace