package com.schoolweb.customExceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    public static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(IllegalStateException.class)
    public ModelAndView handleIllegalStateException(IllegalStateException ex, HttpServletRequest request) {
        logger.error(ex.getMessage(),ex);
        return new ModelAndView("error").addObject("message",ex.getMessage());
    }

    @ExceptionHandler({ DataAccessException.class, JpaSystemException.class })
    public ModelAndView handleDatabaseException(Exception e) {
        logger.error(e.getMessage(), e);
        return new ModelAndView("error").addObject("message", "An database error occurred: " + e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ModelAndView handleRuntimeException(RuntimeException e) {
        logger.error(e.getMessage(), e);
        return new ModelAndView("error").addObject("message", "An runtime error occurred: " + e.getMessage());

    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception e) {
        logger.error(e.getMessage());
        return new ModelAndView("error").addObject("message", "An unexpected error occurred: " + e.getMessage());

    }
}
