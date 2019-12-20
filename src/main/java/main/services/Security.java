package main.services;

import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.charset.Charset;


public class Security {

    long shopId = 0;
    String error;

    public boolean authEmployee(String tocken, long employeeId) {


        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        RestTemplate restTemplate  = new RestTemplate();

        String uri = "http://zcor.ru:8101/shop/session";

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri)
                .queryParam("employeeId", employeeId)
                .queryParam("tocken",tocken);

        restTemplate.getMessageConverters()
                .add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
        System.out.println(builder.toUriString());

        try{
            HttpEntity<?> httpEntity  = new HttpEntity<>(httpHeaders);
        ResponseEntity<String> result = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, httpEntity, String.class);

        JSONObject resJ = new JSONObject(result.getBody());

        shopId = resJ.getLong("shop_id");
            return true;
        }catch (HttpStatusCodeException ex){
            ex.printStackTrace();
            return false;
        }
    }

    public long getShopId() {
        return shopId;
    }

    public void setShopId(long shopId) {
        this.shopId = shopId;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
