/**
 * @author nntshembho mhlongo
 * This is a class to create and manage projects for a structural engineering firm
 */

import java.sql.Date;
import java.time.format.DateTimeFormatter;
import java.util.*;

import java.util.Scanner;
import java.time.LocalDate;
import java.sql.*;


public class PoisedProjectmain {


    static List<PoisedProjectClass> projectObjs = new ArrayList<PoisedProjectClass>();
    // ARRAY LIST TO CONTAIN PROJECT OBJECTS READ FROM THE UNFINALISED DATA FILE

    static int userProj;
    // CONTAINS PROJECT NUMBER THAT USER WANTS TO EDIT


    public static void main(String[] args) throws SQLException {
        Connection connection;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/PoisePMS?useSSL=false", "otheruser", "TheTriadSIXsectsixthsatanofSin666");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        ////KICK START FUNCTION//////////
        PoisedProjectClass project = projectdetails();


        Scanner menuInput = new Scanner(System.in);

        try {
            while (true) {
                System.out.println("""
                        would you like to try to add another project(if you made an error while inputing a project)? type ap
                                                
                        !!! NOTE TO PICK THE FOLLOWING OPTION A PROJECT SHOULD HAVE BEEN CREATED FIRST!!!
                                                
                        would you like to change the due date of a project?,type pdd
                                                
                        would you like to change the amount paid to date? type apd
                                                
                        would you like to update people detals? type ud
                                                
                        would you like to view uncompleted projects? type VUC
                                                
                        would you like to view all overdue projects? type vod
                                                
                        would you like to finalize a project? type  FP
                        
                        would you like to view a specific project? type SP
                                                
                        !! Note to pick the following option,you have to be satisified with all updates to the project!!
                                                
                        would you like to write the projects to the file? type WP
                                                
                                                
                        type exit to exit the programm

                        """);

                String projectMenu = menuInput.nextLine();
                if (projectMenu.equalsIgnoreCase("ap")) {
                    PoisedProjectClass reproject = projectdetails();


                }

                if (projectMenu.equalsIgnoreCase("exit")) {
                    break;

                }//break statement


//////////////////////////////////////////////////////UPDATE SPECIFIC - PROCEDURES//////////////////////////////////////
                else if (projectMenu.equalsIgnoreCase("pdd")) {

                    try {
                        Scanner projectDueDate = new Scanner(System.in);
                        ProjectChanges();

                        System.out.println("please enter the project number you want to edit from the projects displayed next?");
                        System.out.println(projectObjs);

                        int projNumb = Integer.parseInt(projectDueDate.nextLine());

                        String dateQuery = "UPDATE project SET DUE_DATE = ?  WHERE Project_numb = ? ";
                        PreparedStatement preparedStmt = connection.prepareStatement(dateQuery);

                        System.out.println("please enter the new date(yyyy-MM-DD)");

                        String userEdit = projectDueDate.nextLine();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                        LocalDate editDate = LocalDate.parse(userEdit, formatter);

                        preparedStmt.setDate(1, Date.valueOf(editDate));
                        preparedStmt.setInt(2, projNumb);
                        preparedStmt.executeUpdate();
                        System.out.println("date edited SUCCESSFULLY");


                        connection.close();


                    } catch (NumberFormatException e) {
                        System.out.println("something went wrong, maybe please input the right project number: ");
                    } catch (Exception e) {
                        System.out.println("you have selected a project that doesnt exist. Try again");
                    }


                } else if (projectMenu.equalsIgnoreCase("apd")) {
                    try {
                        Scanner amountPaidToDate = new Scanner(System.in);

                        System.out.println("please enter the project number you want to edit from the projects displayed next?");
                        System.out.println(projectObjs);


                        int projNumb = Integer.parseInt(amountPaidToDate.nextLine());
                        System.out.println("please enter the new amount paid to date");
                        double newmount = Double.parseDouble(amountPaidToDate.nextLine());


                        String dateQuery = "UPDATE project SET project_Amount_Paid = ?  WHERE Project_numb = ? ";
                        PreparedStatement preparedStmt = connection.prepareStatement(dateQuery);

                        preparedStmt.setDouble(1, newmount);
                        preparedStmt.setInt(2, projNumb);
                        preparedStmt.executeUpdate();

                        System.out.println("Amount paid to date edited SUCCESSFULLY");
                        ProjectChanges();

                        connection.close();


                    } catch (NumberFormatException e) {
                        System.out.println("something went wrong, maybe please input the right project number: ");
                    } catch (Exception e) {
                        System.out.println("you have selected a project that doesnt exist. Try again");
                    }

//////////////////////////////////////////////////////UPDATE SPECIFIC - PROCEDURES//////////////////////////////////////


                } else if (projectMenu.equalsIgnoreCase("vuc")) {
                    //○ Find all projects that still need to be completed from the database.
                    uncompleteProjectFinder();


                } else if (projectMenu.equalsIgnoreCase("vod")) {
                    //Find all projects that are past the due date from the database.
                    OverdueProjectFinder();


//////////////////////////////////////////////////////UPDATE SPECIFIC - PROCEDURES//////////////////////////////////////
                } else if (projectMenu.equalsIgnoreCase("ud")) {
                    try {
                        Scanner ud = new Scanner(System.in);

                        while (true) {

                            System.out.println("""
                                    please enter which detail you would like to update,
                                    n = name 
                                    t = telephone
                                    e = email 
                                    a = address
                                    d = done """);

                            String userEdit = ud.nextLine();

                            if (userEdit.equalsIgnoreCase("d")) {
                                break;
                            }// break statement

                            else if (userEdit.equalsIgnoreCase("n")) {


                                System.out.println("""
                                            please enter which person you would like to update
                                            architect - A
                                            customer - C
                                            contractor - CT
                                            exit - E
                                        """);


                                String personChoice = ud.nextLine();

                                if (personChoice.equalsIgnoreCase("A")) {
                                    connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/PoisePMS?useSSL=false", "otheruser", "TheTriadSIXsectsixthsatanofSin666");
                                    System.out.println("please look at the available projects to enuser u enter the correct details ");
                                    ProjectChanges();
                                    System.out.println(projectObjs);

                                    System.out.println("please Insert The Name of the Architect You Would Lie to Edit:");
                                    String currentArch = ud.nextLine();

                                    System.out.println("please enter the new name of the architect");
                                    String NewArch = ud.nextLine();
                                    try {
                                        // SQL update statement
                                        String closeForeignChecks = "SET FOREIGN_KEY_CHECKS=0 ";

                                        String openForeignChecks = "SET FOREIGN_KEY_CHECKS= 1 ";




                                        // SQL update statement for project table
                                        String updateProject = "UPDATE project SET Architect_name = ?\n " +
                                                "WHERE project.Architect_name = ?";


                                        // Using a PreparedStatement to change the details for the chosen record/ id.
                                        PreparedStatement preparedStmt = connection.prepareStatement(updateProject);
                                        PreparedStatement DisableFkcheck = connection.prepareStatement(closeForeignChecks);


                                        DisableFkcheck.executeUpdate();

                                        preparedStmt.setString(1, NewArch);
                                        preparedStmt.setString(2, currentArch);

                                        preparedStmt.executeUpdate();
                                        PreparedStatement EnableFkcheck = connection.prepareStatement(openForeignChecks);


                                        // Using a PreparedStatement to change the details for the chosen record/ id.
                                        EnableFkcheck.executeUpdate();

                                        connection.close();








                                    } catch (SQLException e) {

                                        // Error message
                                        e.printStackTrace();
                                    }



                                } else if (personChoice.equalsIgnoreCase("c")) {
                                    System.out.println("please enter the project number you want to edit from the projects below?");
                                    System.out.println(projectObjs);
                                    int projNumb = Integer.parseInt(ud.nextLine());


                                    System.out.println("please enter the new name of the customer");

                                    String newcustomerName = ud.nextLine();

                                    for (PoisedProjectClass num : projectObjs) {
                                        projectObjs.get(projNumb - 1).getCustomer().setName(newcustomerName);

                                        System.out.println("the new customer of this project" + "\n");

                                        System.out.println(projectObjs.get(projNumb - 1));


                                    }


                                } else if (personChoice.equalsIgnoreCase("ct")) {
                                    System.out.println("please enter the project number you want to edit from the projects below?");
                                    System.out.println(projectObjs);
                                    int projNumb = Integer.parseInt(ud.nextLine());


                                    System.out.println("please enter the new name of the contract name");

                                    String newcontractorName = ud.nextLine();
                                    for (PoisedProjectClass num : projectObjs) {

                                        projectObjs.get(projNumb - 1).getCustomer().setName(newcontractorName);

                                        System.out.println("the new contractor of this project" + "\n");

                                        System.out.println(projectObjs.get(projNumb - 1));

                                    }


                                } else if (personChoice.equalsIgnoreCase("e")) {
                                    break;

                                } else {
                                    System.out.println("thats not a valid person/option try again");
                                    break;
                                }


                            } else if (userEdit.equalsIgnoreCase("t")) {


                                System.out.println("""
                                            please enter which person you would like to update
                                            architect - A
                                            customer - C
                                            contractor - CT
                                            exit - E
                                        """);


                                String personChoice = ud.nextLine();

                                if (personChoice.equalsIgnoreCase("A")) {
                                    System.out.println("please enter the project number you want to edit from the projects below?");
                                    System.out.println(projectObjs);
                                    int projNumb = Integer.parseInt(ud.nextLine());

                                    System.out.println("please enter the new telephone of the architect");

                                    long newArchitecttele = Long.parseLong(ud.nextLine());

                                    for (PoisedProjectClass num : projectObjs) {

                                        projectObjs.get(projNumb - 1).getArchitect().setTelenum(newArchitecttele);

                                        System.out.println("the new architect telephone of this project" + "\n");

                                        System.out.println(projectObjs.get(projNumb - 1));

                                    }


                                } else if (personChoice.equalsIgnoreCase("c")) {
                                    System.out.println("please enter the project number you want to edit from the projects below?");
                                    System.out.println(projectObjs);
                                    int projNumb = Integer.parseInt(ud.nextLine());

                                    System.out.println("please enter the new telephone of the customer");

                                    long newcustTele = Long.parseLong(ud.nextLine());
                                    for (PoisedProjectClass num : projectObjs) {

                                        projectObjs.get(projNumb - 1).getCustomer().setTelenum(newcustTele);

                                        System.out.println("the new customer telephone of this project" + "\n");

                                        System.out.println(projectObjs.get(projNumb - 1));


                                    }

                                } else if (personChoice.equalsIgnoreCase("ct")) {
                                    System.out.println("please enter the project number you want to edit from the projects below?");
                                    System.out.println(projectObjs);
                                    int projNumb = Integer.parseInt(ud.nextLine());


                                    System.out.println("please enter the new telephone of the contract name");

                                    long newcontractorTele = Long.parseLong(ud.nextLine());

                                    for (PoisedProjectClass num : projectObjs) {
                                        projectObjs.get(projNumb - 1).getCustomer().setTelenum(newcontractorTele);

                                        System.out.println("the new contractor telephone of this project" + "\n");

                                        System.out.println(projectObjs.get(projNumb - 1));


                                    }


                                } else if (personChoice.equalsIgnoreCase("e")) {
                                    break;

                                } else {
                                    System.out.println("thats not a valid person/option try again");
                                    break;
                                }


                            }// set method for the contractors new telephone

                            else if (userEdit.equalsIgnoreCase("e")) {

                                System.out.println("""
                                            please enter which person you would like to update
                                            architect - A
                                            customer - C
                                            contractor - CT
                                            exit - E
                                        """);


                                String personChoice = ud.nextLine();

                                if (personChoice.equalsIgnoreCase("A")) {
                                    System.out.println("please enter the project number you want to edit from the projects below?");
                                    System.out.println(projectObjs);
                                    int projNumb = Integer.parseInt(ud.nextLine());

                                    System.out.println("please enter the new email of the architect");

                                    String newArchitectEmail = ud.nextLine();
                                    for (PoisedProjectClass num : projectObjs) {
                                        projectObjs.get(projNumb - 1).getArchitect().setEmail(newArchitectEmail);

                                        System.out.println("the new architect email of this project" + "\n");

                                        System.out.println(projectObjs.get(projNumb - 1));


                                    }


                                } else if (personChoice.equalsIgnoreCase("c")) {
                                    System.out.println("please enter the project number you want to edit from the projects below?");
                                    System.out.println(projectObjs);
                                    int projNumb = Integer.parseInt(ud.nextLine());


                                    System.out.println("please enter the new email of the customer");

                                    String newcustEmail = ud.nextLine();
                                    for (PoisedProjectClass num : projectObjs) {
                                        projectObjs.get(projNumb - 1).getCustomer().setEmail(newcustEmail);

                                        System.out.println("the new customer email of this project" + "\n");

                                        System.out.println(projectObjs.get(projNumb - 1));
                                    }


                                } else if (personChoice.equalsIgnoreCase("ct")) {
                                    System.out.println("please enter the project number you want to edit from the projects below?");
                                    System.out.println(projectObjs);
                                    int projNumb = Integer.parseInt(ud.nextLine());


                                    System.out.println("please enter the new email of the contract name");

                                    String newcontractorEmail = ud.nextLine();

                                    for (PoisedProjectClass num : projectObjs) {
                                        projectObjs.get(projNumb - 1).getCustomer().setEmail(newcontractorEmail);

                                        System.out.println("the new contractor email of this project" + "\n");

                                        System.out.println(projectObjs.get(projNumb - 1));

                                    }


                                } else if (personChoice.equalsIgnoreCase("e")) {
                                    break;

                                } else {
                                    System.out.println("thats not a valid person/option try again");
                                    break;
                                }


                            }// set method for the contractors new email

                            else if (userEdit.equalsIgnoreCase("a")) {

                                System.out.println("""
                                            please enter which person you would like to update
                                            architect - A
                                            customer - C
                                            contractor - CT
                                            exit - E
                                        """);


                                String personChoice = ud.nextLine();

                                if (personChoice.equalsIgnoreCase("A")) {
                                    System.out.println("please enter the project number you want to edit from the projects below?");
                                    System.out.println(projectObjs);
                                    int projNumb = Integer.parseInt(ud.nextLine());

                                    System.out.println("please enter the new address of the architect");

                                    String newArchitectAddy = ud.nextLine();

                                    for (PoisedProjectClass num : projectObjs) {

                                        projectObjs.get(projNumb - 1).getArchitect().setAddress(newArchitectAddy);

                                        System.out.println("the new architect address of this project" + "\n");

                                        System.out.println(projectObjs.get(projNumb - 1));
                                    }


                                } else if (personChoice.equalsIgnoreCase("c")) {
                                    System.out.println("please enter the project number you want to edit from the projects below?");
                                    System.out.println(projectObjs);
                                    int projNumb = Integer.parseInt(ud.nextLine());


                                    System.out.println("please enter the new email of the customer");

                                    String newcustaddy = ud.nextLine();
                                    for (PoisedProjectClass num : projectObjs) {
                                        projectObjs.get(projNumb - 1).getCustomer().setAddress(newcustaddy);

                                        System.out.println("the new customer address of this project" + "\n");

                                        System.out.println(projectObjs.get(projNumb - 1));

                                    }


                                } else if (personChoice.equalsIgnoreCase("ct")) {
                                    System.out.println("please enter the project number you want to edit from the projects below?");
                                    System.out.println(projectObjs);
                                    int projNumb = Integer.parseInt(ud.nextLine());


                                    System.out.println("please enter the new address of the contract name");

                                    String newcontractoraddy = ud.nextLine();

                                    for (PoisedProjectClass num : projectObjs) {

                                        projectObjs.get(projNumb - 1).getCustomer().setAddress(newcontractoraddy);

                                        System.out.println("the new contractor email of this project" + "\n");

                                        System.out.println(projectObjs.get(projNumb - 1));

                                    }


                                } else if (personChoice.equalsIgnoreCase("e")) {
                                    break;

                                } else {
                                    System.out.println("thats not a valid person/option try again");
                                    break;
                                }


                            }// set method for the contractors new address


                        }// while loop menu if the user chooses to edit contractor details

                    } catch (Exception e) {
                        System.out.println("you have selected a project that doesnt exist. Try again");
                    }


                } else if (projectMenu.equalsIgnoreCase("fp")) {
                    Scanner finalInput = new Scanner(System.in);
                    System.out.println("please enter the project number you want to finalize from the projects below");
                    ProjectChanges();
                    System.out.println(projectObjs);
                    userProj = Integer.parseInt(finalInput.nextLine());
                    finialiseProject(userProj);


                } else if (projectMenu.equalsIgnoreCase("sp")) {
                    Scanner finalInput = new Scanner(System.in);
                    System.out.println("please enter the project number you want to see");
                    userProj = Integer.parseInt(finalInput.nextLine());

                    String SelectQuery = "SELECT * FROM project WHERE Project_numb = ? "; /// help here

                    PreparedStatement preparedSelection = connection.prepareStatement(SelectQuery);
                    preparedSelection.setInt(1, userProj);
                    ResultSet Projectresults = preparedSelection.executeQuery(SelectQuery);

                    while (Projectresults.next()) {

                        int projectNumb = Projectresults.getInt("Project_numb");
                        String projectName = Projectresults.getString("Project_name");
                        String projectBuilding = Projectresults.getString("Building_type");
                        double projectFee = Projectresults.getDouble("project_totalFee");
                        double projectPaid = Projectresults.getDouble("project_Amount_Paid");
                        int erfNumb = Projectresults.getInt("Erf_numb");
                        Date date = Projectresults.getDate("DUE_DATE");
                        String projectAddy = Projectresults.getString("project_Address");
                        String contractorName = Projectresults.getString("Contractor_name");
                        String ArchitectName = Projectresults.getString("Architect_name");
                        String customerName = Projectresults.getString("Customer_name");



                        System.out.println(projectNumb + ", " + projectName + ", " + projectBuilding + ", " + projectFee + ", " + projectPaid + ", " + erfNumb + ", " + date + ",  " + projectAddy +", " + contractorName +", " + ArchitectName +", " + customerName + "\n");
                    }




                }
            }

        } catch (NumberFormatException e) {
            System.out.println("please dont enter the value encapsulated by quotation marks.");
        } catch (InputMismatchException e) {
            System.err.println("Not a valid input. Error :" + e.getMessage());
        }

        connection.close();


    }// THE MAIN FUNCTION CONTAINS THE MENU TO ALLOW THE USER TO EDIT VARIOUS ASPECTS OF THE PROJECT


