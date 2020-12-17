package View;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;

import Service.Sound;

public class BombMessageJDialog extends JDialog { // ��������̳�JDialog��
	private static final long serialVersionUID = 1L;
	private Sound sound=new Sound();
	public BombMessageJDialog(JFrame frame,int locationMessage,int step,int bombedPlane,boolean Repeat) {
		// ʵ����һ��JDialog�����ָ���Ի���ĸ����塢������������
		super(frame, "��ʾ", true);
		addWindowListener(new WindowAdapter() {// ��Ӵ������
            public void windowClosing(WindowEvent e) {// ����ر�ǰ
                sound.stopPlay();
            }
        });
		Container container = getContentPane(); // ����һ������
		container.setLayout(new BorderLayout());
		String resultText=new String();
		String[] BombResults= {"��ʲô��û��ը����","���һ�ܷɻ���������ˣ�","��ݻ���һ�ܷɻ�"};
		resultText=BombResults[locationMessage];
		String tipText=new String();
		tipText=getTipText(locationMessage,Repeat);
		
		if(bombedPlane==3) {
			resultText="�ɹ��ݻ�ȫ���ɻ���";
			tipText=("<html>��"+step+"����<br/><br/></html>");
		}
		
		switch(locationMessage) {	
		case 0:
				sound.playBOMB0();
				break;
		case 1:
				sound.playBOMB1();
				break;
		case 2:
				sound.playBOMB2();
				break;
		default:resultText="δ֪����";
				break;
		}
		JLabel resultLabel=new JLabel(resultText);
		JLabel tipLabel=new JLabel(tipText);
		resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
		tipLabel.setHorizontalAlignment(SwingConstants.CENTER);
		resultLabel.setFont(new Font("΢���ź�",0,25));
		tipLabel.setFont(new Font("΢���ź�",0,18));
		container.add(BorderLayout.CENTER,resultLabel); // ����������ӱ�ǩ
		container.add(BorderLayout.SOUTH,tipLabel); // ����������ӱ�ǩ
		setResizable(false);
	}	
	
	public String getTipText(int locationMessage,boolean Repeat) {
		String tipText="<html>tips:<br/>";
		String Bomb0Tips[]={"ը�˸���į...","��ʵ֤��������Ҳ��ʵ����һ����","�ೢ�Դ��в���ʼը���������"
				,"��й������һ�´ξ������أ�","��˵��ը�����������ֲ����Ĳ�Ʒ~","���ⲻ��",
				"����ϧ��Դ����߲��Ƽ���̺ʽ��ը~"};
		String Bomb1Tips[]={"�Ѿ������ɹ���ϣ����!","���Ը��ݻ��������ͷ��λ�ã�","һ��һ�������ģ�����л����練�ƣ�",
				"����һ�·ɻ�����״��<br/>˵�����´ξ��Ǿ�׼���У�"};
		String Bomb2Tips[]={"���а��ģ�","Good Job��","�ɵ�Ư����"};
		String Bomb0RepeatTips[]={"����������ʲô��ʲôԹ...","����ը����Ҳ���ᴴ���漣��...","Ī�Ագ������ܱ����Ѷ�~"};
		String Bomb1RepeatTips[]={"Ϊʲôֻը�����޷��ݻٷɻ���<br/>���Ǹ�������...","һλͳ��ѧ��˵����"
				+ "��ǿս��ʱ<br/>Ӧ�������������ٵĲ�λ��<br/>�������һ����ѧ����...<br/>(18��ȫ����������)"};
		String Bomb2RepeatTips[]={"��һ���ɻ�ͷ��ը����<br/>�ǲ�����ʤ����!","����Ҫ�ѷɻ��ֽ�ɷ��Ӽ�����"};
		
		
		if(Repeat==false) {
			if(locationMessage==0) tipText+=Bomb0Tips[(int)(Math.random()*7)];
			if(locationMessage==1) tipText+=Bomb1Tips[(int)(Math.random()*4)];
			if(locationMessage==2) tipText+=Bomb2Tips[(int)(Math.random()*3)];
		}
		else {
			if(locationMessage==0) tipText+=Bomb0RepeatTips[(int)(Math.random()*3)];
			if(locationMessage==1) tipText+=Bomb1RepeatTips[(int)(Math.random()*2)];
			if(locationMessage==2) tipText+=Bomb2RepeatTips[(int)(Math.random()*2)];
		}
			
		
		tipText+="<br/><br/></html>";
		return tipText;
	}
}
