

@FunctionalInterface
interface Myfun{
	void method(int x); //구현을 ... 람다식으로
}

@FunctionalInterface
interface Myfun2{
	int method(int x , int y); //구현을 ... 람다식으로
}

public class Ex04_Lamda {

	public static void main(String[] args) {
		
		Myfun my = new Myfun() {
			
			@Override
			public void method(int x) {
				System.out.println("param x : " + x);				
			}
		};
		
		my.method(100);
		
		/////////////////////////////////////////////////////////
		//람다식 사용하기 (구현부를 내 마음대로 ..... )
		Myfun my2 = (x) -> System.out.println("param -x : " + x);
		my2.method(500);
		
		
		Myfun2 myfun2 = (x,y) -> {int result = x + y;  return result; };
		//더 줄여서 람다식으로
		Myfun2 myfun3 = (x,y) -> x+y;
		
		int data = myfun3.method(4000, 400);
		System.out.println(data);
		
		
		
		

	}

}
