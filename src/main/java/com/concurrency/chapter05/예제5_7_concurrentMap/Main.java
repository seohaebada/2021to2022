package com.concurrency.chapter05.예제5_7_concurrentMap;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 병렬 컬렉션은 여러 스레드에서 동시에 사용할 수 있도록 설계되어있다.
 * HashMap -> ConcurrentHashMap
 *
 * Queue, BlockingQueue, ConcurrentLinkedQueue
 * - BlockingQueue : 큐에 항목을 추가하거나 뽑아낼때 상황에 따라 대기할 수 있도록 구현되어있다.
 *                   예를들어 큐가 비어있다면 큐에서 항목을 뽑아내는 연산은 새로운 항목이 추가될때까지 대기한다.
 *                   반대로 큐에 크기가 지정되어있는 경우에 큐가 지정한 크기만큼 가득 차있다면, 큐에 새로운 항목을 추가하는 연산은
 *                   큐가 빌때까지 기다린다.
 *
 * - ConcurrentQueue
 * 동기화된 컬렉션 클래스는 각 연산을 수행하는 시간 동안 항상 앍을 확보하고 있어야한다.
 * 그런데 HashMap.get 메서드나 List.contains와 같은 몇가지 연산은 생각하는 것보다 훨씬 많은 양의 일을 해야할 수도 있다.
 * HashMap.get
 * 해시를 기반으로 하는 모든 컬렉션클래스는 담고있는 객체들의 hashCode 값이 적절히 넓고 고르게 분포되어 있찌 않다면,
 * 내부 해시 테이블에 한쪽이 치우친 상태로 저장된다.
 * 최악의 상황을 고려한다면 객체의 해시 값을 계싼해주는 hashCode 메서드가 잘못 만들어져 있는 경우 단순한 연결 리스트와
 * 거의 동일한 상태에 이를 수 있다.
 *
 * List.contains
 * List 클래스에 특정 객체가 포함되어 있는지를 찾아내는 연산 역시 최악의 상황에는 포함하고 있는 모든 객체를 대상으로 equals 메서드를 호출해봐야한다.
 * 물론 전체 항목에 대해 equals 메서드를 호출해 확인하는 동안에는 다른 스레드가 List 내부의 값을 사용할 수 없다.
 *
 * ConcurrentHashMap 은 HashMap 과 같이 해시를 기반으로 하는 Map이다.
 * 락 스트라이핑이라 부르는 굉장히 세밀한 동기화 기법을 사용한다.
 * 여러 스레드에서 공유하는 상태에 훨씬 잘 대응할 수 있고, 값을 읽어가는 연산은 많은 수의 스레드라도 얼마든지 동시에 처리할 수 있다.
 * 또한 읽기 연산과 쓰기 연산도 동시에 처리할 수 있으며,
 * 쓰기 연산은 제한된 개수만큼 동시에 수행할 수 있다.
 *
 * ConcurrentHashMap이 만들어낸 Iterator는 ConcurrentModificationException 을 발생시키지 않는다.
 * ConcurrentHashMap에서 만들어낸 ITerator는 즉시 멈춤 대신 미약한 일관성 전략을 취한다.
 * 미약한 일관성 전략 : 반복문과 동시에 컬렉션의 내용을 변경한다 해도 ITerator를 만들었던 시점의 상황대로 반복을 계속할 수 있다.
 * 게다가 Iterator을 만든 시점 이후에 변경된 내용을 반영해 동작할 수도 있따. (반드시 보장은 아니다)
 *
 * 병렬성 문제 때문에 Map의 모든 하위 클래스에서 공통적으로 사용하는 size 메서드나 isEmpty 메서드의 의미가 약한 약해졌다.
 * size 메서드는 그 결과를 리턴하는 시점에 이미 실제 객체의 수가 바뀌었을 수도 있어서 정확하지 않다.
 *
 * (단점)
 * Map을 독점적으ㅏ로 사용할 수 있도록 막아버리는 기능이 지원되지 않는다.
 * Map에 대한 락을 잡아 다른 스레드에서 사용하지 못하도록 막을 수 있다.
 * 이런 작업이 흔히 필요한 일은 아니지만, 단일 연산으로 여러개의 값을 Map에 넣고자 한다거나 Map의 내용을 여러번 반복시켜볼때
 * 반복되는 내용과 순서가 바뀌지 않아야한다는 등의 특별한 상황이라면 필요할 수 있다.
 *
 */
public class Main {
    public static void main(String[] args) {
//        ConcurrentHashMap
    }
}
