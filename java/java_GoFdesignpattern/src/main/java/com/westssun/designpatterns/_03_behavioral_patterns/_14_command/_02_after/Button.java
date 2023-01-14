package com.westssun.designpatterns._03_behavioral_patterns._14_command._02_after;

import com.westssun.designpatterns._03_behavioral_patterns._14_command._01_before.Game;
import com.westssun.designpatterns._03_behavioral_patterns._14_command._01_before.Light;

import java.util.Stack;

/**
 * [장점]
 * - 커맨드를 다양하게 활용할 수 있다.
 * - 다양한 방법으로 Command 객체 활용
 * [단점]
 * - Command 파일이 늘어남
 * - 코드가 복잡해짐
 */
public class Button {

    private Stack<Command> commands = new Stack<>();

    public void press(Command command) {
        command.execute();
        commands.push(command);
    }

    public void undo() {
        if (!commands.isEmpty()) {
            Command command = commands.pop();
            command.undo();
        }
    }

    public static void main(String[] args) {
        Button button = new Button();
        button.press(new GameStartCommand(new Game()));
        button.press(new LightOnCommand(new Light()));
        button.undo();
        button.undo();
    }

}
