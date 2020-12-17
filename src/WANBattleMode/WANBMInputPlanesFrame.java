package WANBattleMode;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

import Service.Sound;
import Calculate.*;
import View.*;

public class WANBMInputPlanesFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	private WANBMFindEnemyFrame messageHandler;
	private JLayeredPane layeredPane = new JLayeredPane();
	private Sound sound = new Sound(); 
	private final Dimension screensize=Toolkit.getDefaultToolkit().getScreenSize();
	private boolean BattleModeAlreadySent;
	private Enemy myInputEnemy;	//�ҷ��ɻ�������Ϣ
	private int myInputCode;
	private int InputtingPlane=1;
	private boolean AlreadyChooseCenter=false;
	private int InputtedcenterRow=-1;
	private int InputtedcenterColumn=-1;

	private ImageIcon blueCircle = new ImageIcon("image/buttonImage/blueCircle.png");
	private ImageIcon cyanCircle = new ImageIcon("image/buttonImage/cyanCircle.png");
	private ImageIcon whiteCircle = new ImageIcon("image/buttonImage/whiteCircle.png");
	private ImageIcon whiteHead = new ImageIcon("image/buttonImage/whiteHead.png");
	
	public WANBMInputPlanesFrame(WANBMFindEnemyFrame myfindEnemyFrame) {
		messageHandler=myfindEnemyFrame;
		setLayout(null);
		sound.playBGMInputPlanes();
		setContentPane(layeredPane);
		BattleModeAlreadySent=false;
		
		blueCircle.setImage(blueCircle.getImage().getScaledInstance(20,20,Image.SCALE_DEFAULT));
		cyanCircle.setImage(cyanCircle.getImage().getScaledInstance(20,20,Image.SCALE_DEFAULT));
		whiteCircle.setImage(whiteCircle.getImage().getScaledInstance(20,20,Image.SCALE_DEFAULT));
		whiteHead.setImage(whiteHead.getImage().getScaledInstance(20,20,Image.SCALE_DEFAULT));
		BattleModeInput();
		
		setBounds((int)screensize.getWidth()/2-500,(int)screensize.getHeight()/2-315, 1000, 630);// ���ú�������Ϳ��
		setTitle("����ɻ�");// ����
		setResizable(false);
        addWindowListener(new WindowAdapter() {// ��Ӵ������
            public void windowClosing(WindowEvent e) {// ����ر�ǰ
            	System.exit(0);
            }
        });
        ImageIcon icon = new ImageIcon("image/LANBM/icon.png");	//����ͼ��
        setIconImage(icon.getImage());
        setVisible(true);
	}
	
	
	//��սģʽ �����λ��Ϣ��
	public void BattleModeInput() { 
		layeredPane.removeAll();
		layeredPane.repaint();
		layeredPane.setLayout(null);
		
		myInputEnemy = new Enemy();
		InputtingPlane=1;
		AlreadyChooseCenter=false;
		InputtedcenterRow=-1;
		InputtedcenterColumn=-1;
		myInputCode=0;
		layeredPane.setLayout(null);
		JButton removeAllJB=new JButton("ȫ��ɾ��");
		removeAllJB.setBounds(650,10,150,40);
		setButton(removeAllJB);
		removeAllJB.addActionListener(new ActionListener() { // Ϊ��ť�����굥���¼�
			public void actionPerformed(ActionEvent e) {
				if(BattleModeAlreadySent) {
					TextDialog AlreadySentDl=new TextDialog(WANBMInputPlanesFrame.this,"�ύ��Ϣ",
							"<html>���Ѿ��ύ���ˣ�<br/>��ȴ��Է��ύ��</html>");
					Dimension screensize=Toolkit.getDefaultToolkit().getScreenSize();
					AlreadySentDl.setSize(400, 200); 
					AlreadySentDl.setLocation((int)screensize.getWidth()/2-200,(int)screensize.getHeight()/2-100);
					AlreadySentDl.setVisible(true);
				}
				else {
					BattleModeInput();
				}
			}
		});
		layeredPane.add(removeAllJB,new Integer(200));
		
		ImageIcon homeIcon= new ImageIcon("image/MainFrame/home.png");
		homeIcon.setImage(homeIcon.getImage().getScaledInstance(44,40,Image.SCALE_DEFAULT));
		JButton returnMainMenu=new JButton(homeIcon);
		returnMainMenu.setBounds(0,0,60,40);
		setButton(returnMainMenu);
		returnMainMenu.setHorizontalAlignment(SwingConstants.CENTER);
		returnMainMenu.setVisible(true);
		returnMainMenu.addActionListener(new ActionListener() { // Ϊ��ť�����굥���¼�
			public void actionPerformed(ActionEvent e) {
				dispose();
				soundStopPlay();
				messageHandler.stopConnect();
				new MainFrame();
			}
		});
		layeredPane.add(returnMainMenu,new Integer(300));
		
		JButton scrB[][]=new JButton[10][10];
		JLabel rowNumberJL[]=new JLabel[10];
		JLabel columbLetterJL[]=new JLabel[10];
		String numbers[]= {"1","2","3","4","5","6","7","8","9","10"};
		String letters[]= {"A","B","C","D","E","F","G","H","I","J"};
		
		for(int i=0;i<=10;i++) {
			for(int j=0;j<=10;j++) {
				if(i==0){
					if(j!=0) {
						rowNumberJL[j-1]=new JLabel(numbers[j-1]);
						rowNumberJL[j-1].setFont(new Font("����", Font.BOLD, 18));
						rowNumberJL[j-1].setForeground(Color.white);
						rowNumberJL[j-1].setBounds(27+50*j, 20+50*i, 50, 50);
						rowNumberJL[j-1].setVisible(true);
						layeredPane.add(rowNumberJL[j-1],new Integer(200));
					}
				}
				else {
					if(j==0) {
						columbLetterJL[i-1]=new JLabel(letters[i-1]);
						columbLetterJL[i-1].setFont(new Font("����", Font.BOLD, 18));
						columbLetterJL[i-1].setForeground(Color.white);
						columbLetterJL[i-1].setBounds(30+50*j, 10+50*i, 50, 50);
						columbLetterJL[i-1].setVisible(true);
						layeredPane.add(columbLetterJL[i-1],new Integer(200));
					}
					else {
						scrB[i-1][j-1]=new JButton();
						setButton(scrB[i-1][j-1]);
						scrB[i-1][j-1].setBounds(10+50*j, 10+50*i, 50, 50);
						final int FiMinus1=i-1;
						final int FjMinus1=j-1;
						scrB[i-1][j-1].addActionListener(new ActionListener() { // Ϊ��ť�����굥���¼�
							/**
							static int inputCode;
							static int InputtingPlane=1;
							static boolean AlreadyChooseCenter=false;
							static int InputtedcenterRow=-1;
							static int InputtedcenterColumn=-1;
							*/
							@Override
							public void actionPerformed(ActionEvent e) {
								if(BattleModeAlreadySent==false) {
									if(InputtingPlane<=3) {
										if(myInputEnemy.seeLocation(FiMinus1,FjMinus1)==0) {
											if(AlreadyChooseCenter==false) {
												scrB[FiMinus1][FjMinus1].setIcon(blueCircle);
												InputtedcenterRow=FiMinus1;
												InputtedcenterColumn=FjMinus1;
												AlreadyChooseCenter=true;
											}//ѡ������ ����
											else {
												if(InputtedcenterRow==FiMinus1&&InputtedcenterColumn==FjMinus1) {
													scrB[FiMinus1][FjMinus1].setIcon(null);
													InputtedcenterRow=-1;
													InputtedcenterColumn=-1;
													AlreadyChooseCenter=false;
												}//ȡ������ѡ�н���
												else if(InputtedcenterRow==FiMinus1+1&&InputtedcenterColumn==FjMinus1) {
													//��0
													Plane plane=new Plane(InputtedcenterRow,InputtedcenterColumn,0);
													if(myInputEnemy.addPlane(plane)) {
														int locationMessages[]=plane.showLocations();
														for(int j=0;j<10;j++) {
															scrB[locationMessages[j]][locationMessages[j+10]].setIcon(whiteCircle);
														}
														scrB[InputtedcenterRow][InputtedcenterColumn].setIcon(cyanCircle);
														scrB[plane.showHeadLocation()[0]][plane.showHeadLocation()[1]].setIcon(whiteHead);
														myInputCode+=(int)(Math.pow(10,InputtingPlane*3-1)*InputtedcenterRow+
																Math.pow(10,InputtingPlane*3-2)*InputtedcenterColumn+Math.pow(10,InputtingPlane*3-3)*0);
														InputtingPlane++;
														InputtedcenterRow=-1;
														InputtedcenterColumn=-1;
														AlreadyChooseCenter=false;
													}
													else {
														String text="�ɻ����벻�Ϸ���";
														TextDialog txtDl=new TextDialog(WANBMInputPlanesFrame.this,"��ʾ",text);
														Dimension screensize=Toolkit.getDefaultToolkit().getScreenSize();
														txtDl.setSize(200, 150); 
														txtDl.setLocation((int)screensize.getWidth()/2-100,(int)screensize.getHeight()/2-75);
														txtDl.setVisible(true);
													}
												}//�Ͻ���
												else if(InputtedcenterRow==FiMinus1-1&&InputtedcenterColumn==FjMinus1) {
													//��1
													Plane plane=new Plane(InputtedcenterRow,InputtedcenterColumn,1);
													if(myInputEnemy.addPlane(plane)) {
														int locationMessages[]=plane.showLocations();
														scrB[InputtedcenterRow][InputtedcenterColumn].setForeground(Color.cyan);
														for(int j=0;j<10;j++) {
															scrB[locationMessages[j]][locationMessages[j+10]].setIcon(whiteCircle);
														}
														scrB[InputtedcenterRow][InputtedcenterColumn].setIcon(cyanCircle);
														scrB[plane.showHeadLocation()[0]][plane.showHeadLocation()[1]].setIcon(whiteHead);
														myInputCode+=(int)(Math.pow(10,InputtingPlane*3-1)*InputtedcenterRow+
																Math.pow(10,InputtingPlane*3-2)*InputtedcenterColumn+Math.pow(10,InputtingPlane*3-3)*1);
														InputtingPlane++;
														InputtedcenterRow=-1;
														InputtedcenterColumn=-1;
														AlreadyChooseCenter=false;
													}
													else {
														String text="�ɻ����벻�Ϸ���";
														TextDialog txtDl=new TextDialog(WANBMInputPlanesFrame.this,"��ʾ",text);
														Dimension screensize=Toolkit.getDefaultToolkit().getScreenSize();
														txtDl.setSize(200, 150); 
														txtDl.setLocation((int)screensize.getWidth()/2-100,(int)screensize.getHeight()/2-75);
														txtDl.setVisible(true);
													}
												}//�½���
												else if(InputtedcenterRow==FiMinus1&&InputtedcenterColumn==FjMinus1+1) {
													//��2
													Plane plane=new Plane(InputtedcenterRow,InputtedcenterColumn,2);
													if(myInputEnemy.addPlane(plane)) {
														int locationMessages[]=plane.showLocations();
														scrB[InputtedcenterRow][InputtedcenterColumn].setForeground(Color.cyan);
														for(int j=0;j<10;j++) {
															scrB[locationMessages[j]][locationMessages[j+10]].setIcon(whiteCircle);
														}
														scrB[InputtedcenterRow][InputtedcenterColumn].setIcon(cyanCircle);
														scrB[plane.showHeadLocation()[0]][plane.showHeadLocation()[1]].setIcon(whiteHead);
														myInputCode+=(int)(Math.pow(10,InputtingPlane*3-1)*InputtedcenterRow+
																Math.pow(10,InputtingPlane*3-2)*InputtedcenterColumn+Math.pow(10,InputtingPlane*3-3)*2);
														InputtingPlane++;
														InputtedcenterRow=-1;
														InputtedcenterColumn=-1;
														AlreadyChooseCenter=false;
													}
													else {
														String text="�ɻ����벻�Ϸ���";
														TextDialog txtDl=new TextDialog(WANBMInputPlanesFrame.this,"��ʾ",text);
														Dimension screensize=Toolkit.getDefaultToolkit().getScreenSize();
														txtDl.setSize(200, 150); 
														txtDl.setLocation((int)screensize.getWidth()/2-100,(int)screensize.getHeight()/2-75);
														txtDl.setVisible(true);
													}
												}//�����
												else if(InputtedcenterRow==FiMinus1&&InputtedcenterColumn==FjMinus1-1) {
													//��3
													Plane plane=new Plane(InputtedcenterRow,InputtedcenterColumn,3);
													if(myInputEnemy.addPlane(plane)) {
														int locationMessages[]=plane.showLocations();
														scrB[InputtedcenterRow][InputtedcenterColumn].setForeground(Color.cyan);
														for(int j=0;j<10;j++) {
															scrB[locationMessages[j]][locationMessages[j+10]].setIcon(whiteCircle);
														}
														scrB[InputtedcenterRow][InputtedcenterColumn].setIcon(cyanCircle);
														scrB[plane.showHeadLocation()[0]][plane.showHeadLocation()[1]].setIcon(whiteHead);
														myInputCode+=(int)(Math.pow(10,InputtingPlane*3-1)*InputtedcenterRow+
																Math.pow(10,InputtingPlane*3-2)*InputtedcenterColumn+Math.pow(10,InputtingPlane*3-3)*3);
														InputtingPlane++;
														InputtedcenterRow=-1;
														InputtedcenterColumn=-1;
														AlreadyChooseCenter=false;
													}
													else {
														String text="�ɻ����벻�Ϸ���";
														TextDialog txtDl=new TextDialog(WANBMInputPlanesFrame.this,"��ʾ",text);
														Dimension screensize=Toolkit.getDefaultToolkit().getScreenSize();
														txtDl.setSize(200, 150); 
														txtDl.setLocation((int)screensize.getWidth()/2-100,(int)screensize.getHeight()/2-75);
														txtDl.setVisible(true);
													}
												}//�ҽ���
												else {
													scrB[InputtedcenterRow][InputtedcenterColumn].setIcon(null);
													scrB[InputtedcenterRow][InputtedcenterColumn].setForeground(Color.black);
													scrB[FiMinus1][FjMinus1].setIcon(blueCircle);
													InputtedcenterRow=FiMinus1;
													InputtedcenterColumn=FjMinus1;
													AlreadyChooseCenter=true;
												}//��������λ�� ����
											}//ѡ�з����� ����
										}//ѡ��λ��Ϊ�� ����
										else {
											String text="ѡ��ʧ�ܣ�";
											TextDialog txtDl=new TextDialog(WANBMInputPlanesFrame.this,"��ʾ",text);
											Dimension screensize=Toolkit.getDefaultToolkit().getScreenSize();
											txtDl.setSize(200, 150); 
											txtDl.setLocation((int)screensize.getWidth()/2-100,(int)screensize.getHeight()/2-75);
											txtDl.setVisible(true);
										}//ѡ��λ�÷ǿ� ����
									}//δ�������ܷɻ� ����
									else {
										String text="<html>���������ܷɻ�!<br/>����ȷ����������ύ��</html>";
										TextDialog txtDl=new TextDialog(WANBMInputPlanesFrame.this,"��ʾ",text);
										Dimension screensize=Toolkit.getDefaultToolkit().getScreenSize();
										txtDl.setSize(400, 200); 
										txtDl.setLocation((int)screensize.getWidth()/2-200,(int)screensize.getHeight()/2-100);
										txtDl.setVisible(true);
									}//���������ܷɻ� ����
								}//δ�ύ ����
								else {
									String text="<html>�Ѿ��ύ���ɻ�������Ϣ!<br/>��ȴ��Է��ύ��</html>";
									TextDialog txtDl=new TextDialog(WANBMInputPlanesFrame.this,"��ʾ",text);
									Dimension screensize=Toolkit.getDefaultToolkit().getScreenSize();
									txtDl.setSize(400, 200); 
									txtDl.setLocation((int)screensize.getWidth()/2-200,(int)screensize.getHeight()/2-100);
									txtDl.setVisible(true);
								}//���ύ ����
							}//actionperformed ����
						});//actionListener ����
						scrB[i-1][j-1].setVisible(true);
						layeredPane.add(scrB[i-1][j-1],new Integer(200));
					}//j!=0 ����
				}//i!=0 ����
			}
		}

		JButton HandInJB=new JButton("�ύ");
		setButton(HandInJB);
		HandInJB.setBounds(830,10,150,40);
		HandInJB.addActionListener(new ActionListener() { // Ϊ��ť�����굥���¼�
			public void actionPerformed(ActionEvent e) {
				if(BattleModeAlreadySent==false) {
					if(InputtingPlane>3) {
						messageHandler.sendMessage("1"+String.valueOf(myInputCode));
						BattleModeAlreadySent=true;
						messageHandler.saveMyInputCode(myInputCode);
						if(messageHandler.getCodeReceiveFromEnemy()==-1) {
							TextDialog SentSuccessDl=new TextDialog(WANBMInputPlanesFrame.this,"�ύ��Ϣ",
									"<html>�ύ�ɹ���<br/>��ȴ��Է��ύ��</html>");
							Dimension screensize=Toolkit.getDefaultToolkit().getScreenSize();
							SentSuccessDl.setSize(400, 200); 
							SentSuccessDl.setLocation((int)screensize.getWidth()/2-200,(int)screensize.getHeight()/2-100);
							SentSuccessDl.setVisible(true);
						}
						else {
							dispose();
							soundStopPlay();
							messageHandler.createMyBombPlanesFrame(false);
						}
					}
					else {
						String text="������3�ܷɻ����ٵ���ύ��";
						TextDialog txtDl=new TextDialog(WANBMInputPlanesFrame.this,"�ύ��Ϣ",text);
						Dimension screensize=Toolkit.getDefaultToolkit().getScreenSize();
						txtDl.setSize(400, 200); 
						txtDl.setLocation((int)screensize.getWidth()/2-200,(int)screensize.getHeight()/2-100);
						txtDl.setVisible(true);
					}
				}
				else {
					TextDialog AlreadySentDl=new TextDialog(WANBMInputPlanesFrame.this,"�ύ��Ϣ",
							"<html>���Ѿ��ύ���ˣ�<br/>��ȴ��Է��ύ��</html>");
					Dimension screensize=Toolkit.getDefaultToolkit().getScreenSize();
					AlreadySentDl.setSize(400, 200); 
					AlreadySentDl.setLocation((int)screensize.getWidth()/2-200,(int)screensize.getHeight()/2-100);
					AlreadySentDl.setVisible(true);
				}
			}
		});
		layeredPane.add(HandInJB,new Integer(200)); // ����ť��ӵ�������	
		
		JPanel chatPanel = messageHandler.getChatPanel();
		chatPanel.setLocation(685,394);
		layeredPane.add(chatPanel,new Integer(200)); // ����ť��ӵ�������	
		
		//����
		ImageIcon backgroundIcon = new ImageIcon("image/LANBM/background.jpg");
		backgroundIcon.setImage(backgroundIcon.getImage().getScaledInstance(1000,630,Image.SCALE_DEFAULT));
		JLabel backgroundLabel=new JLabel(backgroundIcon);
		backgroundLabel.setBounds(0, 0,1000,630);
		backgroundLabel.setHorizontalAlignment(SwingConstants.CENTER);
		layeredPane.add(backgroundLabel,new Integer(200));
		
		layeredPane.revalidate();// ����������֤�������
	}
	
	//�Ƿ��Ѿ�����9λ��Ϣ��
	public boolean getBattleModeAlreadySent() {
		return BattleModeAlreadySent;
	}
	
	//ֹͣ��������
	public void soundStopPlay() {
		sound.stopPlay();
	}
	
	//���ð�ť
	public void setButton(JButton button) {
		button.setFont(new Font("����", Font.BOLD, 18));
		button.setForeground(Color.white);
		button.setContentAreaFilled(false);
		button.setFocusPainted(false);
	}
	
}