    //////////////////////////////////////////////////////////project DATA GETTER///////////////////////////////////////////
    public static PoisedProjectClass projectdetails() {
        /**
         * THIS FUNCTION GETS DATA TO CREATE A NEW PROJECT OBJECT
         * @RETURN A NEW PROJECT OBJECT
         */
        int projectnum = 0;
        String projectname = null;
        String buildingtype = null;
        int erfnumb = 0;
        double totalfee = 0.0;
        double amountpaid = 0.0;
        String projectdeadline = null;
        String physicaladd = null;
        String completedProject = "uncompleted";
        PoisedPersonClass architect = null;
        PoisedPersonClass contractor = null;
        PoisedPersonClass customer = null;
        LocalDate Date = null;

        Scanner userInput = new Scanner(System.in);


        while (true) {
            try {


                System.out.println("please select one of the options below:" +
                        "1- ADD NEW PROJECT - Type ANP" + "\n" +
                        "2 - ADDITIONAL PROJECT OPTIONS - Type APO" + "\n" +
                        "3 - EXIT[E]");

                String choice = userInput.nextLine();

                if (choice.equalsIgnoreCase("E")) {
                    break;

                } else if (choice.equalsIgnoreCase("ANP")) {


                    System.out.println("please enter the project number\n");

                    projectnum = Integer.parseInt(userInput.nextLine());


                    userInput = new Scanner(System.in);

                    System.out.println("please enter the project name\n");

                    projectname = userInput.nextLine();

                    userInput = new Scanner(System.in);

                    System.out.println("please enter what type of building it is");
                    buildingtype = userInput.nextLine();

                    userInput = new Scanner(System.in);
                    System.out.println("please enter the physical address");
                    physicaladd = userInput.nextLine();

                    userInput = new Scanner(System.in);
                    System.out.println("please enter the ERF number");
                    erfnumb = Integer.parseInt(userInput.nextLine());

                    userInput = new Scanner(System.in);
                    System.out.println("please enter the total fee being charged for the project");
                    totalfee = Double.parseDouble(userInput.nextLine());

                    userInput = new Scanner(System.in);
                    System.out.println("please enter the amount paid to date");
                    amountpaid = Double.parseDouble(userInput.nextLine());

                    userInput = new Scanner(System.in);
                    System.out.println(" please enter the project deadline. in this format YYYY-MM-DD.");
                    projectdeadline = userInput.nextLine();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    Date = LocalDate.parse(projectdeadline, formatter);


                    Scanner State = new Scanner(System.in);
                    while (true) {
                        try {
                            System.out.println("Please choose one of the following persons to add new details for " + "\n" +
                                    "please exit the program fully to generate file,in order to use Additional project options"
                                    + "\n" +
                                    "1: - CUSTOMER: Type CD" + "\n" +
                                    "2: -ARCHITECH: TYPE AC" + "\n" +
                                    "3: -CONTRACTOR: TYPE CT" + "\n" +
                                    "4: -EXIT: TYPE E");

                            String choiceTwo = State.nextLine();

                            if (choiceTwo.equalsIgnoreCase("E")) {
                                break;

                            } else if (choiceTwo.equalsIgnoreCase("CD")) {

                                customer = peopledetails();

                            } else if (choiceTwo.equalsIgnoreCase("AC")) {
                                architect = peopledetails();

                            } else if (choiceTwo.equalsIgnoreCase("CT")) {
                                contractor = peopledetails();

                            } else {
                                System.out.println("that is a wrong input please try again with the options provided");
                                break;
                            }


                        } catch (NumberFormatException e) {
                            System.out.println("please dont enter the value encapsulated by quotation marks//the value you have entered is of the wrong type.");
                            break;
                        } catch (InputMismatchException e) {
                            System.err.println("Not a valid input. Error :" + e.getMessage());
                        }

                    }
                    dataBaseFunc(projectnum, projectname, buildingtype, physicaladd, erfnumb, totalfee, amountpaid, Date, completedProject, architect, contractor, customer);

                } else if (choice.equalsIgnoreCase("APO")) {
                    break;

                } else {
                    System.out.println("thats not a valid option please try again with the options provided");
                    break;
                }


            } catch (NumberFormatException e) {
                System.out.println("please dont enter the value encapsulated by quotation marks//the value you have entered is of the wrong type.");
                break;
            } catch (InputMismatchException e) {
                System.err.println("Not a valid input. Error :" + e.getMessage());
                break;
            }


        }
        return new PoisedProjectClass(projectnum, projectname, buildingtype, physicaladd, erfnumb, totalfee, amountpaid, Date, completedProject, architect, contractor, customer);

    }
//////////////////////////////////////////////////////////////project DATA GETTER///////////////////////////////////////


