package org.boilit.ebm;

import org.boilit.ebm.engines.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * @author Boilit
 * @see
 */
public class Main {

    private static class Result {
        private String name;
        private String version;
        private long time;
        private long size;
        private long tps;
        private double rate;
    }

    public static void main(String[] args) throws Exception {
        final int warmCount = 10;
        final int loopCount = 10000;
        final boolean buffered = false;
        final String outputEncoding = "UTF-8";
        final OutputMode outputMode = OutputMode.BYTES;
        final int capacity = StockModel.CAPACITY_5;

        StockModel.setCapacity(capacity);
        EngineInfo[] engines = new EngineInfo[]{
                JdkString.getEngineInfo(),
                Bsl.getEngineInfo(),
                Httl.getEngineInfo(),
                Webit.getEngineInfo(),
                Beetl.getEngineInfo(),
                Velocity.getEngineInfo(),
                FreeMarker.getEngineInfo()
        };

        System.out.println(Utilities.getDelimiter());
        Result[] results = new Result[engines.length];
        String command;
        Process process;
        StringBuilder parameters;
        String commandFile;
        BufferedReader reader;
        String[] values;
        for (int i = 0, n = engines.length; i < n; i++) {
            parameters = new StringBuilder();
            parameters.append(" -class ").append(engines[i].getClassName());
            parameters.append(" -buffered ").append(buffered);
            parameters.append(" -warmCount ").append(warmCount);
            parameters.append(" -loopCount ").append(loopCount);
            parameters.append(" -outputEncoding ").append(outputEncoding);
            parameters.append(" -outputModel ").append(outputMode);
            parameters.append(" -capacity ").append(capacity);
            commandFile = Utilities.write(engines[i], parameters.toString()).getAbsolutePath();
            System.out.println("process engine [" + engines[i].getName() + "] use standlone jvm ...");
            command = "cmd /c ".concat(commandFile);
            process = Runtime.getRuntime().exec(command);
            process.waitFor();
            reader = new BufferedReader(new FileReader(new File(commandFile.replaceAll("\\.bat$", ".txt"))));
            values = reader.readLine().split(";");
            results[i] = new Result();
            results[i].name = values[0];
            results[i].version = values[1];
            results[i].time = Long.parseLong(values[2]);
            results[i].size = Long.parseLong(values[3]);
            results[i].tps = loopCount * 1000 / results[i].time;
            if (i == 0) {
                results[i].rate = 100.00d;
            }
        }

        for (int i = 1, n = results.length; i < n; i++) {
            results[i].rate = (double) results[0].time * 100 / (double) results[i].time;
        }

        System.out.println(Utilities.showEnv(warmCount, loopCount, buffered, outputEncoding, outputMode));

        System.out.print(fit("Engine", 20));
        System.out.print(fit("Version", 20));
        System.out.print(fit("Time(ms)", 10));
        System.out.print(fit("Size(b)", 10));
        System.out.print(fit("Tps", 10));
        System.out.print(fit("Rate(%)", 10));
        System.out.println();
        for (int i = 0, n = results.length; i < n; i++) {
            System.out.print(fit(results[i].name, 20));
            System.out.print(fit(results[i].version, 20));
            System.out.print(fit(results[i].time, 10));
            System.out.print(fit(results[i].size, 10));
            System.out.print(fit(results[i].tps, 10));
            System.out.print(fit(String.format("%.2f", results[i].rate), 10));
            System.out.println();
        }
        System.out.println(Utilities.getDelimiter());
    }

    private static String fit(Object value, int length) {
        String valStr = value.toString();
        java.lang.StringBuilder builder = new java.lang.StringBuilder(valStr);
        for (int i = 0, n = Math.max(length - valStr.length(), 0); i < n; i++) {
            builder.append(' ');
        }
        return builder.toString();
    }
}
