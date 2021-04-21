package br.edu.restaurant.api.exception;

public class NoResultTop10ConsumedRestaurantsPerCostumerException extends Exception {

    public NoResultTop10ConsumedRestaurantsPerCostumerException() {
        super("No result count top 10 consumed restaurant per customer.");
    }
}