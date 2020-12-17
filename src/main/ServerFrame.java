package main;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class ServerFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	private final int maxClient=30;
	private final int maxRoom=9;
	private final int maxSeat=maxRoom*2;
	private BufferedReader[] reader = new BufferedReader[maxClient]; // ����BufferedReader����
	private PrintWriter[] writer = new PrintWriter[maxClient]; // ����BufferedWriter����
	private int[] seatClient = new int[maxSeat]; // ��λ��Ӧ�Ŀͻ���
	private int[] clientSeat = new int[maxClient]; // �ͻ��˶�Ӧ����λ��
	private ServerSocket server; // ����ServerSocket����
	private Socket[] socket = new Socket[maxClient]; // ����Socket����socket
	private final int socketNumber=20119;
	private JLabel alreadyOpenLabel;
	private JLabel seatStatusLabel;
	private JLabel clientStatusLabel;
	private JButton controlButton;
	private boolean serverStatus=false;	//false��δ���� true:����
	private Container container = getContentPane();
	private int alreadyConnect=0;
	private final Dimension screensize=Toolkit.getDefaultToolkit().getScreenSize();
	
	public ServerFrame() {
		setLayout(null);
		
		alreadyOpenLabel = new JLabel("BombPlane������δ����");
		alreadyOpenLabel.setFont(new Font("����", Font.BOLD, 18));
		alreadyOpenLabel.setBounds(50,10,250,30);
		alreadyOpenLabel.setHorizontalAlignment(SwingConstants.CENTER);
		container.add(alreadyOpenLabel);
		
		seatStatusLabel = new JLabel();
		seatStatusLabel.setFont(new Font("΢���ź�", 0, 18));
		seatStatusLabel.setBounds(10,50,480,130);
		seatStatusLabel.setHorizontalAlignment(SwingConstants.CENTER);
		container.add(seatStatusLabel);
		
		clientStatusLabel = new JLabel();
		clientStatusLabel.setFont(new Font("΢���ź�", 0, 18));
		clientStatusLabel.setBounds(10,180,480,180);
		clientStatusLabel.setHorizontalAlignment(SwingConstants.CENTER);
		container.add(clientStatusLabel);
		
		controlButton = new JButton("����");
		controlButton.setFont(new Font("����", Font.BOLD, 18));
		controlButton.setBounds(350,10,100,30);
		controlButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!serverStatus) {
					openServer();
				}
				else {
					stopAllConnect();
				}
			}
		});
		container.add(controlButton);
		
		for(int i=0;i<maxSeat;i++) {
			seatClient[i]=-1;
		}
		for(int i=0;i<maxClient;i++) {
			clientSeat[i]=-1;
		}
		refreshLabels();
		
		setBounds((int)screensize.getWidth()/2-250,(int)screensize.getHeight()/2-200, 500, 400);// ���ú�������Ϳ��
		setTitle("BombPlane������");// ����
		setResizable(false);
        addWindowListener(new WindowAdapter() {// ��Ӵ������
            public void windowClosing(WindowEvent e) {// ����ر�ǰ
            	System.exit(0);
            }
        });
        setVisible(true);
	}
	
	//��client������Ϣ
	public void sendMessage(String message,int clientNumber) {
		if(socket[clientNumber]!=null) {
			writer[clientNumber].println(message);
			System.out.println("��"+clientNumber+"����:"+message);
		}
	}
	
	//�رշ�����
	public void stopAllConnect() {
		try {
			for(int i=0;i<maxClient;i++) {
				stopSingleConnect(i);
			}
			if(server!=null) {
				server.close();
			}
			for(int i=0;i<maxSeat;i++) {
				seatClient[i]=-1;
			}
			for(int i=0;i<maxClient;i++) {
				clientSeat[i]=-1;
			}
			alreadyConnect=0;
			serverStatus=false;
		} catch (IOException e2) {}
		controlButton.setText("����");
		refreshLabels();
		alreadyOpenLabel.setText("BombPlane������δ����");
	}
	
	//ֹͣ��һsocket����
	public void stopSingleConnect(int clientNumber) {
		try {
			if(socket[clientNumber]!=null) {
				socket[clientNumber].close(); // �ر��׽���
				socket[clientNumber]=null;
				alreadyConnect--;
				if(clientSeat[clientNumber]!=-1) {
					if(seatClient[getSeatMateNumber(clientSeat[clientNumber])]!=-1) {
						sendMessage("80",seatClient[getSeatMateNumber(clientSeat[clientNumber])]);
					}
					seatClient[clientSeat[clientNumber]]=-1;
					clientSeat[clientNumber]=-1;
				}
			}
			if(writer[clientNumber]!=null) {
				writer[clientNumber].close(); // �ر��׽���
				writer[clientNumber]=null;
			}
			if(reader[clientNumber]!=null) {
				reader[clientNumber].close(); // �ر��׽���
				reader[clientNumber]=null;
			}
		} catch (IOException e2) {}
		refreshLabels();
	}

	//�򿪷�����
	public void openServer() {
		alreadyOpenLabel.setText("������������...");
		try { // try���鲶׽���ܳ��ֵ��쳣
			server = new ServerSocket(socketNumber); // ʵ����Socket����
			class serverAcceptThread implements Runnable{   //���ն������ӵ��߳�
			    @Override  
			    public void run() {
			    	serverStatus=true;
					alreadyOpenLabel.setText("�������ѿ���");
					controlButton.setText("�ر�");
					Socket tempsocket;
					int tempClientNumber=-1;
			    	while(true) {
			    		if(alreadyConnect<maxClient) {
			    			try {
					    		tempsocket=server.accept();
					    		alreadyConnect++;
					    		tempClientNumber=getEmptyCilent();
					    		socket[tempClientNumber]=tempsocket;
								writer[tempClientNumber]=new PrintWriter(new OutputStreamWriter(socket[tempClientNumber].getOutputStream()),true);
					            new Thread(new ReaderThread(tempClientNumber)).start(); 
					    	} catch (IOException e2) {} 
			    		}
			    		try {
							Thread.sleep(10);
						} catch (InterruptedException e) {}
			    	}
			    }
			}
			new Thread(new serverAcceptThread()).start();
		}
		catch (Exception e) {	//��ȡip��ַ����ʵ��socket�����쳣
			alreadyOpenLabel.setText("����������ʧ�ܣ����������������!");
			stopAllConnect();
		}
	}
	
	//���뷿������
	public void enterRoom(int roomNumber,int clientNumber) {
		System.out.println(clientNumber+"������뷿��"+roomNumber);
		if(clientSeat[clientNumber]!=-1) {
			System.out.println("�ظ����ͼ��뷿������");
			return;
		}
		if(roomNumber<0||roomNumber>=maxRoom) {
			sendMessage("820",clientNumber);
		}
		else {
			if(seatClient[roomNumber*2+1]==-1&&seatClient[roomNumber*2]!=-1) {
				seatClient[roomNumber*2+1]=clientNumber;
				clientSeat[clientNumber]=roomNumber*2+1;
				sendMessage("821",clientNumber);
				sendMessage("831",seatClient[roomNumber*2]);
			}
			else {
				sendMessage("820",clientNumber);
			}
		}
		refreshLabels();
	}
	
	//������������
	public void createRoom(int clientNumber) {
		if(clientSeat[clientNumber]!=-1) return;
		for(int i=0;i<maxRoom;i++) {
			if(seatClient[i*2]==-1) {
				seatClient[i*2]=clientNumber;
				clientSeat[clientNumber]=i*2;
				sendMessage("811"+i,clientNumber);
				refreshLabels();
				return;
			}
		}
		sendMessage("810",clientNumber);
		return;
	}
	
	//�ҵ�client��λ��
	public int getEmptyCilent() {
		for(int i=0;i<maxClient;i++) {
			if(socket[i]==null) {
				return i;
			}
		}
		return -1;
	}
		
	//������Ϣ���߳�
	class ReaderThread extends Thread{
		private int clientNumber;
		public ReaderThread(int clientNumber) {
			this.clientNumber=clientNumber;
		}
		@Override
		public void run(){
			System.out.println("ReaderThread:"+clientNumber+"������");
			reader[clientNumber]=null;
	        try{  
	            while(true){  
	                //��ȡ�ͻ�������    
	            	reader[clientNumber]=new BufferedReader(new InputStreamReader(socket[clientNumber].getInputStream()));
	            	receiveMessageHandle(reader[clientNumber].readLine(), clientNumber);
	            	Thread.sleep(10);
	            }  
	        }catch(Exception e){ 
	        	//e.printStackTrace();
	        	stopSingleConnect(clientNumber); 
	        	refreshLabels();
	        	System.out.println("ReaderThread:"+clientNumber+"�ѵ���");
	        }
		}
	}
	
	//�����ȡ����Ϣ
	private void receiveMessageHandle(String readerMessage,int clientNumber) {
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
		System.out.println("��"+clientNumber+"������Ϣ:"+readerMessage);
		char messageType=readerMessage.charAt(0);
		String messageBody=readerMessage.substring(1);
		if(messageType=='7') {
			/***
			 * ����λ7:
			 * 71:�ͻ��˴�����������
			 * 72+�����:�ͻ��˼��뷿������
			 */
			char clientMessageType=messageBody.charAt(0);
			String clientMessageBody=messageBody.substring(1);
			switch(clientMessageType) {
				case '1':
					createRoom(clientNumber);
					break;
				case '2':
					enterRoom(Integer.parseInt(clientMessageBody), clientNumber);
					break;
			}
		}
		else {
			sendMessage(readerMessage,seatClient[getSeatMateNumber(clientSeat[clientNumber])]);
		}
	}
	
	public void refreshSeatStatus() {
		String status="<html>seats:<br/>";
		for(int i=0;i<maxSeat;i++) {
			status+=i;
			status+=":";
			status+=seatClient[i];
			status+=",";
			if(i%7==6) status+="<br/>";
		}
		status+="</html>";
		seatStatusLabel.setText(status);
	}
	
	public void refreshClientStatus() {
		String status="<html>clients:<br/>";
		for(int i=0;i<maxClient;i++) {
			status+=i;
			status+=":";
			status+=clientSeat[i];
			status+=",";
			if(i%7==6) status+="<br/>";
		}
		status+="</html>";
		clientStatusLabel.setText(status);
	}
	
	public void refreshLabels() {
		refreshClientStatus();
		refreshSeatStatus();
	}
	
	public int getSeatMateNumber(int number) {
		if(number%2==0) return number+1;
		else return number-1;
	}
	
	public static void main(String[] args) {
		new ServerFrame();// ����������
	}
}
