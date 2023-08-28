package antne.imagekeeper.resourceserver.google.drive.api;

import com.google.api.client.http.FileContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Component
@Slf4j
public class GoogleDriveUploader {

    private final Drive driveService;

    @Value("${google.path.parentFolderId}")
    private String parentFolderId;

    @Autowired
    public GoogleDriveUploader(Drive driveService) {
        this.driveService = driveService;
    }

    // TODO: doing exceptions if folderIds length bigger one
    public String uploadImageToDrive(java.io.File image, String uniqName, String mimeType, String folderName) throws IOException {
        String folderId = getFolderIdByName(folderName);

        File fileMetadata = new File();
        fileMetadata.setName(uniqName + ".jpg");
        fileMetadata.setParents(Collections.singletonList(folderId));
        fileMetadata.setMimeType(mimeType);
        FileContent mediaContent = new FileContent(mimeType, image);
        File uploadedFile = driveService.files().create(fileMetadata, mediaContent)
                .setFields("id")
                .execute();
        String fileId = uploadedFile.getId();
        log.info("Successfully upload a file with uniqName: {} and with id: {}", uniqName, fileId);
        return fileId;
    }

    public String createFolder(String folderName) throws IOException {
        File folderMetadata = new File();
        folderMetadata.setName(folderName);
        folderMetadata.setMimeType("application/vnd.google-apps.folder");

        if (parentFolderId != null) {
            folderMetadata.setParents(Collections.singletonList(parentFolderId));
        }

        File folder = driveService.files().create(folderMetadata).execute();
        String folderId = folder.getId();
        log.info("Successfully create a new folder with name: {} and id: {}", folderName, folderId);
        return folderId;
    }

    public List<File> searchFoldersByName(String folderName) throws IOException {
        String query = "mimeType='application/vnd.google-apps.folder' and name='" + folderName + "' and '" + parentFolderId + "' in parents";

        FileList result = driveService.files().list()
                .setQ(query)
                .setSpaces("drive")
                .setFields("files(id)")
                .execute();

        return result.getFiles();
    }

    public String getFolderIdByName(String folderName) throws IOException {
        List<File> folderIds = searchFoldersByName(folderName);
        String folderId;
        if (folderIds.isEmpty()) folderId = createFolder(folderName);
        else folderId = folderIds.get(0).getId();
        return folderId;
    }
}
