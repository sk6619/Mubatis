package com.reverseengineering;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;

import com.mysql.cj.protocol.Warning;

/**
 * 启动逆向工程
 * @author Administrator
 *
 */
public class ReverseGenerator {
	
	public static void generator() throws Exception {
		//指定配置文件
		File configFile = new File("src/com/reverseengineering/generatorConfig.xml");
		List<String> warnings = new ArrayList<>();
		ConfigurationParser cParser = new ConfigurationParser(warnings);
		Configuration configuration = cParser.parseConfiguration(configFile);
		DefaultShellCallback shellCallback = new DefaultShellCallback(true);
		//
		MyBatisGenerator generator = new MyBatisGenerator(configuration, shellCallback, warnings);
		generator.generate(null);
	}
	public static void main(String[] args) throws Exception {
		
		generator();
		
	}

}