    /////////////////////////////////////////////////////////////////people DATA GETTER/////////////////////////////////////
    public static PoisedPersonClass peopledetails() {
        /**
         * @return a new person object
         * @throws exception when wrong value entered
         */
        String name = null;
        long tele = 0;
        String mail = null;
        String addy = null;


        try {


            Scanner userInputOne = new Scanner(System.in);
            System.out.println("please enter your name");
            name = userInputOne.nextLine();

            userInputOne = new Scanner(System.in);
            System.out.println("please enter your telephone");
            tele = Long.parseLong(userInputOne.nextLine());

            userInputOne = new Scanner(System.in);
            System.out.println("please enter your email");
            mail = userInputOne.nextLine();

            userInputOne = new Scanner(System.in);
            System.out.println("please enter your address");
            addy = userInputOne.nextLine();
        } catch (NumberFormatException e) {
            System.out.println("please dont enter the value encapsulated by quotation marks//the value you have entered is of the wrong type.");

        } catch (InputMismatchException e) {
            System.err.println("Not a valid input. Error :" + e.getMessage());
        }


        return new PoisedPersonClass(name, tele, mail, addy);
    }
/////////////////////////////////////////////////////////////////////people DATA GETTER/////////////////////////////////


    ////////////////////////////////////////////////DATABASE OPERATIONS/////////////////////////////////////////////////////
    public static void dataBaseFunc(int projectnum, String projectname, String buildingtype,
                                    String physicaladd,
                                    int erfnumb,
                                    double totalfee,
                                    double amountpaid,
                                    LocalDate projectdeadline, String completedProject, PoisedPersonClass architect, PoisedPersonClass contractor, PoisedPersonClass customer) {

        /**
         * @param project number for project
         * @param project name for project
         * @param Building type for the project
         * @param ErfNumber
         * @param total Fee charged for project
         * @param amount Paid at the moment for project
         * @param physical address for the project
         * @param Project Deadline for the project
         * @param completion status for the project
         * @param architect object
         * @param contractor object
         * @param customer object
         *@throws exception when file not found
         */
        try {

            ContractorDatabaseWriter(contractor);
            CustomerDatabaseWriter(customer);
            ArchitectDatabaseWriter(architect);
            ProjectDatabaseWriter(projectnum, projectname, buildingtype, physicaladd, erfnumb, totalfee, amountpaid, projectdeadline, completedProject, contractor, customer, architect);
            ProjectChanges();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
////////////////////////////////////////////////////DATABASE OPERATIONS/////////////////////////////////////////////////


/////////////////////////////////////////////uncompleteprojectsFinder///////////////////////////////////////////////////

    public static void uncompleteProjectFinder() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/PoisePMS?useSSL=false", "otheruser", "TheTriadSIXsectsixthsatanofSin666");
        String SelectQuery = "SELECT * FROM project WHERE completion_status = 'uncompleted' ";
        PreparedStatement preparedSelection = connection.prepareStatement(SelectQuery);
        ResultSet chanhgeresults = preparedSelection.executeQuery(SelectQuery);

        while (chanhgeresults.next()) {

            int projectNumb = chanhgeresults.getInt("Project_numb");
            String projectName = chanhgeresults.getString("Project_name");
            String projectBuilding = chanhgeresults.getString("Building_type");
            double projectFee = chanhgeresults.getDouble("project_totalFee");
            double projectPaid = chanhgeresults.getDouble("project_Amount_Paid");
            int erfNumb = chanhgeresults.getInt("Erf_numb");
            Date date = chanhgeresults.getDate("DUE_DATE");
            String projectAddy = chanhgeresults.getString("project_Address");


            System.out.println(projectNumb + ", " + projectName + ", " + projectBuilding + ", " + projectFee + ", " + projectPaid + ", " + erfNumb + ", " + date + ",  " + projectAddy + "\n");
        }


    }
///////////////////////////////////////////////uncomplete projects Finder///////////////////////////////////////////////


    /////////////////////////////////////////////////OverdueProjectFinder///////////////////////////////////////////////////
    public static void OverdueProjectFinder() throws SQLException {

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/PoisePMS?useSSL=false", "otheruser", "TheTriadSIXsectsixthsatanofSin666");
        String SelectQuery = "SELECT * FROM project";
        PreparedStatement preparedSelection = connection.prepareStatement(SelectQuery);
        ResultSet chanhgeresults = preparedSelection.executeQuery(SelectQuery);

        while (chanhgeresults.next()) {
            Date date = chanhgeresults.getDate("DUE_DATE");
            Date CurrentDate = Date.valueOf(LocalDate.now());

            if (date.compareTo(CurrentDate) < 0) {
                int projectNumb = chanhgeresults.getInt("Project_numb");
                String projectName = chanhgeresults.getString("Project_name");
                String projectBuilding = chanhgeresults.getString("Building_type");
                double projectFee = chanhgeresults.getDouble("project_totalFee");
                double projectPaid = chanhgeresults.getDouble("project_Amount_Paid");
                int erfNumb = chanhgeresults.getInt("Erf_numb");
                String projectAddy = chanhgeresults.getString("project_Address");
                String completion = chanhgeresults.getString("completion_status");

                System.out.println(projectNumb + ", " + projectName + ", " + projectBuilding + ", " + projectFee + ", " + projectPaid + ", " + erfNumb + ", " + date + ", " + projectAddy + ", " + completion + "\n");

            }

        }


    }

/////////////////////////////////////////////////////OverdueProjectFinder///////////////////////////////////////////////


    public static void finialiseProject(int projectNumb) throws SQLException {

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/PoisePMS?useSSL=false", "otheruser", "TheTriadSIXsectsixthsatanofSin666");

        String query = "SELECT " +
                "project.Project_numb, project.Project_name, project.DUE_DATE,project.Erf_numb, project.completion_status, project.project_Address, project.Architect_name, \n" +
                "project.Contractor_name, project.Customer_name, project.project_totalFee, project.project_Amount_Paid ,project.Building_type, \n" +
                "architect.Architect_phone, architect.Architect_email, architect.Architect_address,\n" +
                "contractor.Contractor_phone, contractor.Contractor_email, contractor.Contractor_address,\n" +
                "customer.Customer_phone, customer.Customer_email, customer.Customer_address\n" +
                "FROM project\n" +
                "JOIN architect ON project.Architect_name  = architect.Architect_name\n" +
                "JOIN contractor ON project.Contractor_name  = contractor.Contractor_name\n" +
                "JOIN customer ON project.Customer_name  = customer.Customer_name\n" +
                "WHERE Project_numb = ?";

        PreparedStatement preparedSelection = connection.prepareStatement(query);
        preparedSelection.setInt(1, projectNumb);
        ResultSet Projectresults = preparedSelection.executeQuery();

        ///////////project below/////////////
        int projectnumb = 0;
        String projectName = null;
        String projectBuilding = null;
        String projectAddy = null;
        int erfNumb = 0;
        double projectFee = 0;
        double projectPaid = 0;
        Date date = null;
        String completion = null;
        ///////////project above /////////////

        String contractorName = null;
        int contractorTele = 0;
        String contractorEmail = null;
        String contractorAddress = null;

        String customerName = "";
        int customerTele = 0;
        String customerEmail = null;
        String customerAddress = null;

        String ArchitectName = null;
        int ArchitectTele = 0;
        String ArchitectEmail = null;
        String ArchitectAddress = null;



        while (Projectresults.next()) {
            projectnumb = Projectresults.getInt("Project_numb");
            projectName = Projectresults.getString("Project_name");
            projectBuilding = Projectresults.getString("Building_type");
            projectFee = Projectresults.getDouble("project_totalFee");
            projectPaid = Projectresults.getDouble("project_Amount_Paid");
            erfNumb = Projectresults.getInt("Erf_numb");
            date = Projectresults.getDate("DUE_DATE");
            projectAddy = Projectresults.getString("project_Address");
            completion = Projectresults.getString("completion_status");

            contractorName = Projectresults.getString("Contractor_name");
            contractorTele = Projectresults.getInt("Contractor_phone");
            contractorEmail = Projectresults.getString("Contractor_email");
            contractorAddress = Projectresults.getString("Contractor_address");

            customerName = Projectresults.getString("Customer_name");
            customerTele = Projectresults.getInt("Customer_phone");
            customerEmail = Projectresults.getString("Customer_email");
            customerAddress = Projectresults.getString("Customer_address");



            ArchitectName = Projectresults.getString("Architect_name");
            ArchitectTele = Projectresults.getInt("Architect_phone");
            ArchitectEmail = Projectresults.getString("Architect_email");
            ArchitectAddress = Projectresults.getString("Architect_address");

            //System.out.println(customerName);



            invoiceGen(customerName,customerTele,customerEmail,customerAddress,projectFee,projectPaid,projectNumb);

        }


    }//get data of chosen project to get finialised



//    /////////////////////////////////////////////////INVOICE GENERATION/////////////////////////////////////////////////
    public static void invoiceGen(String customerName, int customerTele, String customerEmail, String customerAddress, double projectFee, double projectPaid, int projectNumb) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/PoisePMS?useSSL=false", "otheruser", "TheTriadSIXsectsixthsatanofSin666");
        boolean comp = false;

        if(projectPaid < projectFee){



            double Outstanding = projectFee - projectPaid;

            System.out.println("\nName: " + customerName + "\nCustomer project Number: " + projectNumb + "\nEmail: " + customerEmail + "\nPhone Number: " + customerTele + "\nAddress: " + customerAddress + "\nRemaining Balance to be Paid before finalizing project: " +"R"+Outstanding );

            // below procedure works but for the customer who needs an invoice to be generated the above if statement doesnt work
            //reviewer if you're going to send this back because of this, please offer a solution,
            // as me mentor on the call are perplexed as to why this simple if statement is not working
        }

        if (projectPaid > projectFee){
            String ProjectOutput = String.format("%s has already paid the full amount",customerName);
            System.out.println(ProjectOutput);

            String SelectQuery = "UPDATE project SET completion_status = ?  WHERE Project_numb = ? ";
            PreparedStatement preparedSelection = connection.prepareStatement(SelectQuery);
            preparedSelection.setString(1, "completed");
            preparedSelection.setInt(2,projectNumb);

            System.out.println(customerTele);
            System.out.println(customerAddress);
            System.out.println(customerName);
            System.out.println(customerEmail);

            preparedSelection.executeUpdate();
            double Outstanding = projectFee - projectPaid;

            System.out.println("\nName: " + customerName + "\nCustomer project Number: " + projectNumb + "\nEmail: " + customerEmail + "\nPhone Number: " + customerTele + "\nAddress: " + customerAddress + "\nRemaining Balance to be Paid before finalizing project: " +"R"+Outstanding );
            comp = true;

        }
        /////////////////////////////////////////////





    }


