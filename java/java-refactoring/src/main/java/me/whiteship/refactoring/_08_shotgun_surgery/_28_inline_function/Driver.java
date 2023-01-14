package me.whiteship.refactoring._08_shotgun_surgery._28_inline_function;

/**
 * 리팩토링 28. 함수 인라인
 * - 여러 함수를 인라인하여 커다란 함수를 만든 다음에 다시 함수 추출하기를 시도할 수 있다.
 * - 상속 구조에서 오버라이딩 하고있는 메서드는 인라인 할 수 없다.
 * - 간혹, 함수 본문이 함수 이름 만큼 또는 그보다 더 잘 의도를 표현하는 경우도 있다.
 * - 단순히 메서드 호출을 감싸는 우회형 메서드라면 (indirection) 인라인을 없앨 수 있다.
 */
public class Driver {

    private int numberOfLateDeliveries;

    public Driver(int numberOfLateDeliveries) {
        this.numberOfLateDeliveries = numberOfLateDeliveries;
    }

    public int getNumberOfLateDeliveries() {
        return this.numberOfLateDeliveries;
    }
}
