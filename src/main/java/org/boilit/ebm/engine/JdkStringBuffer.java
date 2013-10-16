package org.boilit.ebm.engine;

import org.boilit.ebm.AbstractEngine;
import org.boilit.ebm.IEngine;
import org.boilit.ebm.StockModel;

import java.io.OutputStream;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author Boilit
 * @see
 */
public final class JdkStringBuffer extends AbstractEngine {

    @Override
    public String getName() {
        return "JdkStringBuffer";
    }

    @Override
    public String getVersion() {
        return System.getProperty("java.version");
    }

    @Override
    public String getSite() {
        return "#";
    }

    @Override
    public final void work(final Map<String, Object> model, final Writer writer) throws Exception {
        final String outputEncoding = (String) model.get("outputEncoding");
        final StringBuffer builder = new StringBuffer();
        builder.append("<!DOCTYPE html>\n").append(
                "<html>\n").append(
                "<head>\n").append(
                "    <title>StockModel - Bsl</title>\n").append(
                "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=").append(outputEncoding).append("\"/>\n").append(
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
        List<StockModel> items = (List<StockModel>)model.get("items");
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
        writer.write(builder.toString());
    }

    @Override
    public final boolean isSupportByteStream() {
        return false;
    }
}
