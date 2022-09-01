package com.itvillage.chapter05.chapter0502;

import com.itvillage.utils.LogType;
import com.itvillage.utils.Logger;
import io.reactivex.Observable;

/**
 * 지정한 갯수만큼 데이터를 발행
 */
public class _6_ObservableTakeExample01 {
    public static void main(String[] args) {
        Observable.just("a", "b", "c", "d")
                .take(2) // 2개의 데이터만 가져가겠다
                .subscribe(data -> Logger.log(LogType.ON_NEXT, data));
    }
}
