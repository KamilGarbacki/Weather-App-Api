package org.kgarbacki.weatherAppApi.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kgarbacki.weatherAppApi.apiClient.response.FeignErrorResponse;
import org.kgarbacki.weatherAppApi.dto.ErrorMessageDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Objects;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class CustomExceptionHandler {
    private final ObjectMapper objectMapper;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessageDto> handleBadRequestException(MethodArgumentNotValidException e,
                                                                     HttpServletRequest request) {

        ErrorMessageDto error = new ErrorMessageDto(
                request.getRequestURI(),
                Objects.requireNonNull(e.getFieldError()).getDefaultMessage(),
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now()
        );

        log.info(error.message());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(FeignException.BadRequest.class)
    public ResponseEntity<ErrorMessageDto> handleFeignBadRequestException(FeignException e,
                                                                     HttpServletRequest request) throws JsonProcessingException {

        FeignErrorResponse errorResponse = objectMapper.readValue(e.contentUTF8(), FeignErrorResponse.class);

        ErrorMessageDto error = new ErrorMessageDto(
                request.getRequestURI(),
                errorResponse.reason(),
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now()
        );

        log.info(error.message());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @ExceptionHandler(FeignException.ServiceUnavailable.class)
    public ResponseEntity<ErrorMessageDto> handleFeignServiceUnavailableException(HttpServletRequest request) {

        String errorMessage = "Open Meteo service is unavailable";

        ErrorMessageDto error = new ErrorMessageDto(
                request.getRequestURI(),
                errorMessage,
                HttpStatus.SERVICE_UNAVAILABLE.value(),
                LocalDateTime.now()
        );

        log.info(errorMessage);
        return new ResponseEntity<>(error, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    @ExceptionHandler(FeignException.BadGateway.class)
    public ResponseEntity<ErrorMessageDto> handleFeignException(FeignException e,
                                                                HttpServletRequest request) {

        String errorMessage = e.request().url().formatted("Unexpected error occured while processing request %s");

        ErrorMessageDto error = new ErrorMessageDto(
                request.getRequestURI(),
                errorMessage,
                HttpStatus.BAD_GATEWAY.value(),
                LocalDateTime.now()
        );

        log.info(errorMessage);
        return new ResponseEntity<>(error, HttpStatus.BAD_GATEWAY);
    }
}
