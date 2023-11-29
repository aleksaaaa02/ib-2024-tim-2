package rs.ac.uns.ftn.Bookify.repository;

import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

@Repository
public class FileSystemRepository {
    private static final String RESOURCE_DIR = "../images/";

    public String save(byte[] content, String imageName) throws IOException{
        Path newImagePath = Paths.get(RESOURCE_DIR + new Date().getTime() + "-" + imageName);
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
