package WANBattleMode;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import Service.Sound;
import View.BattleModeChooseFrame;
import View.MainFrame;
import View.TextDialog;

import java.io.*;
import java.net.*;

public class WANBMFindEnemyFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private final String serverIP="150.158.175.212";
	private BufferedReader reader; // ����BufferedReader����
	private PrintWriter writer; // ����BufferedWriter����
	private ServerSocket server; // ����ServerSocket����
	private Socket socket; // ����Socket����socket
	private Sound sound = new Sound(); 
	private final int socketNumber=20119;
	private JLayeredPane layeredPane = new JLayeredPane();;
	private JLabel RoomMessageJL;
	private JButton RoomJB;
	private WANBMInputPlanesFrame myInPutPlanesFrame;
	private WANBMBombPlanesFrame myBombPlanesFrame;
	private WANBMFinishFrame myFinishFrame;
	private int codeReceiveFromEnemy;
	private int myInputCode;
	private int myWANBMStep=0;
	private JTextField EnterRoomJT;
	private boolean isCreater;	//true:��������	false:ȡ������
	private boolean isCancelConnect=false;	//���ҷ�����ȡ������
	private int status=0;	//0:������ 1:�������ӷ�����ʧ�� 2:��������ʧ�� 3:��������ɹ����ȴ����� 4:�����Ѽ��뷿��
							//5:�ӷ����ӷ�����ʧ�� 6:���뷿��ȴ����뷿��� 7:�ѳɹ����뷿�� 
	private ChatPanel chatPanel = new ChatPanel();
	private final Dimension screensize=Toolkit.getDefaultToolkit().getScreenSize();
	public WANBMFindEnemyFrame(boolean IsServer) {
		isCreater=IsServer;
		setLayout(null);
		sound.playBGMFindEnemy();
		myWANBMStep=0;
		isCancelConnect=false;
		setContentPane(layeredPane);
		
		JButton BMCRreturnMainMenuJB=new JButton("���˵�");
		BMCRreturnMainMenuJB.setFont(new Font("����", Font.BOLD, 18));
		BMCRreturnMainMenuJB.setForeground(Color.white);
		BMCRreturnMainMenuJB.setContentAreaFilled(false);
		BMCRreturnMainMenuJB.setFocusPainted(false);
		BMCRreturnMainMenuJB.setBounds(690, 500, 160, 60);
		BMCRreturnMainMenuJB.addActionListener(new ActionListener() { // Ϊ��ť�����굥���¼�
			public void actionPerformed(ActionEvent e) {
				sound.stopPlay();
				stopConnect();
				dispose();
				new MainFrame();
			}
		});
		layeredPane.add(BMCRreturnMainMenuJB,new Integer(300)); // ����ť��ӵ�������

		JButton BMCRLastStepJB=new JButton("��һ��");
		BMCRLastStepJB.setFont(new Font("����", Font.BOLD, 18));
		BMCRLastStepJB.setForeground(Color.white);
		BMCRLastStepJB.setContentAreaFilled(false);
		BMCRLastStepJB.setFocusPainted(false);
		BMCRLastStepJB.setBounds(150, 500, 160, 60);
		BMCRLastStepJB.addActionListener(new ActionListener() { // Ϊ��ť�����굥���¼�
			public void actionPerformed(ActionEvent e) {
				sound.stopPlay();
				stopConnect();
				dispose();
				new BattleModeChooseFrame().BMWANEnterJB();
			}
		});
		layeredPane.add(BMCRLastStepJB,new Integer(300));
		
		RoomMessageJL=new JLabel("");
		RoomMessageJL.setHorizontalAlignment(SwingConstants.CENTER);
		RoomMessageJL.setForeground(Color.white);
		RoomMessageJL.setFont(new Font("����", Font.BOLD, 23));
		RoomMessageJL.setBounds(100, 50, 800, 400);
		layeredPane.add(RoomMessageJL,new Integer(300));
		
		RoomJB=new JButton("");
		RoomJB.setFont(new Font("����", Font.BOLD, 18));
		RoomJB.setForeground(Color.white);
		RoomJB.setContentAreaFilled(false);
		RoomJB.setFocusPainted(false);
		RoomJB.setBounds(420, 500, 160, 60);
		layeredPane.add(RoomJB,new Integer(300));

		//����
		ImageIcon backgroundIcon = new ImageIcon("image/LANBM/background.jpg");
		backgroundIcon.setImage(backgroundIcon.getImage().getScaledInstance(1000,630,Image.SCALE_DEFAULT));
		JLabel backgroundLabel=new JLabel(backgroundIcon);
		backgroundLabel.setBounds(0, 0,1000,630);
		backgroundLabel.setHorizontalAlignment(SwingConstants.CENTER);
		layeredPane.add(backgroundLabel,new Integer(200));
		
		//�ɻ�
		ImageIcon planeIcon = new ImageIcon("image/LANBM/plane.png");
		planeIcon.setImage(planeIcon.getImage().getScaledInstance(128,30,Image.SCALE_DEFAULT));
		JLabel planeLabel=new JLabel(planeIcon);
		planeLabel.setBounds(0,0,128,30);
		planeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		layeredPane.add(planeLabel,new Integer(400));
		
		Runnable planeThread = () -> {
			int step=0;
			while(true) {
				planeLabel.setBounds(step,20,128,30);
				try {
					Thread.sleep(10);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				step++;
				if(step>1000) {
					step=0;
				}
			}
	    };
	    Thread planeLine = new Thread(planeThread);
	    planeLine.start();
		
		if(IsServer) createRoom();
		else enterRoom();
		
		ImageIcon icon = new ImageIcon("image/LANBM/icon.png");	//����ͼ��
        setIconImage(icon.getImage());
		setBounds((int)screensize.getWidth()/2-500,(int)screensize.getHeight()/2-315, 1000, 630);// ���ú�������Ϳ��
		setTitle("��սģʽ");// ����
		setResizable(false);
        addWindowListener(new WindowAdapter() {// ��Ӵ������
            public void windowClosing(WindowEvent e) {// ����ر�ǰ
            	System.exit(0);
            }
        });
        setVisible(true);
		
	}
	
	class ChatPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		private String chatMessages="";
		private JLabel jl = new JLabel();
		private JScrollPane sp;
		
		public ChatPanel() {
	        setLayout(null);
	        setSize(300,200);
	        setBackground(Color.white);
	        
	        jl.setFont(new Font("����", Font.BOLD, 18));
			//jl.setSize(300,160);
			jl.setHorizontalAlignment(SwingConstants.LEFT);
			jl.setVerticalAlignment(SwingConstants.BOTTOM);
			
	        sp=new JScrollPane(jl);
			sp.setForeground(Color.black);
			sp.setBackground(Color.white);
			sp.getVerticalScrollBar().setUnitIncrement(25);	//����������
			sp.setBounds(0,0,300,160);
			sp.setOpaque(false);
			sp.getViewport().setOpaque(false);
			add(sp); // ����������ӱ�ǩ
	        
	        JTextField chatJT=new JTextField("",20);
	        chatJT.setFont(new Font("����", Font.BOLD, 18));
	        chatJT.setForeground(Color.black);
	        chatJT.setBackground(Color.white);
	        chatJT.setBounds(0, 160, 200, 40);
	        chatJT.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String messageToSend=chatJT.getText();
					chatMessages+="�ҷ�:";
					chatMessages+=messageToSend;
					chatMessages+="<br/>";
					jl.setText("<html>"+chatMessages+"<br/></html>");
					sendMessage("9"+messageToSend);
					chatJT.setText("");
					sp.getVerticalScrollBar().setValue(sp.getVerticalScrollBar().getMaximum());
					chatJT.requestFocus();
				}
			});
	        add(chatJT);
	        
	        JButton HandInJB=new JButton("����");
	        HandInJB.setFont(new Font("����", Font.BOLD, 18));
	        HandInJB.setForeground(Color.black);
	        HandInJB.setBackground(Color.white);
	        HandInJB.setFocusPainted(false);
			HandInJB.setBounds(200,160,100,40);
			HandInJB.addActionListener(new ActionListener() { // Ϊ��ť�����굥���¼�
				public void actionPerformed(ActionEvent e) {
					String messageToSend=chatJT.getText();
					chatMessages+="�ҷ�:";
					chatMessages+=messageToSend;
					chatMessages+="<br/>";
					jl.setText("<html>"+chatMessages+"<br/></html>");
					sendMessage("9"+messageToSend);
					chatJT.setText("");
					sp.getVerticalScrollBar().setValue(sp.getVerticalScrollBar().getMaximum());
					chatJT.requestFocus();
				}
			});
			add(HandInJB);
		}
        
		public void receiveMessage(String message) {
			System.out.println(message);
			chatMessages+="�Է�:";
			chatMessages+=message;
			chatMessages+="<br/>";
			jl.setText("<html>"+chatMessages+"<br/></html>");
			sp.getVerticalScrollBar().setValue(sp.getVerticalScrollBar().getMaximum());
		}
	}
	
	//���ChatPanel
	public JPanel getChatPanel() {
		return chatPanel;
	}
	
	//������Ϣ���߳�
	class ReaderThread extends Thread{
		@Override
		public void run(){
			reader=null;
	        try{  
	            while(true){  
	                //��ȡ�ͻ�������    
	            	reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
	            	receiveMessageHandle(reader.readLine());
	            	Thread.sleep(100);
	            }  
	        }catch(Exception e){  
	        	if(isCancelConnect==false) {
	        		TextDialog txtDl=new TextDialog(WANBMFindEnemyFrame.this,"��ʾ","<html>����������ʧ�ܣ�<br/>�����������˵�...</html>");
	        		txtDl.setSize(600, 300); 
	        		txtDl.setLocation((int)screensize.getWidth()/2-300,(int)screensize.getHeight()/2-150);
	        		txtDl.addWindowListener(new WindowAdapter() {// ��Ӵ������
	        			public void windowClosing(WindowEvent e) {// ����ر�ǰ
	        				switch(myWANBMStep) {
		            		case 0:
		            			sound.stopPlay();
		            			dispose();
		            			break;
		            		case 1:
		            			myInPutPlanesFrame.soundStopPlay();
		            			myInPutPlanesFrame.dispose();
		            			break;
		            		case 2:
		            			myBombPlanesFrame.soundStopPlay();
		            			myBombPlanesFrame.dispose();
		            			break;
		            		case 3:
		            			myFinishFrame.soundStopPlay();
		            			myFinishFrame.dispose();
		            			break;
	        				}
	        				stopConnect();
	        				new MainFrame();
	        			}
	        		});
	        		txtDl.setVisible(true);
	        	}
	        }
		}
	}
	
	//������Ϣ�ķ���
	public void sendMessage(String message) {
		writer.println(message);
	}
	
	//�������� 150.158.175.212
	public void createRoom() {
		RoomMessageJL.setText("<html>�������ӷ�������...<br/>���Ժ�</html>");
		ActionListener actionListener=new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text;
				TextDialog txtDl;
				switch(status) {
				case 0:
					text="������,���Ժ�!";
					txtDl=new TextDialog(WANBMFindEnemyFrame.this,"��ʾ",text);
					txtDl.setSize(600, 300); 
					txtDl.setLocation((int)screensize.getWidth()/2-300,(int)screensize.getHeight()/2-150);
					txtDl.setVisible(true);
					break;
				case 1:
					text="<html>����������ʧ��!</html>";
					txtDl=new TextDialog(WANBMFindEnemyFrame.this,"��ʾ",text);
					txtDl.setSize(600, 300); 
					txtDl.setLocation((int)screensize.getWidth()/2-300,(int)screensize.getHeight()/2-150);
					txtDl.setVisible(true);
					break;
				case 2:
					status=0;
					sendMessage("71");
					break;
				case 3:
					text="<html>���ڵȴ��������ӣ�</html>";
					txtDl=new TextDialog(WANBMFindEnemyFrame.this,"��ʾ",text);
					txtDl.setSize(600, 300); 
					txtDl.setLocation((int)screensize.getWidth()/2-300,(int)screensize.getHeight()/2-150);
					txtDl.setVisible(true);
					break;
				case 4:
					sendMessage("0");
					dispose();
					sound.stopPlay();
					createMyInPutPlanesFrame();
					break;
				}
			}
		};
		
		RoomJB.setText("��ʼ��Ϸ");
		RoomJB.addActionListener(actionListener);
		
		class ClientConnectServerThread implements Runnable{   //���ӷ��������߳�
		    @Override  
		    public void run() {
		    	try { // ��׽�쳣
		    		socket=new Socket(serverIP,socketNumber); // ʵ����Socket����
		    		writer=new PrintWriter(new OutputStreamWriter(socket.getOutputStream()),true);
		    		new Thread(new ReaderThread()).start(); 
		    		status=0;
		    		sendMessage("71");
		    	}
		    	catch (Exception e1) {	//���뷿��ʧ��
		    		stopConnect();
		    		status=1;
		    		RoomMessageJL.setText("<html>���ӷ�����ʧ��!</html>");
		    	}
		    }
		}
		new Thread(new ClientConnectServerThread()).start();
	}
	
	//���뷿��	150.158.175.212
	public void enterRoom() {
		RoomMessageJL.setText("<html>�������ӷ�������...<br/>���Ժ�</html>");
		
		EnterRoomJT=new JTextField("",20);
		EnterRoomJT.setFont(new Font("΢���ź�",0,18));
		EnterRoomJT.setBounds(300, 300, 400, 40);
		ActionListener actionListener=new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text;
				TextDialog txtDl;
				switch(status) {
				case 0:
					text="������,���Ժ�!";
					txtDl=new TextDialog(WANBMFindEnemyFrame.this,"��ʾ",text);
					txtDl.setSize(600, 300); 
					txtDl.setLocation((int)screensize.getWidth()/2-300,(int)screensize.getHeight()/2-150);
					txtDl.setVisible(true);
					break;
				case 5:
					text="<html>����������ʧ��!</html>";
					txtDl=new TextDialog(WANBMFindEnemyFrame.this,"��ʾ",text);
					txtDl.setSize(600, 300); 
					txtDl.setLocation((int)screensize.getWidth()/2-300,(int)screensize.getHeight()/2-150);
					txtDl.setVisible(true);
					break;
				case 6:
					sendMessage("72"+EnterRoomJT.getText());
					break;
				case 7:
					text="<html>���ڵȴ�������ʼ��Ϸ��</html>";
					txtDl=new TextDialog(WANBMFindEnemyFrame.this,"��ʾ",text);
					txtDl.setSize(600, 300); 
					txtDl.setLocation((int)screensize.getWidth()/2-300,(int)screensize.getHeight()/2-150);
					txtDl.setVisible(true);
					break;
				}
			}
		};
		EnterRoomJT.addActionListener(actionListener);
		layeredPane.add(EnterRoomJT,new Integer(300));
		EnterRoomJT.requestFocus();
		
		RoomJB.setText("���뷿��");
		RoomJB.addActionListener(actionListener);
		
		class ClientConnectServerThread implements Runnable{   //���ӷ��������߳�
		    @Override  
		    public void run() {
		    	try { // ��׽�쳣
		    		socket=new Socket(serverIP,socketNumber); // ʵ����Socket����
		    		writer=new PrintWriter(new OutputStreamWriter(socket.getOutputStream()),true);
		    		new Thread(new ReaderThread()).start(); 
		    		RoomMessageJL.setText("<html>�����뷿���:</html>");
		    		status=6;
		    	}
		    	catch (Exception e1) {	//���뷿��ʧ��
		    		stopConnect();
		    		RoomMessageJL.setText("<html>���ӷ�����ʧ��!</html>");
		    		status=5;
		    	}
		    }
		}
		new Thread(new ClientConnectServerThread()).start();
	}
	
	//ֹͣ����
	public void stopConnect() {
		try {
			if(server!=null) {
				server.close();
			}
			if (socket!= null) {
				socket.close(); // �ر��׽���
			}
			isCancelConnect=true;
		} catch (IOException e2) {}
	}
	
	//��ȡ�Է����͵���Ϣ��
	public int getCodeReceiveFromEnemy() {
		return codeReceiveFromEnemy;
	}
	
	//�洢�Լ��������Ϣ��
	public void saveMyInputCode(int myInputCode) {
		this.myInputCode=myInputCode;
	}
	
	//����myInPutPlanesFrame
	public void createMyInPutPlanesFrame() {
		codeReceiveFromEnemy=-1;
		myWANBMStep=1;
		myInPutPlanesFrame=new WANBMInputPlanesFrame(WANBMFindEnemyFrame.this);
	}
	
	//����myBombPlanesFrame
	public void createMyBombPlanesFrame(boolean isFirst) {
		myWANBMStep=2;
		myBombPlanesFrame=new WANBMBombPlanesFrame(WANBMFindEnemyFrame.this,myInputCode,codeReceiveFromEnemy,isFirst);
	}
	
	//����myFinishFrame
	public void createMyFinishFrame(boolean isWinner) {
		myWANBMStep=3;
		myFinishFrame=new WANBMFinishFrame(WANBMFindEnemyFrame.this,myInputCode,codeReceiveFromEnemy,isWinner);
	}
	
	public void receiveMessageHandle(String readerMessage){
		/*messageType ��ȡ��Ϣ����: 
		 * 0:��������ʼ��Ϸ
		 * 1:����˷����Ĳ���9λ��Ϣ��
		 * 2:����˷���������
		 * 3:����˷����ļ�����Ϸ��׼����
		 * 4:����˷�����ȡ����Ϸ��ȡ��׼����
		 * 5:����˷�������Ϸ���� ���Ͷ�ʤ��
		 * 7:�ͻ�������������͵���Ϣ
		 * 8:��������ͻ��˷��͵���Ϣ
		 * 9:����
		**/
		System.out.println("������Ϣ"+readerMessage);
		char messageType=readerMessage.charAt(0);
		String messageBody=readerMessage.substring(1);
		switch(messageType) {
			case '0':
				System.out.println("0");
				dispose();
				sound.stopPlay();
				createMyInPutPlanesFrame();
				break;
			case '1':
				if(codeReceiveFromEnemy==-1) {	
					codeReceiveFromEnemy=Integer.parseInt(messageBody);
					if(myInPutPlanesFrame.getBattleModeAlreadySent()==true) {
						myInPutPlanesFrame.dispose();
						myInPutPlanesFrame.soundStopPlay();
						createMyBombPlanesFrame(true);
					}
				}
				break;
			case '2':
				int p,q,r;
				r=Integer.parseInt(messageBody);
				p=r/10;
				q=r%10;
				myBombPlanesFrame.handleReceiveLocation(p,q);
				break;
			case '3':
				myFinishFrame.enemyReadyChange(true);
				if(myFinishFrame.getMyReadyState()==true) {
					myFinishFrame.dispose();
					myFinishFrame.soundStopPlay();
					createMyInPutPlanesFrame();
				}
				break;
			case '4':
				myFinishFrame.enemyReadyChange(false);
				break;
			case '5':
				myBombPlanesFrame.dispose();
				myBombPlanesFrame.soundStopPlay();
				createMyFinishFrame(false);
				break;
			case '8':
				/**
				 * ����λ8:
				 * 80:�з��ѵ���
				 * 810:�ҷ���������ʧ��
				 * 811+�����:�ҷ���������ɹ�
				 * 820:�ҷ����뷿��ʧ��
				 * 821:�ҷ����뷿��ɹ�
				 * 83:�з��Ѽ��뷿��
				 */
				char serverMessageType=messageBody.charAt(0);
				String serverMessageBody=messageBody.substring(1);
				
				switch(serverMessageType) {
				case '0':
					TextDialog txtDl=new TextDialog(WANBMFindEnemyFrame.this,"��ʾ","<html>�����ѵ��ߣ�<br/>�����������˵�...</html>");
	        		txtDl.setSize(600, 300); 
	        		txtDl.setLocation((int)screensize.getWidth()/2-300,(int)screensize.getHeight()/2-150);
	        		txtDl.addWindowListener(new WindowAdapter() {// ��Ӵ������
	        			public void windowClosing(WindowEvent e) {// ����ر�ǰ
	        				switch(myWANBMStep) {
		            		case 0:
		            			sound.stopPlay();
		            			dispose();
		            			break;
		            		case 1:
		            			myInPutPlanesFrame.soundStopPlay();
		            			myInPutPlanesFrame.dispose();
		            			break;
		            		case 2:
		            			myBombPlanesFrame.soundStopPlay();
		            			myBombPlanesFrame.dispose();
		            			break;
		            		case 3:
		            			myFinishFrame.soundStopPlay();
		            			myFinishFrame.dispose();
		            			break;
	        				}
	        				stopConnect();
	        				new MainFrame();
	        			}
	        		});
	        		txtDl.setVisible(true);
					break;
				case '1':
					if(isCreater) {
						if(serverMessageBody.charAt(0)=='0') {
							RoomMessageJL.setText("<html>��������ʧ��!</html>");
							RoomJB.setText("���´���");
							status=2;
						}
						else if(serverMessageBody.charAt(0)=='1') {
							RoomMessageJL.setText("<html>���䴴���ɹ�! ���ڵȴ���������......<br/>�����:"+serverMessageBody.substring(1)+
									"<br/>�ѷ���Ÿ�����ĺ��Ѱɣ�</html>");
							status=3;
						}
					}
					break;
				case '2':
					if(!isCreater) {
						if(serverMessageBody.charAt(0)=='0') {
							RoomMessageJL.setText("<html>���뷿��ʧ�ܣ�<br/>����������ķ�����Ƿ���ȷ��</html>");
							status=6;
						}
						else if(serverMessageBody.charAt(0)=='1') {
							RoomMessageJL.setText("<html>�ɹ����뷿�䣡<br/>�ȴ�������ʼ��Ϸ...</html>");
							status=7;
						}
					}
					break;
				case '3':
					if(isCreater) {
						RoomMessageJL.setText("<html>���������ӣ�<br/>���̿�ʼ��Ϸ�ɣ�</html>");
						status=4;
					}
					break;
				}
				break;
			case '9':
				chatPanel.receiveMessage(messageBody);
				break;
		}
	}
	
	
}
