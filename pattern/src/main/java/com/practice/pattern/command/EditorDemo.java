package com.practice.pattern.command;

import java.util.Stack;

public class EditorDemo {

    public static void main(String[] args) {
        Command cmd = new CopyCommand();
        cmd.execute();

        cmd = new PasteCommand();
        cmd.execute();

        cmd = new InsertCommand();
        cmd.execute();

        cmd = new DeleteCommand();
        cmd.execute();
        cmd.execute();

        //回滚逻辑
        cmd = new UndoCommand();
        cmd.execute();
        cmd.execute();
        cmd.execute();
        cmd.execute();
        cmd.execute();

        //前进
        cmd.undo();
        cmd.undo();
        cmd.undo();
        cmd.undo();
        cmd.undo();

    }
}

//命令接口
interface Command {

    //真正执行命令的接口
    void execute();

    //命令回滚接口，具体的实现细节需要每个动作去实现
    void undo();
}

//真正处理的对象
class TextEditorReceiver {

    volatile static TextEditorReceiver editorReceiver;

    public static TextEditorReceiver getInstance() {

        if (null == editorReceiver) {
            synchronized (TextEditorReceiver.class) {
                if (null == editorReceiver) {
                    editorReceiver = new TextEditorReceiver();
                }
            }
        }
        return editorReceiver;
    }

    private TextEditorReceiver() {
    }

    public void copy() {
        System.out.println("复制");
    }

    public void paste() {
        System.out.println("粘贴");
    }

    public void insert() {
        System.out.println("插入");
    }

    public void delete() {
        System.out.println("删除");
    }
}

//存储历史
class History {
    static final Stack<Command> history = new Stack<>();

    static final Stack<Command> forward = new Stack<>();

    public static void put(Command cmd) {
        history.push(cmd);
    }

    public static Command forward() {
        if (forward.isEmpty()) {
            System.out.println("没有前进了。。");
            return null;
        }
        Command pop = forward.pop();
        return pop;
    }

    public static Command pop() {
        if (history.isEmpty()) {
            System.out.println("没有历史了。。");
            return null;
        }
        Command pop = history.pop();
        forward.push(pop);
        return pop;
    }

}


class CopyCommand implements Command {
    TextEditorReceiver editorReceiver;

    public CopyCommand() {
        this.editorReceiver = TextEditorReceiver.getInstance();
    }

    @Override
    public void execute() {
        editorReceiver.copy();
        History.put(this);
    }

    @Override
    public void undo() {
        System.out.println("回滚：" + this.getClass());
    }
}

class PasteCommand implements Command {
    TextEditorReceiver editorReceiver;

    public PasteCommand() {
        this.editorReceiver = TextEditorReceiver.getInstance();
    }

    @Override
    public void execute() {
        editorReceiver.paste();
        History.put(this);
    }

    @Override
    public void undo() {
        System.out.println("回滚：" + this.getClass());
    }
}

class InsertCommand implements Command {
    TextEditorReceiver editorReceiver;

    public InsertCommand() {
        this.editorReceiver = TextEditorReceiver.getInstance();
    }

    @Override
    public void execute() {
        editorReceiver.insert();
        History.put(this);
    }

    @Override
    public void undo() {
        System.out.println("回滚：" + this.getClass());
    }
}

class DeleteCommand implements Command {
    TextEditorReceiver editorReceiver;

    public DeleteCommand() {
        this.editorReceiver = TextEditorReceiver.getInstance();
    }

    @Override
    public void execute() {
        editorReceiver.delete();
        History.put(this);
    }

    @Override
    public void undo() {
        System.out.println("回滚：" + this.getClass());
    }
}

class UndoCommand implements Command {
    TextEditorReceiver editorReceiver;

    public UndoCommand() {
        this.editorReceiver = TextEditorReceiver.getInstance();
    }

    @Override
    public void execute() {
        //后退
        Command cmd = History.pop();
        if (null != cmd) {
            cmd.undo();
        }
    }

    @Override
    public void undo() {
        //前进
        Command cmd = History.forward();
        if (null != cmd) {
            cmd.execute();
        }
    }
}
