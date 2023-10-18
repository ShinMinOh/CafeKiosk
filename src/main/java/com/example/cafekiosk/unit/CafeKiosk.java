package com.example.cafekiosk.unit;

import com.example.cafekiosk.unit.beverage.Beverage;
import com.example.cafekiosk.unit.order.Order;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class CafeKiosk {

    public static final LocalTime SHOP_OPEN_TIME = LocalTime.of(10,0);
    public static final LocalTime SHOP_CLOSE_TIME = LocalTime.of(22,0);

    private final List<Beverage> beverages = new ArrayList<>();

    // 단순 음료 1개 추가
    public void add(Beverage beverage) {
        beverages.add(beverage);
    }

    // 음료를 선택하고 수량을 입력
    public void add(Beverage beverage, int count) {
        if(count <= 0){
            throw  new IllegalArgumentException("음료는 1잔 이상 주문하실 수 있습니다.");
        }

        for (int i=0;i<count;i++){
            beverages.add(beverage);
        }
    }

    // 고른 음료 한가지 삭제
    public void remove(Beverage beverage){
        beverages.remove(beverage);
    }

    // 고른 음료 전부 삭제
    public void clear(){
        beverages.clear();
    }


    // 총 금액 계산
    public int calculateTotalPrice() {
        int totalPrice = 0;
        for (Beverage beverage : beverages) {
            totalPrice += beverage.getPrice();
        }
        return totalPrice;
    }

    // 주문생성 -> 10시~22시 사이에만 주문이 가능.
    // 단점 : 관측(현재 날짜/시간)할 때마다 다른 값에 의존해 테스트의 성공/실패 여부가 계속 바뀜.
    public Order createOrder(){
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalTime currentTime = currentDateTime.toLocalTime();  //currentDateTime 에서 시간만 뽑아와서 저장

        if(currentTime.isBefore(SHOP_OPEN_TIME) || currentTime.isAfter(SHOP_CLOSE_TIME)){
            throw new IllegalArgumentException("주문 시간이 아닙니다. 관리자에게 문의하세요");
        }
        return new Order(currentDateTime,beverages);
    }

    // 주문생성 -> 현재 시간(LocalDateTime.now())에 관계없이 시간 입력받아 로직이 돌아가는지 확인
    // 우리가 test 하고자 하는것은 LocalDateTime.now()가 아닌 시간이 주어졌을때 로직이 돌아가는지가 중요한 것이다.
    public Order createOrder(LocalDateTime currentDateTime){
        LocalTime currentTime = currentDateTime.toLocalTime();  //currentDateTime 에서 시간만 뽑아와서 저장

        if(currentTime.isBefore(SHOP_OPEN_TIME) || currentTime.isAfter(SHOP_CLOSE_TIME)){
            throw new IllegalArgumentException("주문 시간이 아닙니다. 관리자에게 문의하세요");
        }
        return new Order(currentDateTime,beverages);
    }
}
