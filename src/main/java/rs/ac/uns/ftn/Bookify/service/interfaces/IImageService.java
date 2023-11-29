package rs.ac.uns.ftn.Bookify.service.interfaces;

import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

@Service
public interface IImageService {
    public Long save(byte[] bytes, String imageName) throws Exception;
    public FileSystemResource find(Long imageId);
}
