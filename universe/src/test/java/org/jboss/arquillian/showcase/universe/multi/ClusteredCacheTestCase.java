/*
 * JBoss, Home of Professional Open Source
 * Copyright 2011 Red Hat Inc. and/or its affiliates and other contributors
 * as indicated by the @authors tag. All rights reserved.
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,  
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.arquillian.showcase.universe.multi;

import java.net.URL;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.OperateOnDeployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.container.test.api.TargetsContainer;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.showcase.universe.Deployments;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * 3 node Infinispan cluster Demo.
 * 
 * Deploy 3 replicated caches to 3 different Containers and switch between "in container" and "as client" run mode.
 * 
 * @author <a href="mailto:aslak@redhat.com">Aslak Knutsen</a>
 * @version $Revision: $
 */
@RunWith(Arquillian.class) @Ignore
public class ClusteredCacheTestCase
{
   @Deployment(name = "dep.active-1", order = 1)
   @TargetsContainer("container.active-1")
   public static WebArchive createTestDeployment()
   {
      return Deployments.Client.rest();
   }

   @Deployment(name = "dep.active-2", order = 2)
   @TargetsContainer("container.active-2")
   public static WebArchive createTestDeployment2()
   {
       return Deployments.Client.rest();
   }

   @Deployment(name = "dep.active-3", order = 3)
   @TargetsContainer("container.active-3")
   public static WebArchive createTestDeployment3()
   {
       return Deployments.Client.rest();
   }

//   @Inject
//   private Cache<String, Integer> cache;

   @Test @InSequence(1) @OperateOnDeployment("dep.active-1")
   public void callActive1() throws Exception
   {
      int count = 0;//incrementCache(cache);
      System.out.println("Cache incremented, current count: " + count);
      Assert.assertEquals(1, count);
   }

   @Test @InSequence(2) @OperateOnDeployment("dep.active-2")
   public void callActive2() throws Exception
   {
      int count = 1; //incrementCache(cache);
      System.out.println("Cache incremented, current count: " + count);
      Assert.assertEquals(2, count);
   }

   @Test @InSequence(3) @OperateOnDeployment("dep.active-3")
   public void callActive3() throws Exception
   {
      int count = 2; //incrementCache(cache);
      System.out.println("Cache incremented, current count: " + count);
      Assert.assertEquals(3, count);
   }

   @Test @InSequence(4) @OperateOnDeployment("dep.active-1")
   public void callActive4() throws Exception
   {
      int count = 3; //incrementCache(cache);
      System.out.println("Cache incremented, current count: " + count);
      Assert.assertEquals(4, count);
   }

   @Test @InSequence(5) @OperateOnDeployment("dep.active-3")
   public void callActive5() throws Exception
   {
      int count = 4; //incrementCache(cache);
      System.out.println("Cache incremented, current count: " + count);
      Assert.assertEquals(5, count);
   }

   @Test @RunAsClient @InSequence(6) @OperateOnDeployment("dep.active-2")
   public void callActive6(@ArquillianResource URL baseURL) throws Exception
   {
      int count = 2; //readInt(baseURL.openStream());
      System.out.println("Cache incremented, current count: " + count);
      Assert.assertEquals(6, count);
   }
}
