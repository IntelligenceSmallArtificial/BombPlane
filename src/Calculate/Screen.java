package Calculate;

public class Screen extends Board {
	/* �ࣺScreen ��ʾ����  / 0:��  1:��  2:��  3:δ֪ / ��ʼ״̬����Ϊ 3 */
	public Screen() {         // ���췽��:��ʼ��location���� ȫΪ 3
		super(3);
	}
	
	/* initialScreen �������ܣ���ʼ��initialLocation���� ��Ϊ 3 */
	public void initialScreen(){
		initialBoard(3);
	}
	
	/* changeScreen ��������: ���������꣬�����꣬��ĳֵ������ʾ�����λ�� */
	public void changeScreen(int row,int column,int value) {
		location[row][column]=value;
	}
	
	public static void main(String[] args) {
		Screen screen1=new Screen();
		screen1.printLocation();
	}
}
