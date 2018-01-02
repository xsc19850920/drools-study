package com.genpact.drools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.drools.RuleBase;
import org.drools.RuleBaseFactory;
import org.drools.StatefulSession;
import org.drools.builder.ResourceConfiguration;
import org.drools.builder.ResourceType;
import org.drools.compiler.PackageBuilder;
import org.drools.io.impl.UrlResource;
import org.drools.kproject.Path;
import org.drools.kproject.Resource;
import org.drools.rule.Package;
import org.drools.spi.Activation;
import org.drools.spi.AgendaFilter;

public class PointRuleEngineImpl implements PointRuleEngine {
	private RuleBase ruleBase;  
	/**
	 * 
	 */
	public void initEngine() {
		System.setProperty("drools.dateformat", "yyyy-MM-dd HH:mm:ss");
		if(null == ruleBase ){
			ruleBase =  RuleBaseFactory.newRuleBase();  
		}
		try {
			PackageBuilder backageBuilder = getPackageBuilderFromDrlFile();
			ruleBase.addPackages(backageBuilder.getPackages());  
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void refreshEnginRule() {
		if(null == ruleBase ){
			ruleBase =  RuleBaseFactory.newRuleBase();  
		}
		Package[] packages = ruleBase.getPackages();
		for (Package pg : packages) {
			ruleBase.removePackage(pg.getName());
		}
		initEngine();
	}

	public void executeRuleEngine(PointDomain pointDomain) {
		if(ruleBase.getPackages() == null || ruleBase.getPackages().length == 0){
			return ;
		}
		StatefulSession stateFulSession = ruleBase.newStatefulSession();
		stateFulSession.insert(pointDomain);
		//fire
		stateFulSession.fireAllRules(new AgendaFilter() {
			
			public boolean accept(Activation activation) {
				return !activation.getRule().getName().contains("test_");
			}
		});
		//清除
		stateFulSession.dispose();  
	}
	/** 
     * 从Drl规则文件中读取规则 
     * @return 
     * @throws Exception 
     */  
    private PackageBuilder getPackageBuilderFromDrlFile() throws Exception {  
        // 获取测试脚本文件  
        List<String> drlFilePath = getTestDrlFile();  
        // 装载测试脚本文件  
        List<Reader> readers = readRuleFromDrlFile(drlFilePath);  
  
        PackageBuilder backageBuilder = new PackageBuilder();  
        for (Reader r : readers) {  
        
            backageBuilder.addPackageFromDrl(r);  
        }  
          
        // 检查脚本是否有问题  
        if(backageBuilder.hasErrors()) {  
            throw new Exception(backageBuilder.getErrors().toString());  
        }  
          
        return backageBuilder;  
    }  
    
    /** 
     * @param drlFilePath 脚本文件路径 
     * @return 
     * @throws FileNotFoundException 
     */  
    private List<Reader> readRuleFromDrlFile(List<String> drlFilePath) throws FileNotFoundException {  
        if (null == drlFilePath || 0 == drlFilePath.size()) {  
            return null;  
        }  
        List<Reader> readers = new ArrayList<Reader>();  
        for (String ruleFilePath : drlFilePath) {  
            readers.add(new FileReader(new File(ruleFilePath)));  
        }  
        return readers;  
    }  
  
    /** 
     * 获取测试规则文件 
     *  
     * @return 
     */  
    private List<String> getTestDrlFile() {  
        List<String> drlFilePath = new ArrayList<String>();  
        
        drlFilePath.add(getResourcesPath("addpoint.drl"));
        drlFilePath.add(getResourcesPath("subpoint.drl"));
        return drlFilePath;  
    }  
    
    private String getResourcesPath(String fileName){
    	return Thread.currentThread().getContextClassLoader().getResource(fileName).getPath();
    }

}
