package rs.ac.uns.ftn.Bookify.repository.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.Bookify.model.Image;

@Repository
public interface IImageRepository extends JpaRepository<Image, Long> {

}
