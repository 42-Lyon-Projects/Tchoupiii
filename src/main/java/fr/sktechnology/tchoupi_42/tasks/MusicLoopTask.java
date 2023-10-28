package fr.sktechnology.tchoupi_42.tasks;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;
import java.util.TimerTask;

public class MusicLoopTask extends TimerTask {
    @Override
    public void run() {
       Runtime runtime = Runtime.getRuntime();
        try {
            runtime.exec("pactl set-sink-mute 1 0");
            runtime.exec("pactl set-sink-volume 1 100%");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        playAudio("https://dl.sndup.net/bwvk/wavetouc.wav");
    }

    private void playAudio(String url) {
        AudioFormat decodedFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100, 16, 2, 4, 44100, false);
        try (AudioInputStream din = AudioSystem.getAudioInputStream(decodedFormat, AudioSystem.getAudioInputStream(new URL(url)))) {
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, decodedFormat);
            try (SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info)) {
                if (line != null) {
                    line.open(decodedFormat);
                    byte[] data = new byte[4096];
                    line.start();
                    int nBytesRead;
                    while ((nBytesRead = din.read(data, 0, data.length)) != -1) {
                        line.write(data, 0, nBytesRead);
                    }
                    line.drain();
                    line.stop();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
