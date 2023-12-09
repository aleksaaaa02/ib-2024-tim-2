package rs.ac.uns.ftn.Bookify.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import rs.ac.uns.ftn.Bookify.model.Image;
import rs.ac.uns.ftn.Bookify.repository.FileSystemRepository;
import rs.ac.uns.ftn.Bookify.repository.interfaces.IImageRepository;
import rs.ac.uns.ftn.Bookify.service.interfaces.IImageService;

import java.util.List;

@Service
public class ImageService implements IImageService {
    @Autowired
    FileSystemRepository fileSystemRepository;
    @Autowired
    IImageRepository imageRepository;

    @Override
    public Image save(byte[] bytes, String subFolderName, String imageName) throws Exception {
        String location = fileSystemRepository.save(bytes, subFolderName, imageName);
        return imageRepository.save(new Image(location, imageName));
    }

    public Long save(Long accommodationId, List<MultipartFile> images) throws Exception {
        for (MultipartFile image: images) {
            String location = fileSystemRepository.save(image.getBytes(), accommodationId.toString(), image.getName());
            imageRepository.save(new Image(location, image.getName()));
        }
        return 1L;
    }

    @Override
    public FileSystemResource find(Long imageId) {
        Image image = imageRepository.findById(imageId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return fileSystemRepository.findInFileSystem(image.getImagePath());
    }

    @Override
    public FileSystemResource[] findAll(Long accommodationId) {
        System.out.println(imageRepository.findImagePathsByAccommodationId(accommodationId));
        return null;
    }
}
