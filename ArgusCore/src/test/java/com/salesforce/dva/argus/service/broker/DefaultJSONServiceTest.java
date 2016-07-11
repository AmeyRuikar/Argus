package com.salesforce.dva.argus.service.broker;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.salesforce.dva.argus.service.tsdb.MetricQuery;
import com.salesforce.dva.argus.service.tsdb.MetricQuery.Aggregator;
import com.salesforce.dva.argus.system.SystemConfiguration;

@RunWith(MockitoJUnitRunner.class)
public class DefaultJSONServiceTest {
	private SystemConfiguration configuration;
	@Before
	public void setup(){
		configuration=new SystemConfiguration(new Properties());
		configuration.setProperty("service.property.json.endpoint", "https://arguspm.ops.sfdc.net:443/argusws");
		configuration.setProperty("service.property.json.username", "***REMOVED***");
		configuration.setProperty("service.property.json.password", "***REMOVED***");
	}
	
	@Test
	public void test() {
		DefaultJSONService s=new DefaultJSONService(this.configuration);
		Map<String, String> tags=new HashMap<String, String>();
		tags.put("podId", "*");
		MetricQuery query = new MetricQuery("p90_sandbox", "podperc90", tags, Long.valueOf(1458765618), Long.valueOf(1467405619));
		query.setAggregator(Aggregator.AVG);
		List<MetricQuery> list=new ArrayList<MetricQuery>();
		list.add(query);
		System.out.println(s.getMetrics(list));
	}

	@Test
	public void test2() {
		//-4d:argus.custom:warden.datapoints_per_hour{user=***REMOVED***,host=*}:avg
		DefaultJSONService s=new DefaultJSONService(this.configuration);
		Map<String, String> tags=new HashMap<String, String>();
		tags.put("user", "***REMOVED***");
		tags.put("host", "*");
		MetricQuery query = new MetricQuery("argus.custom", "warden.datapoints_per_hour", tags, Long.valueOf(1458765618), Long.valueOf(1467405619));
		query.setAggregator(Aggregator.SUM);
		List<MetricQuery> list=new ArrayList<MetricQuery>();
		list.add(query);
		System.out.println(s.getMetrics(list));
	}
}
