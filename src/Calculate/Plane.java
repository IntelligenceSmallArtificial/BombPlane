package Calculate;

public class Plane {
//�ࣺ�ɻ�
	private int centerRow;		//Plane��ĳ�Ա�������ɻ����ĵ��в���
	private int centerColumn;	//Plane��ĳ�Ա�������ɻ����ĵ��в���
	private int direction;		//Plane��ĳ�Ա�������ɻ�����     �� 0 ��, 1 ��, 2 ��, 3 �� ��
	public Plane(int centerRow,int centerColumn,int direction){
		this.centerRow=centerRow;		
		this.centerColumn=centerColumn;	
		this.direction=direction;
	}
	
	/* showLocations ��������: ��������ʽ����Plane��10��������locations[a],locations[a+10] */
	public int[] showLocations(){
		int[] locations=new int[20];
		switch(direction) {
		case 0:
			 locations[10]=locations[13]=locations[16]=locations[18]=centerColumn;
			 locations[12]=locations[17]=centerColumn-1;
			 locations[11]=centerColumn-2;
			 locations[14]=locations[19]=centerColumn+1;
			 locations[15]=centerColumn+2;
			 locations[1]=locations[2]=locations[3]=locations[4]=locations[5]=centerRow;
			 locations[0]=centerRow-1;
			 locations[6]=centerRow+1;
			 locations[7]=locations[8]=locations[9]=centerRow+2;
			 break;
		case 1:
			 locations[10]=locations[13]=locations[16]=locations[18]=centerColumn;
			 locations[12]=locations[17]=centerColumn-1;
			 locations[11]=centerColumn-2;
			 locations[14]=locations[19]=centerColumn+1;
			 locations[15]=centerColumn+2;
			 locations[1]=locations[2]=locations[3]=locations[4]=locations[5]=centerRow;
			 locations[0]=centerRow+1;
			 locations[6]=centerRow-1;
			 locations[7]=locations[8]=locations[9]=centerRow-2;
			 break;
		case 2:
			 locations[0]=locations[3]=locations[6]=locations[8]=centerRow;
			 locations[2]=locations[7]=centerRow-1;
			 locations[1]=centerRow-2;
			 locations[4]=locations[9]=centerRow+1;
			 locations[5]=centerRow+2;
			 locations[11]=locations[12]=locations[13]=locations[14]=locations[15]=centerColumn;
			 locations[10]=centerColumn-1;
			 locations[16]=centerColumn+1;
			 locations[17]=locations[18]=locations[19]=centerColumn+2;
			 break;
		case 3:
			 locations[0]=locations[3]=locations[6]=locations[8]=centerRow;
			 locations[2]=locations[7]=centerRow-1;
			 locations[1]=centerRow-2;
			 locations[4]=locations[9]=centerRow+1;
			 locations[5]=centerRow+2;
			 locations[11]=locations[12]=locations[13]=locations[14]=locations[15]=centerColumn;
			 locations[10]=centerColumn+1;
			 locations[16]=centerColumn-1;
			 locations[17]=locations[18]=locations[19]=centerColumn-2;
			 break;
		}
		return locations;
	}
	
	/* showHeadLocation ��������: ����Plane�ɻ�ͷ����head[0][1] */
	public int[] showHeadLocation() {
		int[] head=new int[2];
		switch(direction) {
		case 0:
			head[0]=centerRow-1;
			head[1]=centerColumn;
			break;
		case 1:
			head[0]=centerRow+1;
			head[1]=centerColumn;
			break;
		case 2:
			head[0]=centerRow;
			head[1]=centerColumn-1;
			break;
		case 3:
			head[0]=centerRow;
			head[1]=centerColumn+1;
			break;
		}
		return head;
	}
	
	/* checkNotOverflow �������ܣ����ɻ�λ���Ƿ�Խ�磬���Խ�磬����false */
	public boolean checkNotOverflow(){
		int i=0;
		if (centerRow<1) i++;
		if (centerRow>8) i++;
		if (centerColumn<1) i++;
		if (centerColumn>8) i++;
		if (direction!=0 && direction!=1 && direction!=2 && direction!=3) i++;
		if (centerRow==1 && direction!=0) i++;
		if (centerRow==8 && direction!=1) i++;
		if (centerColumn==1 && direction!=2) i++;
		if (centerColumn==8 && direction!=3) i++;
		if (i==0) return true;
		else return false;
	}
	
	/* printPlaneCode ��ӡ�ɻ���Ϣ�� */
	public void printPlaneCode() {
		System.out.println("�ɻ���Ϣ: "+centerRow+" "+centerColumn+" "+direction);
	}
	
	public static void main(String[] args) {
		
	}

}