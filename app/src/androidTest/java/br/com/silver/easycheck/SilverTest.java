package br.com.silver.easycheck;

import android.os.NetworkOnMainThreadException;
import android.test.suitebuilder.annotation.SmallTest;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by silver on 11/06/16.
 */
@SmallTest
public class SilverTest {

    public static final String BASE_URL = "http://ouirsvp.drivecomunicacao.com.br/scan/porta/recepcao";

    @Test
    public void testConnectServer(){
        String code = "00001400000024";
        String user = "isabel";
        String pass = "2e8d4fedbba9b86eefec01941de3627e20160611";

        OkHttpClient client = new OkHttpClient();
        client.setReadTimeout(5, TimeUnit.SECONDS);
        client.setConnectTimeout(10, TimeUnit.SECONDS);

        try{
            MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
            String jsonSend = "{\"inputLogin\":\""+user+"\",\"inputSenha\":\""+pass+"\",\"inputCodigo\":\""+code+"\"}";
            RequestBody body = RequestBody.create(mediaType, jsonSend);

            Request request = new Request.Builder()
                    .url(BASE_URL)
                    .post(body)
                    .build();

            Response response = client.newCall(request).execute();
            String jsonResp = response.body().string();

            assertThat(jsonResp, true);
            //assertThat(movies.length, is(not(lessThanOrEqualTo(0))));

        } catch (NetworkOnMainThreadException note){
            note.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
