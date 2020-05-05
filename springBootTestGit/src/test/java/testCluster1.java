

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

public class testCluster1 {
		
	@Test
	public void testCluster() {		
		Set<HostAndPort> node = new HashSet<HostAndPort>();
		node.add(new HostAndPort("192.168.2.7", 7000));
		node.add(new HostAndPort("192.168.2.7", 7001));
		node.add(new HostAndPort("192.168.2.7", 7002));
		node.add(new HostAndPort("192.168.2.7", 7003));
		node.add(new HostAndPort("192.168.2.7", 7004));
		node.add(new HostAndPort("192.168.2.7", 7005));	
		JedisCluster cluster = new JedisCluster(node);
		cluster.set("1906", "Redis集群测试成功！！！");
		System.out.println(cluster.get("1906"));
	}
	
	

}
