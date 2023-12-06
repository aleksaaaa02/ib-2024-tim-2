package rs.ac.uns.ftn.Bookify.service.interfaces;

import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.Bookify.model.Image;

@Service
public interface IImageService {
    public Image save(byte[] bytes, String subFolderName,String imageName) throws Exception;
    public FileSystemResource find(Long imageId);
}
