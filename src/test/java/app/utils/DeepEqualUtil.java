package app.utils;


import app.entity.*;


public class DeepEqualUtil {
    public boolean equals(Book a, Book b) {
        return a.getId() == b.getId() &&
               a.getPublisher().getId() == b.getPublisher().getId() &&
               a.getIsbn() == b.getIsbn() &&
               a.getPublicationDate().equals(b.getPublicationDate()) &&
               a.getTotalCopiesNumber() == b.getTotalCopiesNumber() &&
               a.getFreeCopiesNumber() == b.getFreeCopiesNumber() &&
               a.getGradesSum() == b.getGradesSum() &&
               a.getGradesNumber() == b.getGradesNumber() &&
               a.isHasElectronicFormat() == b.isHasElectronicFormat() &&
               a.isValuable() == b.isValuable() &&
               a.isDeleted() == b.isDeleted() &&
               a.getTitle().equals(b.getTitle()) &&
               a.getAuthor().equals(b.getAuthor()) &&
               a.getDescription().equals(b.getDescription()) &&
               a.getGenre() == b.getGenre() &&
               a.getLanguage() == b.getLanguage();
    }

    public boolean equals(User a, User b) {
        return a.getId() == b.getId() &&
               a.getFine() == b.getFine() &&
               a.isBlocked() == b.isBlocked() &&
               a.getEmail().equals(b.getEmail()) &&
               a.getFirstname().equals(b.getFirstname()) &&
               a.getLastname().equals(b.getLastname()) &&
               a.getPasswordHash().equals(b.getPasswordHash()) &&
               a.getRole() == b.getRole() &&
               a.getLastFineRecalculationDate().equals(b.getLastFineRecalculationDate());
    }

    public boolean equals(Order a, Order b) {
        return a.getBook().getId() == b.getBook().getId() &&
               a.getUser().getId() == b.getUser().getId() &&
               a.getState() == b.getState() &&
                ((a.getCreationDate() == null && b.getCreationDate() == null) || a.getCreationDate().equals(b.getCreationDate())) &&
               ((a.getDeadlineDate() == null && b.getDeadlineDate() == null) || a.getDeadlineDate().equals(b.getDeadlineDate()));
    }

    public boolean equals(Bookmark a, Bookmark b) {
        return a.getBook().getId() == b.getBook().getId() &&
               a.getUser().getId() == b.getUser().getId();
    }

    public boolean equals(Publisher a, Publisher b) {
        return a.getId() == b.getId() &&
               a.getTitle().equals(b.getTitle());
    }

    public boolean equals(Feedback a, Feedback b) {
        return a.getBook().getId() == b.getBook().getId() &&
               a.getUser().getId() == b.getUser().getId() &&
               a.getText().equals(b.getText()) &&
               a.getGrade() == b.getGrade() &&
               a.getCreationDate().equals(b.getCreationDate());
    }




    private DeepEqualUtil () { }
    private static DeepEqualUtil instance = null;

    public static DeepEqualUtil getInstance(){
        if (instance==null) instance = new DeepEqualUtil();
        return instance;
    }
}
