package me.whiteship.refactoring._02_duplicated_code._06_pull_up_method;

import java.io.IOException;

/**
 * 리팩토링 6. 메서드 올리기 (Pull up Method)
 * - 중복 코드는 당장은 잘 동작하더라도 미래에 버그를 만들어낼 빌미를 제공한다.
 * - 여러 하위 클래스에 동일한 코드가 있다면 손쉽게 이 방법을 적용할 수 있다. (상위 클래스로 올리기)
 * - 비슷하지만 일부 값만 다른 경우라면, "함수 매개변수화하기" 리팩토리을 적용한 이후에, 이 방법을 사용할 수 있다.
 * - 하위 클래스에 있는 코드가 사위 클래스가 아닌 하위 클래스 기능에 의존하고있다면 "필드 올리기"를 적용한 이후에
 *   이 방법을 적용할 수 있다.
 * - 두 메서드가 비슷한 절차를 따르고 있다면, "템플릿 메서드 패턴"을 고려할 수 있다.
 */
public class Dashboard {

    public static void main(String[] args) throws IOException {
        ReviewerDashboard reviewerDashboard = new ReviewerDashboard();
        reviewerDashboard.printReviewers();

        ParticipantDashboard participantDashboard = new ParticipantDashboard();
        participantDashboard.printParticipants(15);
    }
}
