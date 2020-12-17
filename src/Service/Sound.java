
package Service;

import java.io.FileNotFoundException;

//��Ч��
public class Sound {
    final String DIR = "music/";
    final String BOMB0= "bombnothing.wav";
    final String BOMB1= "bombbody.wav";
    final String BOMB2= "bombhead.wav";
    final String BGMMenu= "bgmMenu.wav";
    final String BGMChooseMode= "bgmChooseMode.wav";
    final String BGMFindEnemy= "bgmFindEnemy.wav";
    final String BGMInputPlanes= "bgmInputPlanes.wav";
    final String BGMBombPlanes= "bgmBombPlanes.wav";
    final String BGMBombFinish= "bgmBombFinish.wav";
    
    MusicPlayer player;
    
    //����bombnothing
    public void playBOMB0() {
        play(DIR+BOMB0,FileDataHanlder.getSoundEffectPercent(),false);
    }
    
    //����bombbody
    public void playBOMB1() {
        play(DIR+BOMB1,FileDataHanlder.getSoundEffectPercent(),false);
    }
    
    //����bombhead
    public void playBOMB2() {
        play(DIR+BOMB2,FileDataHanlder.getSoundEffectPercent(),false);
    }
    
    //����bgm
    public void playBGMMenu() {
        play(DIR+BGMMenu,FileDataHanlder.getBGMPercent(),true);
    }
    
    //����bgm
    public void playBGMFindEnemy() {
        play(DIR+BGMFindEnemy,FileDataHanlder.getBGMPercent(),true);
    }
    
    //����bgm
    public void playBGMChooseMode() {
        play(DIR+BGMChooseMode,FileDataHanlder.getBGMPercent(),true);
    }
    
    //����bgm
    public void playBGMInputPlanes() {
        play(DIR+BGMInputPlanes,FileDataHanlder.getBGMPercent(),true);
    }
    
    //����bgm
    public void playBGMBombPlanes() {
        play(DIR+BGMBombPlanes,FileDataHanlder.getBGMPercent(),true);
    }
    
    //����bgm
    public void playBGMBombFinish() {
        play(DIR+BGMBombFinish,FileDataHanlder.getBGMPercent(),true);
    }
    
    //��ʱ��������
    public void temporaryChangeVolumn(float volumePercent) {
    	player.temporaryChangeVolumn(volumePercent);
    }
    
    
    
    /**
     * ����
     * 
     * @param file
     *            �����ļ���������
     * @param circulate
     *            �Ƿ�ѭ������
     */
    private void play(String file,float volumn,boolean circulate) {
        try {
            // ����������
            player = new MusicPlayer(file,volumn,circulate);
            player.play();// ��������ʼ����
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void stopPlay() {
    	if(player!=null) player.stop();
    }
    
}
