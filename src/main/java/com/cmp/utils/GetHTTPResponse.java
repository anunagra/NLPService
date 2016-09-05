package com.cmp.utils;


import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class GetHTTPResponse {

	public JSONObject getResponse(String taskID) throws ClientProtocolException, IOException {
		
		if(taskID.equalsIgnoreCase("local"))
		{
			return readJSON();
		}
		else
		{
			System.out.println("Exporting task: '" + taskID + "' from Baloo");
			
	    	HttpClient httpclient = new DefaultHttpClient();
	    	HttpGet httpget = new HttpGet("https://baloo.stg-prsn.com/scenarios/" + taskID);

	    	//set cookie
//	    	httpget.setHeader("Cookie", "connect.sid=s%3AguK5CdlgZ6FQSybfeeyDPMPuA_I8PrZA.NL2C48849uHthaJLG7tsoa8H8heZZap2A%2BBizleuwNU;SERVERID=s2");
	    	
	    	httpget.setHeader("Cookie", "connect.sid=s%3AzdhhoGoAgp7xhgK5Qc_Ye3LHZ9vGEmnb.cyflNHsJrN3SwWRec34RtDDq28O152F9GJE27k7ArrU;SERVERID=s1");
	    	
//	    	  httpget.setHeader("Cookie", "connect.sid=s%3AqTCERi9CpPisoH8vbV6SKLYCo-e246l0.xYvOgFkfei2saVy67id8hEsbE7ey2TdD0R%2Byj%2FI957U;;SERVERID=s1");
	    	
	    	HttpResponse response = httpclient.execute(httpget);    	
	    	HttpEntity entity = response.getEntity();

	    	if (entity != null) {
			    String fileContent = EntityUtils.toString(entity);
			    System.out.println(fileContent);
			    try {
			    	System.out.println("Export successful !!!\n\n");
					return ((JSONObject) new JSONParser().parse(fileContent));
				} catch (ParseException e) {
					System.out.println("Error in parsing exported baloo task json");
					e.printStackTrace();
				}
			    
			}
			return null;
		}
		
  }
	
	public JSONObject readJSON(){
		JSONReader jr = new JSONReader("src/main/resources/balootask.json");
		JSONObject jsonObj = jr.getJSONAsObject();
		return jsonObj;
	}

}