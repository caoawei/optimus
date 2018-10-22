package com.souche.elasticsearch;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.entity.BasicHttpEntity;
import org.elasticsearch.client.Node;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;

public class EClient {

    public static void main(String[] args) throws Exception{
        HttpHost httpHost = HttpHost.create("http://127.0.0.1:9200");
        RestClient restClient = RestClient.builder(new Node(httpHost)).build();

//        Map<String,Object> info = new LinkedHashMap<>();
//        Request request = new Request("post","/test/employ/1");
//        request.setJsonEntity(Utils.toJson(info));
//        restClient.performRequest(request);

//        queryWithString(restClient);

        queryWithExpress(restClient);

        queryWithTerm(restClient);


        restClient.close();
    }

    public static void queryWithString(RestClient restClient) throws Exception {
        Request request = new Request("get","/megacorp/employee/_search?q=last_name:Fir");
        Response response = restClient.performRequest(request);
        parseResponse(response);
    }

    public static void queryWithExpress(RestClient restClient) throws Exception {
        Request request = new Request("get","/megacorp/employee/_search");
        HttpEntity httpEntity = new BasicHttpEntity();
        byte[] data = "{\"query\":{\"match\":{\"last_name\":\"Smith\"}}}".getBytes(Charset.forName("utf-8"));
        ((BasicHttpEntity) httpEntity).setContentType("application/json");
        ((BasicHttpEntity) httpEntity).setContent(new ByteArrayInputStream(data));
        request.setEntity(httpEntity);
        Response response = restClient.performRequest(request);
        parseResponse(response);
    }

    public static void queryWithTerm(RestClient restClient) throws Exception {
        Request request = new Request("get","/megacorp/employee/_search");
        HttpEntity httpEntity = new BasicHttpEntity();
        byte[] data = "{\"query\":{\"term\":{\"last_name\":\"Smith\"}}}".getBytes(Charset.forName("utf-8"));
        ((BasicHttpEntity) httpEntity).setContentType("application/json");
        ((BasicHttpEntity) httpEntity).setContent(new ByteArrayInputStream(data));
        request.setEntity(httpEntity);
        Response response = restClient.performRequest(request);
        parseResponse(response);
    }

    public static void parseResponse(Response response) throws Exception {
        if(response != null) {
            StatusLine statusLine = response.getStatusLine();
            if(statusLine != null && statusLine.getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity httpEntity = response.getEntity();
                if(httpEntity != null) {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpEntity.getContent()));
                    StringBuilder rs = new StringBuilder();
                    String line = bufferedReader.readLine();
                    while (line != null) {
                        rs.append(line);
                        line = bufferedReader.readLine();
                    }


                    System.out.println("-----------------------------------------------------------------------------------------------");
                    System.out.println(rs);
                    System.out.println("-----------------------------------------------------------------------------------------------");
                }
            }
        }
    }
}