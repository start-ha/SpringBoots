package Event;

import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.plaf.synth.SynthOptionPaneUI;

//태생 : cs 프로그램 (메모장 , 그림판 , 이클립스 같은 툴)
//1. awt   (OS 가지고 있는 자원)
//2. swing (순수한 자바로 컴포넌트)
class MyFrame extends Frame{
	public MyFrame(String title) {
		super(title);
	}
}

class BtnClickHandler implements ActionListener{  //이벤트 클릭 발생되었을떄 실행되는 홤수 구현

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("나 클릭 ....");
		
	}
	
}



public class Ex14_awt_Frame {

	public static void main(String[] args) {
		MyFrame my = new MyFrame("login");
		my.setSize(350, 300);
		my.setVisible(true);
		my.setLayout(new FlowLayout());
		
		Button btn = new Button("one button");
		Button btn2 = new Button("two button");
		Button btn3 = new Button("three button");

		
		//버튼 이벤트
		//이벤트 : 소스 , 행위 , 감지기 
		
		//일반적인 방법
		BtnClickHandler handler = new BtnClickHandler();
		btn.addActionListener(handler);
		
		
		//익명객체
		btn2.addActionListener(new ActionListener() { //익명타입   원칙 : class BtnClickHandler implements ActionListener
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("인터페이스 익명 객체 구현");
				
			}
		});
		
		my.add(btn);
		my.add(btn2);
		my.add(btn3);
	}

}
