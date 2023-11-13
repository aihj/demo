package zerobase23.demo.service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import zerobase23.demo.domain.Web;
import zerobase23.demo.repository.WebRepository;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class WebService {

    @Value("${openwifi.key}")

    private String apiKey;

    private final WebRepository webRepository;

    public WebService(WebRepository webRepository){
        this.webRepository = webRepository;
    }

    public void createWeb(int i){
        //data 가져오기
        String wifiData = getString();

        //받아온 json 파싱하기
        Map<String, Object> parsedWifi = parseWifi(wifiData, i);

        //파싱된 데이터 우리 db에 저장하기
        Web nowweb = new Web();

        nowweb.setCOUNT(parsedWifi.get("COUNT").toString());
        nowweb.setMGR_NO(parsedWifi.get("관리번호").toString());
        nowweb.setWRDOFC(parsedWifi.get("자치구").toString());
        nowweb.setMAIN_NM(parsedWifi.get("와이파이명").toString());
        nowweb.setADRES1(parsedWifi.get("도로명주소").toString());
        nowweb.setADRES2(parsedWifi.get("상세주소").toString());
        nowweb.setINSTL_TY(parsedWifi.get("설치유형").toString());
        nowweb.setINSTL_MBY(parsedWifi.get("설치기관").toString());
        nowweb.setSVC_SE(parsedWifi.get("서비스구분").toString());
        nowweb.setCMCWR(parsedWifi.get("망종류").toString());
        nowweb.setCNSTC_YEAR(parsedWifi.get("설치년도").toString());
        nowweb.setINOUT_DOOR(parsedWifi.get("실내외구분").toString());
        nowweb.setLNT(parsedWifi.get("LNT").toString());
        nowweb.setLAT(parsedWifi.get("LAT").toString());
        webRepository.save(nowweb);
    }
    private String getString(){
        String apiUrl = "http://openapi.seoul.go.kr:8088/"+apiKey+ "/json/TbPublicWifiInfo/1/20/";


        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
            BufferedReader br;
            if(responseCode==200){
                br = new BufferedReader((new InputStreamReader(connection.getInputStream())));
            }else{
                br = new BufferedReader((new InputStreamReader(connection.getInputStream())));

            }
            String inputLine;
            StringBuilder response = new StringBuilder();
            while((inputLine = br.readLine())!=null){
                response.append(inputLine);

            }
            br.close();


            return response.toString();
        }catch (Exception e){
            return "failed to get response";
        }

    }

    private Map<String,Object> parseWifi(String jsonString, int i){
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject;

        try{
            jsonObject = (JSONObject )jsonParser.parse(jsonString);
        } catch(ParseException e){
            throw new RuntimeException(e);
        }

        Map<String, Object> resultMap = new HashMap<>();


        JSONObject wifiData = (JSONObject) jsonObject.get("TbPublicWifiInfo");

        JSONArray wifiArray = (JSONArray) wifiData.get("row");

        JSONObject wifiInfo = (JSONObject) wifiArray.get(i);

        resultMap.put("COUNT", wifiData.get("list_total_count"));
        resultMap.put("관리번호", wifiInfo.get("X_SWIFI_MGR_NO"));
        resultMap.put("자치구", wifiInfo.get("X_SWIFI_WRDOFC"));
        resultMap.put("와이파이명", wifiInfo.get("X_SWIFI_MAIN_NM"));
        resultMap.put("도로명주소", wifiInfo.get("X_SWIFI_ADRES1"));
        resultMap.put("상세주소", wifiInfo.get("X_SWIFI_ADRES2"));
        resultMap.put("설치유형", wifiInfo.get("X_SWIFI_INSTL_TY"));
        resultMap.put("설치기관", wifiInfo.get("X_SWIFI_INSTL_MBY"));
        resultMap.put("서비스구분", wifiInfo.get("X_SWIFI_SVC_SE"));
        resultMap.put("망종류", wifiInfo.get("X_SWIFI_CMCWR"));
        resultMap.put("설치년도", wifiInfo.get("X_SWIFI_CNSTC_YEAR"));
        resultMap.put("실내외구분", wifiInfo.get("X_SWIFI_INOUT_DOOR"));
        resultMap.put("LNT", wifiInfo.get("LNT"));
        resultMap.put("LAT", wifiInfo.get("LAT"));



        return resultMap;

    }

    public List<Web> webList() {


        return webRepository.findAll();
    }

}
