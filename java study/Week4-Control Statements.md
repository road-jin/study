# 4주차 과제: 제어문

## 1. 선택문

### 1. if

```java
// if문
if ( Expression ) {
	Statement
}

// if-else문
if ( Expression ) {
	Statement
} else {
	Statement
}

// if-else if-else
if ( Expression ) {
	Statement
} else if ( Expression ) {
	Statement
} else {
	Statement
}
```

if문을 사용하면 조건부로 Statements을 실행하여,  둘 중 하나만 실행 또는 둘 다 실행하지 않도록 할 수 있습니다.

**if**
Expression이 true 일 경우 블록 안의 Statements가 실행됩니다.



**if-else**
Expression이 true 일 경우 if 블록 안의 Statements가 실행되고, 아닌 경우 else 블록 안의 Statements가 실행됩니다.



**if-else if-else**
if문의 Expression이 true 일 경우 if 블록 안의  Statements가 실행됩니다.  
if문의 Expression이 false이고, else if문의 Expression이 true 일 경우 else if 블록 안의  Statements가 실행됩니다.  
if문의 Expression이 false이고, else if문의 Expression이 false 일 경우 else 블록 안의  Statements가 실행됩니다. 



### 2. switch

```java
public void foo(int foo) {
  switch (foo) {
    case 1:
      System.out.println("1");
			break;
		case 2, 3, 4:
			System.out.println("2, 3, 4");
			break;
		default:
		  System.out.println("default");
  }
}

// java 12 부터 -> 연산자로 break를 생략할 수 있습니다.
public void foo2(int foo) {
  switch (foo) {
      case 1 -> System.out.println("1");
			case 2, 3, 4 -> System.out.println("2, 3, 4");
      default -> System.out.println("default");
  }
}


// java 13 부터 yield를 통하여 값을 리턴할 수 있습니다.
public int foo3(int foo) {
		return switch (foo) {
			case 1 -> {
				System.out.println("1");
				yield 1;
			}
			case 2, 3, 4 -> {
				System.out.println("2, 3, 4");
				yield 234;
			}
			default -> {
				System.out.println("default");
				yield 0;
			}
		};
}
```

조건값에 따라 여러 case 또는,  case 중 하나를 실행하도록 합니다.  
case 값의 타입은 char, byte, short, int, Character, Byte, Short, Integer, String,  enum 이어야 합니다.  
case 값은 실수 및 변수(final 키워드가 없는)가 될수가 없습니다.



## 2. 반복문

### 1. for

```java
// for 문
for (ForInit; Expression; ForUpdate){
  Statement
}

// for 문 
for (int i = 0; i < 10; i++) {
  Statement
}

// 향상된 for 문
for (LocalVariableDeclaration : Expression) {
  Statement
}

for (foo : fooList) {
  Statement
}
```

**for**
초기화 코드(ForInit)를 실행한 다음 조건(Expression)값이 false가 될 때까지 상태를 변경(ForUpdate)하면서 반복적으로 동작합니다. 



**향상된 for**
반복될 때마다 배열의 연속적인 요소가 지역 변수로 초기화 됩니다.



### 2. while

```java
// while
while (Expression) {
  Statement
}

// do-while
do {
  Statement
} while(Expression)
```

**while**
조건값이 false가 될 때까지 반복적으로 Statement를 실행합니다.



**do-while**
먼저 do안의  Statement를 실행하고, 조건값이 false가 될 때까지 반복적으로  do안의 Statement를 실행합니다. 



## 3. 과제

### 1. LinkedList 구현

- LinkedList에 대해 공부하세요.
- 정수를 저장하는 ListNode 클래스를 구현하세요.
- ListNode add(ListNode head, ListNode nodeToAdd, int position)를 구현하세요.
- ListNode remove(ListNode head, int positionToRemove)를 구현하세요.
- boolean contains(ListNode head, ListNode nodeTocheck)를 구현하세요.

