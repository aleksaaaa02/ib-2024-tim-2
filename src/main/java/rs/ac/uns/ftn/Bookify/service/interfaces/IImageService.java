package rs.ac.uns.ftn.Bookify.service.interfaces;

import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import rs.ac.uns.ftn.Bookify.model.Image;

@Service
public interface IImageService {
    public Long save(Long accommodationId, List<MultipartFile> images) throws Exception;

    public Image save(byte[] bytes, String subFolderName,String imageName) throws Exception;

    public FileSystemResource find(Long imageId);
    public FileSystemResource[] findAll(Long accommodationId);
}
