package Service;

import java.io.*;
import java.util.Arrays;


public class FileDataHanlder {
	private static final String VOLUMEFILE = "data/Volume";// ������¼�ļ�
	private static int[] volumes=new int[3];	
	//MasterVolume,BGMVolume,SoundVolume 0-100��Χ
    private static final int initVolumnPercent=80;
	
	//��ʼ������
	public static void initVolumes() {
		 File f = new File(VOLUMEFILE);// ������¼�ļ�
	        if (!f.exists()) {// ����ļ�������
	            try {
	                f.createNewFile();// �������ļ�
	                Arrays.fill(volumes, initVolumnPercent);// �������
                    saveVolumes();
	            } catch (IOException e) {}
	            return;// ֹͣ����
	        }
	        FileInputStream fis = null;
	        InputStreamReader isr = null;
	        BufferedReader br = null;
	        try {
	            fis = new FileInputStream(f);// �ļ��ֽ�������
	            isr = new InputStreamReader(fis);// �ֽ���ת�ַ���
	            br = new BufferedReader(isr);// �����ַ���
	            String value = br.readLine();// ��ȡһ��
	            if (!(value == null || "".equals(value))) {// �����Ϊ��ֵ
	                String vs[] = value.split(",");// �ָ��ַ���
	                if (vs.length != 3) {// ����ָ���������3
	                    Arrays.fill(volumes,initVolumnPercent);// �������70
	                    saveVolumes();
	                } else {
	                    for (int i=0;i<3;i++) {
	                        // ����¼�ļ��е�ֵ������ǰ��������
	                    	 volumes[i]=Integer.parseInt(vs[i]);
	                    }
	                }
	            }
	        } catch (FileNotFoundException e) {
	        } catch (IOException e) {
	        } finally {// ���ιر���
	            try {
	                br.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	            try {
	                isr.close();
	            } catch (IOException e) {
	            }
	            try {
	                fis.close();
	            } catch (IOException e) {
	            }

	        }
	}

	//�洢����ֵ
	public static void saveVolumes() {
		 // ƴ�ӵ÷�����
        String value = volumes[0] + "," + volumes[1] + "," + volumes[2];
        FileOutputStream fos = null;
        OutputStreamWriter osw = null;
        BufferedWriter bw = null;
        try {
            fos = new FileOutputStream(VOLUMEFILE);// �ļ��ֽ������
            osw = new OutputStreamWriter(fos);// �ֽ���ת�ַ���
            bw = new BufferedWriter(osw);// �����ַ���
            bw.write(value);// д��ƴ�Ӻ���ַ���
            bw.flush();// �ַ���ˢ��
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        } finally {// ���ιر���
            try {
                bw.close();
            } catch (IOException e) {
            }
            try {
                osw.close();
            } catch (IOException e) {
            }
            try {
                fos.close();
            } catch (IOException e) {
            }
        }
		
	}
	
	//�ı���������ֵ
	public static void changeVolulmes(int receiveVolumes[]) {
		for(int i=0;i<3;i++) {
			volumes[i]=receiveVolumes[i];
		}
	}
	
	//��ȡ��������ֵ
	public static int[] getVolulmes() {
		return volumes;
	}
	
	//��ȡ���������ٷ���
	public static float getBGMPercent() {
		float BGMPercent= ((float)(volumes[0]*volumes[1]))/10000;
		return BGMPercent;
	}
	
	//��ȡ��Ч�����ٷ���
	public static float getSoundEffectPercent() {
		float BGMPercent= ((float)(volumes[0]*volumes[2]))/10000;
		return BGMPercent;
	}
	
	
	
}