```java
package org.example;

import java.util.Objects;

public class LinkedList<T> {

	private static final int HEAD_INDEX = 0;

	public ListNode<T> add(ListNode<T> head, ListNode<T> nodeToAdd, int position) {
		validateNull(head);
		validateNull(nodeToAdd);

		if (position == HEAD_INDEX) {
			return addFirst(head, nodeToAdd);
		}

		return addIndex(head, nodeToAdd, position);
	}

	private void validateNull(ListNode<T> node) {
		if (Objects.isNull(node)) {
			throw new IllegalArgumentException("node가 null 입니다.");
		}
	}

	private ListNode<T> addFirst(ListNode<T> head, ListNode<T> nodeToAdd) {
		nodeToAdd.changeNextNode(head);
		return nodeToAdd;
	}

	private ListNode<T> addIndex(ListNode<T> head, ListNode<T> nodeToAdd, int position) {
		ListNode<T> node = getNode(head, --position);

		if (nodeToAdd.equals(node.getNext())) {
			return head;
		}

		nodeToAdd.changeNextNode(node.getNext());
		node.changeNextNode(nodeToAdd);
		return head;
	}

	private ListNode<T> getNode(ListNode<T> head, int index) {
		if (index < HEAD_INDEX) {
			throw new IndexOutOfBoundsException();
		}

		ListNode<T> node = head;

		while (index-- > HEAD_INDEX) {
			node = node.getNext();
			validateIndexOverFlow(node);
		}

		return node;
	}
	
	private void validateIndexOverFlow(ListNode<T> node) {
		if (Objects.isNull(node)) {
			throw new IndexOutOfBoundsException();
		}
	}

	public T get(ListNode<T> head, int position) {
		validateNull(head);
		ListNode<T> node = getNode(head, position);
		return node.getItem();
	}

	public ListNode<T> remove(ListNode<T> head, int positionToRemove) {
		validateNull(head);

		if (positionToRemove == HEAD_INDEX) {
			return head.getNext();
		}

		return removeIndex(head, positionToRemove);
	}

	private ListNode<T> removeIndex(ListNode<T> head, int positionToRemove) {
		ListNode<T> node = getNode(head, --positionToRemove);
		ListNode<T> removedNode = node.getNext();
		validateIndexOverFlow(removedNode);
		node.changeNextNode(removedNode.getNext());
		return head;
	}
	

	public boolean contains(ListNode<T> head, ListNode<T> nodeToCheck) {
		validateNull(head);
		validateNull(nodeToCheck);

		if (head.equals(nodeToCheck)) {
			return true;
		}

		while (Objects.nonNull(head.getNext())) {
			if (nodeToCheck.equals(head.getNext())) {
				return true;
			}

			head = head.getNext();
		}

		return false;
	}
}

```

```java
package org.example;

public class ListNode<T> {

	private final T item;
	private ListNode<T> next;

	public ListNode(T item) {
		this(item, null);
	}

	public ListNode(T item, ListNode<T> next) {
		this.item = item;
		this.next = next;
	}

	public T getItem() {
		return item;
	}

	public ListNode<T> getNext() {
		return next;
	}

	public void changeNextNode(ListNode<T> nextNode) {
		this.next = nextNode;
	}
}

```

