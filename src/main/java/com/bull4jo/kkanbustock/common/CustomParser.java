package com.bull4jo.kkanbustock.common;

import com.bull4jo.kkanbustock.stock.domain.MarketType;
import com.bull4jo.kkanbustock.stock.domain.Stock;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomParser {

    public static Stock parseSingleJsonResponse(String jsonResponse) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode responseJson = objectMapper.readTree(jsonResponse);

        JsonNode item = responseJson
                .path("response")
                .path("body")
                .path("items")
                .path("item")
                .get(0);

        float fltRt = CustomParser.formatFltRt(item.get("fltRt").asText());
        Stock stock = Stock.builder()
                .srtnCd(item.get("srtnCd").asText())
                .itmsNm(item.get("itmsNm").asText())
                .mrktCtg(MarketType.valueOf(item.get("mrktCtg").asText()))
                .clpr(item.get("clpr").asInt())
                .mkp(item.get("mkp").asInt())
                .basDt(item.get("basDt").asInt())
                .vs(item.get("vs").asInt())
                .fltRt(fltRt)
                .trPrc(item.get("trPrc").asLong())
                .mrktTotAmt(item.get("mrktTotAmt").asLong())
                .build();

        return stock;
    }

    public static List<Stock> parseJsonResponses(String jsonResponse) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode responseJson = objectMapper.readTree(jsonResponse);
        JsonNode items = responseJson.path("response").path("body").path("items").path("item");

        List<Stock> list = new ArrayList<>();


        for (JsonNode itemNode : items) {
            float fltRt = CustomParser.formatFltRt(itemNode.get("fltRt").asText());

            Stock stock = Stock.builder()
                    .srtnCd(itemNode.get("srtnCd").asText())
                    .itmsNm(itemNode.get("itmsNm").asText())
                    .mrktCtg(MarketType.valueOf(itemNode.get("mrktCtg").asText()))
                    .clpr(itemNode.get("clpr").asInt())
                    .mkp(itemNode.get("mkp").asInt())
                    .basDt(itemNode.get("basDt").asInt())
                    .vs(itemNode.get("vs").asInt())
                    .fltRt(fltRt)
                    .trPrc(itemNode.get("trPrc").asLong())
                    .mrktTotAmt(itemNode.get("mrktTotAmt").asLong())
                    .build();

            list.add(stock);
        }

        return list;
    }

    public static float formatFltRt(String input) {
        try {
            // 문자열을 부동 소수점 숫자로 파싱
            double parsedValue = Double.parseDouble(input);

            // 소수점 이하 두 자리까지 포맷
            String formattedValue = String.format("%.2f", parsedValue);
            // 포맷된 문자열을 float로 파싱하여 반환
            return Float.parseFloat(formattedValue);
        } catch (NumberFormatException e) {
            return 0.0f;
        }
    }

}
