package rs.ac.uns.ftn.Bookify.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

@Repository
public class FileSystemRepository {
    @Value("${image.path}")
    private String IMAGES_DIR;

    public String save(byte[] content, String subFolderName, String imageName) throws IOException{
        Path newImagePath = Paths.get(IMAGES_DIR + "/" +subFolderName + "/" + new Date().getTime() + "-" + imageName + ".png");
        Files.createDirectories(newImagePath.getParent());
        Files.write(newImagePath, content);
        return newImagePath.toString();
    }

    public FileSystemResource findInFileSystem(String location){
        try{
            return new FileSystemResource(Paths.get(location));
        } catch (Exception e){
            throw new RuntimeException();
        }
    }

}
