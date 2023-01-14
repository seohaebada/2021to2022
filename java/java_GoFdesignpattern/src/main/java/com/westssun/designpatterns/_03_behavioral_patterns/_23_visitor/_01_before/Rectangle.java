package com.westssun.designpatterns._03_behavioral_patterns._23_visitor._01_before;

public class Rectangle implements Shape {

    /**
     * 디바이스마다 다르게 출력하고싶다
     * @param device
     */
    @Override
    public void printTo(Device device) {
        if (device instanceof Phone) {
            System.out.println("print Rectangle to phone");
        } else if (device instanceof Watch) {
            System.out.println("print Rectangle to watch");
        }
    }
}
