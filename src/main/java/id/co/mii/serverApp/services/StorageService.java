package id.co.mii.serverApp.services;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class StorageService {
    @Value("${file.dir}")
    private String FOLDER_PATH;

    public String uploadImageToFileSystem(MultipartFile file) throws IOException {
        String unique = file.getOriginalFilename();
        String filePath = FOLDER_PATH + unique;
        File image = new File(filePath);
        if (image.exists()) {
            unique = generateUniqueFileName(file.getOriginalFilename());
            filePath = FOLDER_PATH + unique;
        }
        file.transferTo(new File(filePath));

        return unique;
    }

    public ResponseEntity<FileSystemResource> getPhoto(String filename) throws IOException {
        // Cari file foto di sistem file berdasarkan nama file
        String filePath = FOLDER_PATH + filename;
        File file = new File(filePath);

        // Bungkus file dalam FileSystemResource
        FileSystemResource resource = new FileSystemResource(file);

        // Atur header respons
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA); // Atur tipe konten sesuai dengan jenis file foto
        headers.setContentLength(file.length());

        // Kembalikan file foto sebagai respons API
        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(headers)
                .body(resource);
    }

    public boolean deleteImage(String photo) {
        String filePath = FOLDER_PATH + photo;
        File file = new File(filePath);
        if (file.exists()) {
            return file.delete();
        }
        return false;
    }

    private static String generateUniqueFileName(String fileName) {
        String baseName = fileName.substring(0, fileName.lastIndexOf('.'));
        String extension = fileName.substring(fileName.lastIndexOf('.'));
        String uniqueFileName = baseName + "_" + UUID.randomUUID().toString() + extension;
        return uniqueFileName;
    }

}
