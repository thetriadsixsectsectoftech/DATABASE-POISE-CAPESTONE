import java.time.LocalDate;

public class PoisedProjectClass {

    int Projectnumb;
    double Projecttotalfee;
    double Amountpaidtodate;

    //change int
    String projectname;
    String buildingtype;
    int erfnumb;


    LocalDate date;
    String physicaladdy;

    String completedProject;

    PoisedPersonClass architect;
    PoisedPersonClass contractor;
    PoisedPersonClass customer;


    // CLASS ATTRIBUTES




    public PoisedProjectClass(int projectnumb, String projectname, String buildingtype, String physicaladdy, int erfnumb, double projecttotalfee, double amountpaidtodate, LocalDate date, String completedProject, PoisedPersonClass archiect, PoisedPersonClass contractor, PoisedPersonClass customer){
        this.Projectnumb = projectnumb;
        this.setProjectname(projectname);
        this.setBuildingtype(buildingtype);
        this.erfnumb = erfnumb;
        this.Projecttotalfee = projecttotalfee;
        this.Amountpaidtodate = amountpaidtodate;
        this.setDate(date);
        this.setPhysicaladdy(physicaladdy);
        this.setCompletedProject(completedProject);
        this.setArchitect(archiect);
        this.setContractor(contractor);
        this.setCustomer(customer);




    }// CONSTRUCTOR FOR PROJECT OBJECT


    public String toString(){
        String output = "\nproject number: " + getProjectnumb();
        output += "\nproject name: " + getProjectname();
        output += "\nbuilding type: " + getBuildingtype();
        output += "\nerfnumber: " + getErfnumb();
        output += "\nproject total fee: " + getProjecttotalfee();
        output += "\namount paid to date: " + getAmountpaidtodate();
        output += "\ndue date: " + getDate();
        output += "\nCompletion status: " + getCompletedProject();
        output += "\narchitect details\n";
        output += getArchitect();
        output += "\ncontractor details\n";
        output += getContractor();
        output += "\ncustomer details\n";
        output += getCustomer() + "\n";





        return output;
    }

    //////////////////VARIOUS GETTERS AND SETTERS USED IN THE MAIN///////////////////////////
    public int getProjectnumb() {
        return Projectnumb;
    }


    public String getProjectname() {
        return projectname;
    }

    public void setProjectname(String projectname) {
        this.projectname = projectname;
    }

    public String getBuildingtype() {
        return buildingtype;
    }

    public void setBuildingtype(String buildingtype) {
        this.buildingtype = buildingtype;
    }

    public int getErfnumb() {
        return erfnumb;
    }

    //////////////////VARIOUS GETTERS AND SETTERS USED IN THE MAIN///////////////////////////


    public double getProjecttotalfee() {
        return Projecttotalfee;
    }



    public double getAmountpaidtodate() {
        return Amountpaidtodate;
    }

    public void setAmountpaidtodate(double Amountpaidtodate) {
        this.Amountpaidtodate = (Amountpaidtodate);
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }



    public void setPhysicaladdy(String physicaladdy) {
        this.physicaladdy = physicaladdy;
    }

    public String getCompletedProject() {
        return completedProject;
    }

    public void setCompletedProject(String completedProject) {
        this.completedProject = completedProject;
    }

    public PoisedPersonClass getArchitect() {
        return architect;
    }

    public void setArchitect(PoisedPersonClass architect) {
        this.architect = architect;
    }

    public PoisedPersonClass getContractor() {
        return contractor;
    }

    public void setContractor(PoisedPersonClass contractor) {
        this.contractor = contractor;
    }

    public PoisedPersonClass getCustomer() {
        return customer;
    }

    public void setCustomer(PoisedPersonClass customer) {
        this.customer = customer;
    }
    //////////////////VARIOUS GETTERS AND SETTERS USED IN THE MAIN///////////////////////////
}
