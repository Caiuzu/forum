package br.com.alura.forum.controller;

import br.com.alura.forum.controller.dto.DetalhesDoTopicoDto;
import br.com.alura.forum.controller.dto.TopicoDto;
import br.com.alura.forum.controller.form.AtualizacaoTopicoForm;
import br.com.alura.forum.controller.form.TopicoForm;
import br.com.alura.forum.model.Topico;
import br.com.alura.forum.repository.CursoRepository;
import br.com.alura.forum.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController // quando nao tiver view, quando for uma api de retorno por exemplo api
@RequestMapping("/topicos")
public class TopicosController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    //@RequestMapping(value = "/topicos", method = RequestMethod.GET)
    @GetMapping /*http://localhost:8080/topicos?pagina=0&qtd=3&ordenacao=id*/
    public Page<TopicoDto> lista(@RequestParam(required = false) String nomeCurso,
                                 @RequestParam int pagina, @RequestParam int qtd, @RequestParam String ordenacao){ // se não houver parametro recebido na url ele chama o findAll(), senao ele filtra pelo parametro recebido
        //DTO é quando a api envia para o cliente //System.out.println(nomeCurso); // printa no console

        Pageable paginacao = PageRequest.of(pagina, qtd, Sort.Direction.ASC, ordenacao);

        if(nomeCurso == null){
            Page<Topico> topicos = topicoRepository.findAll(paginacao);
            return TopicoDto.converter(topicos);
        }
        else{
            Page<Topico> topicos = topicoRepository.findByCursoNome(nomeCurso, paginacao);
            return TopicoDto.converter(topicos);
        }

    }

    //@RequestMapping(value = "/topicos", method = RequestMethod.POST)
    @PostMapping
    @Transactional // por garantia colocar transaction casa haja mudança de bd
    public ResponseEntity<TopicoDto> cadastrar(@RequestBody @Valid TopicoForm form, UriComponentsBuilder uriBuilder){ //@resquestbody avisa que isso sera feito na requisição do body as info, @valid avisa q usa validação e mostra se tiver errado no terminal
        //Form é quando o cliente envia para a api
        Topico topico = form.converter(cursoRepository);
        topicoRepository.save(topico);


        /*a boa pratica é retonar 201 e não 200, sendo asism utilizamos o uri para realizar o retorno na url certa com o valor 201 (valor de cadastrado com sucesso)*/
        URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(new TopicoDto(topico));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalhesDoTopicoDto> detalhar(@PathVariable Long id){ // @PathVariable informa ao spring que a informação do id vem na url e "/{id}", parametro dinamico

        Optional<Topico> topico = topicoRepository.findById(id); //recupera do banco o item unico referente ao id | mudei de findByOne para findById Pois o findByone retorna exeception o byId nao retorna nada Optional

        if(topico.isPresent()) {
            return ResponseEntity.ok(new DetalhesDoTopicoDto(topico.get()));
        }

        return ResponseEntity.notFound().build();

    }

    @PutMapping("/{id}") //update de campos referente ao id dinamico passado na url
    @Transactional // é necessario colocar para disparar o commit no banco de dados
    public ResponseEntity<TopicoDto> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoTopicoForm form) { /*put é quando quero sobreesvrecer todas as informações, e o pat é mudar só alguns campos. Mas no geral usa put*/

        Optional<Topico> optional = topicoRepository.findById(id);

        if(optional.isPresent()) {
            Topico topico = form.atualizar(id, topicoRepository);
            return ResponseEntity.ok(new TopicoDto(topico));
        }

        return ResponseEntity.notFound().build();

    }

    @DeleteMapping("/{id}")
    @Transactional// por garantia colocar transaction casa haja mudança de bd
    public ResponseEntity<?> remover(@PathVariable Long id){

        Optional<Topico> optional = topicoRepository.findById(id);

        if(optional.isPresent()) {
            topicoRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();

    }
}
