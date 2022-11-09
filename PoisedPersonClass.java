
public class PoisedPersonClass {
        private String name;
        private long telenum;
        private String email;
        private String address;
        // CLASS ATTRIBUTES





        public PoisedPersonClass(String name,long telenum,String email, String address){
                this.name = name;
                this.telenum = telenum;
                this.email = email;
                this.address = address;

        } // CONSTRUCTOR FOR PEOPLE OBJECT

        public String toString(){
                String output = getName() +": ";output += getTelenum() +": ";output += getEmail() +": ";output += getAddress();


                return output;
        }

        //////////////////VARIOUS GETTERS AND SETTERS USED IN THE MAIN///////////////////////////
        public void setName(String name) {this.name = name;}

        public void setTelenum(long telenum) {this.telenum = telenum;}

        public void setAddress(String address) {this.address = address;}

        public void setEmail(String email) {this.email = email;}

        public String getName() {
                return name;
        }

        public long getTelenum() {
                return telenum;
        }

        public String getEmail() {
                return email;
        }

        public String getAddress() {
                return address;
        }

        //////////////////VARIOUS GETTERS AND SETTERS USED IN THE MAIN///////////////////////////


}
