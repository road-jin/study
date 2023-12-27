# 10주차 과제: 멀티쓰레드 프로그래밍

## 1. Thread 클래스와 Runnable 인터페이스

자바에서 지원하는 동시성 프로그래밍(concurrent programming)에는 두가지 종류가 있습니다.

- 멀티프로세싱(ProcessBuilder)
- 멀티쓰레드



### 프로세스와 스레드

**프로세스**
운영체제로부터 자원(cpu, virtual memory)을 할당받아 실행하는 프로그램(어플리케이션)을 의미합니다.  
또한 최소 1개의 스레드를 가지고 있습니다.



**스레드**
스레드는 프로세스로 부터 자원을 할당받고, 프로세스의 코드, 데이터, 힙영역을 공유받아 실제 작업을 수행하는 단위를 말합니다.  
경량 프로세스라고 불리며 가장 작은 실행 단위이며, 두 개 이상의 스레드를 멀티 쓰레드라고 합니다.



### 자바의 Thread

스레드를 구현하기 위해 Runnable이나 Thread를 상속받아 구현할 수 있습니다.

**Runnable interface**

```java
@FunctionalInterface
public interface Runnable {
    
    public abstract void run();
}

public class HelloRunnable implements Runnable {

    @override
    public void run() {
        System.out.println("Hello from a thread!");
    }

    public static void main(String args[]) {
      Thread thread = new Thread(new HelloRunnable());
      thread.start();
    }
}
```

스레드에서 실행되는 코드를 포함하는 단일 메서드인 run을 정의합니다.



**Thread Class**

```java
public class HelloThread extends Thread {

  	@override
    public void run() {
        System.out.println("Hello from a thread!");
    }

    public static void main(String args[]) {
        HelloThread thread = new HelloThread();
        thread.start();
    }
}
```

Thread 클래스는 Runnable 인터페이스를 상속받아 구현된 객체로써 run 메서드만 재정의를 하면 Thread를 사용할 수 있습니다.

- Thread 메서드
    - join(), join(long millis)
        - 이 스레드가 종료될 때까지 대기하며, 해당 스레드의 상태는 Waiting 입니다.
        - 지정한 시간만큼 현재 실행중인 스레드의 종료를 기다리며 해당 스레드의 상태는 Timed Waiting입니다.
    - sleep(long timeout)
        - 지정한 시간만큼 현재 실행중인 스레드를 대기하며 (Timed Waiting)로 만듭니다.
    - interrupt()
        - 이 스레드를 중단합니다.
    - yield()
        - 현재 프로세스의 해당 스레드가 사용을 양보할 의향이 있다는 것을 스케줄러에 알리는 힌트입니다.
    - start()
        - JVM이 이 스레드의 run 메서드를 호출합니다.
- Object에서 Thread와 관련된 메서드
    - wait(), wait(long timeout)
        - notify(), notifyAll() 메서드가 호출될 때까지 대기상태이며, 해당 스레드의 상태를 Waiting입니다.
        - 지정한 시간만큼 notify(), notifyAll() 메서드가 호출될 때까지 대기상태이며, 해당 스레드의 상태를 Timed Waiting입니다.
    - notify()
        - Object 객체의 모니터에 대기하고 있는 단일 쓰레드를 깨웁니다.
    - notifyAll()
        - Object 객체의 모니터에 대기하고 있는 모든 쓰레드를 깨웁니다.



## 2. 쓰레드의 상태

