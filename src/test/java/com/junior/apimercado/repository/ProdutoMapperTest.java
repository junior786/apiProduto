package com.junior.apimercado.repository;

import com.junior.apimercado.dto.ProdutoDto;
import com.junior.apimercado.exception.ExcepeptionNameInvalid;
import com.junior.apimercado.exception.ExceptionNotFound;
import com.junior.apimercado.model.Produto;
import com.junior.apimercado.service.ProdutoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(value = "/load-database.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@TestPropertySource("classpath:application-test.properties")
class ProdutoMapperTest {

    @Autowired
    private ProdutoService produtoService;

    @Test
    public void list_Produto() {
        int size = produtoService.listAll().size();
        assertEquals(1, size);
    }

    @Test
    public void save_Produto() {
        produtoService.save(createProduto());
        int size = produtoService.listAll().size();
        assertEquals(2, size);
    }

    @Test
    public void list_Like() {
        ProdutoDto teste = produtoService.findByLike("teste").get(0);
        assertEquals("Teste", teste.getNome());
    }

    @Test
    public void find_Id() {
        ProdutoDto produto = produtoService.findById(3);
        assertEquals(3,produto.getId());
    }

    @Test
    public void list_Price_Smaller(){
        List<ProdutoDto> produtos = produtoService.findByPrice(60.0);
        assertEquals(1,produtos.size());
    }
    @Test
    public void put_Produto(){
        Produto produto = createProduto();
        produto.setId(3);
        produtoService.putById(3,produto);
        ProdutoDto byId = produtoService.findById(3);
        assertEquals("Modificado",byId.getNome());
    }

    @Test
    public void delete_From_Id(){
        produtoService.removeByid(3);
        List<ProdutoDto> list = produtoService.listAll();
        assertEquals(0,list.size());
    }

    @Test
    public void list_Produto_Like(){
        List<ProdutoDto> list = produtoService.findByLike("es");
        assertEquals(1,list.size());
    }

    @Test
    public void error_Name_Exist(){
        Produto produto = createProduto();
        produto.setNome("Teste");

        assertThrows(ExcepeptionNameInvalid.class, () -> produtoService.save(produto));
    }

    @Test
    public void error_Id_NotExist_List(){
        assertThrows(ExceptionNotFound.class, () -> produtoService.findById(100));
    }

    @Test
    public void error_Id_NotExist_Remove(){
        assertThrows(ExceptionNotFound.class, () -> produtoService.removeByid(100));
    }

    @Test
    public void error_Id_NotExist_Put(){
        assertThrows(ExceptionNotFound.class, () -> produtoService.putById(100, createProduto()));
    }

    @Test
    public void error_Name_NotExist(){
        assertThrows(ExceptionNotFound.class, () -> produtoService.findByLike("ABC"));
    }


    private Produto createProduto() {
        return Produto.builder()
                .nome("Modificado")
                .id(10)
                .description("Teste")
                .price(10.50)
                .estoque(20)
                .date(LocalDate.now())
                .build();
    }
}