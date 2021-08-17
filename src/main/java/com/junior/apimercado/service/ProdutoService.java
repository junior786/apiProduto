package com.junior.apimercado.service;

import com.junior.apimercado.dto.ProdutoDto;
import com.junior.apimercado.exception.ExcepeptionNameInvalid;
import com.junior.apimercado.exception.ExceptionNotFound;
import com.junior.apimercado.model.Produto;
import com.junior.apimercado.repository.ProdutoMapper;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProdutoService {

    final ProdutoMapper produtoMapper;

    public ProdutoService(ProdutoMapper produtoMapper) {
        this.produtoMapper = produtoMapper;
    }

    public List<ProdutoDto> listAll() {

        List<ProdutoDto> all = produtoMapper.findAll();
        all.sort(Comparator.comparing(ProdutoDto::getNome));
        return all;
    }

    public Produto save(Produto produto) {
        List<ProdutoDto> list = produtoMapper.findAll().stream()
                .filter(x -> x.getNome().equals(produto.getNome()))
                .collect(Collectors.toList());

        if (list.isEmpty()) {
            produtoMapper.save(produto);
            return produto;
        } else {
            throw new ExcepeptionNameInvalid(produto.getNome() + " ja existe um produto com esse nome");
        }
    }

    public ProdutoDto removeByid(Integer id) {
        Optional<ProdutoDto> produto = produtoMapper.findById(id);
        if (produto.isPresent()) {
            produtoMapper.deleteById(id);
            return produto.get();
        } else {
            throw new ExceptionNotFound("ID não encontrado " + id);
        }

    }

    public ProdutoDto findById(Integer id) {
        Optional<ProdutoDto> produto = produtoMapper.findById(id);
        if (produto.isPresent()){
            return produto.get();
        }else{
            throw new ExceptionNotFound("ID não encontrado " + id);
        }

    }

    public ProdutoDto putById(Integer id, Produto produto) {
        Optional<ProdutoDto> byId = produtoMapper.findById(id);
        if (byId.isPresent()) {
            List<ProdutoDto> list = produtoMapper.findAll().stream()
                    .filter(x -> x.getNome().equals(produto.getNome()))
                    .collect(Collectors.toList());
            list.remove(byId.get());
            if (list.isEmpty()) {
                produto.setId(byId.get().getId());
                produtoMapper.putById(produto);
                return byId.get();
            } else {
                throw new ExcepeptionNameInvalid(produto.getNome() + " Já existe um produto com esse nome");
            }
        } else {
            throw new ExceptionNotFound("ID não encontrado " + id);
        }
    }

    public List<ProdutoDto> findByLike(String nome) {
        List<ProdutoDto> produtoDtos = produtoMapper.findyByLike(nome);

        if (produtoDtos.isEmpty()) {
            throw new ExceptionNotFound("Nome não encontrado " + nome);
        }
        return produtoDtos;
    }

    public List<ProdutoDto> findByPrice(double price) {
        List<ProdutoDto> list = produtoMapper.findByPrice(price);
        if (list.isEmpty()) {
            throw new ExceptionNotFound("Nenhum produto até esse preço " + price);
        }
        return list;
    }
}
