package app.service.file;

import app.exceptions.FileException;

import javax.servlet.http.Part;

/**
 * Service interface, providing file operations with book data (book cover images and book content pdf documents)
 **/
public interface FileService {
    /**
     * Saves image file representing the book cover
     * @param bookId id of the book
     * @param part part file
     * @throws FileException if file cannot be saved
     **/
    void saveFileAsBookCoverImage(int bookId, byte[] part) throws FileException;

    /**
     * Saves pdf file representing the book content
     * @param bookId id of the book
     * @param part part file
     * @throws FileException if file cannot be saved
     **/
    void saveFileAsBookContent(int bookId, byte[] part) throws FileException;

    /**
     * Uploads book cover png image file
     * @param bookId id of the book
     * @return file data in byte array
     * @throws FileException if file cannot be uploaded
     **/
    byte[] getBookCoverImageFileAsBytes(int bookId) throws FileException;

    /**
     * Uploads book content pdf document file
     * @param bookId id of the book
     * @return file data in byte array
     * @throws FileException if file cannot be uploaded
     **/
    byte[] getBookContentFileAsBytes(int bookId) throws FileException;
}
