package hello.advanced;

import hello.advanced.trace.logtrace.FieldLogTrace;
import hello.advanced.trace.logtrace.LogTrace;
import hello.advanced.trace.logtrace.ThreadLocalLogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogTraceConfig {
    /**
     * 동시성 문제 발생
     * 싱글톤으로 등록된 스프링 빈
     * 객체의 인스턴스가 애플리케이션의 딱 1개이므로 여러 쓰레드가 동시에 접근하면 문제가 발생한다.
     * @return
     */
    @Bean
    public LogTrace logTrace() {
        // return new FieldLogTrace();
        /**
         * 이것만 바꾸면 구현이 다 바뀐다.
         * 구현된 코드 변경은 필요 없다.
         */
        return new ThreadLocalLogTrace();
    }
}
