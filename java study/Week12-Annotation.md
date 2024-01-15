# 12주차 과제: 애노테이션

## 1. 애노테이션이란?

사전적 의미로 주석을 의미하며, 프로그램에게 메타데이터(데이터를 설명해주는 데이터)를 제공합니다.

### 애노테이션의 용도

- 컴파일러는 애노테이션을 사용하여 오류를 감지하거나, 경고를 없앨 수 있도록 정보를 제공합니다.
- 컴파일 및 배포시 애노테이션의 정보로 코드, 파일 등을 생성할 수 있도록 정보를 제공합니다.
- 런타임 시 특정 기능을 실행 하도록 정보를 제공합니다.



### 애노테이션 정의하는 방법

```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CustomAnnotation {

  String name() default "CustomAnnotation";

  int[] value();

  Class<?> foo() default Foo.class;

  Day day() default Day.SUNDAY;
}

public enum Day {
		SUNDAY,
		MONDAY,
		TUESDAY,
		WEDNESDAY,
		THURSDAY,
		FRIDAY,
		SATURDAY
}

@CustomAnnotation({1,2})
public class AnnotationMain {
}
```

위와 같이 애노테이션을 정의할 수 있으며, 컴파일 과정에서 java.lang.annotation.Annotation implements를 합니다.  
어노테이션은 매개변수와 예외를 선언할 수 없으며, 애노테이션에는 요소(element)를 가질 수 있습니다.  
요소는 상수만 가질 수 있으며 아래와 같습니다.

**요소의 종류**

- primitive type
- String Type
- enum type
- Class Type
- Annotation type
- Array type



## 2. 애노테이션 종류

### General Purpose Annotation

이미 사전에 정의된 범용적인 주석의 유형을 말합니다.  
종류는 아래와 같습니다.

**@Deprecated**

더 이상 사용 되지 않으며, 사용되어서는 안 됨을 나타냅니다.  
컴파일러는 헤딩 애노테이션이 포함된 메서드, 클래스, 필드를 사용할 때마다 경고를 생성합니다.



**@Override**

메서드에 선언되며, Super Class에 메서드를 재정의 함을 나타냅니다.  
Super Class 중에서 메서드를 올바르게 재정의 하지 못하는 경우에 컴파일러는 오류를 생성합니다.



**@SuppressWarnings**

선언된 곳에 컴파일가 생성하는 특정 경고를 무시하도록 합니다.  
String 배열 형태로 여러개의 경고를 무시할 수 있도록 값을 지정할 수 있으며,   
`javac --help-lint` 명령어를 통해 어떤 경고를 지원하는지 알 수 있습니다.



**@SafeVarargs**

가변인자를 사용하는 메서드나, 생성자에 선언할 경우 해당 타입은 안전성을 보장함을 나타냅니다.  
가변인자 사용시 발생하는 컴파일 경고를 무시합니다.



**@FunctionalInterface**

해당 인터페이스는 함수형 인터페이스를 지원함을 나타냅니다.  
메서드가 없거나 두개 이상인 경우에 컴파일 오류를 생성합니다.



### Meta Annotation

애노테이션에 적용되는 애노테이션으로써, 애노테이션을 설명하는 데이터입니다.

**@Retention**

해당 애노테이션이 어느 시점까지 유지되는지를 나타냅니다.

- RetentionPolicy.SOURCE : 소스 수준에서만 유지되며 컴파일러에서는 무시됩니다.
- RetentionPolicy.CLASS : 컴파일 시 컴파일러에 의해 유지되지만 JVM(Java Virtual Machine)에서는 무시됩니다.
- RetentionPolicy.RUNTIME :  JVM에 의해 유지되므로 런타임 환경에서 사용할 수 있습니다.



**@Document**

  해당 애노테이션을 Javadoc 도구를 사용하여 문서화되어야 함을 나타냅니다



**@Tartget**

해당 어노테이션을 적용될 수 있는 위치를 나타냅니다.

- ElementType.ANNOTATION_TYPE : 애노테이션 유형에 적용할 수 있습니다.
- ElementType.CONSTRUCTOR : 생성자에 적용할 수 있습니다.
- ElementType.FIELD : 필드나 속성에 적용할 수 있습니다.
- ElementType.LOCAL_VARIABLE : 지역 변수에 적용할 수 있습니다.
- ElementType.METHOD : 메소드에 적용할 수 있습니다.
- ElementType.PACKAGE : 패키지 선언에 적용될 수 있습니다.
- ElementType.PARAMETER : 메소드의 매개변수에 적용할 수 있습니다.
- ElementType.TYPE : 클래스의 모든 요소에 적용할 수 있습니다.



**@Inherit**

해당 애노테이션이 Sub Class에도 상속될 수 있음을 나타냅니다.



**@Repeatable**

자바 8에 도입 되었으며, 해당 애노테이션은 두번 이상 선언할 수 있음을 나타냅니다.



### Custom Annotation

개발자가 정의한 애노테이션을 말합니다.



## 3. 애노테이션 프로세서

Annotation Processor는 컴파일 단계에서 Annotation에 정의된 일렬의 프로세스를 동작하게 하는 것을 의미합니다.   
컴파일 단계에서 실행되기 때문에, 빌드 단계에서 에러를 출력하게 할 수 있고, 소스코드 및 바이트 코드를 생성할 수도 있습니다.  
Abstract Pocessor라는 추상 클래스를 상속하여 Annotation Processor를 구현해 보겠습니다.



**주요 메서드 및 종류**

- processingEnv
    - Annotation Processor가 컴파일 동안 사용할 있는 도구와 정보를 제공하는 인터페이스입니다.
- roundEnv
    - getElementsAnnotatedWith(Magic.class)
        - Magic Annotation이 붙어 있는 Element들을 가져옵니다
- Element 
    - 클래스, 인터페이스, 메소드 등 애노테이션을 붙일 수 있는 target
    - getKind()
        - 해당 Type 종류를 반환합니다. ex) ElementKind.INTERFACE
    - getSimpleName()
        - 해당 Type의 이름을 반환합니다. ex) Interface 이름

- getSupportedAnnotationTypes() 
    -  해당 Processor가 어떤 Annotation을 처리할 것인지를 정의하는 메소드입니다.
- getSupportedSourceVersion()
    - 어떤 자바버전을 지원할지 정의하는 메소드입니다.
- process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv)
    - AbstractProcessor의 필수적으로 구현해야 하며, Processor가 어떻게 동작해야 하는지를 정의하는 메소드입니다.  
        return 값이 true일 경우 해당 Annotation에 대한 다른 Processor한테 더 이상 처리하지 말라고 합니다.  
        false일 경우 또 다른 Processor가 처리할 수 있습니다.





참조

https://www.geeksforgeeks.org/annotations-in-java

https://docs.oracle.com/javase/tutorial/java/annotations/index.html

https://www.baeldung.com/java-annotation-attribute-value-restrictions

https://roadj.tistory.com/9

https://stackoverflow.com/questions/1205995/what-is-the-list-of-valid-suppresswarnings-warning-names-in-java
