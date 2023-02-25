package app.service.file.impl;

import app.database.dao.BookContentDAO;
import app.database.dao.BookCoverDAO;
import app.exceptions.DatabaseException;
import app.exceptions.FileException;
import app.exceptions.InitializationError;
import app.service.file.FileService;
import app.utils.ResourceUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Service class, providing file operations with book data (book cover
 * images and book content pdf documents) using database to store files
 **/
public class DatabaseFileServiceImpl implements FileService {
    private static final Logger logger = LogManager.getLogger(DatabaseFileServiceImpl.class);

    private final BookCoverDAO bookCoverDAO;
    private final BookContentDAO bookContentDAO;

    private final byte[] defaultCoverData;

    public DatabaseFileServiceImpl(BookCoverDAO bookCoverDAO, BookContentDAO bookContentDAO) {
        this.bookCoverDAO = bookCoverDAO;
        this.bookContentDAO = bookContentDAO;

        try {
            defaultCoverData = ResourceUtil.getResourceAsByteArray("default-cover.png");

            logger.info("File service impl initialization finished successfully");
        } catch (Exception exception) {
            logger.fatal("File service impl initialization failed", exception);
            throw new InitializationError("File service impl initialization failed", exception);
        }
    }

    @Override
    public void saveFileAsBookCoverImage(int bookId, byte[] data) throws FileException {
        try {
            logger.debug("Save book cover file (png image) for book with id = "+bookId+" started");

            bookCoverDAO.saveBookCover(bookId, data);

            logger.debug("Save book cover file (png image) for book with id = "+bookId+" successfully finished");
        } catch (DatabaseException exception) {
            throw new FileException(exception);
        }
    }

    @Override
    public void saveFileAsBookContent(int bookId, byte[] data) throws FileException {
        try {
            logger.debug("Save book content file (pdf document) for book with id = "+bookId+" started");

            bookContentDAO.saveBookContent(bookId, data);

            logger.debug("Save book content file (pdf document) for book with id = "+bookId+" successfully finished");
        } catch (DatabaseException exception) {
            throw new FileException(exception);
        }
    }

    @Override
    public byte[] getBookCoverImageFileAsBytes(int bookId) throws FileException {
        try {
            logger.debug("Get book cover file (png image) as bytes  for book with id = "+bookId+" started");

            byte[] cover = bookCoverDAO.findBookCoverByBookId(bookId);

            if (cover == null) {
                cover = defaultCoverData.clone();
            }

            logger.debug("Get book cover file (png image) as bytes  for book with id = "+bookId+" successfully finished");

            return cover;
        } catch (DatabaseException exception) {
            throw new FileException(exception);
        }
    }

    @Override
    public byte[] getBookContentFileAsBytes(int bookId) throws FileException {
        try {
            logger.debug("Get book content file (pdf document) as bytes for book with id = "+bookId+" started");

            byte[] content = bookContentDAO.findBookContentByBookId(bookId);

            logger.debug("Get book content file (pdf document) as bytes for book with id = "+bookId+" successfully finished");
            return content;
        } catch (DatabaseException exception) {
            throw new FileException(exception);
        }
    }
}
