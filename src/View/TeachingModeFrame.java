package View;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import Calculate.*;
import Service.Sound;
public class TeachingModeFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private int step;
	private int bombedPlane;
	private Sound sound = new Sound(); 
	private JLayeredPane layeredPane = new JLayeredPane();
	private int[][] scrBStatus = new int[10][10];
	private ImageIcon[] circles;
	private ImageIcon[] heads;
	private ImageIcon[] crosses;
	
	public TeachingModeFrame() {
		Dimension screensize=Toolkit.getDefaultToolkit().getScreenSize();
		setBounds((int)screensize.getWidth()/2-500,(int)screensize.getHeight()/2-315, 1000, 630);// ���ú�������Ϳ��
		setTitle("��ѧģʽ");// ����
		
		circles = new ImageIcon[5];
		heads = new ImageIcon[5];
		crosses = new ImageIcon[5];
		String [] colors = {"white","blue","red","yellow","green"};
		for(int i=0;i<5;i++) {
			heads[i]= new ImageIcon("image/buttonImage/"+colors[i]+"Head.png");
			heads[i].setImage(heads[i].getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT));
			circles[i]= new ImageIcon("image/buttonImage/"+colors[i]+"Circle.png");
			circles[i].setImage(circles[i].getImage().getScaledInstance(20,20,Image.SCALE_DEFAULT));
			crosses[i]= new ImageIcon("image/buttonImage/"+colors[i]+"Cross.png");
			crosses[i].setImage(crosses[i].getImage().getScaledInstance(15,15,Image.SCALE_DEFAULT));
		}
		
		setContentPane(layeredPane);
        sound.playBGMBombPlanes();
        addWindowListener(new WindowAdapter() {// ��Ӵ������
            public void windowClosing(WindowEvent e) {// ����ر�ǰ
            	System.exit(0);
            }
        }); 
        ImageIcon icon = new ImageIcon("image/MainFrame/icon.png");	//����ͼ��
        setIconImage(icon.getImage());
        setResizable(false);
        setVisible(true);
        TeachingMode(1);
	}
	
	public void TeachingMode(int difficulty) {
		layeredPane.removeAll();
		layeredPane.repaint();
		layeredPane.setLayout(null);
		
		Enemy enemy = new Enemy();
		Screen screen = new Screen();
		step=1;
		bombedPlane=0;
		int Planes9Codes[]= {471721221,222752272,743273223,630362221,722473221
		,633370221,851283212,783821212,861712212,443712212,430150221,642723412};
		if(difficulty<5) {
			int TMPlanes9Code=Planes9Codes[(int)(Math.random()*3)+3*difficulty-3];
			enemy.addPlanes9Code(TMPlanes9Code);
		}
		else {
			Plane plane;
			for(int rest=3;rest>0;rest--) {
				plane=enemy.getRandomPlane();
				if(!enemy.addPlane(plane)) {
					rest++;
				}
			}
		}
		
		JButton returnMainMenu=new JButton("���˵�");
		returnMainMenu.setBounds(700,510,150,40);
		setButton(returnMainMenu);
		returnMainMenu.setVisible(true);
		returnMainMenu.addActionListener(new ActionListener() { // Ϊ��ť�����굥���¼�
			public void actionPerformed(ActionEvent e) {
				sound.stopPlay();
				dispose();
				new MainFrame();
			}
		});
		layeredPane.add(returnMainMenu,new Integer(200));
		
			
		JButton TMHelpJb=new JButton("����");
		TMHelpJb.setBounds(700,440,150,40);
		setButton(TMHelpJb);
		TMHelpJb.setVisible(true);
		TMHelpJb.addActionListener(new ActionListener() { // Ϊ��ť�����굥���¼�
			public void actionPerformed(ActionEvent e) {
				String HelpJBTextArray[]= {"��ͼ�й������ܷɻ����ɻ���ռ������ͷ��β��Ϊ1513��<br/>�ɻ�ͷ�����м���Ϊ�üܷɻ����ݻ١�" + 
"<br/>���������ͨ��ѡ�������������ٵĲ����ݻ����зɻ���<br/>�����ͼ�ϵ����갴ť�Խ��к�ը��",
				"<br/><br/>�ɻ����Գ������������ĸ�����",
				"<br/><br/>�ɻ���ͷ�����ܻ�ƫ����䣬���ʹ�ɻ������ױ��ݻ١�<br/>���Դݻٵл���ͬʱ��ҲҪע�ⴧĦ���˵�����"
		+ "<br/>�������ǵĹ���˼ά�������Լ��ķɻ��ʹݻ����ǵķɻ���",
				"<br/><br/>�ɻ���Ȼ�����ص����������ǿ������ڡ�<br/>" + 
				"<br/><br/>ͨ��λ�úͷ���ĵ���ʹ�ɻ�������һ��<br/>���Ӵ��Ʋ�ɻ�ͷλ�õ��Ѷ�<br/>����������裬�벻Ҫ�������ֿ����ԡ�",
				"<br/><br/>ʤ��������ǰ��"
				};
				String HelpJBText=new String();
				HelpJBText="<html>";
				for(int i=0;i<difficulty;i++) {
					HelpJBText+=HelpJBTextArray[i];
				}
				HelpJBText+="</html>";
				TextDialog txtDl=new TextDialog(TeachingModeFrame.this,"����",HelpJBText);
				Dimension screensize=Toolkit.getDefaultToolkit().getScreenSize();
				int Dlheight=300+50*difficulty;
				txtDl.setSize(600, Dlheight); 
				txtDl.setLocation((int)screensize.getWidth()/2-300,((int)screensize.getHeight()-Dlheight)/2);
				txtDl.setVisible(true);
			}
		});
		layeredPane.add(TMHelpJb,new Integer(200));
		
		JLabel LevelJL=new JLabel("��ѧģʽ�� "+difficulty+" ��");
		LevelJL.setBounds(695,45,250,40);
		LevelJL.setFont(new Font("����", Font.BOLD, 25));
		LevelJL.setForeground(Color.white);
		LevelJL.setVisible(true);
		layeredPane.add(LevelJL,new Integer(200));
		
		JLabel StepJL=new JLabel("��ǰ�ǵ�  1  ��");
		StepJL.setBounds(700,100,250,40);
		StepJL.setFont(new Font("����", Font.BOLD, 25));
		StepJL.setForeground(Color.white);
		StepJL.setVisible(true);
		layeredPane.add(StepJL,new Integer(200));
		
		
		JLabel BombedPlaneJL=new JLabel("�Ѵݻٵз��ɻ�  0  ��");
		BombedPlaneJL.setBounds(670,160,300,40);
		BombedPlaneJL.setFont(new Font("����", Font.BOLD, 25));
		BombedPlaneJL.setForeground(Color.white);
		BombedPlaneJL.setVisible(true);
		layeredPane.add(BombedPlaneJL,new Integer(200));
		
		ImageIcon backgroundIcon = new ImageIcon("image/MainFrame/background.jpg");
		backgroundIcon.setImage(backgroundIcon.getImage().getScaledInstance(1000,630,Image.SCALE_DEFAULT));
		JLabel backgroundLabel=new JLabel(backgroundIcon);
		backgroundLabel.setBounds(0, 0,1000,630);
		backgroundLabel.setHorizontalAlignment(SwingConstants.CENTER);
		layeredPane.add(backgroundLabel,new Integer(100));
		
		JButton scrB[][]=new JButton[10][10];
		JLabel rowNumberJL[]=new JLabel[10];
		JLabel columbLetterJL[]=new JLabel[10];
		String numbers[]= {"1","2","3","4","5","6","7","8","9","10"};
		String letters[]= {"A","B","C","D","E","F","G","H","I","J"};
		
		for(int i=0;i<10;i++) {
			for(int j=0;j<10;j++) {
				scrBStatus[i][j]=0;	
			}
		}
		
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
						scrB[i-1][j-1].addMouseListener(new MouseListener() { // Ϊ��ť�����굥���¼�
							@Override
							public void mouseReleased(MouseEvent e) {
								//0:��  1:��Ƿɻ��� 2:��Ƿɻ�ͷ 3:��ǿ� 4:�Ѻ�ը�ɻ��� 5:�Ѻ�ը�ɻ�ͷ 6:�Ѻ�ը��
								//0:�� 1:�� 2:�� 3:�� 4:��

								switch(e.getButton()) {
								case 1:
									int locationMessage=enemy.seeLocation(FiMinus1,FjMinus1);
									boolean repeat=false;
									if(screen.seeLocation(FiMinus1, FjMinus1)!=3) repeat=true;
									else {
										screen.changeScreen(FiMinus1,FjMinus1,locationMessage);
										switch (locationMessage) {
											case 0:
												scrBStatus[FiMinus1][FjMinus1]=60;
												scrB[FiMinus1][FjMinus1].setIcon(crosses[0]);
												break;
											case 1:
												scrBStatus[FiMinus1][FjMinus1]=40;
												scrB[FiMinus1][FjMinus1].setIcon(circles[0]);
												break;
											case 2:
												if(locationMessage==2) bombedPlane++;
												scrBStatus[FiMinus1][FjMinus1]=50+bombedPlane+1;
												scrB[FiMinus1][FjMinus1].setIcon(heads[bombedPlane+1]);
												break;
										}
									}
									BombedPlaneJL.setText("�Ѵݻٵз��ɻ�  "+bombedPlane+"  ��");
									BombMessageJDialog bmjd=new BombMessageJDialog(TeachingModeFrame.this,locationMessage,step,bombedPlane,repeat);
									Dimension screensize=Toolkit.getDefaultToolkit().getScreenSize();
									bmjd.setSize(500, 250); 
									bmjd.setLocation((int)screensize.getWidth()/2-250,(int)screensize.getHeight()/2-150);
									bmjd.addWindowListener(new WindowAdapter() {// ��Ӵ������
										public void windowClosing(WindowEvent e) {// ����ر�ǰ
											if(bombedPlane>=3) {
												if(difficulty<5) TeachingMode(difficulty+1);
												else {
													String FRtext="<html>�����ģ���ͨ�������йؿ���<br/>ȡ��������ʤ������ϲ��ɽ�ѧģʽ��"
															+"<br/>���ȥ����ģʽ�����һ��BombPlane<br/>�ĸ�����Ȥ�ɣ�\r\n" + 
															"<br/>����Ϊ���������˵���</html>";
													TextDialog finishReturnDialog=new TextDialog(TeachingModeFrame.this,"��ʾ",FRtext);
													finishReturnDialog.setSize(500, 300); 
													finishReturnDialog.setLocation((int)screensize.getWidth()/2-250,(int)screensize.getHeight()/2-150);
													finishReturnDialog.addWindowListener(new WindowAdapter() {// ��Ӵ������
											            public void windowClosing(WindowEvent e) {// ����ر�ǰ
											            	sound.stopPlay();
											            	dispose();
															new MainFrame();
											            }
											        });
													finishReturnDialog.setVisible(true);
												}
											}
										}
									});	
									bmjd.setVisible(true);
									step++;
									StepJL.setText("��ǰ�ǵ�  "+step+"  ��");
									break;
								case 2:
									switch(scrBStatus[FiMinus1][FjMinus1]) {
									case 0:
										scrBStatus[FiMinus1][FjMinus1]=2;
										scrB[FiMinus1][FjMinus1].setIcon(heads[1]);
										break;
									case 1:
										scrBStatus[FiMinus1][FjMinus1]=2;
										scrB[FiMinus1][FjMinus1].setIcon(heads[1]);
										break;
									case 2:
										scrBStatus[FiMinus1][FjMinus1]=3;
										scrB[FiMinus1][FjMinus1].setIcon(crosses[1]);
										break;
									case 3:
										scrBStatus[FiMinus1][FjMinus1]=0;
										scrB[FiMinus1][FjMinus1].setIcon(null);
										break;
									}
									break;
								case 3:
									int guessColor=bombedPlane-1;
									if(guessColor<0) guessColor=0;
									guessColor+=2;
									System.out.println("guessColor:"+guessColor);
									switch(scrBStatus[FiMinus1][FjMinus1]) {
									case 0:
										scrBStatus[FiMinus1][FjMinus1]=1;
										scrB[FiMinus1][FjMinus1].setIcon(circles[1]);
										break;
									case 1:
										scrBStatus[FiMinus1][FjMinus1]=0;
										scrB[FiMinus1][FjMinus1].setIcon(null);
										break;
									case 2:
										scrBStatus[FiMinus1][FjMinus1]=1;
										scrB[FiMinus1][FjMinus1].setIcon(circles[1]);
										break;
									case 3:
										scrBStatus[FiMinus1][FjMinus1]=1;
										scrB[FiMinus1][FjMinus1].setIcon(circles[1]);
										break;
									case 40:
										scrBStatus[FiMinus1][FjMinus1]=40+guessColor;
										scrB[FiMinus1][FjMinus1].setIcon(circles[guessColor]);
										break;
									case 42:
										if(guessColor==3) {
											scrBStatus[FiMinus1][FjMinus1]=40;
											scrB[FiMinus1][FjMinus1].setIcon(circles[0]);
										}
										else {
											scrBStatus[FiMinus1][FjMinus1]=43;
											scrB[FiMinus1][FjMinus1].setIcon(circles[3]);
										}
										break;
									case 43:
										if(guessColor==4) {
											scrBStatus[FiMinus1][FjMinus1]=40;
											scrB[FiMinus1][FjMinus1].setIcon(circles[0]);
										}
										else {
											scrBStatus[FiMinus1][FjMinus1]=44;
											scrB[FiMinus1][FjMinus1].setIcon(circles[4]);
										}
										break;
									case 44:
										if(guessColor==2) {
											scrBStatus[FiMinus1][FjMinus1]=40;
											scrB[FiMinus1][FjMinus1].setIcon(circles[0]);
										}
										else {
											scrBStatus[FiMinus1][FjMinus1]=42;
											scrB[FiMinus1][FjMinus1].setIcon(circles[2]);
										}
										break;
									}
									break;
								}
							}
							@Override
							public void mouseEntered(MouseEvent e) {}
							@Override
							public void mouseExited(MouseEvent e) {}
							@Override
							public void mouseClicked(MouseEvent e) {}
							@Override
							public void mousePressed(MouseEvent e) {}
						});
						
						scrB[i-1][j-1].setVisible(true);
						layeredPane.add(scrB[i-1][j-1],new Integer(200));
					}
				}
			}
		}
						
		String TMtext[]= {"<html>��ӭ������ѧģʽ�� 1 �أ�<br/>��ѧģʽ������أ��Ѷ����ε�����"
+ "<br/>��ͼ�й������ܷɻ����ɻ���ռ������ͷ��β��Ϊ1513��<br/>�ɻ�ͷ�����м���Ϊ�üܷɻ����ݻ١�" + 
"<br/>���������ͨ��ѡ�������������ٵĲ����ݻ����зɻ���<br/>�������ǣ���ο����·���[����]���������ͨ�أ�" + 
"<br/>[����]Ҳ����ӷḻ����Ҳ�����ո��༼�ɡ�<br/>�ȳ��Ե�������ϵ�λ�ðɣ�<br/>��ô��ս����ʽ��ʼ��</html>" ,
				"<html>̫���ˣ�������˵�һ�أ�<br/>��ϲ����ڶ��أ�����������һ���Ѷȡ�<br/>��һ�ص�ͼ�еķɻ�"
+ "����ͬ��ġ�<br/>����һ�أ��ɻ����Գ������������ĸ�����<br/>�������������˵���������⣡</html>",
				"<html>��ϲ����ɵڶ��أ�<br/>�ڶ����зɻ��ķ�����ӵĶ�������ʹ�ɻ��Ĳ������˸�����¿��ܡ�<br/>"
+ "��֮��Ĺؿ��У���ᷢ������ĸ������<br/>�������зɻ���ͷ����ƫ����䣬���ʹ�ɻ������ױ��ݻ١�" + 
"<br/>���Դݻٵл���ͬʱ��ҲҪע�ⴧĦ���˵�����<br/>�������ǵĹ���˼ά�������Լ��ķɻ��ʹݻ����ǵķɻ�<br/>"
+ "���ˣ������ǽ�������ذɣ�</html>" ,
				"<html>�ɵ�Ư����������˵����أ�<br/>�ɻ���Ȼ�����ص����������ǿ������ڡ�<br/>"
+ "ͨ��λ�úͷ���ĵ���ʹ�ɻ�������һ��<br/>���Ӵ��Ʋ�ɻ�ͷλ�õ��Ѷ�<br/>����������裬�벻Ҫ�������ֿ����ԣ�"
+ "<br/>ϣ�����ܴ������Ĺأ�</html>" ,
				"<html>GOOD JOB��������˵��Ĺأ�<br/>����أ��ɻ���������ɡ�<br/>�ݻ����зɻ���ȡ������ʤ����"+
"<br/>�ɹ�ֻ��һ��֮ң�ˣ�</html>"
		};
		TextDialog txtDl=new TextDialog(TeachingModeFrame.this,"��ѧģʽ��ʾ",TMtext[difficulty-1]);
		Dimension screensize=Toolkit.getDefaultToolkit().getScreenSize();
		txtDl.setSize(650, 350); 
		txtDl.setLocation((int)screensize.getWidth()/2-325,(int)screensize.getHeight()/2-175);
		txtDl.setVisible(true);
		layeredPane.revalidate();// ����������֤�������	
	}
	
	//���ð�ť
	public void setButton(JButton button) {
		button.setFont(new Font("����", Font.BOLD, 18));
		button.setForeground(Color.white);
		button.setContentAreaFilled(false);
		button.setFocusPainted(false);
	}
}
