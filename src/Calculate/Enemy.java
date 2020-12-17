package Calculate;

import java.util.Scanner;

public class Enemy extends Board{
	/* �ࣺEnemy ��������  / 0:��  1:�ɻ���  2:�ɻ�ͷ  3:δ֪  / ��ʼ״̬����Ϊ 0 */
	public Enemy() {		  // ���췽��:��ʼ��location���� ȫΪ 0
		super(0);
	}
	
	/*initialEnemy �������ܣ���ʼ��initialEnemy���� ��Ϊ 0 */
	public void initialEnemy(){
		initialBoard(0);
	}
	
	/*addPlane �������ܣ�����ɻ����ڲ�����������Ӹ÷ɻ�*/
	public boolean addPlane(Plane plane) {
		if(checkPlane(plane)) {
			int locationMessages[]=plane.showLocations();
			for(int j=0;j<10;j++)
			{
			location[locationMessages[j]][locationMessages[j+10]]=1;
			}
			location[plane.showHeadLocation()[0]][plane.showHeadLocation()[1]]=2;
			return true;
		}
		else {
			return false;
		}
	}
	
	/*checkPlane �������ܣ� ����ɻ����жϲ��������и÷��÷ɻ��Ƿ�Ϸ� */
	public boolean checkPlane(Plane plane) {
		int i=0;
		if(plane.checkNotOverflow()==false) return false;
		int locationMessages[]=plane.showLocations();
		for(int j=0;j<10;j++)
		{
			if(seeLocation(locationMessages[j],locationMessages[j+10])!=0)
			{
				i++;
			}
		}
		if(i==0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/*addPlanes9Code ��������ר�ã��������ܣ�����9λ��Ϣ�룬�ڲ������������3�ܷɻ�*/
	public void addPlanes9Code(int code) {
		int rest=code;
		int codeArray[]=new int[9];
		int space;
		for(space=8;space>=0;space--) {
			codeArray[space]=rest/(int)Math.pow(10,space);
			rest=rest%(int)Math.pow(10,space);
		}
		
		addPlane(new Plane(codeArray[8],codeArray[7],codeArray[6]));
		addPlane(new Plane(codeArray[5],codeArray[4],codeArray[3]));
		addPlane(new Plane(codeArray[2],codeArray[1],codeArray[0]));
	}
	
	/*addPlane3Code �������ܣ�����3λ��Ϣ�룬�ڲ��������������ܷɻ��������Ƿ���ӳɹ�*/
	public boolean addPlane3Code(int code) {
		int rest=code;
		int codeArray[]=new int[2];
		int space;
		for(space=2;space>=0;space--) {
			codeArray[space]=rest/(int)Math.pow(10,space);
			rest=rest%(int)Math.pow(10,space);
		}
		return addPlane(new Plane(codeArray[2],codeArray[1],codeArray[0]));
	}
	
	/* inputPlane �������ܣ������в������в������������������Plane */
	public Plane inputPlane(int centerRow,int centerColumn,int direction) {
		Plane plane=new Plane(centerRow,centerColumn,direction);
		return plane;
	}
	
	/* ���һ����Խ�������ɻ�*/
	public Plane getRandomPlane() {
		Plane plane;
		do {
			plane=new Plane((int)(Math.random()*8)+1,(int)(Math.random()*8)+1,(int)(Math.random()*4));
		}while(!plane.checkNotOverflow());
		return plane;
	}
	
	/* arrayFill �������ܣ������ȡ��������ȡ����������������*/
	public int[] arrayFill(int num) {
		Scanner sc=new Scanner(System.in);
		int[] message=new int[num];
		for(int i=0;i<num;i++) {
			System.out.println("Input "+(i+1)+ "number");
			message[i]=sc.nextInt();
		}
		sc.close();
		return message;
	}
	
	public static void main(String[] args) {
		Enemy enemy1=new Enemy();
		enemy1.printLocation();
	}
}
