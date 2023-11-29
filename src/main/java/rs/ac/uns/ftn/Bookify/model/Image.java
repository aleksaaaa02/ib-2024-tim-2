package rs.ac.uns.ftn.Bookify.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Image {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String imagePath;

    @Column(nullable = false)
    private String imageName;

    public Image(String imagePath, String imageName){
        this.imageName = imageName;
        this.imagePath = imagePath;
    }
}
