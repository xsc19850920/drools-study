package com.genpact.drools;

import org.junit.Test;


/**
 * Unit test for simple App.
 */
public class AppTest {
	/**
	 * 规则引擎
	 */
	@Test
	public void borthDayTest(){
		PointRuleEngine pointRuleEngine = new PointRuleEngineImpl();  
		System.out.println("初始化规则引擎开始...");  
        pointRuleEngine.initEngine();  
        System.out.println("初始化规则引擎结束...");  
        
        
        PointDomain pointDomain = new PointDomain();  
        pointDomain.setUserName("hello kity");  
        pointDomain.setBackMondy(100d);  
        pointDomain.setBuyMoney(500d);  
        pointDomain.setBackNums(1);  
        pointDomain.setBuyNums(5);  
        pointDomain.setBillThisMonth(5);  
        pointDomain.setBirthDay(true);  
        pointDomain.setPoint(0l);  
        pointRuleEngine.executeRuleEngine(pointDomain);  
        
        
        System.out.println("执行完毕BillThisMonth："+pointDomain.getBillThisMonth());  
        System.out.println("执行完毕BuyMoney："+pointDomain.getBuyMoney());  
        System.out.println("执行完毕BuyNums："+pointDomain.getBuyNums());  
        System.out.println("执行完毕规则引擎决定发送积分："+pointDomain.getPoint()); 
        
        
        System.out.println("刷新规则引擎开始..."); 
        pointRuleEngine.refreshEnginRule();
        System.out.println("刷新规则引擎结束...");  
        
        
        
	}
}
