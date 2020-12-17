package View;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;

import javax.swing.*;

public class TextDialog extends JDialog {  
	private static final long serialVersionUID = 1L;
	public TextDialog(JFrame frame,String title,String text){
		super(frame, title, true);
		Container container = getContentPane(); // ����һ������
		JLabel jl=new JLabel(text);
		jl.setFont(new Font("΢���ź�",0,18));
		jl.setHorizontalAlignment(SwingConstants.CENTER);
		container.add(jl); // ����������ӱ�ǩ
		container.setBackground (Color.white);
		setResizable(false);
	}

}

