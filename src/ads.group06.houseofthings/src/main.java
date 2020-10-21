import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class main {
    private JButton button1;
    private JPanel panel1;

    public main() {
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //
                //ICommand command = meditor.getCommand(CreateActionCommand);
                //command.execute();

            }
        });
    }
}


//mediator pattern
/*
class CommandMediator{

    public ICommand getCommand(Enum commandType){
        switch (commandType){
            case CreateActionCommand:
                return new CreateActionCommandSomething()
                break;

                ....

        }

    }

}

//command
class CreateActionCommandSomething implements ICommand{
    execute(){
        System.out.print("hello");
    }
}
*/
