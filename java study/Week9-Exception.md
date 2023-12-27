# 9주차 과제: 예외 처리



## 1. 자바에서 예외 처리 방법 (try, catch, throw, throws, finally)

### 	try block

```java
try {
  // 예외를 발생시킬 수 있는 코드를 작성
}
```

예외 처리를 구성하는 첫 번째 단계이며, 예외를 발생시킬 수 있는 코드를 try block 내에 포함시킵니다.  
try block에서 예외가 발생하면 catch block, finally block이 호출됩니다.



### catch block

```java
try {
  // 예외를 발생시킬 수 있는 코드를 작성 
} catch( 예외 유형 | 예외 유형2 변수명){
  // 예외 유형이 발생하였을 때, 예외를 처리하기 위한 코드를 작성
} catch( 예외 유형3 변수명){
  // 예외 유형이 발생하였을 때, 예외를 처리하기 위한 코드를 작성
}

// ex
try { 

} catch(IOException | SQLException ex) {
  System.err.println("IOException or SQLException: " + e.getMessage()); 
} catch (IndexOutOfBoundsException e) { 
   System.err.println("IndexOutOfBoundsException: " + e.getMessage()); 
} catch (IOException e) { 
   System.err.println("Caught IOException: " + e.getMessage()); 
}
```

해당 예외가 발생하였을 때, 예외를 처리하기 위한 block입니다.  
예외는 Throwable을 상속받은 클래스만 가능합니다.  
하나의 예외 처리기에 두 가지 이상의 예외 유형을 잡을 수 도 있으며, 여러개의 예외 처리기를 작성 할 수도 있습니다.

**Chained Exceptions**

```java
try {
  throw new RuntimeException("RuntimeException");
} catch (RuntimeException e) {
  throw new IllegalArgumentException("IllegalArgumentException", e);
}

/*
reuslt 
 
Exception in thread "main" java.lang.IllegalArgumentException: IllegalArgumentException
	at Main.main(Main.java:18)
Caused by: java.lang.RuntimeException: RuntimeException
	at Main.main(Main.java:16) 
*/
```

첫번째 예외로 인해서 다른 예외를 발생되는 경우에 대해서 추적할 수 있게 합니다.  
Throwable의 initCause 메서드를 통하여  사용이 가능하며, 생성자를 통해서도 가능합니다. 

**initCause를 사용하는 메서드 및 생성자**

- Throwable getCause() 

- Throwable initCause(Throwable) 

- Throwable(String, Throwable) 

- Throwable(Throwable)

    



### finally block

```java
try {
  // 예외를 발생시킬 수 있는 코드를 작성 
} catch( 예외 유형 | 예외 유형2 변수명){try {
	// 예외 유형이 발생하였을 때, 예외를 처리하기 위한 코드를 작성
} finally {
	// 예외가 발생하거나, 발생하지 않았을 때 모두 실행되는 코드를 작성
}
```

예외가 발생하거나 발생하지 않았을 때,  모든 경우에 실행됩니다.  
try block에서 종료하기 전에 무조건 실행되어야 하는 있을 경우에 주로 사용합니다.  
try, catch block에서 return, continue, break를 사용하더라도 finally는 실행됩니다.



### try-with-resources

```java
// 이전의 방식
FileReader fr = new FileReader(path); 
BufferedReader br = new BufferedReader(fr); 

try { 
    return br.readLine(); 
} finally { 
    br.close(); 
    fr.close(); 
} 


// try-with-resources
try (FileReader fr = new FileReader(path);
    BufferedReader br = new BufferedReader(fr)) {
    return br.readLine();
}
```

자바 7부터 자원을 닫히도록 보장 해주는 구문이 추가되었습니다.  
AutoCloseable 인터페이스를 구현한 클래스의 경우에만 사용할 수 있습니다.



### throw

```java
throw new EmptyStackException();
```

throw 키워드를 통하여 예외를 발생시키는 구문입니다.  
발생시킬 수 있는 예외 클래스는 Throwable를 상속받은 Sub Class입니다.



### throws

```java
public void writeList() throws IOException {
	...
}

public void writeList() throws IOException, IndexOutOfBoundsException {
	...
}
```

해당 메서를 호출하는 상위 메서드가 예외를 처리하도록 하고 싶은 경우에 사용합니다.  
throws {예외 유형} 으로 작성하며, 추가적인 예외 유형이 더 있을 경우 ,를 통해서 작성합니다.



## 2. 자바가 제공하는 예외 계층 구조

![예외 구조](https://docs.oracle.com/javase/tutorial/figures/essential/exceptions-throwable.gif)

### Exception과 Error의 차이

**Error**  
JVM에서 동적 연결 오류나 기타 하드 오류가 발생하면 에러를 던집니다.  
보통은 시스템 리소스 문제로 발생하게 되며, 개발자가 예외 처리를 통하여 프로그램을 정상적으로 복구할 수가 없습니다.



**Exception**  
예외는 심각한 시스템 문제가 아니며, 프르그램 실행 중에 발생하여 프로그램 명령의 정상적인 흐름을 방해하는 이벤트입니다.  
개발자가 예외 처리를 통하여 프로그램을 정상적으로 복구 할 수 가 있습니다.



### UnChecked Exception과 Checked Exception 차이

**UnCheckedException**  
자바 컴파일 타임에서 확인되지 않은 예외입니다.   
RuntimeException, Error 클래스 및 Sub Class들을 말합니다.  
예외를 처리하지 않아도 컴파일 되지만, 런타임 시점에 예외가 발생합니다.



**CheckedException**  
자바 컴파일 타임에 확인될 수 있는 예외입니다.   
UnChecked Exception을 제외한 나머지 예외입니다.  
호출 스택에 예외를 선언전으로 던지거나 직접 처리를 해야하며, 프로그램 제어 범위 밖의 오류를 나타냅니다.  
예를 들어 파일이 없는 경우 FileNotFoundException과 같은 예외가 발생하기 때문에 예외를 무조건 처리를 해야 컴파일이 가능합니다.



## 3. 커스텀한 예외 만드는 방법

```java
public class UserNotFoundException extends RuntimeException {

	public UserNotFoundException() {
	}

	public UserNotFoundException(String message) {
		super(message);
	}

	public UserNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public UserNotFoundException(Throwable cause) {
		super(cause);
	}
}
```

Exception 클래스 및 Exception Sub Class를 상속받아서 사용자가 정의한 Exception을 만들 수 있습니다.



참조

https://docs.oracle.com/javase/tutorial/essential/exceptions/index.html

https://www.baeldung.com/java-checked-unchecked-exceptions
