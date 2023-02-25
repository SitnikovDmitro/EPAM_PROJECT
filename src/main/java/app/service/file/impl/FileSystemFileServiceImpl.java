package app.service.file.impl;

import app.exceptions.FileException;
import app.exceptions.InitializationError;
import app.service.file.FileService;
import app.utils.ResourceUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.Part;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * Service class, providing file operations with book data (book cover
 * images and book content pdf documents) using file system to store files
 **/
public class FileSystemFileServiceImpl implements FileService {
    private static final Logger logger = LogManager.getLogger(FileSystemFileServiceImpl.class);

    private final String bookCoverPath;
    private final String bookContentPath;

    public FileSystemFileServiceImpl() {
        //throw new UnsupportedOperationException("File service is unavailable now");

        try {
            Map<String, String> properties = ResourceUtil.getResourceAsMap("book-data-storage-folder-path.json");

            bookCoverPath = properties.get("cover");
            bookContentPath = properties.get("content");

            logger.info("Localizator impl initialization finished successfully");
        } catch (IOException exception) {
            logger.fatal("Localizator impl initialization failed", exception);
            throw new InitializationError("Localizator impl initialization failed", exception);
        }
    }


    @Override
    public void saveFileAsBookCoverImage(int bookId, byte[] data) throws FileException {
        try {
            logger.debug("Save book cover file (png image) for book with id = "+bookId+" started");
            File file = new File(bookCoverPath+"/"+bookId+".png");
            Files.copy(new ByteArrayInputStream(data), file.toPath());
            logger.debug("Save book cover file (png image) for book with id = "+bookId+" successfully finished");
        } catch (IOException ex) {
            throw new FileException(ex);
        }
    }

    @Override
    public void saveFileAsBookContent(int bookId, byte[] data) throws FileException {
        try {
            logger.debug("Save book content file (pdf document) for book with id = "+bookId+" started");
            File file = new File(bookContentPath+"/"+bookId+".pdf");
            Files.copy(new ByteArrayInputStream(data), file.toPath());
            logger.debug("Save book content file (pdf document) for book with id = "+bookId+" successfully finished");
        } catch (IOException ex) {
            throw new FileException(ex);
        }
    }

    @Override
    public byte[] getBookCoverImageFileAsBytes(int bookId) throws FileException {
        try {
            if (Files.exists(Path.of(bookCoverPath+"/"+bookId+".png"))) {
                return Files.readAllBytes(Path.of(bookCoverPath+"/"+bookId+".png"));
            } else {
                return Files.readAllBytes(Path.of(bookCoverPath+"/"+"default.png"));
            }
        } catch (IOException ex) {
            throw new FileException(ex);
        }
    }

    @Override
    public byte[] getBookContentFileAsBytes(int bookId) throws FileException {
        try {
            return Files.readAllBytes(Path.of(bookContentPath+"/"+bookId+".pdf"));
        } catch (IOException ex) {
            throw new FileException(ex);
        }
    }
}
