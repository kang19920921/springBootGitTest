package cn.tedu.config;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;


@Configuration
@PropertySource("classpath:/properties/redis.properties")
public class RedisConfig{
	
	@Value("${redis.nodes}")
	private String nodes;
	
	@Bean
	public JedisCluster jedisCluster() {
		
		Set<HostAndPort> nodeSet = new HashSet<HostAndPort>();
		String [] arrayNodes = nodes.split(",");
		for (String node : arrayNodes) {
			String host = node.split(":")[0];
			int  port = Integer.parseInt(node.split(":")[1]);
			System.out.println("============="+host+port);
			nodeSet.add(new HostAndPort(host,port));
			
		}
		
		return new JedisCluster(nodeSet);		
	}
}
