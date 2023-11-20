package rs.ac.uns.ftn.Bookify.model;

import java.util.List;

public class Admin extends User {

    private List<Accommodation> editedAccommodations;
    
    public List<Accommodation> getEditedAccommodations(){
        return this.editedAccommodations;
    }

    public void setEditedAccommodations(List<Accommodation> editedAccommodations){
        this.editedAccommodations = editedAccommodations;
    }
}
