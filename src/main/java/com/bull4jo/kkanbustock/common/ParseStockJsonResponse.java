package com.bull4jo.kkanbustock.common;

import com.bull4jo.kkanbustock.stock.domain.MarketType;
import com.bull4jo.kkanbustock.stock.domain.Stock;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

public class ParseStockJsonResponse {

    public static Stock parseSingleResponse(String jsonResponse) {
        // Parse the JSON response
        JsonObject responseJson = JsonParser.parseString(jsonResponse).getAsJsonObject();
        JsonObject response = responseJson.getAsJsonObject("response");
        JsonObject body = response.getAsJsonObject("body");
        JsonObject items = body.getAsJsonObject("items");
        JsonObject item = items.getAsJsonArray("item").get(0).getAsJsonObject();

        Stock stock = Stock.builder()
                .srtnCd(item.get("srtnCd").getAsString())
                .itmsNm(item.get("itmsNm").getAsString())
                .mrktCtg(MarketType.valueOf(item.get("mrktCtg").getAsString()))
                .clpr(item.get("clpr").getAsInt())
                .mkp(item.get("mkp").getAsInt())
                .basDt(item.get("basDt").getAsInt())
                .vs(item.get("vs").getAsInt())
                .fltRt((item.get("fltRt")).getAsFloat())
                .trPrc(item.get("trPrc").getAsLong())
                .mrktTotAmt(item.get("mrktTotAmt").getAsNumber().longValue())
                .build();
        return stock;
    }

    public static List<Stock> parseResponses(String jsonResponse) {
        // Parse the JSON response
        JsonObject responseJson = JsonParser.parseString(jsonResponse).getAsJsonObject();
        JsonObject response = responseJson.getAsJsonObject("response");
        JsonObject body = response.getAsJsonObject("body");
        JsonObject items = body.getAsJsonObject("items");

        JsonArray item = items.getAsJsonArray("item");
        List<Stock> list = new ArrayList<>();

        for (int i = 0; i < item.size(); i++) {
            JsonObject asJsonObject = item.get(i).getAsJsonObject();

            Stock stock = Stock.builder()
                    .srtnCd(asJsonObject.get("srtnCd").getAsString())
                    .itmsNm(asJsonObject.get("itmsNm").getAsString())
                    .mrktCtg(MarketType.valueOf(asJsonObject.get("mrktCtg").getAsString()))
                    .clpr(asJsonObject.get("clpr").getAsInt())
                    .mkp(asJsonObject.get("mkp").getAsInt())
                    .basDt(asJsonObject.get("basDt").getAsInt())
                    .vs(asJsonObject.get("vs").getAsInt())
                    .fltRt(asJsonObject.get("fltRt").getAsFloat())
                    .trPrc(asJsonObject.get("trPrc").getAsLong())
                    .mrktTotAmt(asJsonObject.get("mrktTotAmt").getAsNumber().longValue())
                    .build();
        }

        return list;
    }
}