    /////////////////////////////////////////////////INVOICE GENERATION/////////////////////////////////////////////////


    /////////////////////////////////////////CREATION OF PROJECT OBJECTS FUNCTION///////////////////////////////////////


    ///////////////////////////////////////DATABASE READERS BELOW///////////////////////////////////////////////////////

    public static void ProjectChanges() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/PoisePMS?useSSL=false", "otheruser", "TheTriadSIXsectsixthsatanofSin666");
        String SelectQuery = "SELECT * FROM project";
        PreparedStatement preparedSelection = connection.prepareStatement(SelectQuery);
        ResultSet Projectresults = preparedSelection.executeQuery(SelectQuery);

        int projectNumb = 0;
        String projectName = null;
        String projectBuilding = null;
        String projectAddy = null;
        int erfNumb = 0;
        double projectFee = 0;
        double projectPaid = 0;
        Date date = null;
        String completion = null;

        while (Projectresults.next()) {

            projectNumb = Projectresults.getInt("Project_numb");
            projectName = Projectresults.getString("Project_name");
            projectBuilding = Projectresults.getString("Building_type");
            projectFee = Projectresults.getDouble("project_totalFee");
            projectPaid = Projectresults.getDouble("project_Amount_Paid");
            erfNumb = Projectresults.getInt("Erf_numb");
            date = Projectresults.getDate("DUE_DATE");
            projectAddy = Projectresults.getString("project_Address");
            completion = Projectresults.getString("completion_status");

            PoisedProjectClass projectObject = new PoisedProjectClass(projectNumb, projectName, projectBuilding, projectAddy, erfNumb, projectFee, projectPaid, date.toLocalDate(), completion, ArchitectObject(), Contractorobject(), CustomerObject());

            projectObjs.add(projectObject);

        }


