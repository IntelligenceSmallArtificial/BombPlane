package View;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.event.*;

import Service.FileDataHanlder;
import Service.Sound;


public class MainFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	private Sound sound = new Sound(); 
	private JLayeredPane layeredPane = new JLayeredPane();		//�㼶���
	private boolean continueMove=true; 
	
	public MainFrame() {
		setLayout(null);
		Dimension screensize=Toolkit.getDefaultToolkit().getScreenSize();
		setContentPane(layeredPane);
	    sound.playBGMMenu();
		
		
		//������־��ť
		JButton blUpdate=new JButton("������־");
		setButton(blUpdate);
		blUpdate.setBounds(10, 520, 110, 30);
		blUpdate.addActionListener(new ActionListener() { // Ϊ��ť�����굥���¼�
			public void actionPerformed(ActionEvent e) {
				String text="<html><br/><br/><br/>"
						+ "��ǰ�汾:V1.8.0&#12288Version:V1.8.0<br/><br/>"
						+ "V1.8.0:Զ���������� 2020.10.20<br/>"
						+ "V1.7.0:����ϵͳ����,���������Ų�,ը�ɻ�������ӱ�ǹ��� 2020.10.19<br/>"
						+ "V1.6.0:���ù��ܿ���,�����������ڹ��ܺ����ݴ�ȡ���� 2020.8.7<br/>"
						+ "V1.5.0:��սģʽ�������bug�޸�,��սģʽģ������,�˻�ģʽ����,<br/>"
						+ "&#12288&#12288&#12288��սģʽ�����жϵз��Ƿ���߹���,�ײ�����Ż�,<br/>"
						+ "&#12288&#12288&#12288BGM�������䲢��Դ, ������־�������� 2020.8.6<br/>"
						+ "V1.4.0:��սģʽ���ţ������������˳���ť���� 2020.8.3<br/>"
						+ "V1.3.2:��ѧģʽ��ͼ���� 2020.7.27<br/>"
						+ "V1.3.1:��ѧģʽ�����Ż� 2020.7.27<br/>"
						+"V1.3.0:��ѧģʽ������� 2020.7.27<br/>"
						+ "V1.2.0:��սģʽ���ֿ��ţ��Ż����ı� 2020.7.26"
						+ "<br/>V1.1.0:��ѧģʽ���ţ��޸��˽���ʱ���������bug��"
						+ "<br/>&#12288&#12288&#12288�޸��˷�����������bug���Ż�����ը�ɻ��������߼���"
						+ "<br/>&#12288&#12288&#12288����ϵ���ǰ��������޸� 2020.7.24"
						+ "<br/>V1.0.3:����˻�ģʽ������ɷɻ����� 2020.7.22<br/>"
						+ "V1.0.2:�޸��˷�������ϵ�bug 2020.7.22<br/>V1.0.1:���ı��������޸� 2020.7.14<br/>"
						+ "V1.0.0:�����汾���� 2020.7.14<br/>"
						
						+"<br/><br/>Ԥ�ڸ���ǰհ :<br/>"
						+ "(��Щ�����Ѿ���������!���ȶȴ��ϵ������ν���)<br/>"
						+"ը�ɻ�ģ��غϼ�ʱ����<br/>��սģʽԶ����������<br/>"
						+ "��սģʽ�˻���ս����<br/>����ģʽ����<br/>"
						
						+ "<br/><br/>��������Ϣ:����Ϸ��BombPlane��Ŀ����п�����ά������¡�<br/>"
						+ "QQ:1731019653&#12288Tel:18646393118<br/>"
						+ "��л������BombPlane��ף�������죡<br/><br/><br/><br/></html>";
				JDialog UpdateJD=new JDialog(MainFrame.this,"������־",true);
				Container UDcontainer=UpdateJD.getContentPane();
				UDcontainer.setBackground (Color.white);
				JLabel jl=new JLabel(text);
				jl.setFont(new Font("΢���ź�",0,18));
				jl.setHorizontalAlignment(SwingConstants.CENTER);
				
				JScrollPane sp=new JScrollPane(jl);
				sp.getVerticalScrollBar().setUnitIncrement(25);	//����������
				sp.setOpaque(false);
				sp.getViewport().setOpaque(false);
				UDcontainer.add(sp); // ����������ӱ�ǩ
				
				UpdateJD.setSize(800, 600); 
				UpdateJD.setLocation((int)screensize.getWidth()/2-400,(int)screensize.getHeight()/2-300);
				UpdateJD.setVisible(true);
			}
		});
		layeredPane.add(blUpdate,new Integer(300));
		
		//������ť
		JButton blHelp=new JButton("����");
		setButton(blHelp);
		blHelp.setBounds(225, 520, 110, 30);
		blHelp.addActionListener(new ActionListener() { // Ϊ��ť�����굥���¼�
			public void actionPerformed(ActionEvent e) {
				String text="<html>1.��ӭ����BombPlane�����磡����Ϸ��BombPlane��Ŀ�����������ά������¡�<br/>"+
		"2.��Ϸ������Ч����ע����Χ������������<br/>3.BombPlane��Ŀ��ֻ��һ�����Ǿ��Ǵݻٵз������ܷɻ���<br/>"
		+ "4.����ԶԵ�ͼ������к�ը�Գ��Դݻٵл���<br/>5.ֻ�е����ը�ɻ�ͷʱ������Ϊ�ݻٵз��ɻ���<br/>"
		+ "6.�볢��������֪��Ϣ����з��ɻ�ͷλ�á�<br/>7.����㻹���Ǻܶ���Ϸ�淨�����Գ��Խ�ѧģʽ��"
		+ "<br/>8.?��ʾδ֪���ո��ʾ�޷ɻ���O��ʾ����X��ʾ��ͷ��<br/>9.������κδ������bug����������ϵ������: QQ 1731019653"
		+ "&#12288Tel 18646393118<br/>10.��л������BombPlane��ף�������죡</html>";
				TextDialog txtDl=new TextDialog(MainFrame.this,"����",text);
				txtDl.setSize(800, 400); 
				txtDl.setLocation((int)screensize.getWidth()/2-400,(int)screensize.getHeight()/2-200);
				txtDl.setVisible(true);
			}
		});
		layeredPane.add(blHelp,new Integer(300));
				
		//������Ա��ť
		JButton blContactUs=new JButton("������Ա");
		setButton(blContactUs);
		blContactUs.setBounds(440, 520, 110, 30);
		blContactUs.addActionListener(new ActionListener() { // Ϊ��ť�����굥���¼�
			public void actionPerformed(ActionEvent e) {
				String text="<html>BombPlane(ը�ɻ�)��Ϸ��BombPlane��Ŀ��<br/>���п�����ά������¡�<br/>"
+ "BombPlane��Ŀ������ڹ��������ڣ���һ��������<br/>BombPlane��Ŀ���Ա:<br/>��Ŀ��ʦ �����޸�����<br/>�鳤 ������"
+ "<br/>��Ա �ƹ�ǰ<br/>��Ա ���<br/>��Ա ������<br/><br/>��ϵ����:<br/>QQ:1731019653&#12288Tel:18646393118<br/><br/>"
+ "��������Ϣ:<br/>����ƽ<br/>��л�����߶���Ϸ�����Ĵ���֧�֣�</html>";
				TextDialog txtDl=new TextDialog(MainFrame.this,"������Ա",text);
				txtDl.setSize(600, 600); 
				txtDl.setLocation((int)screensize.getWidth()/2-300,(int)screensize.getHeight()/2-300);
				txtDl.setVisible(true);
				}
			});
		layeredPane.add(blContactUs,new Integer(300));
			
		//���ð�ť              
		JButton blSetting=new JButton("����");
		setButton(blSetting);
		blSetting.setBounds(655, 520, 110, 30);
		blSetting.addActionListener(new ActionListener() { // Ϊ��ť�����굥���¼�
			public void actionPerformed(ActionEvent e) {
				JDialog SettingJD=new JDialog(MainFrame.this,"����",true);
				Container SettingJDcontainer=SettingJD.getContentPane();
				SettingJDcontainer.setBackground (Color.white);
				SettingJD.setLayout(null);
				
				String text="<html>������<br/><br/>&#12288����<br/><br/>&#12288��Ч</html>";
				JLabel jl=new JLabel(text);
				jl.setFont(new Font("΢���ź�",0,20));
				jl.setHorizontalAlignment(SwingConstants.CENTER);
				jl.setBounds(22,15,75,150);
				SettingJDcontainer.add(jl);
				
				int[] volumns=FileDataHanlder.getVolulmes();
				
				JSlider MasterVolumnJS=new JSlider(0,100,volumns[0]);
				MasterVolumnJS.setMajorTickSpacing(20); // �������̶ȼ��
				MasterVolumnJS.setMinorTickSpacing(5); // ���ôο̶ȼ��
				MasterVolumnJS.setPaintTicks(true);  // ���� �̶� �� ��ǩ
				MasterVolumnJS.setPaintLabels(true);
				//MasterVolumnJS.setForeground(Color.white);
				MasterVolumnJS.setOpaque(false);
				MasterVolumnJS.setBounds(100, 20, 300, 50);
				MasterVolumnJS.addChangeListener(new ChangeListener() {
			            @Override
			            public void stateChanged(ChangeEvent e) {
			            	volumns[0]=MasterVolumnJS.getValue();
			            	sound.temporaryChangeVolumn(((float)volumns[0]*volumns[1])/10000);
			            }
			    });
				SettingJDcontainer.add(MasterVolumnJS);
		        
				JSlider BGMVolumnJS=new JSlider(0,100,volumns[1]);
				BGMVolumnJS.setMajorTickSpacing(20); // �������̶ȼ��
				BGMVolumnJS.setMinorTickSpacing(5); // ���ôο̶ȼ��
				BGMVolumnJS.setPaintTicks(true);  // ���� �̶� �� ��ǩ
				BGMVolumnJS.setPaintLabels(true);
				//BGMVolumnJS.setForeground(Color.white);
				BGMVolumnJS.setOpaque(false);
				BGMVolumnJS.setBounds(100, 76, 300, 50);
				BGMVolumnJS.addChangeListener(new ChangeListener() {
		            @Override
		            public void stateChanged(ChangeEvent e) {
		            	volumns[1]=BGMVolumnJS.getValue();
		            	sound.temporaryChangeVolumn(((float)volumns[0]*volumns[1])/10000);
		            }
				});
				SettingJDcontainer.add(BGMVolumnJS);
				
				JSlider SoundEffectVolumnJS=new JSlider(0,100,volumns[2]);
				SoundEffectVolumnJS.setMajorTickSpacing(20); // �������̶ȼ��
				SoundEffectVolumnJS.setMinorTickSpacing(5); // ���ôο̶ȼ��
				SoundEffectVolumnJS.setPaintTicks(true);  // ���� �̶� �� ��ǩ
				SoundEffectVolumnJS.setPaintLabels(true);
				//SoundEffectVolumnJS.setForeground(Color.white);
				SoundEffectVolumnJS.setOpaque(false);
				SoundEffectVolumnJS.setBounds(100, 135, 300, 50);
				SoundEffectVolumnJS.addChangeListener(new ChangeListener() {
		            @Override
		            public void stateChanged(ChangeEvent e) {
		            	volumns[2]=BGMVolumnJS.getValue();
		            }
				});
				SettingJDcontainer.add(SoundEffectVolumnJS);
				
				SettingJD.addWindowListener(new WindowAdapter() {// ��Ӵ������
		            public void windowClosing(WindowEvent e) {// ����ر�ǰ
		            	FileDataHanlder.changeVolulmes(volumns);
		            	FileDataHanlder.saveVolumes();
		            }
		        });
				SettingJD.setSize(480, 230); 
				SettingJD.setLocation((int)screensize.getWidth()/2-240,(int)screensize.getHeight()/2-115);
				SettingJD.setVisible(true);
			}
		});
		layeredPane.add(blSetting,new Integer(300));
				
		//�˳���ť               
		JButton blExit=new JButton("�˳�");
		setButton(blExit);
		blExit.setBounds(870, 520, 110, 30);
		blExit.addActionListener(new ActionListener() { // Ϊ��ť�����굥���¼�
			public void actionPerformed(ActionEvent e) {
				whetherExit();
			}
		});
		layeredPane.add(blExit,new Integer(300));
				
		//��սģʽ��ť     
		JButton blBattleMode=new JButton("��սģʽ");
		setButton(blBattleMode);
		blBattleMode.setBounds(750, 27, 160, 60);
		blBattleMode.addActionListener(new ActionListener() { // Ϊ��ť�����굥���¼�
			public void actionPerformed(ActionEvent e) {
				sound.stopPlay();
				dispose();
				continueMove=false;
				new BattleModeChooseFrame();
			}
		});
		layeredPane.add(blBattleMode,new Integer(300));
						
		//�˻�ģʽ��ť       
		JButton blMachineMode=new JButton("�˻�ģʽ");
		setButton(blMachineMode);
		blMachineMode.setBounds(750, 157, 160, 60);
		blMachineMode.addActionListener(new ActionListener() { // Ϊ��ť�����굥���¼�
			public void actionPerformed(ActionEvent e) {
				sound.stopPlay();
				dispose();
				continueMove=false;
				new MachineModeFrame();
			}
		});
		layeredPane.add(blMachineMode,new Integer(300));
					
		//����ģʽ��ť         TODO
		JButton blCreativityMode=new JButton("����ģʽ");
		setButton(blCreativityMode);
		blCreativityMode.setBounds(750, 287, 160, 60);
		blCreativityMode.addActionListener(new ActionListener() { // Ϊ��ť�����굥���¼�
			public void actionPerformed(ActionEvent e) {
				String text="<html>��ع������ڿ����У������ڴ���</html>";
				TextDialog txtDl=new TextDialog(MainFrame.this,"����ģʽ",text);
				txtDl.setSize(800, 400); 
				txtDl.setLocation((int)screensize.getWidth()/2-400,(int)screensize.getHeight()/2-200);
				txtDl.setVisible(true);
			}
		});
		layeredPane.add(blCreativityMode,new Integer(300));
		
		//��ѧģʽ��ť         
		JButton blTeachingMode=new JButton("��ѧģʽ");
		setButton(blTeachingMode);
		blTeachingMode.setBounds(750, 417, 160, 60);
		blTeachingMode.addActionListener(new ActionListener() { // Ϊ��ť�����굥���¼�
			public void actionPerformed(ActionEvent e) {
				sound.stopPlay();
				dispose();
				continueMove=false;
				new TeachingModeFrame();
			}
		});
		layeredPane.add(blTeachingMode,new Integer(300));
				
		//�����߱�ǩ
		ImageIcon teamIcon = new ImageIcon("image/MainFrame/Team.png");
		teamIcon.setImage(teamIcon.getImage().getScaledInstance(300,50,Image.SCALE_DEFAULT));
		JLabel jlDeveloper = new JLabel(teamIcon);
		jlDeveloper.setBounds(50,25,300,50);
		jlDeveloper.setHorizontalAlignment(SwingConstants.CENTER);
		
		layeredPane.add(jlDeveloper,new Integer(300));
		
		//����
		ImageIcon backgroundIcon = new ImageIcon("image/MainFrame/longbackground.jpg");
		backgroundIcon.setImage(backgroundIcon.getImage().getScaledInstance(2000,630,Image.SCALE_DEFAULT));
		JLabel backgroundLabel=new JLabel(backgroundIcon);
		backgroundLabel.setBounds(0, 0, 2000, 630);
		backgroundLabel.setHorizontalAlignment(SwingConstants.CENTER);
		layeredPane.add(backgroundLabel,new Integer(200));
		
		//layeredPane.revalidate();	//�ػ�
		Runnable backgroundThread = () -> {
			int step=1000;
			while(continueMove) {
				backgroundLabel.setBounds(step-1000, 0, 2000, 630);
				try {
					Thread.sleep(50);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				step--;
				if(step<0) {
					step=1000;
				}
			}
	    };
	    Thread backgroundLine = new Thread(backgroundThread);
	    backgroundLine.start();
	    
		ImageIcon icon = new ImageIcon("image/MainFrame/icon.png");	//����ͼ��
        setIconImage(icon.getImage());
		setBounds((int)screensize.getWidth()/2-500,(int)screensize.getHeight()/2-315, 1000, 630);// ���ú�������Ϳ��
		setTitle("BombPlane");// ����
        addWindowListener(new WindowAdapter() {// ��Ӵ������
            public void windowClosing(WindowEvent e) {// ����ر�ǰ
            	System.exit(0);
            }
        });
        setResizable(false);
        setVisible(true);
	}
	
	//���ð�ť
	public void setButton(JButton button) {
		button.setFont(new Font("����", Font.BOLD, 18));
		button.setForeground(Color.white);
		button.setContentAreaFilled(false);
		button.setFocusPainted(false);
	}
	
	//ѯ���Ƿ��˳���Ϸ
	public void whetherExit() {
		JDialog exitDialog = new JDialog(this,"��ʾ",true);
		exitDialog.setLayout(null);
		exitDialog.setSize(300, 180);
		exitDialog.setLocationRelativeTo(null);
		Container container = exitDialog.getContentPane(); // ����һ������
		
		JLabel exitLabel=new JLabel("�Ƿ��˳���Ϸ?");
		exitLabel.setFont(new Font("����", Font.BOLD, 20));
		exitLabel.setForeground(Color.BLACK);
		exitLabel.setHorizontalAlignment(SwingConstants.CENTER);
		exitLabel.setBounds(45,20,200,40);
		container.add(exitLabel); // ����������ӱ�ǩ
		
		JButton enterButton = new JButton("ȷ��");
		enterButton.setFont(new Font("����", Font.BOLD, 18));
		enterButton.setForeground(Color.BLACK);
		enterButton.setContentAreaFilled(false);
		enterButton.setFocusPainted(false);
		enterButton.setBounds(30, 100, 80, 30);
		enterButton.addActionListener(new ActionListener() { // Ϊ��ť�����굥���¼�
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		container.add(enterButton);
		
		JButton cancelButton = new JButton("ȡ��");
		cancelButton.setFont(new Font("����", Font.BOLD, 18));
		cancelButton.setForeground(Color.BLACK);
		cancelButton.setContentAreaFilled(false);
		cancelButton.setFocusPainted(false);
		cancelButton.setBounds(180, 100, 80, 30);
		cancelButton.addActionListener(new ActionListener() { // Ϊ��ť�����굥���¼�
			@Override
			public void actionPerformed(ActionEvent e) {
				exitDialog.dispose();
			}
		});
		container.add(cancelButton);
		
		container.setBackground (Color.white);
		exitDialog.setResizable(false);
		exitDialog.setVisible(true);
	}
	
}