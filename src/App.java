import java.util.Scanner;
import java.util.ArrayList;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class App {
    // Assign each newly created project to an array
    public static ArrayList<Project> ProjectList = new ArrayList<Project>();

    // Scanner Method for user's input
    private static Scanner input = new Scanner(System.in);

    // Global method to format date in an appropriate format
    private static DateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");

    // Method to display a menu from which the user selects
    public static String menuSelection() throws ParseException {
        System.out.print("\nWelcome to Poised!");
        System.out.print("\n1\t-\tAdd a new project");
        System.out.print("\n2\t-\tChange due date of a project");
        System.out.print("\n3\t-\tChange payment on project");
        System.out.print("\n4\t-\tUpdate contact details");
        System.out.print("\n5\t-\tFinalise a project");
        System.out.print("\nPlease make your selection: ");

        // User's selection determine which method should be called
        // If user's selection is '1', then user can create a new project
        int userSelection = input.nextInt();
        input.nextLine();
        if (userSelection == 1) {
            ProjectList.add(createProject());
        }

        // If user selection '2', then can amend the due date of the project
        else if (userSelection == 2) {
            Project selectedProj = getProject();
            if (selectedProj == null) {
                return menuSelection();
            }

            newDeadline(selectedProj);
        }

        // If user selects '3', then user can change the payment on the project
        else if (userSelection == 3) {
            Project selectedProj = getProject();
            if (selectedProj == null) {
                return menuSelection();
            }

            makePayment(selectedProj);
        }

        // If user selects '4', then user can update the details of either the
        // contractor, client or architect.
        else if (userSelection == 4) {
            Project selectedProj = getProject();
            if (selectedProj == null) {
                return menuSelection();
            }

            updateDetails(selectedProj);
        }

        // If user selects '5', then user can finalise the project
        else if (userSelection == 5) {
            Project selectedProj = getProject();
            if (selectedProj == null) {
                return menuSelection();
            }

            finalisation(selectedProj);
        }

        return menuSelection();
    }

    // An example project is created for the user to use in the program
    public static Project exampleProject() throws ParseException {
        Person willem = new Person("Contractor", "Willem du Plessis", "074 856 4561", "willem@dup.com",
                "452 Tugela Avenue");
        Person rene = new Person("Architect", "Rene Malan", "074 856 4561", "rene@malan", "452 Tugela Avenue");
        Person tish = new Person("Client", "Tish du Plessis", "074 856 4561", "tish@dup.com", "452 Tugela Avenue");
        Date deadline = dateformat.parse("30/03/2021");
        Project housePlessis = new Project(789, "House Plessis", "House", "102 Jasper Avenue", "1025", 20000, 5000,
                deadline, willem, rene, tish);
        ProjectList.add(housePlessis);

        return housePlessis;
    }

    // Method to get the project that the user wishes to work on. If there is no
    // project in the program an appropriate message will appear.
    public static Project getProject() {
        if (ProjectList.size() == 0) {
            System.out.print("No Projects were found.");
            return null;
        }

        System.out.print("\nPlease enter the project number: ");
        int projNum = input.nextInt();
        input.nextLine();

        for (Project proj : ProjectList) {
            if (proj.projNum == projNum) {
                return proj;
            }
        }

        System.out.print("\nProject was not found. Please try again.");
        return getProject();
    }

    // Method to create a project by requesting for user's input for each instance
    // in the Project class.
    public static Project createProject() throws ParseException {
        System.out.println("Please enter the project number: ");
        int projNum = input.nextInt();
        input.nextLine();

        System.out.println("Please enter the project name: ");
        String projName = input.nextLine();

        System.out.println("Please enter the type of building: ");
        String buildingType = input.nextLine();

        System.out.println("Please enter the physical address for the project: ");
        String address = input.nextLine();

        System.out.println("Please enter the ERF number: ");
        String erfNum = input.nextLine();

        System.out.println("Please enter the total fee for the project: ");
        int totalFee = input.nextInt();
        input.nextLine();

        System.out.println("Please enter the total amount paid to date: ");
        int amountPaid = input.nextInt();
        input.nextLine();

        System.out.println("Please enter the deadline for the project (dd/MM/yyyy): ");
        String sdeadline = input.nextLine();
        Date deadline = dateformat.parse(sdeadline);

        Person architect = createPerson("architect", input);
        Person contractor = createPerson("contractor", input);
        Person client = createPerson("client", input);

        // If the user did not enter a name for the project, then the program will
        // concatenate the building type and the client's surname as the new project
        // name.

        if (projName == "") {
            String[] clientname = client.name.split(" ");
            String lastname = clientname[clientname.length - 1];
            projName = buildingType + " " + lastname;
        }

        return new Project(projNum, projName, buildingType, address, erfNum, totalFee, amountPaid, deadline, contractor,
                architect, client);

    }

    // CreatePerson method let the user add the details of each person who works on
    // the project.
    private static Person createPerson(String type, Scanner input) {
        System.out.println("\nPlease enter the details of the " + type + ": ");
        System.out.println("\nName: ");
        String name = input.nextLine();

        System.out.println("\nContact number: ");
        String contactNo = input.nextLine();

        System.out.println("\nEmail address : ");
        String email = input.nextLine();

        System.out.println("\nAddress: ");
        String address = input.nextLine();

        return new Person(type, name, contactNo, email, address);

    }

    // Method to change the due date of the project, by requesting for user's input
    // an changing the input in the appropriate format.
    public static void newDeadline(Project proj) throws ParseException {
        System.out.print("Please enter the new deadline for the project (dd/MM/yyyy): ");
        String snewDeadline = input.nextLine();
        Date newDeadline = dateformat.parse(snewDeadline);
        proj.changeDeadline(newDeadline);
    }

    // User can add the payments made for the project to the amount paid by the
    // client.
    public static void makePayment(Project proj) {
        System.out.print("Add client's next payment: ");
        int newAmmount = input.nextInt();
        input.nextLine();
        proj.addAmount(newAmmount);
    }

    // Method to update a client, architect or contractor's details. User selects
    // the type of person he/she wishes to update and the program will prompt the
    // user for the new details.
    public static void updateDetails(Project proj) {
        System.out.print("Whose details do you want to update (contractor / architect / client): ");
        String type = input.nextLine();

        System.out.print("\nContact number: ");
        String contactNo = input.nextLine();

        System.out.print("\nEmail address : ");
        String email = input.nextLine();

        System.out.print("\nAddress: ");
        String address = input.nextLine();

        if (type == "architect") {
            proj.architect.updateDetails(contactNo, email, address);
        } else if (type == "contractor") {
            proj.contractor.updateDetails(contactNo, email, address);
        } else if (type == "client") {
            proj.client.updateDetails(contactNo, email, address);
        }
    }

    // To finalise a project, user needs to complete the date on which the project
    // has been finalised and an invoice will be generated
    public static void finalisation(Project proj) throws ParseException {
        if (proj.isFinalised) {
            System.out.println("Project already completed on " + dateformat.format(proj.completionDate));
            return;
        }

        System.out.print("Please enter the date of completion for the project (dd/MM/yyyy): ");
        String scomplete = input.nextLine();
        Date complete = dateformat.parse(scomplete);

        // If there is an outstanding amount on the project, an invoice will be
        // generated. If the project has been paid in full, the appropriate message will
        // display
        Invoice invoice = proj.finalise(complete);
        if (invoice != null) {
            System.out.print(invoice.toString());
        } else {
            System.out.print("Project complete and paid in full");
        }
    }

    public static void main(String[] args) throws ParseException {
        // User has the choice to either use the example project in program or to
        // generate a new project to navigate through the program.
        System.out.println("Would you like to use the example project? (y/n) ");
        String selection = input.nextLine();
        if (selection.equals("y")) {
            Project example = exampleProject();
            System.out.print(example.toString());
            menuSelection();
        } else {
            menuSelection();
        }
    }
}

// Reference:
// https://www.codegrepper.com/code-examples/java/taking+date+as+input+in+java
// To covert user's date input into readable format.