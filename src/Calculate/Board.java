package Calculate;

public class Board {             //����:Board
	protected int[][] location= new int[10][10];
	public Board(int value) {	//���췽��������int value ��ʼ��location����:��Ϊvalue
		initialBoard(value);
	}
	
	/* initialBoard �������ܣ�����int value ��ʼ��location����:��Ϊvalue */
	public void initialBoard(int value) {   
		for(int i=0;i<10;i++)
		{
			for(int j=0;j<10;j++)
			{
				location[i][j]=value;
			}
		}
	}
	
	/*seeLocation �������ܣ� �����������꣬���ظ�λ�ö�ά����ֵ */
	public int seeLocation(int row,int column) {
		return location[row][column];
	}
	
	/* printLocation �������ܣ���ӡlocation����  */
	public void printLocation(){
		for(int i=0;i<10;i++)
		{
			for(int j=0;j<10;j++)
			{
				System.out.print(location[i][j]+" ");
			}
			System.out.printf("\n");
		}
	}
	
	public static void main(String[] args) {
		
	}
}