![](https://media.geeksforgeeks.org/wp-content/uploads/20230620182419/Lifecycle-and-States-of-a-Thread-in-Java-768.png)



스레드의 상태는 다음 중 하나를 가집니다.

- New 

    - 아직 시작되지 않은 쓰레드의 상태입니다. 

        ```java
        public class HelloThread {
        
        	public static void main(String[] args) throws InterruptedException {
        		FooThread fooThread = new FooThread();
        		fooThread.printThreadStatus(1);
        		
        	}
        
        	public static class FooThread extends Thread {
        
        		@Override
        		public void run() {
        		
            }
        
        		public void printThreadStatus(int order) {
        			System.out.printf("%d. thread status : %s\n", order, this.getState());
        		}
        	}
        }
        
        /*
        result
        1. thread status : NEW
        */
        ```

    

- Runnable

    - 실행 가능한 스레드 상태이거나 실행 중인 상태입니다.

        ```java
        
        public class HelloThread {
        
        	public static void main(String[] args) throws InterruptedException {
        		FooThread fooThread = new FooThread();
        		fooThread.printThreadStatus(1);
        		fooThread.start();
        	}
        
        	public static class FooThread extends Thread {
        
        		@Override
        		public void run() {
        				printThreadStatus(2);
        		}
        
        		public void printThreadStatus(int order) {
        			System.out.printf("%d. thread status : %s\n", order, this.getState());
        		}
        	}
        }
        
        /*
        result
        1. thread status : NEW
        2. thread status : RUNNABLE
        */
        ```

    

- Blocked

    - 모니터 락이 풀리기를 기다리는 상태입니다.   
        모니터 락이 동기화된 블록/메서드에 들어가거나 Object.wait()을 호출한 후 동기화된 블록/메서드에 다시 들어가기를 기다립니다.

        ```java
        
        public class HelloThread {
        
        	public static void main(String[] args) throws InterruptedException {
        		FooThread fooThread = new FooThread();
        		fooThread.printThreadStatus(1);
        		fooThread.start();
        
        		Thread.sleep(300);
        		fooThread.printThreadStatus(3);
        
        		synchronized (fooThread) {
        			fooThread.notify();
        			fooThread.printThreadStatus(4);
        		}
        	}
        
        	public static class FooThread extends Thread {
        
        		@Override
        		public void run() {
        			try {
        				printThreadStatus(2);
        				waitThread(0);
        			} catch (InterruptedException e) {
        				e.printStackTrace();
        			}
        
        		}
        
        		private void waitThread(int time) throws InterruptedException {
        			synchronized (this) {
        				wait(time);
        			}
        		}
        
        		public void printThreadStatus(int order) {
        			System.out.printf("%d. thread status : %s\n", order, this.getState());
        		}
        	}
        }
        
        /*
        result
        1. thread status : NEW
        2. thread status : RUNNABLE
        3. thread status : WAITING
        4. thread status : BLOCKED
        */
        ```

    

- Waiting

    - 다른 쓰레드가 특정 작업을 완료할 때까지 기다리고 있는 상태입니다.

    - Object.wait with no timeout  
        Thread.join with no timeout  
        LockSupport.park

        

- Timed Waiting

    - 지정한 대기 시간까지 다른 쓰레드가 특정 작업을 완료하기를 기다리는 상태입니다.

    - Thread.sleep  
        Object.wait with timeout  
        Thread.join with timeout  
        LockSupport.parkNanos  
        LockSupport.parkUntil

        ```java
        
        public class HelloThread {
        
        	public static void main(String[] args) throws InterruptedException {
        		FooThread fooThread = new FooThread();
        		fooThread.printThreadStatus(1);
        		fooThread.start();
        
        		Thread.sleep(300);
        		fooThread.printThreadStatus(3);
        
        		synchronized (fooThread) {
        			fooThread.notify();
        			fooThread.printThreadStatus(4);
        		}
        
        		Thread.sleep(300);
        		fooThread.printThreadStatus(6);
        	}
        
        	public static class FooThread extends Thread {
        
        		@Override
        		public void run() {
        			try {
        				printThreadStatus(2);
        				waitThread(0);
        				printThreadStatus(5);
        				waitThread(1000);
        			} catch (InterruptedException e) {
        				e.printStackTrace();
        			}
        
        		}
        
        		private void waitThread(int time) throws InterruptedException {
        			synchronized (this) {
        				wait(time);
        			}
        		}
        
        		public void printThreadStatus(int order) {
        			System.out.printf("%d. thread status : %s\n", order, this.getState());
        		}
        	}
        }
        /*
        result
        1. thread status : NEW
        2. thread status : RUNNABLE
        3. thread status : WAITING
        4. thread status : BLOCKED
        5. thread status : RUNNABLE
        6. thread status : TIMED_WAITING
        */
        ```

        

- Terminated

    - 종료된 쓰레드의 상태입니다.

        ```java
        
        public class HelloThread {
        
        	public static void main(String[] args) throws InterruptedException {
        		FooThread fooThread = new FooThread();
        		fooThread.printThreadStatus(1);
        		fooThread.start();
        
        		Thread.sleep(300);
        		fooThread.printThreadStatus(3);
        
        		synchronized (fooThread) {
        			fooThread.notify();
        			fooThread.printThreadStatus(4);
        		}
        
        		Thread.sleep(300);
        		fooThread.printThreadStatus(6);
        
        		fooThread.join();
        		fooThread.printThreadStatus(7);
        	}
        
        	public static class FooThread extends Thread {
        
        		@Override
        		public void run() {
        			try {
        				printThreadStatus(2);
        				waitThread(0);
        				printThreadStatus(5);
        				waitThread(1000);
        			} catch (InterruptedException e) {
        				e.printStackTrace();
        			}
        
        		}
        
        		private void waitThread(int time) throws InterruptedException {
        			synchronized (this) {
        				wait(time);
        			}
        		}
        
        		public void printThreadStatus(int order) {
        			System.out.printf("%d. thread status : %s\n", order, this.getState());
        		}
        	}
        }
        /*
        result
        1. thread status : NEW
        2. thread status : RUNNABLE
        3. thread status : WAITING
        4. thread status : BLOCKED
        5. thread status : RUNNABLE
        6. thread status : TIMED_WAITING
        7. thread status : TERMINATED
        */
        ```



## 3. 쓰레드의 우선순위

멀티쓰레드 환경내에서는 스케줄러에 의해 쓰레드의 우선순위에 따라 할당합니다.  
우선순위가 높다고 무조건 먼저 실행되는 것은 아니며, 스케줄러에 조금 더 할당을 받을 뿐입니다.  
쓰레드를 생성할 때마다 항상 우선순위가 할당됩니다.  
쓰레드의 우선순위는 1부터 10까지의 숫자로 표시되며, Thread 생성시 기본 값은 5이며, 상수로 다음과 같이 정의되어 있습니다.

- public final static int MIN_PRIORITY = 1
-  public final static int NORM_PRIORITY = 5
- public final static int MAX_PRIORITY = 10

Thread 클래스에서 우선순위를 가져오고 설정하는 방법은 다음과 같습니다.

- getPriority() 
    - 해당 스레드의 우선순위를 반환합니다.
- setPriority(int newPriority)
    - 해당 스레드의 우선순위를 변경합니다.

```java
public class ThreadPriority extends Thread {

	@Override
	public void run() {
		IntStream.range(0, 10)
			.forEach(i -> System.out.println(this.getName()));
	}

	public static void main(String[] args) {
		ThreadPriority thread1 = new ThreadPriority();
		ThreadPriority thread2 = new ThreadPriority();
		ThreadPriority thread3 = new ThreadPriority();

		thread1.setPriority(10);
		thread2.setPriority(7);

		System.out.printf("%s Priority : %d\n", thread1.getName(), thread1.getPriority());
		System.out.printf("%s Priority : %d\n", thread2.getName(), thread2.getPriority());
		System.out.printf("%s Priority : %d\n", thread3.getName(), thread3.getPriority());

		thread3.start();
		thread2.start();
		thread1.start();
	}
}
/*
result
Thread-0 Priority : 10
Thread-1 Priority : 7
Thread-2 Priority : 5
Thread-0
Thread-0
Thread-0
Thread-2
Thread-2
Thread-2
Thread-1
Thread-1
Thread-1
/*
```



## 4. Main 쓰레드

![](https://media.geeksforgeeks.org/wp-content/uploads/main-thread-in-java.jpeg)

Java 프로그램이 시작될 때 자동으로 실행되는 main 메서드가 Main 쓰레드입니다.  
Main 쓰레드는 기본 우선순위는 5이며, Thread.currentThread() 쓰레드의 참조를 얻을 수 있습니다.  
자바는 사용자 쓰레드와 데몬 쓰레드로 구분됩니다.



### 데몬 쓰레드

Java에서 데몬 쓰레드는 가비지 수집과 같은 작업을 수행하거나 사용자 스레드에 서비스를 제공하기 위해   
백그라운에서 실행되는 우선순위가 낮은 스레드입니다.  
모든 사용자 쓰레드가 실행을 마치면 JVM이 자동으로 데몬 스레드를 종료합니다.

```java
public class MonitoringDaemonThread extends Thread {

	@Override
	public void run() {
		while(true) {
			System.out.println("모니터링 중");
		}
	}

	public static void main(String[] args) {
		MonitoringDaemonThread daemonThread = new MonitoringDaemonThread();
		daemonThread.setDaemon(true);
		System.out.printf("데몬 쓰레드 여부 : %s \n", daemonThread.isDaemon());
		daemonThread.start();
	}
}
/*
result
데몬 쓰레드 여부 : true 
모니터링 중
모니터링 중
모니터링 중
모니터링 중
모니터링 중
모니터링 중
모니터링 중
모니터링 중
모니터링 중
모니터링 중
모니터링 중
모니터링 중
모니터링 중
모니터링 중
모니터링 중
모니터링 중
모니터링 중
모니터링 중
모니터링 중
모니터링 중
모니터링 중
모니터링 중
모니터링 중
모니터링 중
모니터링 중
모니터링 중
모니터링 중
모니터링 중
모니터링 중
모니터링 중
모니터링 중
모니터링 중
*/
```

데몬 쓰레드가 무한 루프로 백그라운드에서 돌고 있지만, Main 쓰레드가 종료 시 데몬 쓰레드도 종료 됩니다.    
데몬 쓰레드 여부와 설정하는 방법은 다음과 같습니다.

- isDeamon()
    - 데몬 쓰레드인지 아닌지 확인하는데 사용되며, 쓰레드가 데몬일 경우 ture 아니면 false가 반환됩니다.
- setDaemon(boolean on)
    - 파라미터가 true 일 경우 데몬 쓰레드로 설정하며, false 일 경우 사용자 쓰레드로 설정합니다.
    - 쓰레드를 시작한 후에 setDaemon 메서드를 호출하면 IllegalThreadStateException 예외가 발생합니다.



## 5.동기화

특정 시점에 하나의 쓰레드만 자원에 접근할 수 있도록 합니다.  
여러 쓰레드가 동시에 자원을 접근하여 상태를 변화시키면 데이터의 일관성이 일치하지 않기 때문입니다.

```java
public class NotSynchronizedClass {

	public static void main(String[] args) throws InterruptedException {
		int threadCount = 1_000_000;
		Item item = new Item();
		ExecutorService executorService = Executors.newFixedThreadPool(10);
		CountDownLatch countDownLatch = new CountDownLatch(threadCount);

		IntStream.range(0, threadCount)
			.forEach(i ->
				executorService.execute(() -> {
					item.increment();
					countDownLatch.countDown();
			}));

		executorService.shutdown();
		countDownLatch.await();

		System.out.println(item.getCount());
	}

	static class Item {
		private int count = 0;

		public void increment() {
			count++;
		}

		public int getCount() {
			return count;
		}
	}
}
/*
result
999127
*/
```

이와 같이 여러 쓰레드가 item의 count를 접근하여 증가시킬 경우에  쓰레드 간섭으로 인하여 제대로 된 값이 나오지 않게 됩니다.   
그래서 동기화를 통하여 자원 접근 시 하나의 쓰레드만 접근할 수 있도록 하고 나머지 쓰레드는 일시 중지 상태로 변경됩니다.  
동기화 방법에는 다음과 같은 방법이 있습니다.



### 동기화 방법

- Synchronized keyward

    - 메서드에 synchronized keyward를 선언하여 해당 메서드에 접근시 객체에 락을 걸어 하나의 스레드만 접근할 수 있도록 합니다.

        ```java
        public class NotSynchronizedClass {
        
        	public static void main(String[] args) throws InterruptedException {
        		int threadCount = 1_000_000;
        		Item item = new Item();
        		ExecutorService executorService = Executors.newFixedThreadPool(10);
        		CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        
        		IntStream.range(0, threadCount)
        			.forEach(i ->
        				executorService.execute(() -> {
        					item.increment();
        					countDownLatch.countDown();
        			}));
        
        		executorService.shutdown();
        		countDownLatch.await();
        
        		System.out.println(item.getCount());
        	}
        
        	static class Item {
        		private int count = 0;
        
        		public synchronized void increment() {
        			count++;
        		}
        
        		public int getCount() {
        			return count;
        		}
        	}
        }
        /*
        result
        1000000
        */
        ```

        ```java
        public class SynchronizedClass {
        
        	public static void main(String[] args) throws InterruptedException {
        		int threadCount = 1_000_000;
        		Item item = new Item();
        		ExecutorService executorService = Executors.newFixedThreadPool(10);
        		CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        
        		IntStream.range(0, threadCount)
        			.forEach(i ->
        				executorService.execute(() -> {
        					item.increment();
        					countDownLatch.countDown();
        			}));
        
        		executorService.shutdown();
        		countDownLatch.await();
        
        		System.out.println(item.getCount());
        	}
        
        	static class Item {
        		private int count = 0;
        
        		public void increment() {
        			synchronized (this) {
        				count++;
        			}
        		}
        
        		public int getCount() {
        			return count;
        		}
        	}
        }
        /*
        result
        1000000
        */
        ```

        synchronized 문에서 this 대신 다른 클래스 사용할 경우 해당 객체의 잠금을 획득합니다.

        ```java
        public class SynchronizedClass {
        
        	public static void main(String[] args) throws InterruptedException {
        		int threadCount = 1_000_000;
        		ProxyItem proxyItem = new ProxyItem(new Item());
        		ExecutorService executorService = Executors.newFixedThreadPool(10);
        		CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        
        		IntStream.range(0, threadCount)
        			.forEach(i ->
        				executorService.execute(() -> {
        					proxyItem.increment();
        					countDownLatch.countDown();
        			}));
        
        		executorService.shutdown();
        		countDownLatch.await();
        
        		System.out.println(proxyItem.getCount());
        	}
        
        	static class ProxyItem {
        		private final Item item;
        
        		public ProxyItem(Item item) {
        			this.item = item;
        		}
        
        		public void increment() {
        			synchronized (item) {
        				item.increment();
        			}
        		}
        
        		public int getCount() {
        			return item.getCount();
        		}
        	}
        
        	static class Item {
        		private int count = 0;
        
        		public void increment() {
        			count++;
        		}
        
        		public int getCount() {
        			return count;
        		}
        	}
        }
        /*
        result
        1000000
        */
        ```

        

- Lock interface

    - 락의 시작과 종료지점을 명시적으로 표현합니다.

    - lock 우선순위에 밀려 쓰레드가 계속해서 할당받지 못하는 쓰레드가 존재하는 상황을 기아 상태라고 말합니다.  
        기아 상태를 해결할 수 있도록 하는 공정성을 지원합니다..  
        가장 오랫동안 기다린 쓰레드에게 락을 제공하며, 생성자에서  true를 인자로 넣으면 기아 상태를 해결할 수 있습니다.  
        하지만 프로그램의 처리량을 감소시켜 대체적으로 불공정성 방식이 사용됩니다.

    - 메서드

        - lock()
            - Lock 인스턴스에 잠금을 걸어둔다. Lock 인스턴스가 이미 잠겨있는 상태라면  
                잠금을 걸어둔 쓰레드가 unlock()을 호출할 때까지 실행이 비활성화합니다.
        - lockInterruptibly()
            - 현재 쓰레드가 interrupted 상태가 아닐 때 Lock 인스턴스에 잠금을 겁니다.  
                 현재 쓰레드가 intterupted 상태면 InterruptedException를 발생합니다.
        - tryLock()
            - 즉시 Lock 인스턴스에 잠금을 시도하고 성공 여부를 boolean 타입으로 반환합니다.  
            - tryLock(long timeout, TimeUnit timeUnit)
                - tryLock()과 동일하지만, 잠금이 실패했을 때 바로 false를 반환하지 않고 인자로 주어진 시간동안 기다립니다.
        - unlock()
            - Lock 인스턴스의 잠금을 해제합니다.

    - 종류

        - ReentrantLock

            ```java
            public class LockSynchronizedClass {
            
               public static void main(String[] args) throws InterruptedException {
                  int threadCount = 1_000_000;
                  Item item = new Item();
                  ExecutorService executorService = Executors.newFixedThreadPool(10);
                  CountDownLatch countDownLatch = new CountDownLatch(threadCount);
            
                  IntStream.range(0, threadCount)
                     .forEach(i ->
                        executorService.execute(() -> {
                           item.increment();
                           countDownLatch.countDown();
                     }));
            
                  executorService.shutdown();
                  countDownLatch.await();
            
                  System.out.println(item.getCount());
               }
            
            
               static class Item {
                  private final ReentrantLock lock = new ReentrantLock();
                  private int count = 0;
            
                  public void increment() {
                     lock.lock();
                    
                     try {
                        count++;
                     } finally {
                        lock.unlock();
                     }
                  }
            
                  public int getCount() {
                     return count;
                  }
               }
            }
            /*
            result
            1000000
            */
            ```

        - ReentrantReadWriteLock

        

- Atomic Class과 같은 Thread-safe한 클래스 

    - AtomicBoolean
    - AtomicInteger
    - AtomicLong
    - AtomicIntegerArray
    - AtomicDoubleArray
    - ConcurrentHashMap 등



## 6. 데드락

```java
public class DeadLock {

	public static void main(String[] args) {
		Friend mandu = new Friend("만두");
		Friend jjinppang = new Friend("찐빵");

		new Thread(() -> mandu.bow(jjinppang)).start();
		new Thread(() -> jjinppang.bow(mandu)).start();
	}

	static class Friend {
		private final String name;

		public Friend(String name) {
			this.name = name;
		}

		public String getName() {
			return this.name;
		}

		public synchronized void bow(Friend bower) {
			System.out.printf("%s: %s에게 절을 한다!%n", this.name, bower.getName());
			bower.bowBack(this);
		}

		public synchronized void bowBack(Friend bower) {
			System.out.printf("%s: %s이(가) 절이 끝날 때 까지 기달린다!%n", this.name, bower.getName());
		}
	}
}
```

데드락은 둘 이상의 쓰레드가 락을 획득하기 위해서 대기하고 있는데,  
획득하기 위한 락을 가진 쓰레드도 다른 락을 기다리는 것을 의미합니다.  

![](https://media.geeksforgeeks.org/wp-content/uploads/22-2.png)

위의 예제처럼 동방예의지국에서는 상대방이 절을 하면 절이 끝날 때 까지 기다리고, 그 후에 절을 하는 예절이 있다고 가정을 하면  
만두가 찐빵한테 절을 하고, 찐빵이 만두의 절이 끝날 때까지 기다리고 그 후에 절을 하면 되는 프로그램입니다.  
하지만 동시에 만두와 찐빵이 절을 하면 서로 절이 끝날 때 까지 기다리는 상태가 되며 이를 데드락이라고 합니다. 

자바로 풀어보면  첫번째 쓰레드에 의해서 Friend의 bow 메서드를 호출하여   
synchronized에 의해서 mandu 인스턴스의 락을 잡고 화면에 문자열을 출력합니다.  
그리고 두번째 쓰레드에 의해서 Friend의 bow 메서드를 호출하여 jjinppang 인스턴스의 락을 잡습니다.  
두 동작이 거의 동시에 이루어지며, 그 후 첫번째 쓰레드가 jjinppang의 bowBack 메서드를 호출 시  
synchronized에 의해서 jjnppangd 인스턴스의 락을 가져오기 위해 두번째 쓰레드가 락을 풀 때까지 기다리며,  
두번째 쓰레드도 mandu 인스턴스의 락을 가져오기 위해 첫번째 쓰레드가 락을 풀 까지 기다립니다.



참조

https://docs.oracle.com/javase/tutorial/essential/concurrency/simple.html

https://www.geeksforgeeks.org/lifecycle-and-states-of-a-thread-in-java/?ref=lbp

https://www.geeksforgeeks.org/java-lang-thread-class-java/?ref=lbp

https://www.geeksforgeeks.org/java-thread-priority-multithreading

https://www.geeksforgeeks.org/daemon-thread-java

https://www.geeksforgeeks.org/deadlock-in-java-multithreading/?ref=header_search

https://www.geeksforgeeks.org/reentrant-lock-java

https://sujl95.tistory.com/63