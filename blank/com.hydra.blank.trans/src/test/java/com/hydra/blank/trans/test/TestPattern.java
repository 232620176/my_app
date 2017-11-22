package com.hydra.blank.trans.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class TestPattern {
	@Test
	public void testInt(){
		String s = "304";
		String sp = "(.*)";
		Pattern p = Pattern.compile(sp);
		Matcher m = p.matcher(s);
		if(m.matches()){
			int z = m.groupCount();
			System.out.println(z);
			for(int i = 0; i <= z; i++){
				System.out.println(m.group(i));
			}
		}
	}
	
	@Test
	public void testAccessLog() {
		String str = "121.31.250.72 - - [01/Nov/2017:12:40:04 +0800] \"POST /pmobile/commonUpgradeInfo.do HTTP/1.1\" 200 129 \"-\" 10.1.22.37:8084 0.021 0.031 \"Lakala IOS Client\" \"-\" \"693\"";
		String pattern = "([^ ]*) ([^ ]*) ([^ ]*) (-|\\[.*\\]) ([^ \\\"]*|\\\"[^\\\"]*\\\") (-|[0-9]*) (-|[0-9]*) ([^ \\\"]*|\\\"[^\\\"]*\\\") ([^ ]*) ([^ ]*) ([^ ]*)(?: ([^ \\\"]*|\\\".*\\\") ([^ \\\"]*|\\\"[^\\\"]*\\\") ([^ \\\"]*|\\\".*\\\"))?";
		Pattern pat = Pattern.compile(pattern);
		Matcher mat = pat.matcher(str);
		boolean match = mat.matches();
		System.out.println(match);
		if(match){
			int size = mat.groupCount();
			for(int i = 1; i <= size; i++){
				System.out.println(mat.group(i));
			}
		}
	}
}
