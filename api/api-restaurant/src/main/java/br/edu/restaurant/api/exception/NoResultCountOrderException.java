package br.edu.restaurant.api.exception;

public class NoResultCountOrderException extends Exception {

    public NoResultCountOrderException() {
        super("No result count order.");
    }
}
