package org.boilit.ebm;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class Report {
    public static void write(long loop, Result[] results, File directory) throws Exception {
        String ebm = "Engine Benchmark 2.0.0";
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        //@formatter:off
        StringBuffer buffer = new StringBuffer();
        buffer.append("<!DOCTYPE html>\n")
              .append("<html>\n")
              .append("<head>\n")
              .append("    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n")
              .append("    <style type=\"text/css\">\n")
              .append("        body {\n")
              .append("            margin: 0px;\n")
              .append("            padding: 0px;\n")
              .append("        }\n")
              .append("        a {\n")
              .append("            white-space:nowrap;\n")
              .append("            font-size: 12px;\n")
              .append("            text-decoration: none;\n")
              .append("        }\n")
              .append("        a:active {\n")
              .append("            color: #0000ff;\n")
              .append("        }\n")
              .append("        a:hover {\n")
              .append("            color: #ff0000;\n")
              .append("        }\n")
              .append("        a.engine {\n")
              .append("            margin-right: 120px;\n")
              .append("            text-align: right;\n")
              .append("        }\n")
              .append("        div.baner {\n")
              .append("            padding: 4px;\n")
              .append("            text-align: center;\n")
              .append("            color: #ff0000;\n")
              .append("            font-size: 11pt;\n")
              .append("            font-weight: 600;\n")
              .append("        }\n")
              .append("        div.title {\n")
              .append("            text-align: center;\n")
              .append("            color: #0000ff;\n")
              .append("            font-size: 11pt;\n")
              .append("            font-weight: 600;\n")
              .append("        }\n")
              .append("        div.canvas {\n")
              .append("            width: 70%;\n")
              .append("            height: ")
              .append(results.length * 24)
              .append("px;\n")
              .append("            margin: 4px auto;\n")
              .append("        }\n")
              .append("    </style>\n")
              .append("</head>\n")
              .append("<body style=\"text-align: center;\">\n")
              .append("<div class=\"baner\">以下测试结果由<a href=\"https://github.com/boilit/ebm\">")
              .append("Engine Benchmark 2.0.0")
              .append("</a>生成， 测试日期：")
              .append(date)
              .append("</div>\n")
              .append("<hr/>\n")
              .append("<div class=\"title\">每秒钟渲染模板次数：越大越好</div>\n")
              .append("<div id=\"tps\" class=\"canvas\"></div>\n")
              .append("<div class=\"title\">渲染")
              .append(loop)
              .append("次耗时(ms)：越小越好</div>\n")
              .append("<div id=\"time\" class=\"canvas\"></div>\n")
              .append("<div class=\"title\">单次渲染输出字节数：大小与排版的取舍?</div>\n")
              .append("<div id=\"size\" class=\"canvas\"></div>\n")
              .append("<!--[if IE]>\n")
              .append("<script type=\"text/javascript\" src=\"flotr2.ie.min.js\"></script>\n")
              .append("<![endif]-->\n")
              .append("<script type=\"text/javascript\" src=\"flotr2.min.js\"></script>\n")
              .append("<script type=\"text/javascript\">\n")
              .append("    function bars(container, data, yAxis) {\n")
              .append("        Flotr.draw(\n")
              .append("                container, [data], {\n")
              .append("                    bars: { show: true, horizontal: true, shadowSize: 0.2, barWidth:0.8 },\n")
              .append("                    mouse: { track: true, relative: true },\n")
              .append("                    xaxis: { min: 0, showLabels: true},\n")
              .append("                    yaxis: { ticks: yAxis, min:0 }\n")
              .append("                }\n")
              .append("        );\n")
              .append("    }\n")
              .append("    (function basic_bars(container, horizontal) {\n")
              .append("        var tps = [], time=[], size=[], yAxis = [];\n");
        //@formatter:on

        int i = 0;
        for (int n = results.length; i < n; i++) {
            //@formatter:off
            buffer.append("        tps.push([")
                  .append(results[i].getTps())
                  .append(", ")
                  .append(i + 1)
                  .append("]);\n")
                  .append("        time.push([")
                  .append(results[i].getTime())
                  .append(", ")
                  .append(i + 1)
                  .append("]);\n")
                  .append("        size.push([")
                  .append(results[i].getSize())
                  .append(", ")
                  .append(i + 1)
                  .append("]);\n")
                  .append("        yAxis.push([")
                  .append(i + 1)
                  .append(", '<a href=\"")
                  .append(results[i].getSite())
                  .append("\" class=\"engine\">")
                  .append(results[i].getName())
                  .append("</a>']);\n");
            //@formatter:on
        }

        //@formatter:off
        buffer.append("        bars(document.getElementById(\"tps\"), tps, yAxis);\n")
              .append("        bars(document.getElementById(\"time\"), time, yAxis);\n")
              .append("        bars(document.getElementById(\"size\"), size, yAxis);\n")
              .append("    })();\n")
              .append("</script>\n")
              .append("</body>\n")
              .append("</html>");
        //@formatter:on

        //copyFlotr2(directory);
        //copyFlotr2ie(directory);
        writeReport(directory, buffer.toString());
    }

//    private static void copyFlotr2(File dir) throws Exception {
//        copyJs(dir, "flotr2.min.js");
//    }
//
//    private static void copyFlotr2ie(File dir) throws Exception {
//        copyJs(dir, "flotr2.ie.min.js");
//    }

//    private static void copyJs(File dir, String js) throws Exception {
//        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
//        File file = createFile(dir, js);
//        InputStream inputStream = classLoader.getResourceAsStream("tpl/" + js);
//        OutputStream outputStream = null;
//        try {
//            byte[] b = new byte[1024];
//            outputStream = new FileOutputStream(file);
//            int len;
//            while ((len = inputStream.read(b, 0, b.length)) != -1) {
//                outputStream.write(b, 0, len);
//            }
//            outputStream.flush();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (outputStream != null) {
//                outputStream.close();
//            }
//            if (inputStream != null) inputStream.close();
//        }
//    }

    private static void writeReport(File dir, String html) throws Exception {
        File file = createFile(dir, "report.html");
        Writer writer = null;
        try {
            writer = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
            writer.write(html);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) writer.close();
        }
    }

    private static File createFile(File dir, String fileName) throws Exception {
        File file = new File(dir, fileName);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }
}
