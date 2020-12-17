package LANBattleMode;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

import Service.Sound;
import Calculate.*;
import View.*;

public class LANBMFinishFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	private LANBMFindEnemyFrame messageHandler;
	private final Dimension screensize=Toolkit.getDefaultToolkit().getScreenSize();
	private JLayeredPane layeredPane = new JLayeredPane();
	private Enemy enemy = new Enemy();		//�з��ɻ�������Ϣ
	private Enemy myInputEnemy= new Enemy();		//�ҷ��ɻ�������Ϣ
	private final boolean thisIsWinner;
	private Sound sound = new Sound(); 
	private final int myInputCode;
	private final int codeReceiveFromEnemy;
	private boolean BattleModeEnemyCintinueReady=false;
	private boolean BattleModeICintinueReady=false;
	
	public LANBMFinishFrame(LANBMFindEnemyFrame myfindEnemyFrame,int inputCode,int codeReceive,boolean isWinner) {
		messageHandler=myfindEnemyFrame;
		myInputCode=inputCode;
		codeReceiveFromEnemy=codeReceive;
		thisIsWinner=isWinner;
		setLayout(null);
		sound.playBGMBombFinish();
		myInputEnemy.addPlanes9Code(myInputCode);
		enemy.addPlanes9Code(codeReceiveFromEnemy);
		
		setContentPane(layeredPane);
		BattleModeEnemyCintinueReady=false;
		BattleModeICintinueReady=false;
		
		JLabel BMFEJL[][]=new JLabel[10][10];
		JLabel rowNumberJL[]=new JLabel[10];
		JLabel columbLetterJL[]=new JLabel[10];
		JLabel LMJL[][]=new JLabel[10][10];
		
		ImageIcon homeIcon= new ImageIcon("image/MainFrame/home.png");
		homeIcon.setImage(homeIcon.getImage().getScaledInstance(44,40,Image.SCALE_DEFAULT));
		ImageIcon circle= new ImageIcon("image/buttonImage/whiteCircle.png");
		circle.setImage(circle.getImage().getScaledInstance(20,20,Image.SCALE_DEFAULT));
		ImageIcon head= new ImageIcon("image/buttonImage/whitehead.png");
		head.setImage(head.getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT));
		ImageIcon smallCircle= new ImageIcon("image/buttonImage/whiteCircle.png");
		smallCircle.setImage(circle.getImage().getScaledInstance(20,20,Image.SCALE_DEFAULT));
		ImageIcon smallHead= new ImageIcon("image/buttonImage/whitehead.png");
		smallHead.setImage(smallHead.getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT));
		
		String numbers[]= {"1","2","3","4","5","6","7","8","9","10"};
		String letters[]= {"A","B","C","D","E","F","G","H","I","J"};
		Icon messages[]= {null,circle,head};
		Icon samllMessages[]= {null,smallCircle,smallHead};
		
		for(int i=0;i<=10;i++) {
			for(int j=0;j<=10;j++) {
				if(i==0){
					if(j!=0) {
						rowNumberJL[j-1]=new JLabel(numbers[j-1]);
						rowNumberJL[j-1].setFont(new Font("����", Font.BOLD, 18));
						rowNumberJL[j-1].setForeground(Color.white);
						rowNumberJL[j-1].setBounds(27+50*j, 20+50*i, 50, 50);
						rowNumberJL[j-1].setVisible(true);
						layeredPane.add(rowNumberJL[j-1],new Integer(300));
					}
				}
				else {
					if(j==0) {
						columbLetterJL[i-1]=new JLabel(letters[i-1]);
						columbLetterJL[i-1].setFont(new Font("����", Font.BOLD, 18));
						columbLetterJL[i-1].setForeground(Color.white);
						columbLetterJL[i-1].setBounds(30+50*j, 10+50*i, 50, 50);
						columbLetterJL[i-1].setVisible(true);
						layeredPane.add(columbLetterJL[i-1],new Integer(300));
					}
					else {
						BMFEJL[i-1][j-1]=new JLabel(messages[enemy.seeLocation(i-1,j-1)]);
						BMFEJL[i-1][j-1].setFont(new Font("����", Font.BOLD, 18));
						BMFEJL[i-1][j-1].setForeground(Color.white);
						BMFEJL[i-1][j-1].setBounds(10+50*j, 10+50*i, 50, 50);
						BMFEJL[i-1][j-1].setVisible(true);
						layeredPane.add(BMFEJL[i-1][j-1],new Integer(300));
					}
				}
			}
		}
		
		for(int p=0;p<10;p++) {
			for(int q=0;q<10;q++) {
				switch(myInputEnemy.seeLocation(p,q)) {
					case 0:
						LMJL[p][q]=new JLabel();
						break;
					case 1:
						LMJL[p][q]=new JLabel(smallCircle);
						break;
					case 2:
						LMJL[p][q]=new JLabel(smallHead);
						break;
				}
				LMJL[p][q].setBounds(600+33*q,60+33*p,33,33);
				LMJL[p][q].setHorizontalAlignment(SwingConstants.CENTER);
				LMJL[p][q].setVisible(true);
				layeredPane.add(LMJL[p][q],new Integer(300));	
			}
		}
		
		JLabel WinnerJL;
		if(thisIsWinner) {
			WinnerJL=new JLabel("Victory!");
		}
		else {
			WinnerJL=new JLabel("Defeat!");
		}
		WinnerJL.setFont(new Font("����", Font.BOLD, 25));
		WinnerJL.setHorizontalAlignment(SwingConstants.CENTER);
		WinnerJL.setForeground(Color.white);
		WinnerJL.setBounds(640,0,150,40);
		WinnerJL.setVisible(true);
		layeredPane.add(WinnerJL,new Integer(300));
		
		//����
		ImageIcon backgroundIcon = new ImageIcon("image/MainFrame/background.jpg");
		backgroundIcon.setImage(backgroundIcon.getImage().getScaledInstance(1000,630,Image.SCALE_DEFAULT));
		JLabel backgroundLabel=new JLabel(backgroundIcon);
		backgroundLabel.setBounds(0, 0,1000,630);
		backgroundLabel.setHorizontalAlignment(SwingConstants.CENTER);
		layeredPane.add(backgroundLabel,new Integer(200));
		
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
		
		JPanel chatPanel = messageHandler.getChatPanel();
		chatPanel.setLocation(685,394);
		layeredPane.add(chatPanel,new Integer(300)); // ����ť��ӵ�������	
		
		JButton BMContinnueJB=new JButton("������Ϸ");
		BMContinnueJB.setBounds(830,5,150,40);
		setButton(BMContinnueJB);
		BMContinnueJB.setVisible(true);
		BMContinnueJB.addActionListener(new ActionListener() { // Ϊ��ť�����굥���¼�
			public void actionPerformed(ActionEvent e) {
				if(BattleModeICintinueReady==false) {
					BattleModeICintinueReady=true;
					messageHandler.sendMessage("3");
					if(BattleModeEnemyCintinueReady) {	//����Է���׼��
						dispose();
						soundStopPlay();
						messageHandler.createMyInPutPlanesFrame();
					}
					else {	//����Է�δ׼��
						BMContinnueJB.setText("ȡ��׼��");
						TextDialog txtDl=new TextDialog(LANBMFinishFrame.this,"��׼��","<html>��׼����<br/>��ȴ��Է���ʼ��Ϸ</html>");
						Dimension screensize=Toolkit.getDefaultToolkit().getScreenSize();
						txtDl.setSize(600, 300); 
						txtDl.setLocation((int)screensize.getWidth()/2-300,(int)screensize.getHeight()/2-150);
						txtDl.setVisible(true);
					}
				}
				else {	
					messageHandler.sendMessage("4");
					BattleModeICintinueReady=false;
					BMContinnueJB.setText("׼��");
					TextDialog txtDl=new TextDialog(LANBMFinishFrame.this,"ȡ��׼��","��ȡ��׼����");
					Dimension screensize=Toolkit.getDefaultToolkit().getScreenSize();
					txtDl.setSize(600, 300); 
					txtDl.setLocation((int)screensize.getWidth()/2-300,(int)screensize.getHeight()/2-150);
					txtDl.setVisible(true);
				}
			}
		});
		layeredPane.add(BMContinnueJB,new Integer(300));
		
		
		setBounds((int)screensize.getWidth()/2-500,(int)screensize.getHeight()/2-315, 1000, 630);// ���ú�������Ϳ��
		setTitle("����");// ����
		setResizable(false);
        addWindowListener(new WindowAdapter() {// ��Ӵ������
            public void windowClosing(WindowEvent e) {// ����ر�ǰ
            	System.exit(0);
            }
        });
        setVisible(true);
	}
	
	//�ı�з�׼��״̬
	public void enemyReadyChange(boolean enemyIsReady) {
		 BattleModeEnemyCintinueReady=enemyIsReady;
	}
	
	//��ȡ�ҷ�׼��״̬
	public boolean getMyReadyState() {
		return BattleModeICintinueReady;
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
