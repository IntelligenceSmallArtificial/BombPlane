package View;

import java.awt.*;
import javax.swing.*;

import LANBattleMode.LANBMFindEnemyFrame;

import java.awt.event.*;
import Service.Sound;
import WANBattleMode.WANBMFindEnemyFrame;

public class BattleModeChooseFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	private Sound sound = new Sound(); 
	private JLayeredPane layeredPane = new JLayeredPane();		//�㼶���
	private final Dimension screensize=Toolkit.getDefaultToolkit().getScreenSize();
	public BattleModeChooseFrame() {
		setBounds((int)screensize.getWidth()/2-200,(int)screensize.getHeight()/2-315, 400, 630);// ���ú�������Ϳ��
		setTitle("��սģʽ");// ����
		sound.playBGMChooseMode();
		setContentPane(layeredPane);
		NetChoose();
		
		ImageIcon icon = new ImageIcon("image/BattleModeChooseFrame/icon.png");	//����ͼ��
        setIconImage(icon.getImage());
        
        addWindowListener(new WindowAdapter() {// ��Ӵ������
            public void windowClosing(WindowEvent e) {// ����ر�ǰ
            	System.exit(0);
            }
        });
        setResizable(false);
        setVisible(true);
	}
	
	//ѡ��������ʽ
	public void NetChoose() {
		layeredPane.removeAll();
		layeredPane.repaint();
		layeredPane.setLayout(null);
		setTitle("��սģʽ");
		
		JButton BMWANJB=new JButton("Զ�̶�ս");
		setButton(BMWANJB);
		BMWANJB.setBounds(120,150,160, 60);
		BMWANJB.addActionListener(new ActionListener() { // Ϊ��ť�����굥���¼�
			public void actionPerformed(ActionEvent e) {
				BMWANEnterJB();
			}
		});
		layeredPane.add(BMWANJB,new Integer(300)); // ����ť��ӵ�������
		
		JButton BMLANJB=new JButton("������ս");
		setButton(BMLANJB);
		BMLANJB.setBounds(120, 250, 160, 60);
		BMLANJB.addActionListener(new ActionListener() { // Ϊ��ť�����굥���¼�
			public void actionPerformed(ActionEvent e) {
				BMLANEnterJB();
			}
		});
		layeredPane.add(BMLANJB,new Integer(300)); // ����ť��ӵ�������
		
		JButton BMMachineJB=new JButton("�˻���ս");
		setButton(BMMachineJB);
		BMMachineJB.setBounds(120, 350, 160, 60);
		BMMachineJB.addActionListener(new ActionListener() { // Ϊ��ť�����굥���¼�
			public void actionPerformed(ActionEvent e) {
				String text="<html>��ع������ڿ����У������ڴ���</html>";
				TextDialog txtDl=new TextDialog(BattleModeChooseFrame.this,"��ʾ",text);
				txtDl.setSize(800, 400); 
				txtDl.setLocation((int)screensize.getWidth()/2-400,(int)screensize.getHeight()/2-200);
				txtDl.setVisible(true);
			}
		});
		layeredPane.add(BMMachineJB,new Integer(300)); // ����ť��ӵ�������
		
		JButton BMreturnMainMenuJB=new JButton("���˵�");
		setButton(BMreturnMainMenuJB);
		BMreturnMainMenuJB.setBounds(120, 450, 160, 60);
		BMreturnMainMenuJB.addActionListener(new ActionListener() { // Ϊ��ť�����굥���¼�
			public void actionPerformed(ActionEvent e) {
				sound.stopPlay();
				dispose();
				new MainFrame();
			}
		});
		layeredPane.add(BMreturnMainMenuJB,new Integer(300)); // ����ť��ӵ�������
		
		//����
		ImageIcon backgroundIcon = new ImageIcon("image/BattleModeChooseFrame/background.png");
		backgroundIcon.setImage(backgroundIcon.getImage().getScaledInstance(400,630,Image.SCALE_DEFAULT));
		JLabel backgroundLabel=new JLabel(backgroundIcon);
		backgroundLabel.setBounds(0, 0,400,630);
		backgroundLabel.setHorizontalAlignment(SwingConstants.CENTER);
		layeredPane.add(backgroundLabel,new Integer(200));
		
//		ImageIcon icon = new ImageIcon("image/BattleChooseFrame/icon.png");	//����ͼ��
//      setIconImage(icon.getImage());
		layeredPane.revalidate();	//�ػ�
	}
	
	//ѡ�������ģʽ���뷽ʽ
	public void BMLANEnterJB() {
		layeredPane.removeAll();
		layeredPane.repaint();
		layeredPane.setLayout(null);
		setTitle("������ս");
		
		JButton BMCreateRoomJB=new JButton("��������");
		setButton(BMCreateRoomJB);
		BMCreateRoomJB.setBounds(120, 150, 160, 60);
		BMCreateRoomJB.addActionListener(new ActionListener() { // Ϊ��ť�����굥���¼�
			public void actionPerformed(ActionEvent e) {
				sound.stopPlay();
				dispose();
				new LANBMFindEnemyFrame(true);
			}
		});
		layeredPane.add(BMCreateRoomJB,new Integer(300)); // ����ť��ӵ�������
		
		JButton BMEnterRoomJB=new JButton("���뷿��");
		setButton(BMEnterRoomJB);
		BMEnterRoomJB.setBounds(120, 250, 160, 60);
		BMEnterRoomJB.addActionListener(new ActionListener() { // Ϊ��ť�����굥���¼�
			public void actionPerformed(ActionEvent e) {
				sound.stopPlay();
				dispose();
				new LANBMFindEnemyFrame(false);
			}
		});
		layeredPane.add(BMEnterRoomJB,new Integer(300)); // ����ť��ӵ�������
		
		JButton BMLastStepJB=new JButton("��һ��");
		setButton(BMLastStepJB);
		BMLastStepJB.setBounds(120, 350, 160, 60);
		BMLastStepJB.addActionListener(new ActionListener() { // Ϊ��ť�����굥���¼�
			public void actionPerformed(ActionEvent e) {
				NetChoose();
			}
		});
		layeredPane.add(BMLastStepJB,new Integer(300)); // ����ť��ӵ�������
		
		JButton BMreturnMainMenuJB=new JButton("���˵�");
		setButton(BMreturnMainMenuJB);
		BMreturnMainMenuJB.setBounds(120, 450, 160, 60);
		BMreturnMainMenuJB.addActionListener(new ActionListener() { // Ϊ��ť�����굥���¼�
			public void actionPerformed(ActionEvent e) {
				sound.stopPlay();
				dispose();
				new MainFrame();
			}
		});
		layeredPane.add(BMreturnMainMenuJB,new Integer(300)); // ����ť��ӵ�������
		
		//����
		ImageIcon backgroundIcon = new ImageIcon("image/BattleModeChooseFrame/background.png");
		backgroundIcon.setImage(backgroundIcon.getImage().getScaledInstance(400,630,Image.SCALE_DEFAULT));
		JLabel backgroundLabel=new JLabel(backgroundIcon);
		backgroundLabel.setBounds(0, 0,400,630);
		backgroundLabel.setHorizontalAlignment(SwingConstants.CENTER);
		layeredPane.add(backgroundLabel,new Integer(200));
		
		layeredPane.revalidate();	//�ػ�
	}
	
	//ѡ�������ģʽ���뷽ʽ
		public void BMWANEnterJB() {
			layeredPane.removeAll();
			layeredPane.repaint();
			layeredPane.setLayout(null);
			
			setTitle("Զ�̶�ս");
			JButton BMCreateRoomJB=new JButton("��������");
			setButton(BMCreateRoomJB);
			BMCreateRoomJB.setBounds(120, 150, 160, 60);
			BMCreateRoomJB.addActionListener(new ActionListener() { // Ϊ��ť�����굥���¼�
				public void actionPerformed(ActionEvent e) {
					sound.stopPlay();
					dispose();
					new WANBMFindEnemyFrame(true);
				}
			});
			layeredPane.add(BMCreateRoomJB,new Integer(300)); // ����ť��ӵ�������
			
			JButton BMEnterRoomJB=new JButton("���뷿��");
			setButton(BMEnterRoomJB);
			BMEnterRoomJB.setBounds(120, 250, 160, 60);
			BMEnterRoomJB.addActionListener(new ActionListener() { // Ϊ��ť�����굥���¼�
				public void actionPerformed(ActionEvent e) {
					sound.stopPlay();
					dispose();
					new WANBMFindEnemyFrame(false);
				}
			});
			layeredPane.add(BMEnterRoomJB,new Integer(300)); // ����ť��ӵ�������
			
			JButton BMLastStepJB=new JButton("��һ��");
			setButton(BMLastStepJB);
			BMLastStepJB.setBounds(120, 350, 160, 60);
			BMLastStepJB.addActionListener(new ActionListener() { // Ϊ��ť�����굥���¼�
				public void actionPerformed(ActionEvent e) {
					NetChoose();
				}
			});
			layeredPane.add(BMLastStepJB,new Integer(300)); // ����ť��ӵ�������
			
			JButton BMreturnMainMenuJB=new JButton("���˵�");
			setButton(BMreturnMainMenuJB);
			BMreturnMainMenuJB.setBounds(120, 450, 160, 60);
			BMreturnMainMenuJB.addActionListener(new ActionListener() { // Ϊ��ť�����굥���¼�
				public void actionPerformed(ActionEvent e) {
					sound.stopPlay();
					dispose();
					new MainFrame();
				}
			});
			layeredPane.add(BMreturnMainMenuJB,new Integer(300)); // ����ť��ӵ�������
			
			//����
			ImageIcon backgroundIcon = new ImageIcon("image/BattleModeChooseFrame/background.png");
			backgroundIcon.setImage(backgroundIcon.getImage().getScaledInstance(400,630,Image.SCALE_DEFAULT));
			JLabel backgroundLabel=new JLabel(backgroundIcon);
			backgroundLabel.setBounds(0, 0,400,630);
			backgroundLabel.setHorizontalAlignment(SwingConstants.CENTER);
			layeredPane.add(backgroundLabel,new Integer(200));
			
			layeredPane.revalidate();	//�ػ�
		}
	
	//���ð�ť
	public void setButton(JButton button) {
		button.setFont(new Font("����", Font.BOLD, 18));
		button.setForeground(Color.white);
		button.setContentAreaFilled(false);
		button.setFocusPainted(false);
	}
	
}
