package com.bull4jo.kkanbustock.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    /*
     * 400 BAD_REQUEST: 잘못된 요청
     */
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),

    /*
     * 403 Forbidden: 승인을 거부함
     */
    DAILY_QUIZ_FORBIDDEN(HttpStatus.FORBIDDEN, "오늘의 퀴즈를 이미 풀었습니다."),

    GROUP_APPLICATION_FORBIDDEN(HttpStatus.FORBIDDEN, "같은 깐부 신청이 존재합니다."),

    GROUP_FORBIDDEN(HttpStatus.FORBIDDEN, "이미 깐부 관계입니다."),

    /*
     * 404 NOT_FOUND: 리소스를 찾을 수 없음
     */
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "멤버 정보를 찾을 수 없습니다."),

    QUIZ_NOT_FOUND(HttpStatus.NOT_FOUND, "퀴즈 정보를 찾을 수 없습니다."),

    UNSOLVED_QUIZ_NOT_FOUND(HttpStatus.NOT_FOUND, "풀지 않은 퀴즈 정보를 찾을 수 없습니다."),

    SOLVED_QUIZ_NOT_FOUND(HttpStatus.NOT_FOUND, "푼 퀴즈 정보를 찾을 수 없습니다."),

    GROUP_NOT_FOUND(HttpStatus.NOT_FOUND, "깐부 정보를 찾을 수 없습니다."),

    MY_GROUP_NOT_FOUND(HttpStatus.NOT_FOUND, "내 깐부 정보를 찾을 수 없습니다"),
    TOP_GROUP_NOT_FOUND(HttpStatus.NOT_FOUND,"그룹을 찾을 수 없습니다."),

    GROUP_APPLICATION_NOT_FOUND(HttpStatus.NOT_FOUND, "받은 깐부 신청을 찾을 수 없습니다."),

    CANT_CALCULATE_TOTAL_PURCHASE_AMOUNT(HttpStatus.NOT_FOUND, "멤버 포트폴리오의 매수 금액을 계산 할 수 없습니다."),

    CANT_CALCULATE_EQUITIES_VALUE_AMOUNT(HttpStatus.NOT_FOUND, "멤버 포트폴리오의 평가금액을 계산 할 수 없습니다."),

    PORTFOLIO_NOT_FOUND(HttpStatus.NOT_FOUND, "포트폴리오를 찾을 수 없습니다."),

    STOCK_NOT_FOUND(HttpStatus.NOT_FOUND, "주식 정보를 찾을 수 없습니다."),

    RECOMMENDED_STOCK_NOT_FOUND(HttpStatus.NOT_FOUND, "추천 종목을 찾을 수 없습니다."),

    DICTIONARY_NOT_FOUND(HttpStatus.NOT_FOUND, "사전을 찾을 수 없습니다."),

    /*
     * 405 METHOD_NOT_ALLOWED: 허용되지 않은 Request Method 호출
     */
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "허용되지 않은 메서드입니다."),

    /*
     * 500 INTERNAL_SERVER_ERROR: 내부 서버 오류
     */
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "내부 서버 오류입니다."),

    ;

    private final HttpStatus status;
    private final String message;

}
