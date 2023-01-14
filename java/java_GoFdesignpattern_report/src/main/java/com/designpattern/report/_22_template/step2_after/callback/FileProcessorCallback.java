package com.designpattern.report._22_template.step2_after.callback;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * AbstractClass
 */
public abstract class FileProcessorCallback {

    private String path;
    public FileProcessorCallback(String path) {
        this.path = path;
    }

    public final int process(Operator operator) {
        try(BufferedReader reader = new BufferedReader(new FileReader(path))) {
            int result = 0;
            String line = null;
            while((line = reader.readLine()) != null) {
                /** 다른 부분 */
                result = operator.getResult(result, Integer.parseInt(line));
            }
            return result;
        } catch (IOException e) {
            throw new IllegalArgumentException(path + "에 해당하는 파일이 없습니다.", e);
        }
    }
}
