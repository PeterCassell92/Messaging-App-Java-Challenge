package com.messagingchallenge;

import com.optionbehaviour.ActionOption;
import com.optionbehaviour.OptionSelector;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

/**
 * The class responsible for providing a UI to the user for managing contacts and viewing messages
 */
public class MessagingApp {

    private Stack<String> stageStack = new Stack<>();

    private OptionSelector optionSelector;

    private Boolean finish = false;

    public Boolean getFinish() {
        return finish;
    }

    public void setFinish(Boolean finish) {
        this.finish = finish;
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

    private final ContactService contactService = new ContactService();

    private final MessageService messageService = new MessageService();

    private Contact currentConversationContact;

    public Contact getCurrentConversationContact() {
        return currentConversationContact;
    }

    public void setCurrentConversationContact(Contact currentConversationContact) {
        this.currentConversationContact = currentConversationContact;
    }

    public Stack<String> getStageStack() {
        return stageStack;
    }

    public ContactService getContactService() {
        return contactService;
    }

    public MessageService getMessageService() {
        return messageService;
    }



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



    public ActionOption[] getConversationsMenuOptions(){
        List<ConversationPreview> conversations =  this.contactService.getConversations();

        ActionOption[] actionOptions = conversations.stream()
                .map( conversation -> new ActionOption(conversation.getContact().getName(), () -> {
                    this.setCurrentConversationContact(conversation.getContact());
                    this.nextStage("ConversationView");
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
                    System.out.println("Add New Contact");
                    Scanner input = new Scanner(System.in);
                    String contactName = input.next();
                    this.contactService.addNewContact(contactName, "0739289290");

                }),
                new ActionOption("Search for a contact", () -> {
                    //TODO
                }),
                new ActionOption("Delete a contact", () -> {
                    //TODO
                }),
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
                    this.nextStage("Conversations");
                }),
                new ActionOption("Quit", () -> {
                    this.setFinish(true);
                })
        };

        //Set the main menu list to the option selector
        this.optionSelector.setOptions(menuListOptions);
        this.optionSelector.promptSelectAndRunSelectedAction();
    }

    public void chooseFromConversationsMenu(){
        System.out.println("Choose a Contact:");

        ActionOption[] conversationMenuOptions = this.getConversationsMenuOptions();

        ActionOption[] allListOptions = new ActionOption[conversationMenuOptions.length + 1];

        System.arraycopy(conversationMenuOptions, 0, allListOptions, 0, conversationMenuOptions.length);

        // Add the "Back" option to allListOptions
        allListOptions[conversationMenuOptions.length] = new ActionOption("Back", this::previousStage);

        //Set the actions list to the option selector
        this.optionSelector.setOptions(allListOptions);
        this.optionSelector.promptSelectAndRunSelectedAction();
    }

    public void chooseFromConversationViewMenu(){
        ActionOption[] menuListOptions = {
                new ActionOption("Compose Message", () -> {
                    //TODO - fill functionality for sending message
                    //new scanner
                    //use messageServiceAPI
                    //messageService uses DBservice
                    //UI is updated with the new message in the chat
                    this.chooseFromConversationViewMenu();
                }),
                new ActionOption("Back", this::previousStage)
        };

        //Set the main menu list to the option selector
        this.optionSelector.setOptions(menuListOptions);
        this.optionSelector.promptSelectAndRunSelectedAction();
    }

    public void displayFormattedConversation(Contact contact){
        List<Message> messages = this.getMessageService().getMessagesByContactId(contact.getContact_id());

        //for this simple app we use contact id = 1 for this user
        int userContactId = 1;

        // ANSI escape codes for color formatting
        final String ANSI_GREEN = "\u001B[32m";  // Green for "Me"
        final String ANSI_WHITE = "\u001B[37m";  // White for "Robert"
        final String ANSI_RESET = "\u001B[0m";  // Reset color to default

        int maxNameLength = Math.max("You".length(), contact.getName().length());

        for (Message message : messages) {
            String formattedName;
            if (message.getSenderContactId() == userContactId) {
                // Message sent by "Me"
                formattedName = "You";
            } else {
                // Message sent by "Robert"
                formattedName = contact.getName();
            }
            // Pad the name string with spaces to a fixed width
            formattedName = String.format("%-" + (maxNameLength - 1) + "s", formattedName);

            String formattedMessage;
            if (message.getSenderContactId() == userContactId) {
                formattedMessage = String.format("[%s] %s: %s%s%s",
                        message.getSent(),
                        formattedName,
                        ANSI_GREEN,
                        message.getContent(),
                        ANSI_RESET);
            } else {
                formattedMessage = String.format("[%s] %s%s : %s%s",
                        message.getSent(),
                        formattedName,
                        ANSI_WHITE,
                        message.getContent(),
                        ANSI_RESET);
            }
            System.out.println(formattedMessage);
        }
    }


    public void run() {
        while (!(this.getFinish() || this.stageStack.isEmpty())) {
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
                this.chooseFromConversationsMenu();
                break;
            case "ConversationView":
                //TODO: show conversation view and show a menu for options
                this.displayFormattedConversation(this.getCurrentConversationContact());
                this.chooseFromConversationViewMenu();
                break;
            default:
                System.out.println("Invalid stage");
                previousStage(); // Fallback or error handling
        }
    }
}
