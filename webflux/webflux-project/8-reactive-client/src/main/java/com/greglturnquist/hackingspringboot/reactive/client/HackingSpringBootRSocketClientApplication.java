package com.greglturnquist.hackingspringboot.reactive.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 HTTP
 - 리액티브 하지 않다.
 - "하나만 더 보내줘.", "남은 것 모두 다 보내줘"와 같은 메시지를 표현할 수도 이해할 수도 없다.
 - HTTP는 요청-응답 패러다임에 뿌리를 두고 있다.

 HTTP를 통해 단순한 요청-응답을 넘어서 터널로 서로 연결하는 방법을 고민해왔고, 그 첫번째 해답은
 클라이언트가 서버에게 요청을 보낸 후에 즉각적인 대답을 기대하지 않고, 오래 기다리더라도 언제든 서버가 데이터를 보낼
 준비가 됐을때 서버로부터 응답을 받으면 응답을 처리하고 바로 새로운 요청을 서버에게 보내서 또 오래 기다리는 식으로
 연결 지속성을 확보하는 롱 폴링이다.

 롱 폴링은 코멧(comet)이라고 부르기도 한다.
 이 방식은 자원을 점유한다는 한계가 있었고, 이는 웹소켓(WebSocket) 의 등장을 앞당겼다.

 웹소켓도 OSI 7계층에 위치하며 2011년에 표준화된 최신 프로토콜이다.
 웹소켓은 요청-응답 방식의 HTTP 완느 다르게 양방향이다.
 웹소켓은 가볍고 양방향 비동기 통신을 지원하지만 배압 개념이 없으므로 리액티브하지 않다.

 API가 비동기 방식이라고 해서 전체 과정이 리액티브한 것은 아니다.
 그래서 진정한 해결책이 나오려면 새 프로토콜이 필요하며, 리액티브 스트림을 근간으로 하는 새로운 프로토콜, 바로 R소켓이다.

 [R소켓]
 R소켓은 HTTP, 웹소켓과 마찬가지로 OSI 7계층 프로토콜이다. VM웨어를 비롯한 여러 회사가 설립한 리액티브 재단에서 공동으로 만들었다.
 리액티브 프로토콜을 만들려면 고려해야할점은?
 - R소켓은 웹소켓, TCP, Aeron 등 여러가지 프로토콜 위에서 동작하도록 설계됐다. 웹소켓에 대해서는 이미 짧게 언급했는데, 정리하자면 웹소켓은
 아주 가볍고 유연해서 R소켓이 필요로하는 모든것을 지원한다.
 TCP는 OSI 4 계층에 위치하는 강력한 프로토콜이다. HTTP는 TCP의 연결 관리를 사용해서 TCP위에서 동작한다.
 R소켓도 TCP를 사용해서 장애내성(fault-tolerant)과 확장성을 가진 리액티브 연결을 만들 수 있다.

 애런은 UDP 위에서 동작하는 메시징 프로토콜이다. UDP는 신뢰성 있는 연결을 필요로 하지 않는 프로토콜이다.
 리액터 애플리케이션은 작업 부하 사이클 오가는 워커 스레드를 사용하므로, 작업 부하가 여러가지 메시지로부터 만들어진다는 사실은 어렵지 않게 유추할 수 있다.

 [R소켓 패러다임]
 - 소켓은 연결을 맺고, 데이터를 송수신하는데 신뢰성이 입증된 방식이다.
 - R소켓은 단순히 연결에 사용되는 채널에 다른 API를 추가한 것이라고 이해할 수 있다.

 1) 요청-응답 (request-response) : 1개의 스트림

 2) 요청-스트림 (request-stream) : 다수의 유한한 스트림
 - 한번의 요청을 보내고 스트림 형태로 응답을 계속 받을 수 있다.
 - 롱 폴링이나 코멧은 응답을 받을때마다 처리를 하고 응답을 받은 후에 다시 요청을 보내는 일을 반복해야한다.
 이렇게 비슷한 처리 작업을 위해 요청-응답을 반복하는 것은 많은 오버헤드를 유발한다.
 응답을 기다리면서 스레드가 점유되므로 트래픽이 많은 상황에서는 지연이 발생하는 주요 원인이 되기도 한다.
 - 채널을 열고 요청을 보낸 후에 스레드를 점유하지 않고 스트림 형태로 응답을 받을 수 있다.

 3) 실행후 망각(fire-and-forget) : 무응답
 - 요청을 보내고나서 응답은 신경쓰지 않는 뒤끝 없는 방식이다.
 - 비동기 전송 방식으로 요청-응답을 주고받아본 적이 있다면, 응답은 원래의 요청과 연관돼야 한다는 점을 알고 있을 것이다.
 그래서 연관 ID (correlation ID)를 사용하기도 하는데, 이 과정에서 여러가지 복잡성이 생겨난다.
 리액티브 스트림에서는 어떤것도 스레드를 점유해서는 안된다.

 4) 채널 (channel) : 양방향
 - 세가지 패러다임에서는 요청을 보내는 클라이언트와 요청을 처리하는 서버가 등장한다.
 클라이언트와 서버는 다음과 같은 세가지 선택지를 가지고있다.
 1) 응답 대기
 2) 응답 대기 안함
 3) 무한 응답 대기

 어느쪽이든 요청을 보내는 것은 클라이언트라는 사실에는 변함이 없다.
 채널 패러다임은 이런 틀을 깨고 진정한 메시지 지향(message-driven) 양방향 통신 채널을 실현한다.
 채널의 어느쪽이든 상대방에게 메시지를 전송할 수 있고, 양쪽 모두 리액티브 메시지 리스너를 반드시 등록해야한다.

 */
@SpringBootApplication
public class HackingSpringBootRSocketClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(HackingSpringBootRSocketClientApplication.class, args);
	}
}
