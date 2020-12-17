package LANBattleMode;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import Service.Sound;
import View.BattleModeChooseFrame;
import View.MainFrame;
import View.TextDialog;

import java.io.*;
import java.net.*;

public class LANBMFindEnemyFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private BufferedReader reader; // ����BufferedReader����
	private PrintWriter writer; // ����BufferedWriter����
	private ServerSocket server; // ����ServerSocket����
	private Socket socket; // ����Socket����socket
	private Sound sound = new Sound(); 
	private final int socketNumber=20118;
	private JLayeredPane layeredPane = new JLayeredPane();;
	private JLabel RoomMessageJL;
	private JButton RoomJB;
	private ActionListener actionListener=null;
	private LANBMInputPlanesFrame myInPutPlanesFrame;
	private LANBMBombPlanesFrame myBombPlanesFrame;
	private LANBMFinishFrame myFinishFrame;
	private int codeReceiveFromEnemy;
	private int myInputCode;
	private int myLANBMStep=0;
	private boolean isCancelConnect=false;	//���ҷ�����ȡ������
	private ChatPanel chatPanel = new ChatPanel();
	
	
	private final Dimension screensize=Toolkit.getDefaultToolkit().getScreenSize();
	public LANBMFindEnemyFrame(boolean IsServer) {
		setLayout(null);
		sound.playBGMFindEnemy();
		myLANBMStep=0;
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
				new BattleModeChooseFrame().BMLANEnterJB();
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
	        		TextDialog txtDl=new TextDialog(LANBMFindEnemyFrame.this,"��ʾ","<html>�����ѵ��ߣ�<br/>�����������˵�...</html>");
	        		txtDl.setSize(600, 300); 
	        		txtDl.setLocation((int)screensize.getWidth()/2-300,(int)screensize.getHeight()/2-150);
	        		txtDl.addWindowListener(new WindowAdapter() {// ��Ӵ������
	        			public void windowClosing(WindowEvent e) {// ����ر�ǰ
	        				switch(myLANBMStep) {
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
	
	//��������
	public void createRoom() {
		RoomMessageJL.setText("����������...");
		
		InetAddress ip;
		try { // try���鲶׽���ܳ��ֵ��쳣
			server = new ServerSocket(socketNumber); // ʵ����Socket����
			ip = InetAddress.getLocalHost(); // ʵ��������
			String localip = ip.getHostAddress(); // ��ȡ��IP��ַ
			RoomMessageJL.setText("<html>���䴴���ɹ�! ���ڵȴ���������......<br/>�����:"+localip+
					"<br/>�ѷ���Ÿ�����ĺ��Ѱɣ�</html>");
			
			removeActionListener();
			RoomJB.setText("��ʼ��Ϸ");
			actionListener=new ActionListener() { // Ϊ��ť�����굥���¼�
				@Override
				public void actionPerformed(ActionEvent e) {
					String text="���ڵȴ��������ӣ�";
					TextDialog txtDl=new TextDialog(LANBMFindEnemyFrame.this,"��ʾ",text);
					txtDl.setSize(600, 300); 
					txtDl.setLocation((int)screensize.getWidth()/2-300,(int)screensize.getHeight()/2-150);
					txtDl.setVisible(true);
				}
			};
			RoomJB.addActionListener(actionListener);
			
			class serverAcceptThread implements Runnable{   //���ն������ӵ��߳�
			    @Override  
			    public void run() {
			    	try {
						socket=server.accept();
						writer=new PrintWriter(new OutputStreamWriter(socket.getOutputStream()),true);
			            new Thread(new ReaderThread()).start(); 
			            
			            removeActionListener();
			            actionListener=new ActionListener() { // Ϊ��ť�����굥���¼�
							@Override
							public void actionPerformed(ActionEvent e) {
								sendMessage("0");
								dispose();
								sound.stopPlay();
								createMyInPutPlanesFrame();
							}
						};
						RoomJB.addActionListener(actionListener);
			            
						RoomMessageJL.setText("<html>���������ӣ�<br/>���̿�ʼ��Ϸ�ɣ�</html>");
			    	} catch (IOException e2) {} 
			    }
			}
			new Thread(new serverAcceptThread()).start();
			
		}
		catch (Exception e) {	//��ȡip��ַ����ʵ��socket�����쳣
			RoomMessageJL.setText("���䴴��ʧ�ܣ����������������!");
			stopConnect();
			RoomJB.setText("�ٴδ���");
			
			removeActionListener();
            actionListener=new ActionListener() { // Ϊ��ť�����굥���¼�
				@Override
				public void actionPerformed(ActionEvent e) {
					createRoom();
				}
			};
			RoomJB.addActionListener(actionListener);
		}
	}
	
	//���뷿��
	public void enterRoom() {
		RoomMessageJL.setText("�����뷿���:");
		removeActionListener();
		
		JTextField EnterRoomJT=new JTextField("",20);
		RoomJB.setText("���뷿��");
		
		class ClientEnterRoomThread implements Runnable{   //���ӷ�����߳�
		    @Override  
		    public void run() {
		    	RoomMessageJL.setText("<html>����������...<br/>���Ժ�</html>");
		    	try { // ��׽�쳣
		    		socket=new Socket(EnterRoomJT.getText(),socketNumber); // ʵ����Socket����
		    		writer=new PrintWriter(new OutputStreamWriter(socket.getOutputStream()),true);
		    		new Thread(new ReaderThread()).start(); 
		    		RoomMessageJL.setText("<html>�ɹ����뷿�䣡<br/>�ȴ�������ʼ��Ϸ...</html>");
		    		
		    		if(actionListener!=null) EnterRoomJT.removeActionListener(actionListener);
		    		removeActionListener();
		    		actionListener=new ActionListener() { // Ϊ��ť�����굥���¼�
		    			@Override
		    			public void actionPerformed(ActionEvent e) {
		    				String text="���ڵȴ�������ʼ��Ϸ��";
							TextDialog txtDl=new TextDialog(LANBMFindEnemyFrame.this,"��ʾ",text);
							txtDl.setSize(600, 300); 
							txtDl.setLocation((int)screensize.getWidth()/2-300,(int)screensize.getHeight()/2-150);
							txtDl.setVisible(true);
		    			}
		    		};
		    		RoomJB.addActionListener(actionListener);
		    		EnterRoomJT.addActionListener(actionListener);
		    	} catch (Exception e1) {	//���뷿��ʧ��
		    		stopConnect();
		    		RoomMessageJL.setText("<html>���뷿��ʧ�ܣ�<br/>�����������򷿼���Ƿ���ȷ��</html>");
		    	}
		    }
		}
		
		EnterRoomJT.setFont(new Font("΢���ź�",0,18));
		EnterRoomJT.setBounds(300, 300, 400, 40);
		EnterRoomJT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Thread(new ClientEnterRoomThread()).start();
			}
		});
		layeredPane.add(EnterRoomJT,new Integer(300));
		EnterRoomJT.requestFocus();
		
		
		removeActionListener();
		actionListener=new ActionListener() { // Ϊ��ť�����굥���¼�
			@Override
			public void actionPerformed(ActionEvent e) {
				new Thread(new ClientEnterRoomThread()).start();
			}
		};
		RoomJB.addActionListener(actionListener);
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
	
	//�Ƴ�RoomJB�ϵ�ActionLister
	public void removeActionListener() {
		if(actionListener!=null) {
			RoomJB.removeActionListener(actionListener);
			actionListener=null;
		}
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
		myLANBMStep=1;
		myInPutPlanesFrame=new LANBMInputPlanesFrame(LANBMFindEnemyFrame.this);
	}
	
	//����myBombPlanesFrame
	public void createMyBombPlanesFrame(boolean isFirst) {
		myLANBMStep=2;
		myBombPlanesFrame=new LANBMBombPlanesFrame(LANBMFindEnemyFrame.this,myInputCode,codeReceiveFromEnemy,isFirst);
	}
	
	//����myFinishFrame
	public void createMyFinishFrame(boolean isWinner) {
		myLANBMStep=3;
		myFinishFrame=new LANBMFinishFrame(LANBMFindEnemyFrame.this,myInputCode,codeReceiveFromEnemy,isWinner);
	}
	
	public void receiveMessageHandle(String readerMessage){
		/*messageType ��ȡ��Ϣ����: 
		 * 0:��������ʼ��Ϸ
		 * 1:����˷����Ĳ���9λ��Ϣ��
		 * 2:����˷���������
		 * 3:����˷����ļ�����Ϸ��׼����
		 * 4:����˷�����ȡ����Ϸ��ȡ��׼����
		 * 5:����˷�������Ϸ���� ���Ͷ�ʤ��
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
			case '9':
				chatPanel.receiveMessage(messageBody);
				break;
		}
	}
	
	
}
