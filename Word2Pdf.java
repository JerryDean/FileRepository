package com.stee.emer.util;

import java.io.File;
import java.util.regex.Pattern;

import org.artofsolving.jodconverter.OfficeDocumentConverter;
import org.artofsolving.jodconverter.office.DefaultOfficeManagerConfiguration;
import org.artofsolving.jodconverter.office.OfficeManager;

public class Word2Pdf {
	public static void word2pdf(String inputFilePath) {
		DefaultOfficeManagerConfiguration config = new DefaultOfficeManagerConfiguration();

		String officeHome = getOfficeHome();
		config.setOfficeHome(officeHome);

		OfficeManager officeManager = config.buildOfficeManager();
		officeManager.start();

		OfficeDocumentConverter converter = new OfficeDocumentConverter(officeManager);
		String outputFilePath = getOutputFilePath(inputFilePath);
		File inputFile = new File(inputFilePath);
		if (inputFile.exists()) {// 找不到源文件, 则返回
			File outputFile = new File(outputFilePath);
			if (!outputFile.getParentFile().exists()) { // 假如目标路径不存在, 则新建该路径
				outputFile.getParentFile().mkdirs();
			}
			converter.convert(inputFile, outputFile);
		}

		officeManager.stop();
	}

	public static String getOutputFilePath(String inputFilePath) {
		String outputFilePath = inputFilePath.replaceAll(".doc", ".pdf");
		return outputFilePath;
	}

	public static String getOfficeHome() {
		String osName = System.getProperty("os.name");
		if (Pattern.matches("Linux.*", osName)) {
			return "/opt/openoffice.org3";
		} else if (Pattern.matches("Windows.*", osName)) {
			return "C:/Program Files (x86)/OpenOffice 4";
		} else if (Pattern.matches("Mac.*", osName)) {
			return "/Application/OpenOffice.org.app/Contents";
		}
		return null;
	}
}