import sun.audio.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;

public class Sound {
int volume;
boolean moveSound,gameSound;
AudioPlayer MGP = AudioPlayer.player;
AudioStream BGM;
AudioData MD;

public Sound(Board gameBoard){
	volume = gameBoard.getGameNoiseVal();
	moveSound = gameBoard.getPieceNoise();
	gameSound = gameBoard.getGameNoise();
}

public  void music() 
{       
   

    ContinuousAudioDataStream loop = null;

    try
    {
        InputStream test = new FileInputStream("Downloads/Chess.mp3");
        
        BGM = new AudioStream(test);
        AudioPlayer.player.start(BGM);
       
        //MD = BGM.getData();
        //loop = new ContinuousAudioDataStream(MD);

    }
    catch(FileNotFoundException e){
        System.out.print(e.toString());
    }
    catch(IOException error)
    {
        System.out.print(error.toString());
    }
    MGP.start(loop);
}
}