```java
package org.example;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

public class LinkedListTest {

	LinkedList<Integer> linkedList;
	ListNode<Integer> headNode;
	ListNode<Integer> secondNode;

	@BeforeEach
	void init() {
		linkedList = new LinkedList<>();
		headNode = new ListNode<>(1);
		secondNode = new ListNode<>(2);
		linkedList.add(headNode, secondNode, 1);
	}

	@DisplayName("링크드 리스트에 원하는 위치로 노드를 추가한다")
	@ParameterizedTest
	@CsvSource({"3, 0", "3, 1", "3, 2"})
	void add(int nextNodeItem, int position) {
		// given
		ListNode<Integer> nextNode = new ListNode<>(nextNodeItem);

		// when
		headNode = linkedList.add(headNode, nextNode, position);

		// then
		Integer num = linkedList.get(headNode, position);
		Assertions.assertThat(num).isEqualTo(nextNodeItem);
	}

	@DisplayName("링크드 리스트에 존재하지 않는 위치로 노드를 추가할 때 예외가 발생한다")
	@ParameterizedTest
	@CsvSource({"3, -1", "3, 3", "3, 100"})
	void addFailIndexOutOfBound(int nextNodeItem, int position) {
		// given
		ListNode<Integer> nextNode = new ListNode<>(nextNodeItem);

		// when

		// then
		assertThrows(IndexOutOfBoundsException.class, () -> linkedList.add(headNode, nextNode, position));
	}

	@DisplayName("링크드 리스트에 null인 headNode로 추가하면 예외가 발생한다")
	@Test
	void addFailNullHeadNode() {
		// given

		// when

		// then
		assertThrows(IllegalArgumentException.class, () -> linkedList.add(null, secondNode, 1));
	}

	@DisplayName("링크드 리스트에 head 위치의 노드를 제거한다")
	@Test
	void removeHeadNode() {
		// given
		ListNode<Integer> preHeadNode = headNode;

		// when
		headNode = linkedList.remove(headNode, 0);

		// then
		assertFalse(linkedList.contains(headNode, preHeadNode));
	}

	@DisplayName("링크드 리스트에 중간 위치의 노드를 제거한다")
	@Test
	void removeMiddleNode() {
		// given
		linkedList.add(headNode, new ListNode<>(3), 2);

		// when
		headNode = linkedList.remove(headNode, 1);

		// then
		assertFalse(linkedList.contains(headNode, secondNode));
	}

	@DisplayName("링크드 리스트에 마지막 위치의 노드를 제거한다")
	@Test
	void removeLastNode() {
		// given

		// when
		headNode = linkedList.remove(headNode, 1);

		// then
		assertFalse(linkedList.contains(headNode, secondNode));
	}

	@DisplayName("링크드 리스트에 존재하지 않는 위치의 노드를 제거할 때 예외가 발생한다")
	@ParameterizedTest
	@ValueSource(ints = {-1, 2, 100})
	void removeFail(int position) {
		// given

		// when

		// then
		assertThrows(IndexOutOfBoundsException.class, () -> linkedList.remove(headNode, position));
	}

	@DisplayName("링크드 리스트에 존재하는 노드를 있는지 확인한다")
	@Test
	void contains() {
		// given

		// when
		boolean actual = linkedList.contains(headNode, secondNode);

		// then
		Assertions.assertThat(actual).isEqualTo(true);
	}

	@DisplayName("링크드 리스트에 존재하지 않는 노드를 있는지 확인한다")
	@Test
	void containsFalse() {
		// given

		// when
		boolean actual = linkedList.contains(headNode, new ListNode<>(1));

		// then
		Assertions.assertThat(actual).isEqualTo(false);
	}
}

```



### 2. Stack 구현

- int 배열을 사용해서 정수를 저장하는 Stack을 구현하세요.
- void push(int data)를 구현하세요.
- int pop()을 구현하세요.

```java
package org.example;

public interface Stack {

	void push(int data);

	int pop();
}
```

```java
package org.example;

import java.util.Arrays;
import java.util.EmptyStackException;

public class ArrayStack implements Stack {

	private static final int MIN_INITIAL_CAPACITY = 10;
	private static final int ARRAY_LENGTH_MAGNIFICATION = 2;
	private static final int EMPTY_LENGTH = 0;

	private int[] elements;
	private int top;

	public ArrayStack() {
		elements = new int[MIN_INITIAL_CAPACITY];
	}

	@Override
	public void push(int data) {
		if (elements.length <= top) {
			elements = Arrays.copyOf(elements, elements.length * ARRAY_LENGTH_MAGNIFICATION);
		}

		elements[top++] = data;
	}

	@Override
	public int pop() {
		validateEmptyElements();
		return elements[--top];
	}

	private void validateEmptyElements() {
		if (top == EMPTY_LENGTH) {
			throw new EmptyStackException();
		}
	}
}

```

