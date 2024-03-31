package com.optionbehaviour;
import com.optionbehaviour.ActionOption;
import com.optionbehaviour.IOption;

import java.util.Scanner;

public class OptionSelector {
    private Scanner userInput;

    private ActionOption[] options;

    public ActionOption[] getOptions() {
        return options;
    }

    public void setOptions(ActionOption[] options) {
        this.options = options;
    }

    public OptionSelector(Scanner userInput, ActionOption[] options) {
        this.userInput = userInput;
        this.options = options;
    }
    
    public void listOptions() {
        for (int x = 0; x < this.options.length; x++) {
            ActionOption option = this.options[x];
            //The options are not zero indexed
            System.out.printf("%s. %s%n", x + 1, option.getDisplayString());
        };
    }

    private int promptValidEntry(){
        System.out.println("Please enter a number greater than 0 and less than " + options.length);
        return this.getUserSelection(false);
    }

    /**
     *
     * @param displayOptionsList
     * @return int the index (zeroed) of the selected action.
     */
    public int getUserSelection(boolean displayOptionsList){
        if (displayOptionsList) {
            listOptions();
        }

        int optionSelection = userInput.nextInt();
        int zeroIndexedSelection = optionSelection - 1;

        if (optionSelection <= 0 || zeroIndexedSelection >= options.length) {
            return promptValidEntry();
        } else {
            return zeroIndexedSelection;
        }
    }

    public void promptSelectAndRunSelectedAction(){
        int optionIndex = this.getUserSelection(true);
        this.options[optionIndex].performAction();
    }
}
