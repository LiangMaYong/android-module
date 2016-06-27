package com.androidmodule.actionbar.iconfont;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * AMActionIconFontCreator<BR>
 * iconfont -> demo.html
 * 
 * @author LiangMaYong
 * @version 1.0
 */
public final class AMActionIconFontCreator {

	private AMActionIconFontCreator() {
	}

	public static void create(String readPath, String packageName, String className, String fontPath) {
		Map<String, String> values = readValue("./" + readPath);
		if (values.isEmpty()) {
			System.out.println("failure");
			return;
		}
		String content = "";
		content += getJavaHead(packageName, className);
		content += "\tprivate static final String FONT_PATH = \"" + fontPath + "\";\n";

		content += "\n\tpublic static void loadFont(Context context) {";
		content += "\n\t\tAMIconFont.loadFont(context, FONT_PATH);";
		content += "\n\t}\n\n";

		for (Map.Entry<String, String> entry : values.entrySet()) {
			content += "\tpublic static final AMIconValue " + entry.getKey() + " = new AMIconValue(FONT_PATH,"
					+ entry.getValue() + ");" + "\n";
		}
		content += getJavaFooter();
		System.out.println(content);
		writeFile("./" + className + ".java", content);
	}

	private static String getJavaHead(String packageName, String className) {
		String head = "package " + packageName + ";";
		head += "\n\nimport com.androidmodule.actionbar.iconfont.AMIconFont;";
		head += "\nimport com.androidmodule.actionbar.iconfont.AMIconValue;";
		head += "\n";
		head += "\nimport android.content.Context;";
		head += "\n\n/**\n* " + className + ",Using AMActionIconFontCreator generated";
		head += "\n*\n* @author LiangMaYong\n* @version 1.0 \n**/";
		head += "\npublic final class " + className + " {\n";
		head += "\n";
		head += "\n\tprivate " + className + " (){}\n\n";
		return head;
	}

	private static String getJavaFooter() {
		return "\n}";
	}

	private static Map<String, String> readValue(String readPath) {
		Map<String, String> values = new HashMap<String, String>();
		String html = readFile(readPath, "utf-8");
		Document doc = Jsoup.parse(html);
		Elements icons = doc.getElementsByTag("li");
		Document icondoc = null;
		for (Element link : icons) {
			icondoc = Jsoup.parse(link.html());
			String name = icondoc.getElementsByClass("fontclass").text();
			String code = icondoc.getElementsByClass("code").text().toLowerCase();
			String key = "icon_" + name.replaceAll(".icon", "").replaceAll("-", "_").replaceAll(" ", "").substring(1);
			String value = code.replaceAll("&#", "0").replaceAll(";", "");
			values.put(key, value);
		}
		return values;
	}

	private static void writeFile(String filename, String content) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File(filename)));
			writer.write(content);
			writer.close();
			System.out.println("path:" + filename);
			System.out.println("successful");
		} catch (Exception e) {
			System.out.println("failure");
		}
	}

	private static String readFile(String fileName, String charsetName) {
		File file = new File(fileName);
		BufferedReader reader = null;
		String content = "";
		try {
			InputStreamReader isr = new InputStreamReader(new FileInputStream(file), charsetName);
			reader = new BufferedReader(isr);
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				content += tempString;
			}
			reader.close();
		} catch (IOException e) {
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		return content;
	}
}
