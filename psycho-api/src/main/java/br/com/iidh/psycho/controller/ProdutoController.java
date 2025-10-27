package br.com.iidh.psycho.controller;

import br.com.iidh.psycho.dto.ProdutoDTO;
import br.com.iidh.psycho.service.ProdutoService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;

@Path("/api/produtos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Produtos", description = "Operações relacionadas aos produtos/serviços")
public class ProdutoController {
    
    @Inject
    ProdutoService produtoService;
    
    @GET
    @Operation(summary = "Listar todos os produtos", description = "Retorna uma lista de todos os produtos cadastrados")
    public List<ProdutoDTO> listarTodos() {
        return produtoService.listarTodos();
    }
    
    @GET
    @Path("/buscar")
    @Operation(summary = "Buscar produtos por nome", description = "Busca produtos que contenham o nome especificado")
    public List<ProdutoDTO> buscarPorNome(@QueryParam("nome") String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            return produtoService.listarTodos();
        }
        return produtoService.buscarPorNome(nome);
    }
    
    @GET
    @Path("/{id}")
    @Operation(summary = "Buscar produto por ID", description = "Retorna um produto específico pelo seu ID")
    public Response buscarPorId(@PathParam("id") Long id) {
        try {
            ProdutoDTO produto = produtoService.buscarPorId(id);
            return Response.ok(produto).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }
    
    @GET
    @Path("/{id}/preco/{categoria}")
    @Operation(summary = "Buscar preço por categoria", description = "Retorna o preço de um produto para uma categoria específica")
    public Response buscarPrecoPorCategoria(@PathParam("id") Long id, @PathParam("categoria") int categoria) {
        try {
            Double preco = produtoService.buscarPrecoPorCategoria(id, categoria);
            return Response.ok("{\"preco\": " + preco + "}").build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }
    
    @POST
    @Operation(summary = "Criar novo produto", description = "Cria um novo produto no sistema")
    public Response criar(@Valid ProdutoDTO produtoDTO) {
        try {
            ProdutoDTO produtoCriado = produtoService.criar(produtoDTO);
            return Response.status(Response.Status.CREATED).entity(produtoCriado).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }
    
    @PUT
    @Path("/{id}")
    @Operation(summary = "Atualizar produto", description = "Atualiza os dados de um produto existente")
    public Response atualizar(@PathParam("id") Long id, @Valid ProdutoDTO produtoDTO) {
        try {
            ProdutoDTO produtoAtualizado = produtoService.atualizar(id, produtoDTO);
            return Response.ok(produtoAtualizado).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }
    
    @DELETE
    @Path("/{id}")
    @Operation(summary = "Excluir produto", description = "Remove um produto do sistema")
    public Response excluir(@PathParam("id") Long id) {
        try {
            produtoService.excluir(id);
            return Response.noContent().build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }
}
