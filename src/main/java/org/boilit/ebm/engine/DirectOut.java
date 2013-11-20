// Copyright (c) 2013, Webit Team. All Rights Reserved.
package org.boilit.ebm.engine;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.boilit.ebm.AbstractEngine;
import org.boilit.ebm.StockModel;

/**
 *
 * @author Zqq
 */
public class DirectOut extends AbstractEngine {

    String encoding;

    @Override
    public final void init(Properties properties) throws Exception {
        encoding = properties.getProperty("outputEncoding", "UTF-8");
    }

    @Override
    public final void work(final Map<String, Object> model, final Writer writer) throws Exception {
        final String outputEncoding = (String) model.get("outputEncoding");
        List<StockModel> items = (List<StockModel>) model.get("items");

        writer.write("<!DOCTYPE html>\n");
        writer.write("<html>\n");
        writer.write("<head>\n");
        writer.write("    <title>StockModel - JdkStringBuilder</title>\n");
        writer.write("    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=");
        writer.write(outputEncoding);
        writer.write("\"/>\n");
        writer.write("\n");
        writer.write("    <style type=\"text/css\">\n");
        writer.write("        body {\n");
        writer.write("            color: #333333;\n");
        writer.write("            line-height: 150%;\n");
        writer.write("        }\n");
        writer.write("\n");
        writer.write("        td {\n");
        writer.write("            text-align: center;\n");
        writer.write("        }\n");
        writer.write("\n");
        writer.write("        thead {\n");
        writer.write("            font-weight: bold;\n");
        writer.write("            background-color: #C8FBAF;\n");
        writer.write("        }\n");
        writer.write("\n");
        writer.write("        .odd {\n");
        writer.write("            background-color: #F3DEFB;\n");
        writer.write("        }\n");
        writer.write("\n");
        writer.write("        .even {\n");
        writer.write("            background-color: #EFFFF8;\n");
        writer.write("        }\n");
        writer.write("    </style>\n");
        writer.write("</head>\n");
        writer.write("<body>\n");
        writer.write("<h1>StockModel - Bsl</h1>\n");
        writer.write("<table>\n");
        writer.write("    <thead>\n");
        writer.write("    <tr>\n");
        writer.write("        <th>#</th>\n");
        writer.write("        <th>id</th>\n");
        writer.write("        <th>code</th>\n");
        writer.write("        <th>name</th>\n");
        writer.write("        <th>price</th>\n");
        writer.write("        <th>range</th>\n");
        writer.write("        <th>amount</th>\n");
        writer.write("        <th>gravity</th>\n");
        writer.write("    </tr>\n");
        writer.write("    </thead>\n");
        writer.write("    <tbody>\n");
        StockModel item;
        final int size = items.size();
        for (int i = 0; i < size; i++) {
            item = items.get(i);
            writer.write("    <tr class=\"");
            writer.write(i % 2 == 0 ? "odd" : "even");
            writer.write("\">\n");
            writer.write("        <td>");
            writer.write(Integer.toString(i));
            writer.write("</td>\n");
            writer.write("        <td>");
            writer.write(Integer.toString(item.getId()));
            writer.write("</td>\n");
            writer.write("        <td>");
            writer.write(item.getCode());
            writer.write("</td>\n");
            writer.write("        <td style=\"text-align: left;\">");
            writer.write(item.getName());
            writer.write("</td>\n");
            writer.write("        <td>");
            writer.write(Double.toString(item.getPrice()));
            writer.write("</td>\n");
            writer.write("        <td style=\"color: ");
            writer.write(item.getRange() >= 10 ? "red" : "blue");
            writer.write(";\">${item.range}%</td>\n");
            writer.write("        <td>");
            writer.write(item.getAmount());
            writer.write("</td>\n");
            if (item.getGravity() >= 20) {
                writer.write("        <td style=\"color: red;\">");
                writer.write(Double.toString(item.getGravity()));
                writer.write("%</td>\n");
            } else {
                writer.write("        <td style=\"color: blue;\">");
                writer.write(Double.toString(item.getGravity()));
                writer.write("%</td>\n");
            }
            writer.write("    </tr>\n");
        }
        writer.write("    </tbody>\n");
        writer.write("</table>\n");
        writer.write("</body>\n");
        writer.write("</html>");
    }

    @Override
    public void work(Map<String, Object> model, OutputStream outputStream) throws Exception {
        work(model, new OutputStreamWriter(outputStream, encoding));
    }
}
