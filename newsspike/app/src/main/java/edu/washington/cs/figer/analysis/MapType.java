package edu.washington.cs.figer.analysis;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;


import edu.washington.cs.figer.util.X;

public class MapType {
	public static String typeFile = "types.map";
	public static Hashtable<String, String> mapping = null;
	
	public static void init() {
		if (mapping == null) {
			if (X.get("tagset") != null) {
				typeFile = X.get("tagset");
			}
                        System.out.println("MapType: " + typeFile);
        try {
			Scanner scanner = new Scanner(new File(typeFile));
//			Scanner scanner = new Scanner(MapType.class.getResourceAsStream(typeFile));
			mapping = new Hashtable<String, String>();
			while(scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String arg = line.substring(0, line.indexOf("\t")), newType = line
						.substring(line.indexOf("\t") + 1).trim()
						.replace("\t", "/");
				mapping.put(arg, newType);
			}
			scanner.close();
        } catch (FileNotFoundException e) {
          e.printStackTrace();
        } catch (IOException e) {
          e.printStackTrace();
        }

		}
	}

	/**
	 * 
	 * @param types
	 * @return
	 */
	public static String getMappedTypes(String str) {
		init();
		StringBuilder sb = new StringBuilder();
		String[] types = str.split(",");
		HashSet<String> set = new HashSet<String>();
		for (String type : types) {
			if (mapping.containsKey(type)) {
				set.add(mapping.get(type));
			}
		}
		for (String s : set) {
			sb.append(s + ",");
		}
		if (sb.length() == 0) {
			return null;
		} else {
			return sb.substring(0, sb.length() - 1);
		}
	}
}
