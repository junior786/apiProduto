package com.junior.apimercado.controller;

import com.junior.apimercado.dto.ProdutoDto;
import com.junior.apimercado.model.Produto;
import com.junior.apimercado.service.ProdutoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping(value = "/api/v1")
@RestController
@Api(value = "Mercado API MVC")
public class ProdutoController {

    final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/produtos")
    @ApiOperation(value = "Retorna um JSON de produtos")
    public List<ProdutoDto> retornarAll() {
        return produtoService.listAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Salva um produto em formato JSON")
    @PostMapping(value = "/produto")
    public Produto save(@Valid @RequestBody Produto produto) {
        return produtoService.save(produto);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/produto/{id}")
    @ApiOperation(value = "Retorna um produto pelo ID digita na URL")
    public ProdutoDto findById(@PathVariable("id") Integer id) {
        return produtoService.findById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/produto/{id}")
    @ApiOperation(value = "Delete um produto pelo ID digitado na URL")
    public ProdutoDto deletById(@PathVariable("id") Integer id) {
        return produtoService.removeByid(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/produto/{id}")
    @ApiOperation(value = "Modifica um produto pelo ID digitado na URL, passando um body")
    public ProdutoDto putById(@PathVariable("id") Integer id, @Valid @RequestBody Produto produto) {
        return produtoService.putById(id, produto);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/produto")
    @ApiOperation(value = "Retorna uma lista de produtos pelo nome digitado (usando operador LIKE)")
    public List<ProdutoDto> findByLike(@RequestParam("name") String name) {
        return produtoService.findByLike(name);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/produto/price={price}")
    @ApiOperation(value = "Retorna uma lista de produtos do valor menor e igual ao digitado na URL")
    public List<ProdutoDto> findByPrice(@PathVariable("price") double price) {
        return produtoService.findByPrice(price);
    }

}
