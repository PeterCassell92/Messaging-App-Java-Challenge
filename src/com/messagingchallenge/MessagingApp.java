package com.messagingchallenge;

import com.optionbehaviour.ActionOption;
import com.optionbehaviour.OptionSelector;

import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;

/** The class responsible for providing a UI to the user for examining/interacting with a patient
 */
public class MessagingApp {

    final private String[] stages = { "Main", "Contact", "Messages" };

    private Stack<String> stageStack = new Stack<>();

    Contact[] contacts = { new Contact("Jeff", 1), new Contact("Rodger", 2)};

    private int currentStageIndex = 0;

    private OptionSelector optionSelector;


    public int getCurrentStageIndex() {
        return currentStageIndex;
    }

    public void setCurrentStageIndex(int currentStageIndex) {
        this.currentStageIndex = currentStageIndex;
    }

    public OptionSelector getOptionSelector() {
        return optionSelector;
    }

    public void setOptionSelector(OptionSelector optionSelector) {
        this.optionSelector = optionSelector;
    }

    public MessagingApp() {
        Scanner userInput = new Scanner(System.in);
        this.optionSelector = new OptionSelector(userInput, null);
        stageStack.push("Main");
    }

    private ContactService contactService;

    private MessageService messageService;

    public void nextStage(String nextStage){
        stageStack.push(nextStage);
    }

    public void previousStage(){
        if (!stageStack.isEmpty()) {
            stageStack.pop();
        }
        if (stageStack.isEmpty()) {
            // Exit condition or revert to a default stage if necessary
            stageStack.push("Main"); // Example to revert back to Main
        }
    }

    public String getCurrentStage() {
        return stageStack.peek();
    }

    public ActionOption[] getConversationMenuOptions(){

        ActionOption[] actionOptions = Arrays.stream(this.contacts)
                .map( contact -> new ActionOption(contact.getName(), () -> {
                    //TODO fill out
                    this.nextStage("Conversation");
                } ))
                .toArray(ActionOption[]::new);
        return actionOptions;
    }

    public void chooseFromManageContactsMenu(){
        System.out.println("Manage Contacts - Choose an Option:");

        ActionOption[] menuListOptions = {
                new ActionOption("Show All Contacts", () -> {
                    //TODO - fill functionality
                    System.out.println("Show All Contacts");
                }),
                new ActionOption("Add New Contact", () -> {
                    //TODO - fill functionality
                    System.out.println("Add New Contact");
                    Scanner input = new Scanner(System.in);
                    String contactName = input.next();
                    this.contactService.addNewContact(contactName);

                }),
                new ActionOption("Search for a contact", () -> {
                    //TODO
                })
                new ActionOption("Delete a contact", () -> {
                    //TODO
                })
                new ActionOption("Quit", () -> {
                    this.previousStage();
                })
        };

        //Set the main menu list to the option selector
        this.optionSelector.setOptions(menuListOptions);
        this.optionSelector.promptSelectAndRunSelectedAction();
    }

    public void chooseFromMainMenu(){
        System.out.println("Main Menu - Choose an Option:");

        ActionOption[] menuListOptions = {
                new ActionOption("Manage Contacts", () -> {
                    //TODO - fill functionality
                    System.out.println("Manage Contacts");
                    this.nextStage("ManageContacts");
                }),
                new ActionOption("Messages", () -> {
                    //TODO - fill functionality
                    System.out.println("Go to Messages");
                    this.nextStage("Messages");
                }),
                new ActionOption("Quit", () -> {
                    this.previousStage();
                })
        };

        //Set the main menu list to the option selector
        this.optionSelector.setOptions(menuListOptions);
        this.optionSelector.promptSelectAndRunSelectedAction();
    }

    public void chooseFromConversationList(){
        System.out.println("Choose a Contact:");

        ActionOption[] conversationMenuOptions = this.getConversationMenuOptions();

        ActionOption[] allListOptions = new ActionOption[conversationMenuOptions.length + 1];
        // Copy organListOptions to allListOptions
        System.arraycopy(conversationMenuOptions, 0, allListOptions, 0, conversationMenuOptions.length);

        // Add the "Back" option to allListOptions
        allListOptions[conversationMenuOptions.length] = new ActionOption("Back", this::previousStage);

        //Set the actions list to the option selector
        this.optionSelector.setOptions(allListOptions);
        this.optionSelector.promptSelectAndRunSelectedAction();

    }


    public void run() {
        while (!stageStack.isEmpty()) {
            this.performStage(this.getCurrentStage());
        }
        System.out.println("DONE");
    }

    public void performStage(String currentStage){
        // Logic to determine which method to call based on currentStage
        switch (currentStage) {
            case "Main":
                this.chooseFromMainMenu();
                break;
            case "ManageContacts":
                this.chooseFromManageContactsMenu();
                break;
            case "Conversations":
                //TODO - show all conversations, make selectable by user name, order by most recent
                //this.chooseFromManageContactsMenu();
                break;
            // Add more cases as needed
            default:
                System.out.println("Invalid stage");
                previousStage(); // Fallback or error handling
        }
    }
}
