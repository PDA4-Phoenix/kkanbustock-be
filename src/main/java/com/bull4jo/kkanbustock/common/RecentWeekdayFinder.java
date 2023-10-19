package com.bull4jo.kkanbustock.common;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RecentWeekdayFinder {

    public static String findMostRecentWeekday() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String today = dateFormat.format(new Date());

        while (true) {
            calendar.add(Calendar.DAY_OF_MONTH, -1); // 하루씩 이전 날짜로 이동
            Date currentDate = calendar.getTime();
            String currentDateStr = dateFormat.format(currentDate);
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

            // 주말(토요일 또는 일요일)이 아니면 반환
            if (dayOfWeek != Calendar.SATURDAY && dayOfWeek != Calendar.SUNDAY) {
                return currentDateStr;
            }

            // 오늘과 같은 날짜면 루프 종료
            if (currentDateStr.equals(today)) {
                break;
            }
        }

        return null; // 가장 최근 평일을 찾을 수 없을 경우
    }
}

