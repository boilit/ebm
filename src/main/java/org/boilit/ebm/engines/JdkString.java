package org.boilit.ebm.engines;

import org.boilit.ebm.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.List;

/**
 * @author Boilit
 * @see
 */
public final class JdkString extends Benchmark {

    @Override
    public void init() {
    }

    @Override
    protected void workOnBytes(List<StockModel> items) throws Exception {
        this.workOnChars(items);
    }

    @Override
    protected void workOnChars(List<StockModel> items) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("<!DOCTYPE html>\n").append(
                "<html>\n").append(
                "<head>\n").append(
                "    <title>StockModel - Bsl</title>\n").append(
                "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=").append(this.getOutputEncoding()).append("\"/>\n").append(
                "\n").append(
                "    <style type=\"text/css\">\n").append(
                "        body {\n").append(
                "            color: #333333;\n").append(
                "            line-height: 150%;\n").append(
                "        }\n").append(
                "\n").append(
                "        td {\n").append(
                "            text-align: center;\n").append(
                "        }\n").append(
                "\n").append(
                "        thead {\n").append(
                "            font-weight: bold;\n").append(
                "            background-color: #C8FBAF;\n").append(
                "        }\n").append(
                "\n").append(
                "        .odd {\n").append(
                "            background-color: #F3DEFB;\n").append(
                "        }\n").append(
                "\n").append(
                "        .even {\n").append(
                "            background-color: #EFFFF8;\n").append(
                "        }\n").append(
                "    </style>\n").append(
                "</head>\n").append(
                "<body>\n").append(
                "<h1>StockModel - Bsl</h1>\n").append(
                "<table>\n").append(
                "    <thead>\n").append(
                "    <tr>\n").append(
                "        <th>#</th>\n").append(
                "        <th>id</th>\n").append(
                "        <th>code</th>\n").append(
                "        <th>name</th>\n").append(
                "        <th>price</th>\n").append(
                "        <th>range</th>\n").append(
                "        <th>amount</th>\n").append(
                "        <th>gravity</th>\n").append(
                "    </tr>\n").append(
                "    </thead>\n").append(
                "    <tbody>\n");
        StockModel item;
        final int size = items.size();
        for (int i = 0; i < size; i++) {
            item = items.get(i);
            builder.append(
                    "    <tr class=\"").append(i % 2 == 0 ? "odd" : "even").append("\">\n").append(
                    "        <td>").append(i).append("</td>\n").append(
                    "        <td>").append(item.getId()).append("</td>\n").append(
                    "        <td>").append(item.getCode()).append("</td>\n").append(
                    "        <td style=\"text-align: left;\">").append(item.getName()).append("</td>\n").append(
                    "        <td>").append(item.getPrice()).append("</td>\n").append(
                    "        <td style=\"color: ").append(item.getRange() >= 10 ? "red" : "blue").append(";\">${item.range}%</td>\n").append(
                    "        <td>").append(item.getAmount()).append("</td>\n");
            if (item.getGravity() >= 20) {
                builder.append("        <td style=\"color: red;\">").append(item.getGravity()).append("%</td>\n");
            } else {
                builder.append("        <td style=\"color: blue;\">").append(item.getGravity()).append("%</td>\n");
            }
            builder.append("    </tr>\n");
        }
        builder.append(
                "    </tbody>\n").append(
                "</table>\n").append(
                "</body>\n").append(
                "</html>");
        final Writer writer = this.getWriter();
        writer.write(builder.toString());
        this.close(writer);
    }

    public static EngineInfo getEngineInfo() {
        EngineInfo ei = new EngineInfo();
        ei.setName("StringBuilder");
        ei.setVersion("jdk"+System.getProperty("java.version"));
        ei.setClassName(JdkString.class.getName());
        ei.setJarFiles(new String[]{});
        return ei;
    }

    public static void main(String[] args) throws Exception {
        EngineInfo ei = getEngineInfo();
        Benchmark benchmark = Utilities.inject(args);
        benchmark.run();

        File file = new File(Utilities.getClassPath(), ei.getName().concat(".txt"));
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(ei.getName() + ";");
        writer.write(ei.getVersion() + ";");
        writer.write(benchmark.getElapsedTime() + ";");
        writer.write(String.valueOf(benchmark.getOutputSize()));
        writer.flush();
        writer.close();
    }
}
