package com.escola.domain.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.escola.domain.model.Aluno;
import com.escola.domain.model.Endereco;
import com.escola.domain.service.AlunoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class AlunoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AlunoService alunoServiceMock;

    private Aluno alunoExistente  = generateAluno();
    private List<Aluno> listAlunoExistente = generateListAluno();

    @BeforeEach
    public void setUp() {}

    @Test
    public void instanceOfAlunoServiceTest() {
        // Verifica se o mock é uma instância de AlunoService
        assertEquals(true, alunoServiceMock instanceof AlunoService);
    }

    @Test
    public void createReturn500Test() throws Exception {
        //Arrange
        Aluno aluno = listAlunoExistente.get(1);

        // Quando o método create do serviço for chamado com o alunoToCreate, retorne alunoCreated
        when(alunoServiceMock.create(aluno)).thenThrow(new Exception());        

        // Act
        // Assert
        mockMvc.perform(
            MockMvcRequestBuilders
            .post("/aluno")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(aluno)))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content().string("Unexpected Server Error, see the logs."));

    }

    @Test
    public void findByIdReturn200Test() throws Exception {
        //Arrange
        Long id = alunoExistente.getId();

        // Configuração do mock para o serviço retornar o aluno criado acima
        when(alunoServiceMock.findById(id)).thenReturn(alunoExistente);

        // Act
        // Assert
        mockMvc.perform(
            MockMvcRequestBuilders
            .get("/aluno/" + id))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(alunoExistente.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value(alunoExistente.getNome()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dataNascimento").value(alunoExistente.getDataNascimento()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endereco[0].id").value(alunoExistente.getEndereco().get(0).getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endereco[0].tipo").value(alunoExistente.getEndereco().get(0).getTipo()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endereco[0].rua").value(alunoExistente.getEndereco().get(0).getRua()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endereco[0].numero").value(alunoExistente.getEndereco().get(0).getNumero()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endereco[0].cep").value(alunoExistente.getEndereco().get(0).getCep()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endereco[0].complemento").value(alunoExistente.getEndereco().get(0).getComplemento()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endereco[1].id").value(alunoExistente.getEndereco().get(1).getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endereco[1].tipo").value(alunoExistente.getEndereco().get(1).getTipo()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endereco[1].rua").value(alunoExistente.getEndereco().get(1).getRua()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endereco[1].numero").value(alunoExistente.getEndereco().get(1).getNumero()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endereco[1].cep").value(alunoExistente.getEndereco().get(1).getCep()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endereco[1].complemento").value(alunoExistente.getEndereco().get(1).getComplemento()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.serie").value(alunoExistente.getSerie()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.segmento").value(alunoExistente.getSegmento()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nomePai").value(alunoExistente.getNomePai()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nomeMae").value(alunoExistente.getNomeMae()));

    }

    @Test
    public void findBySerieReturn200Test() throws Exception {
        //Arrange
        String serie = alunoExistente.getSerie();

        // Configuração do mock para o serviço retornar o aluno criado acima
        when(alunoServiceMock.findBySerie(serie)).thenReturn(listAlunoExistente);

        // Act
        // Assert
        mockMvc.perform(
            MockMvcRequestBuilders
            .get("/aluno/serie/" + serie))
            //.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(listAlunoExistente.get(0).getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nome").value(listAlunoExistente.get(0).getNome()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].dataNascimento").value(listAlunoExistente.get(0).getDataNascimento()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].endereco[0].id").value(listAlunoExistente.get(0).getEndereco().get(0).getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].endereco[0].tipo").value(listAlunoExistente.get(0).getEndereco().get(0).getTipo()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].endereco[0].rua").value(listAlunoExistente.get(0).getEndereco().get(0).getRua()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].endereco[0].numero").value(listAlunoExistente.get(0).getEndereco().get(0).getNumero()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].endereco[0].cep").value(listAlunoExistente.get(0).getEndereco().get(0).getCep()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].endereco[0].complemento").value(listAlunoExistente.get(0).getEndereco().get(0).getComplemento()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].endereco[1].id").value(listAlunoExistente.get(0).getEndereco().get(1).getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].endereco[1].tipo").value(listAlunoExistente.get(0).getEndereco().get(1).getTipo()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].endereco[1].rua").value(listAlunoExistente.get(0).getEndereco().get(1).getRua()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].endereco[1].numero").value(listAlunoExistente.get(0).getEndereco().get(1).getNumero()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].endereco[1].cep").value(listAlunoExistente.get(0).getEndereco().get(1).getCep()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].endereco[1].complemento").value(listAlunoExistente.get(0).getEndereco().get(1).getComplemento()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].serie").value(listAlunoExistente.get(0).getSerie()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].segmento").value(listAlunoExistente.get(0).getSegmento()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nomePai").value(listAlunoExistente.get(0).getNomePai()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nomeMae").value(listAlunoExistente.get(0).getNomeMae()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(listAlunoExistente.get(1).getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].nome").value(listAlunoExistente.get(1).getNome()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].dataNascimento").value(listAlunoExistente.get(1).getDataNascimento()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].endereco[0].id").value(listAlunoExistente.get(1).getEndereco().get(0).getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].endereco[0].tipo").value(listAlunoExistente.get(1).getEndereco().get(0).getTipo()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].endereco[0].rua").value(listAlunoExistente.get(1).getEndereco().get(0).getRua()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].endereco[0].numero").value(listAlunoExistente.get(1).getEndereco().get(0).getNumero()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].endereco[0].cep").value(listAlunoExistente.get(1).getEndereco().get(0).getCep()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].endereco[0].complemento").value(listAlunoExistente.get(1).getEndereco().get(0).getComplemento()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].endereco[1].id").value(listAlunoExistente.get(1).getEndereco().get(1).getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].endereco[1].tipo").value(listAlunoExistente.get(1).getEndereco().get(1).getTipo()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].endereco[1].rua").value(listAlunoExistente.get(1).getEndereco().get(1).getRua()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].endereco[1].numero").value(listAlunoExistente.get(1).getEndereco().get(1).getNumero()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].endereco[1].cep").value(listAlunoExistente.get(1).getEndereco().get(1).getCep()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].endereco[1].complemento").value(listAlunoExistente.get(1).getEndereco().get(1).getComplemento()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].serie").value(listAlunoExistente.get(1).getSerie()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].segmento").value(listAlunoExistente.get(1).getSegmento()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].nomePai").value(listAlunoExistente.get(1).getNomePai()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].nomeMae").value(listAlunoExistente.get(1).getNomeMae()));
            
    }

    @Test
    public void getAllReturn200Test() throws Exception {
        //Arrange
        // Configuração do mock para o serviço retornar o aluno criado acima
        when(alunoServiceMock.getAll()).thenReturn(listAlunoExistente);

        // Act
        // Assert
        mockMvc.perform(
            MockMvcRequestBuilders
            .get("/aluno"))
            //.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(listAlunoExistente.get(0).getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nome").value(listAlunoExistente.get(0).getNome()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].dataNascimento").value(listAlunoExistente.get(0).getDataNascimento()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].endereco[0].id").value(listAlunoExistente.get(0).getEndereco().get(0).getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].endereco[0].tipo").value(listAlunoExistente.get(0).getEndereco().get(0).getTipo()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].endereco[0].rua").value(listAlunoExistente.get(0).getEndereco().get(0).getRua()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].endereco[0].numero").value(listAlunoExistente.get(0).getEndereco().get(0).getNumero()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].endereco[0].cep").value(listAlunoExistente.get(0).getEndereco().get(0).getCep()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].endereco[0].complemento").value(listAlunoExistente.get(0).getEndereco().get(0).getComplemento()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].endereco[1].id").value(listAlunoExistente.get(0).getEndereco().get(1).getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].endereco[1].tipo").value(listAlunoExistente.get(0).getEndereco().get(1).getTipo()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].endereco[1].rua").value(listAlunoExistente.get(0).getEndereco().get(1).getRua()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].endereco[1].numero").value(listAlunoExistente.get(0).getEndereco().get(1).getNumero()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].endereco[1].cep").value(listAlunoExistente.get(0).getEndereco().get(1).getCep()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].endereco[1].complemento").value(listAlunoExistente.get(0).getEndereco().get(1).getComplemento()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].serie").value(listAlunoExistente.get(0).getSerie()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].segmento").value(listAlunoExistente.get(0).getSegmento()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nomePai").value(listAlunoExistente.get(0).getNomePai()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nomeMae").value(listAlunoExistente.get(0).getNomeMae()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(listAlunoExistente.get(1).getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].nome").value(listAlunoExistente.get(1).getNome()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].dataNascimento").value(listAlunoExistente.get(1).getDataNascimento()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].endereco[0].id").value(listAlunoExistente.get(1).getEndereco().get(0).getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].endereco[0].tipo").value(listAlunoExistente.get(1).getEndereco().get(0).getTipo()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].endereco[0].rua").value(listAlunoExistente.get(1).getEndereco().get(0).getRua()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].endereco[0].numero").value(listAlunoExistente.get(1).getEndereco().get(0).getNumero()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].endereco[0].cep").value(listAlunoExistente.get(1).getEndereco().get(0).getCep()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].endereco[0].complemento").value(listAlunoExistente.get(1).getEndereco().get(0).getComplemento()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].endereco[1].id").value(listAlunoExistente.get(1).getEndereco().get(1).getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].endereco[1].tipo").value(listAlunoExistente.get(1).getEndereco().get(1).getTipo()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].endereco[1].rua").value(listAlunoExistente.get(1).getEndereco().get(1).getRua()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].endereco[1].numero").value(listAlunoExistente.get(1).getEndereco().get(1).getNumero()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].endereco[1].cep").value(listAlunoExistente.get(1).getEndereco().get(1).getCep()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].endereco[1].complemento").value(listAlunoExistente.get(1).getEndereco().get(1).getComplemento()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].serie").value(listAlunoExistente.get(1).getSerie()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].segmento").value(listAlunoExistente.get(1).getSegmento()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].nomePai").value(listAlunoExistente.get(1).getNomePai()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].nomeMae").value(listAlunoExistente.get(1).getNomeMae()));
            
    }

    @Test
    public void generateUriByIdTest() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        // Arrange
        // Obtenção da classe AlunoController
        Class<AlunoController> clazz = AlunoController.class;
        
        // Obtenção do método privado generateUriById
        Method method = clazz.getDeclaredMethod("generateUriById", Aluno.class);

        // Habilitar o acesso ao método privado
        method.setAccessible(true);

        // Verificar se a URI gerada corresponde ao esperado
        URI expectedUri1 = URI.create("http://localhost:8080/aluno/" + listAlunoExistente.get(0).getId());
        URI expectedUri2 = URI.create("http://localhost:8080/aluno/" + listAlunoExistente.get(1).getId());

        // Crie um MockHttpServletRequest
        MockHttpServletRequest request = new MockHttpServletRequest();

        // Configure o MockHttpServletRequest conforme necessário
        request.setServerName("localhost");
        request.setScheme("http");
        request.setServerPort(8080);
        request.setRequestURI("/aluno");

        // Defina o ServletRequestAttributes para o contexto de solicitação atual
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        
        // Act
        // Invocar o método privado com os dados de entrada
        URI result1 = (URI) method.invoke(new AlunoController(alunoServiceMock), listAlunoExistente.get(0));
        URI result2 = (URI) method.invoke(new AlunoController(alunoServiceMock), listAlunoExistente.get(1));

        // Assert
        assertEquals(expectedUri1, result1);
        assertEquals(expectedUri2, result2);
        
    }

    /*
    TODO: Implementar uma factory ou algo semelhante para
          fornecer esse método para mais classes
    */

    private String asJsonString(Aluno aluno) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        String alunoJson = objectMapper.writeValueAsString(aluno);

        return alunoJson;
    }

    private List<Aluno> generateListAluno() {
        Aluno aluno1 = generateAluno();
        Aluno aluno2 = generateAluno();

        aluno2.setId(2L);
        aluno2.setNome("Aluno2");
        aluno2.getEndereco().get(0).setId(3L);
        aluno2.getEndereco().get(1).setId(4L);

        List<Aluno> listAluno = new ArrayList<>();

        listAluno.add(aluno1);
        listAluno.add(aluno2);

        return listAluno;
    }

    private Aluno generateAluno() {
        Aluno aluno = new Aluno();
        List<Endereco> listEndereco = generateListEndereco();

        aluno.setId(1L);
        aluno.setNome("Aluno1");
        aluno.setDataNascimento("2020-03-18"); // Modifiquei
        aluno.setEndereco(listEndereco);
        aluno.setSerie("G1");
        aluno.setSegmento("Infantil"); // Modifiquei
        aluno.setNomePai("Pai do Aluno1");
        aluno.setNomeMae("Mãe do Aluno1");

        return aluno;
    }

    private List<Endereco> generateListEndereco() {
        List<Endereco> listEndereco = new ArrayList<>();
        Endereco endereco1 = new Endereco();
        Endereco endereco2 = new Endereco();

        endereco1.setId(1L);
        endereco1.setTipo("residencial");
        endereco1.setRua("Rua dos calçados");
        endereco1.setNumero("123");
        endereco1.setCep("82222-585");
        endereco1.setComplemento("casa 1");

        endereco2.setId(2L);
        endereco2.setTipo("cobrança");
        endereco2.setRua("Rua dos pés");
        endereco2.setNumero("456");
        endereco2.setCep("82222-585");
        endereco2.setComplemento("casa 3");

        listEndereco.add(endereco1);
        listEndereco.add(endereco2);

        return listEndereco;
    }    
}
