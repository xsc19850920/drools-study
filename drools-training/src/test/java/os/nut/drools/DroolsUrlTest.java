package os.nut.drools;

import genpact.drltest.User;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.drools.core.io.impl.UrlResource;
import org.junit.Before;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.builder.KieModule;
import org.kie.api.builder.KieRepository;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class DroolsUrlTest {
	private KieSession kieSession;
	
	
	
	@Test
	public void test() throws IOException {
//		demo1();
//		demo2();
		demo3();
	}
	
	@Before
	public void before(){
		
	}

	private void demo3() {
		String path = "http://192.168.80.128:8080/drools/maven2/genpact/drltest/1.0/drltest-1.0.jar";
		try {
			init(path);
			User user = new User();
			user.setNew(false);
			user.setName("xsc");
			kieSession.insert(user);
			kieSession.fireAllRules();

			System.out.println(user.toString());

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void demo2() {

		String path = "http://192.168.80.128:8080/drools/maven2/com/drools/myDrools/1.0/myDrools-1.0.jar";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("reimbursementType", "Domestic");
		map.put("employeeCode", "710004893");
		map.put("sapCode", "496320");
		map.put("totalAmount", 20000);
		Map<String, Integer> returnMap = new HashMap<String, Integer>();
		try {
			init(path);
			kieSession.setGlobal("returnMap", returnMap);
			kieSession.insert(map);
			kieSession.fireAllRules();
			for (Map.Entry<String, Integer> m : returnMap.entrySet()) {
				System.out.println("==== key : " + m.getKey() + ",value : " + m.getValue() + "  ====");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void demo1() {

		String path = "http://192.168.80.128:8080/drools/maven2/genpact/drltest/1.0/drltest-1.0.jar";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("reimbursementType", "Domestic");
		map.put("employeeCode", "710004893");
		map.put("sapCode", "496320");
		map.put("totalAmount", 20000);
		List<Integer> list = new ArrayList<Integer>();

		try {
			init(path);
			kieSession.setGlobal("list", list);
			kieSession.insert(map);
			kieSession.fireAllRules();
			for (Integer i : list) {
				System.out.println(i);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void init(String path) throws IOException {
		String userName = "admin";
		String password = "admin";

		KieServices ks = KieServices.Factory.get();
		UrlResource urlResource = (UrlResource) ks.getResources().newUrlResource(path);
		urlResource.setUsername(userName);
		urlResource.setPassword(password);
		urlResource.setBasicAuthentication("enabled");

		KieRepository kr = ks.getRepository();
		InputStream is = urlResource.getInputStream();
		KieModule kModule = kr.addKieModule(ks.getResources().newInputStreamResource(is));
		KieContainer kn = ks.newKieContainer(kModule.getReleaseId());
		kieSession = kn.newKieSession();
		if (is != null) {
			is.close();
		}

	}

}
