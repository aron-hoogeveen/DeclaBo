package ch.bolkhuis.declabo.fund;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class FundErrorAdvice {

    @ResponseBody
    @ExceptionHandler(FundNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String fundNotFoundHandler(FundNotFoundException ex) {
        return ex.getMessage();
    }

}
