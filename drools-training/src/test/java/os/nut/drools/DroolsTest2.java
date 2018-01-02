package os.nut.drools;

import org.drools.core.io.impl.UrlResource;
import org.junit.Test;
import org.kie.api.io.ResourceType;
import org.kie.internal.KnowledgeBase;
import org.kie.internal.KnowledgeBaseFactory;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderError;
import org.kie.internal.builder.KnowledgeBuilderErrors;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.runtime.StatefulKnowledgeSession;

public class DroolsTest2 {
private static KnowledgeBase readKnowledgeBase(String path,String userName,String password) throws Exception {
        
        // String path = "http://58.2.221.119:8080/drools-guvnor/org.drools.guvnor.Guvnor/package/Drools/LATEST";
         KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
         
         //修改此方法
         //kbuilder.add(ResourceFactory.newClassPathResource("Sample.drl"), ResourceType.DRL);
         UrlResource urlResource = (UrlResource)ResourceFactory.newUrlResource(path);
         urlResource.setBasicAuthentication("enabled");
         urlResource.setUsername(userName);
         urlResource.setPassword(password);
       // kbuilder.add(ResourceFactory.newUrlResource(path), ResourceType.PKG);
         kbuilder.add(urlResource, ResourceType.PKG);
         KnowledgeBuilderErrors errors = kbuilder.getErrors();
         if (errors.size() > 0) {
          for (KnowledgeBuilderError error: errors) {
           System.err.println(error);
          }
          throw new IllegalArgumentException("Could not parse knowledge.");
         }
          KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
         kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
         //采用Agent的方式
       /*  KnowledgeAgent kagent=KnowledgeAgentFactory.newKnowledgeAgent("/deploy.properties");
         
         KnowledgeBase kbase=kagent.getKnowledgeBase();*/
         
         return kbase;
       }
       
        
        public void runDecisionTable(String path,String userName,String password,String droolsPackage,String droolsFunction){
           try {
                   // load up the knowledge base
                   KnowledgeBase kbase = readKnowledgeBase(path,userName,password);
                   StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
                  // KnowledgeRuntimeLogger logger = KnowledgeRuntimeLoggerFactory.newFileLogger(ksession, "Country");
                   // go !
                   //now create some test data
//                   org.drools.examples.decisiontable.Driver driver = new org.drools.examples.decisiontable.Driver();
//                   org.drools.examples.decisiontable.Policy policy = new org.drools.examples.decisiontable.Policy();
//
//                   ksession.insert(driver);
//                   ksession.insert(policy);

                   ksession.fireAllRules();

//                   System.out.println("BASE PRICE IS: " + policy.getBasePrice());
//                   System.out.println("DISCOUNT IS: " + policy.getDiscountPercent());
                   
        
                  // list = (List)ksession.getGlobal("myGlobalList");
                   //System.out.println("==============list=========="+list);
                   ksession.dispose();
                //  logger.close();
                  } catch (Throwable t) {
                   t.printStackTrace();
                  }
       }

@Test
  public void testMap() throws Exception {
       String path = "http://58.2.221.119:8080/drools-guvnor/org.drools.guvnor.Guvnor/package/test/LATEST";
                    //"http://58.2.221.119:8080/drools-guvnor/org.drools.guvnor.Guvnor/package/twsDrools/LATEST";
       String userName = "admin";
       String password = "admin";
       String droolsPackage = "Drools";
       String droolsFunction = "Country";
       runDecisionTable(path,userName,password,droolsPackage,droolsFunction);
       
  }
}
