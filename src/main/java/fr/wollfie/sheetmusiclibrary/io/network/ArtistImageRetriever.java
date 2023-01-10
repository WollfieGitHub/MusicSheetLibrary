package fr.wollfie.sheetmusiclibrary.io.network;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.json.JsonMapper;
import fr.wollfie.sheetmusiclibrary.dto.Artist;
import fr.wollfie.sheetmusiclibrary.io.logging.Logger;
import javafx.scene.image.Image;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.controlsfx.control.cell.ColorGridCell;
import org.json.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;

public final class ArtistImageRetriever {
    
    private ArtistImageRetriever() {}
    
    private static final CloseableHttpClient client = HttpClients.createDefault();
    
    private static final String tokenRetrievalAddress = "https://open.spotify.com/get_access_token?reason=transport&productType=web_player";
    private static String imageRetrievalAddress(String artistName) {
        return String.format("https://api.spotify.com/v1/search?type=artist&q=%s&decorate_restrictions=false&best_match=true&include_external=audio&limit=1",
                artistName).replace(" ", "%20");
    }
    
    public static String fetchFor(Artist artist) {
        HttpGet tokenRetrievalReq = new HttpGet(tokenRetrievalAddress);
        
        String token = "";
        String responseString = "No response";
        try (CloseableHttpResponse response = client.execute(tokenRetrievalReq)) {
            int responseCode = response.getCode();
            if (responseCode != HttpURLConnection.HTTP_OK) { throw new IOException("Bad response from Spotify Server !"); }
            
            responseString = EntityUtils.toString(response.getEntity());
            token = new JSONObject(responseString).getString("accessToken");
        } catch (Exception e) {
            Logger.error(e);
            Logger.error(responseString);
        }
        
        HttpGet imageRetrievalReq = new HttpGet(imageRetrievalAddress(artist.getName()));
        imageRetrievalReq.addHeader("Authorization", "Bearer " + token);
        String imageAddress = null;
        responseString = "No response string found";
        try (CloseableHttpResponse response = client.execute(imageRetrievalReq)) {
            int responseCode = response.getCode();
            if (responseCode != HttpURLConnection.HTTP_OK) { throw new IOException("Bad response from Spotify Server !"); }
            responseString = EntityUtils.toString(response.getEntity());

            JSONObject jsonObject = new JSONObject(responseString);
            JSONObject bestMatch = jsonObject.getJSONObject("best_match");

            JSONArray items = bestMatch.getJSONArray("items");
            if (items.isEmpty()) { throw new ArtistNotFoundException("Empty items received from Server"); }

            JSONObject item = items.getJSONObject(0);
            JSONArray images = item.getJSONArray("images");
            if (images.isEmpty()) { throw new ArtistNotFoundException("Empty images received from Server"); }

            JSONObject imageObj = images.getJSONObject(1);
            imageAddress = imageObj.getString("url");
            Logger.info(imageObj);
        } catch (Exception e) {
            e.printStackTrace();
            Logger.error(responseString);
        }
        return imageAddress;
    }

}
