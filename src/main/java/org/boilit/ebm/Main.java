package org.boilit.ebm;

import java.io.*;

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
        int warmCount = 10;
        int loopCount = 10000;
        boolean buffered = false;
        String outputEncoding = "UTF-8";
        OutputMode outputMode = OutputMode.BYTES;
        int capacity = StockModel.CAPACITY_2;

        if (args == null || args.length >0) {
            for (int i = 0, n = args.length; i < n; i += 2) {
                if (args[i].trim().equals("-warmCount")) {
                    warmCount = Integer.parseInt(args[i + 1]);
                } else if (args[i].trim().equals("-loopCount")) {
                    loopCount = Integer.parseInt(args[i + 1]);
                } else if (args[i].trim().equals("-buffered")) {
                    buffered = Boolean.parseBoolean(args[i + 1]);
                } else if (args[i].trim().equals("-outputEncoding")) {
                    outputEncoding = args[i + 1];
                } else if (args[i].trim().equals("-outputModel")) {
                    outputMode = OutputMode.valueOf(args[i + 1]);
                } else if (args[i].trim().equals("-capacity")) {
                    capacity = Integer.parseInt(args[i + 1]);
                }
            }
        }

        StockModel.setCapacity(capacity);
        EngineInfo[] engines = new EngineInfo[]{
                EngineInfoFactory.getJdkStringEngineInfo(),
                EngineInfoFactory.getBslEngineInfo(),
                EngineInfoFactory.getHttlEngineInfo(),
                EngineInfoFactory.getWebitEngineInfo(),
                EngineInfoFactory.getBeetlEngineInfo(),
                EngineInfoFactory.getVelocityEngineInfo(),
                EngineInfoFactory.getFreeMarkerEngineInfo()
        };
        System.out.println(Utilities.getDelimiter());
        Result[] results = new Result[engines.length];
        String command;
        Process process;
        StringBuilder parameters;
        File commandFile;
        File resultFile;
        String commandFilePath;
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
            commandFile = Utilities.write(engines[i], parameters.toString());
            commandFilePath = commandFile.getAbsolutePath();
            System.out.println("process engine [" + engines[i].getName() + "] use standlone jvm ...");
            command = "cmd /c ".concat(commandFilePath);
            process = Runtime.getRuntime().exec(command);
            process.waitFor();
            resultFile = new File(commandFilePath.replaceAll("\\.bat$", ".txt"));
            reader = new BufferedReader(new FileReader(resultFile));
            values = reader.readLine().split(";");
            reader.close();
            results[i] = new Result();
            results[i].name = values[0];
            results[i].version = values[1];
            results[i].time = Long.parseLong(values[2]);
            results[i].size = Long.parseLong(values[3]);
            results[i].tps = loopCount * 1000 / results[i].time;
            if (i == 0) {
                results[i].rate = 100.00d;
            }
            commandFile.delete();
            resultFile.delete();
        }

        for (int i = 1, n = results.length; i < n; i++) {
            results[i].rate = (double) results[0].time * 100 / (double) results[i].time;
        }

        StringBuilder result = new StringBuilder();
        result.append(Utilities.showEnv(warmCount, loopCount, buffered, outputEncoding, outputMode)).append(Utilities.CR_LF);

        System.out.println(Utilities.showEnv(warmCount, loopCount, buffered, outputEncoding, outputMode));

        result.append(fit("Engine", 20));
        result.append(fit("Version", 20));
        result.append(fit("Time(ms)", 10));
        result.append(fit("Size(b)", 10));
        result.append(fit("Tps", 10));
        result.append(fit("Rate(%)", 10));
        result.append(Utilities.CR_LF);

        System.out.print(fit("Engine", 20));
        System.out.print(fit("Version", 20));
        System.out.print(fit("Time(ms)", 10));
        System.out.print(fit("Size(b)", 10));
        System.out.print(fit("Tps", 10));
        System.out.print(fit("Rate(%)", 10));
        System.out.println();
        for (int i = 0, n = results.length; i < n; i++) {
            result.append(fit(results[i].name, 20));
            result.append(fit(results[i].version, 20));
            result.append(fit(results[i].time, 10));
            result.append(fit(results[i].size, 10));
            result.append(fit(results[i].tps, 10));
            result.append(fit(String.format("%.2f", results[i].rate), 10));
            result.append(Utilities.CR_LF);

            System.out.print(fit(results[i].name, 20));
            System.out.print(fit(results[i].version, 20));
            System.out.print(fit(results[i].time, 10));
            System.out.print(fit(results[i].size, 10));
            System.out.print(fit(results[i].tps, 10));
            System.out.print(fit(String.format("%.2f", results[i].rate), 10));
            System.out.println();
        }
        result.append(Utilities.getDelimiter());
        result.append(Utilities.CR_LF);

        System.out.println(Utilities.getDelimiter());

        File file = new File(Utilities.getClassPath(), "benchmark.txt");
        if(!file.exists()) {
            file.createNewFile();
        }
        FileWriter writer = new FileWriter(file);
        writer.write(result.toString());
        writer.close();
        System.out.println("Benchmark Test Result has saved to file["+file.getAbsolutePath()+"]");
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
