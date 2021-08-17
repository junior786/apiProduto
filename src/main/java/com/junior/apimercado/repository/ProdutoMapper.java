package com.junior.apimercado.repository;

import com.junior.apimercado.dto.ProdutoDto;
import com.junior.apimercado.model.Produto;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Mapper
@Repository
public interface ProdutoMapper {

    @Select("SELECT*FROM tb_produto")
    List<ProdutoDto> findAll();

    @Insert("INSERT INTO tb_produto (nome,price,date,description, estoque) VALUES(#{nome}, #{price}, #{date}, #{description}, #{estoque})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void save(Produto produto);

    @Delete("DELETE FROM tb_produto WHERE id = #{id}")
    void deleteById(@Param("id") Integer id);

    @Select("SELECT * FROM tb_produto WHERE id = #{id}")
    Optional<ProdutoDto> findById(@Param("id") Integer id);

    @Update("UPDATE tb_produto SET nome = #{nome}, price = #{price}, description = #{description}, date = #{date}, estoque = #{estoque} WHERE id = #{id}")
    void putById(Produto produto);

    @Select("SELECT * FROM tb_produto where nome ILIKE CONCAT ('%' , #{nome}, '%')")
    List<ProdutoDto> findyByLike(String nome);

    @Select("SELECT * FROM tb_produto where price <= #{price}")
    List<ProdutoDto> findByPrice(double price);
}
