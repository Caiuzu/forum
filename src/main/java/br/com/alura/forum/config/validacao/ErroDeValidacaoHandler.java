package br.com.alura.forum.config.validacao;
/*
* o hendler é o interceptador de mensagem que faz tratamento para exibição de erro
*
* Pense que o handler é um interceptador, em que estou configurando a Spring, para que sempre que houver um erro,
* alguma exception em algum método de qualquer controller, ele chama automaticamente esse interceptador, passando o erro que aconteceu.
*
* como no meu retorno de erro para o @valid ele vem um json enorme e criamos essa classe para tratar os retornos de error via json para exceptions de formularios
* usaremos o @RestControllerAdvice
*/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;


@RestControllerAdvice
public class ErroDeValidacaoHandler {

    @Autowired
    private MessageSource messageSource; // ajuda pegar mensagens de erro conforme a linguagem requisitada pelo cliente

    @ResponseStatus(code = HttpStatus.BAD_REQUEST) // mesmo que eu tenha tratado o erro ele tem que retornar 400 (bad_request) e nao 200
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<ErroDeFormularioDto> handle(MethodArgumentNotValidException exception){
        List<ErroDeFormularioDto> dto = new ArrayList<>();

        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();

        // tenho que percorrer a lista de erros e buscar somento oq quero
        fieldErrors.forEach(e -> {
            String mensagem = messageSource.getMessage(e, LocaleContextHolder.getLocale()); // pega mensagem no idioma local
            ErroDeFormularioDto erro = new ErroDeFormularioDto(e.getField(), mensagem);
            dto.add(erro);
        });

        return dto;
    }


}