```java
package org.example;

import static org.junit.jupiter.api.Assertions.*;

import java.util.EmptyStackException;
import java.util.stream.IntStream;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ArrayStackTest {

	Stack stack;

	@BeforeEach
	void init() {
		stack = new ArrayStack();
	}

	@DisplayName("스택에 데이터를 추가한다")
	@Test
	void push() {
		// given
		int num = 1;

		// when
		stack.push(num);

		// then
		Assertions.assertThat(stack.pop()).isEqualTo(num);
	}

	@DisplayName("스택에 초기 사이즈 초과하는 데이터를 추가할 때 이상 없는지 확인")
	@Test
	void pushForStackInitializeLengthOverFlow() {
		// given
		int begin = 0;
		int last = 10;
		IntStream.rangeClosed(begin, last)
			.forEach(num -> stack.push(num));

		// when


		// then
		Assertions.assertThat(stack.pop()).isEqualTo(last);
	}

	@DisplayName("스택에 마지막 저장된 데이터를 가져온다")
	@Test
	void pop() {
		// given
		stack.push(1);
		stack.push(2);

		// when
		int actual = stack.pop();

		// then
		Assertions.assertThat(actual).isEqualTo(2);
	}

	@DisplayName("스택을 생성하고 데이터를 가져오면 예외가 발생한다")
	@Test
	void popFailNullHead() {
		// given

		// when

		// then
		assertThrows(EmptyStackException.class, () -> stack.pop());
	}

	@DisplayName("스택에 저장되어 있는 데이터가 없을 때 데이터를 가져오면 예외가 발생한다")
	@Test
	void popFailNotFoundData() {
		// given
		stack.push(1);

		// when
		stack.pop();

		// then
		assertThrows(EmptyStackException.class, () -> stack.pop());
	}
}

```



### 3. ListNode를 사용해서 Stack 구현

- ListNode head를 가지고 있는 ListNodeStack 클래스를 구현하세요.
- void push(int data)를 구현하세요.
- int pop()을 구현하세요.

```java
package org.example;

import java.util.EmptyStackException;
import java.util.Objects;

public class ListNodeStack implements Stack {

	private static final int EMPTY_LENGTH = 0;

	private ListNode<Integer> headNode;
	private ListNode<Integer> previousLastNode;
	private ListNode<Integer> lastNode;
	private int top;

	@Override
	public void push(int data) {
		if (Objects.isNull(headNode)) {
			pushHead(data);
			return;
		}

		pushLast(data);
	}

	private void pushHead(int data) {
		headNode = new ListNode<>(data);
		previousLastNode = headNode;
		lastNode = headNode;
		top++;
	}

	private void pushLast(int data) {
		ListNode<Integer> node = new ListNode<>(data);
		lastNode.changeNextNode(node);
		previousLastNode = lastNode;
		lastNode = node;
		top++;
	}

	@Override
	public int pop() {
		validateEmptyElements();
		Integer item = lastNode.getItem();
		previousLastNode.changeNextNode(null);
		lastNode = previousLastNode;
		top--;
		return item;
	}

	private void validateEmptyElements() {
		if (top == EMPTY_LENGTH) {
			throw new EmptyStackException();
		}
	}
}

```

```java
package org.example;

import static org.junit.jupiter.api.Assertions.*;

import java.util.EmptyStackException;
import java.util.stream.IntStream;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ListNodeStackTest {

	Stack stack;

	@BeforeEach
	void init() {
		stack = new ListNodeStack();
	}

	@DisplayName("스택에 데이터를 추가한다")
	@Test
	void push() {
		// given
		int num = 1;

		// when
		stack.push(num);

		// then
		Assertions.assertThat(stack.pop()).isEqualTo(num);
	}

	@DisplayName("스택에 초기 사이즈 초과하는 데이터를 추가할 때 이상 없는지 확인")
	@Test
	void pushForStackInitializeLengthOverFlow() {
		// given
		int begin = 0;
		int last = 10;
		IntStream.rangeClosed(begin, last)
			.forEach(num -> stack.push(num));

		// when


		// then
		Assertions.assertThat(stack.pop()).isEqualTo(last);
	}

	@DisplayName("스택에 마지막 저장된 데이터를 가져온다")
	@Test
	void pop() {
		// given
		stack.push(1);
		stack.push(2);

		// when
		int actual = stack.pop();

		// then
		Assertions.assertThat(actual).isEqualTo(2);
	}

	@DisplayName("스택을 생성하고 데이터를 가져오면 예외가 발생한다")
	@Test
	void popFailNullHead() {
		// given

		// when

		// then
		assertThrows(EmptyStackException.class, () -> stack.pop());
	}

	@DisplayName("스택에 저장되어 있는 데이터가 없을 때 데이터를 가져오면 예외가 발생한다")
	@Test
	void popFailNotFoundData() {
		// given
		stack.push(1);

		// when
		stack.pop();

		// then
		assertThrows(EmptyStackException.class, () -> stack.pop());
	}
}

```



