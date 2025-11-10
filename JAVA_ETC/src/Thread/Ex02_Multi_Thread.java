package Thread;

import java.util.Iterator;

/*
Thread : 프로세스에서 하나의 최소 실행단위 (흐름) >> method >> 실행되는 공간 stack

결과 : stack 여러개 생성해서 >> 동시에 실행 (실행가능 한다) >> cpu점유 할 수있는 상태로 만든다 

Thrad 생성방법
1. Thread 클래스 상속 >> class Task extends Thread >> run 함수 
2. Runnable 인터페이스 구현 >> class Task implements Runnable >> run 추상함수 강제 구현 :Thread 아니고요 
   Thread thread = new Thread(new Task());

ex) class Task extends Car 를 이미상속 .... extends Thread 안되요 >> 인터페이스 RUN 강제구현 
*/

class Task_1 extends Thread{
	
	//why > run() 추상함수가 아닐까 .....  > 일반함수 
	@Override
	public void run() {  //run 함수가 Thread 의 main 함수 역할 >> 새로 생성된 stack 올라가는 녀석
		for(int i = 0 ; i < 1000 ; i++) {
			System.out.println("Task_1 " + i + this.getName());
		}
		System.out.println("Task_1 run() 함수 END");
	}
}


class Task_2 implements Runnable{  //Thread 가 아니예요 >> run 을 가지고 있는 클래스일뿐

	@Override
	public void run() {
		for(int i = 0 ; i < 1000 ; i++) {
			System.out.println("Task_2 " + i );
		}
		System.out.println("Task_2 run() 함수 END");
	}
	
}


public class Ex02_Multi_Thread {

	public static void main(String[] args) {
		//main thread
		
		Task_1 th = new Task_1();
		th.start();
		//POINT
		//start(); 실행되면
		//1. memory call stack 생성하고  그리고 Thread 가지고 있는 public void run() stack 올려 놓고 ....생명 끝
		
		
		Task_2 ta = new Task_2(); //runnable 구현한 그냥 객체 
		Thread th2 = new Thread(ta);
		th2.start();
		//POINT
		//start(); 실행되면
		//1. memory call stack 생성하고  그리고 Thread 가지고 있는 public void run() stack 올려 놓고 ....생명 끝

		//Thread 익명객체
		Thread th3 = new Thread(new Runnable() {   // 한단계 발전 > 함수형 인터페이스 > 람다표현식
			
			@Override
			public void run() {
				
				for(int i = 0 ; i < 1000 ; i++) {
					System.out.println("th3 " + i );
				}
				System.out.println("th3 run() 함수 END");
			}
		});
		
		 
		
		for(int i = 0 ; i < 1000 ; i++) {
			System.out.println("main " + i );
		}
		System.out.println("main run() 함수 END");
	}

}