        //////////////////////////////////////////CREATION OF COMPLETE PROJECT ONJECT///////////////////////////////////

        //////////////////////////////////////////CREATION OF COMPLETE PROJECT ONJECT///////////////////////////////////


    }


    public static PoisedPersonClass Contractorobject() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/PoisePMS?useSSL=false", "otheruser", "TheTriadSIXsectsixthsatanofSin666");
        String SelectQuery = "SELECT * FROM contractor";
        PreparedStatement preparedSelection = connection.prepareStatement(SelectQuery);
        ResultSet contractorresults = preparedSelection.executeQuery(SelectQuery);


        String contractorName = null;
        int contractorTele = 0;
        String contractorEmail = null;
        String contractorAddress = null;
        PoisedPersonClass contractor = null;

        while (contractorresults.next()) {

            contractorName = contractorresults.getString("Contractor_name");
            contractorTele = contractorresults.getInt("Contractor_phone");
            contractorEmail = contractorresults.getString("Contractor_email");
            contractorAddress = contractorresults.getString("Contractor_address");
            contractor = new PoisedPersonClass(contractorName, contractorTele, contractorEmail, contractorAddress);
        }


        return contractor;
    }

    public static PoisedPersonClass CustomerObject() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/PoisePMS?useSSL=false", "otheruser", "TheTriadSIXsectsixthsatanofSin666");
        String SelectQuery = "SELECT * FROM customer";
        PreparedStatement preparedSelection = connection.prepareStatement(SelectQuery);
        ResultSet custoomerResults = preparedSelection.executeQuery(SelectQuery);

        String customerName = null;
        int customerTele = 0;
        String customerEmail = null;
        String customerAddress = null;
        PoisedPersonClass customer = null;

        while (custoomerResults.next()) {


            customerName = custoomerResults.getString("Customer_name");
            customerTele = custoomerResults.getInt("Customer_phone");
            customerEmail = custoomerResults.getString("Customer_email");
            customerAddress = custoomerResults.getString("Customer_address");
            customer = new PoisedPersonClass(customerName, customerTele, customerEmail, customerAddress);


        }


        return customer;
    }

    public static PoisedPersonClass ArchitectObject() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/PoisePMS?useSSL=false", "otheruser", "TheTriadSIXsectsixthsatanofSin666");
        String SelectQuery = "SELECT * FROM Architect";
        PreparedStatement preparedSelection = connection.prepareStatement(SelectQuery);
        ResultSet Architecetresults = preparedSelection.executeQuery(SelectQuery);

        String ArchitectName = null;
        int ArchitectTele = 0;
        String ArchitectEmail = null;
        String ArchitectAddress = null;
        PoisedPersonClass architect = null;
        while (Architecetresults.next()) {


            ArchitectName = Architecetresults.getString("Architect_name");
            ArchitectTele = Architecetresults.getInt("Architect_phone");
            ArchitectEmail = Architecetresults.getString("Architect_email");
            ArchitectAddress = Architecetresults.getString("Architect_address");
            architect = new PoisedPersonClass(ArchitectName, ArchitectTele, ArchitectEmail, ArchitectAddress);


        }


        return architect;
    }
    ///////////////////////////////////////DATABASE READERS ABOVE///////////////////////////////////////////////////////


    //################################################################################################################//


    ///////////////////////////////////////////DATABASE WRITER FUNCTIONS BELOW//////////////////////////////////////////
    public static void ProjectDatabaseWriter(int projectnum, String projectname, String buildingtype,
                                             String physicaladd,
                                             int erfnumb,
                                             double totalfee,
                                             double amountpaid,
                                             LocalDate projectdeadline, String completedProject, PoisedPersonClass contractor, PoisedPersonClass customer, PoisedPersonClass architect) throws SQLException {
        try {
            Connection connection;
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/PoisePMS?useSSL=false", "otheruser", "TheTriadSIXsectsixthsatanofSin666");

            String query = "INSERT INTO project (Project_numb,Project_name,Building_type,project_totalFee,project_Amount_Paid,Erf_numb,DUE_DATE,project_Address,completion_status,Contractor_name,Customer_name,Architect_name)" + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?)";
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setInt(1, projectnum);
            preparedStmt.setString(2, projectname);
            preparedStmt.setString(3, buildingtype);
            preparedStmt.setDouble(4, totalfee);
            preparedStmt.setDouble(5, amountpaid);
            preparedStmt.setInt(6, erfnumb);
            preparedStmt.setDate(7, Date.valueOf(projectdeadline));
            preparedStmt.setString(8, physicaladd);
            preparedStmt.setString(9, completedProject);
            preparedStmt.setString(10, contractor.getName());
            preparedStmt.setString(11, customer.getName());
            preparedStmt.setString(12, architect.getName());


            preparedStmt.executeUpdate();

            System.out.println("PROJECT DATA ADDED SUCCESSFULLY.");
            //connection.close();


            //ProjectChanges();


        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    public static void ContractorDatabaseWriter(PoisedPersonClass contractor) {
        try {
            Connection connection;
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/PoisePMS?useSSL=false", "otheruser", "TheTriadSIXsectsixthsatanofSin666");

            String query = "INSERT INTO contractor (Contractor_name,Contractor_phone,Contractor_email,Contractor_address)" + "VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString(1, contractor.getName());
            preparedStmt.setInt(2, (int) contractor.getTelenum());
            preparedStmt.setString(3, contractor.getEmail());
            preparedStmt.setString(4, contractor.getAddress());
            preparedStmt.executeUpdate();

            System.out.println("Contractor DATA ADDED SUCCESSFULLY.");

            //connection.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static void CustomerDatabaseWriter(PoisedPersonClass customer) {
        try {
            Connection connection;
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/PoisePMS?useSSL=false", "otheruser", "TheTriadSIXsectsixthsatanofSin666");

            String query = "INSERT INTO customer (Customer_name,Customer_phone,Customer_email,Customer_address)" + "VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString(1, customer.getName());
            preparedStmt.setInt(2, (int) customer.getTelenum());
            preparedStmt.setString(3, customer.getEmail());
            preparedStmt.setString(4, customer.getAddress());
            preparedStmt.executeUpdate();

            System.out.println("Customer DATA ADDED SUCCESSFULLY.");
            //connection.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static void ArchitectDatabaseWriter(PoisedPersonClass architect) {
        try {
            Connection connection;
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/PoisePMS?useSSL=false", "otheruser", "TheTriadSIXsectsixthsatanofSin666");

            String query = "INSERT INTO Architect(Architect_name,Architect_phone,Architect_email,Architect_address)" + "VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString(1, architect.getName());
            preparedStmt.setInt(2, (int) architect.getTelenum());
            preparedStmt.setString(3, architect.getEmail());
            preparedStmt.setString(4, architect.getAddress());
            preparedStmt.executeUpdate();

            System.out.println("Architect DATA ADDED SUCCESSFULLY.");

            //connection.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    ///////////////////////////////////////////DATABASE WRITER FUNCTIONS ABOVE//////////////////////////////////////////
}