### 4. Queue 구현

- 배열을 사용해서 한번
- ListNode를 사용해서 한번.

```java
package org.example;

import java.util.Arrays;
import java.util.NoSuchElementException;

public class ArrayQueue implements Queue {

	private static final int MAX_INITIAL_CAPACITY = 10;
	private static final int FIRST_INDEX = 0;
	private static final int FILL_DATA = 0;
	private static final int ORIGINAL_ELEMENTS_COPY_START_INDEX = 1;
	private static final int COPY_ELEMENTS_START_INDEX = 0;


	private final int[] elements;
	private int size;

	public ArrayQueue() {
		elements = new int[MAX_INITIAL_CAPACITY];
	}

	@Override
	public boolean add(int e) {
		validateOverFlowSize();
		elements[size++] = e;
		return true;
	}

	private void validateOverFlowSize() {
		if (size == MAX_INITIAL_CAPACITY) {
			throw new IllegalArgumentException("Queue full");
		}
	}

	@Override
	public int remove() {
		validateEmptyElement();
		return removeFirstElement();
	}

	private int removeFirstElement() {
		int firstElement = elements[FIRST_INDEX];
		System.arraycopy(elements, ORIGINAL_ELEMENTS_COPY_START_INDEX, elements, COPY_ELEMENTS_START_INDEX, --size);
		Arrays.fill(elements, size, elements.length - 1, FILL_DATA);
		return firstElement;
	}

	private void validateEmptyElement() {
		if (size == FIRST_INDEX) {
			throw new NoSuchElementException();
		}
	}

	@Override
	public int element() {
		validateEmptyElement();
		return elements[FIRST_INDEX];
	}
}

```

```java
package org.example;

import static org.junit.jupiter.api.Assertions.*;

import java.util.NoSuchElementException;
import java.util.stream.IntStream;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ArrayQueueTest {

	Queue queue;

	@BeforeEach
	void init() {
		queue = new ArrayQueue();
	}

	@DisplayName("큐에 데이터를 추가한다")
	@Test
	void add() {
		// given

		// when
		queue.add(1);

		// then
		Assertions.assertThat(queue.element()).isEqualTo(1);
	}

	@DisplayName("큐에 데이터를 추가 할 때 사이즈가 초과되면 예외가 발생한다")
	@Test
	void addFailOverFlow() {
		// given
		int begin = 0;
		int last = 10;
		IntStream.range(begin, last)
			.forEach(num -> queue.add(num));

		// when

		// then
		assertThrows(IllegalArgumentException.class, () -> queue.add(last));
	}

	@DisplayName("큐에 제일 먼저 추가한 데이터를 가져오고 가져온 데이터를 제거한다")
	@Test
	void remove() {
		// given
		queue.add(1);
		queue.add(2);

		// when
		int actual = queue.remove();

		// then
		Assertions.assertThat(actual).isEqualTo(1);
	}

	@DisplayName("큐에 저장되어 있는 데이터가 없을 때 데이터를 가져오면 예외가 발생한다")
	@Test
	void removeFailEmptyElement() {
		// given

		// when

		// then
		assertThrows(NoSuchElementException.class, () -> queue.remove());
	}

	@DisplayName("큐에 제일 먼저 추가한 데이터를 가져온다")
	@Test
	void element() {
		// given
		queue.add(1);
		queue.add(2);

		// when
		int actual = queue.element();

		// then
		Assertions.assertThat(actual).isEqualTo(1);
	}

	@DisplayName("큐에 저장되어 있는 데이터가 없을 때 데이터를 가져오면 예외가 발생한다")
	@Test
	void elementFailEmptyElement() {
		// given

		// when

		// then
		assertThrows(NoSuchElementException.class, () -> queue.element());
	}
}

```

출처

https://docs.oracle.com/javase/specs/jls/se17/html/jls-14.html#jls-14.9.1  
https://docs.oracle.com/en/java/javase/17/language/java-language-changes.html#GUID-8FD2B5E3-46C7-4C6C-8E8A-64AB49ABF855 