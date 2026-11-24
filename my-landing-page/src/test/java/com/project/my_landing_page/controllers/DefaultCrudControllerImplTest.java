package com.project.my_landing_page.controllers;

import com.project.my_landing_page.http.DTOs.BiografiaDTO;
import com.project.my_landing_page.models.Biografia;
import com.project.my_landing_page.services.DefaultServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DefaultCrudControllerImplTest {

    @Mock
    private DefaultServiceImpl<Biografia> service;

    private DefaultCrudControllerImpl<BiografiaDTO, Biografia> controller;

    @BeforeEach
    void setUp() {
        controller = new DefaultCrudControllerImpl<>(service, BiografiaDTO::fromEntity, BiografiaDTO::toEntity) {};
    }

    @Test
    void deveSalvarConvertendoDTOEmEntidadeERetornar200OK() {
        when(service.save(any(Biografia.class))).thenReturn(new Biografia(1L, "Bio"));

        ResponseEntity<BiografiaDTO> res = controller.save(new BiografiaDTO(null, "Bio"));

        ArgumentCaptor<Biografia> captor = ArgumentCaptor.forClass(Biografia.class);
        verify(service).save(captor.capture());
        assertEquals("Bio", captor.getValue().getBiografia());
        assertEquals(HttpStatus.OK, res.getStatusCode());
        assertEquals(new BiografiaDTO(1L, "Bio"), res.getBody());
    }

    @Test
    void deveListarTodosConvertendoParaDTO() {
        when(service.findAll()).thenReturn(List.of(new Biografia(1L, "Bio1"), new Biografia(2L, "Bio2")));

        ResponseEntity<List<BiografiaDTO>> res = controller.findAll();

        assertEquals(HttpStatus.OK, res.getStatusCode());
        assertEquals(List.of(new BiografiaDTO(1L, "Bio1"), new BiografiaDTO(2L, "Bio2")), res.getBody());
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoHouverRegistros() {
        when(service.findAll()).thenReturn(List.of());

        ResponseEntity<List<BiografiaDTO>> res = controller.findAll();

        assertEquals(HttpStatus.OK, res.getStatusCode());
        assertEquals(List.of(), res.getBody());
    }

    @Test
    void deveRetornar200QuandoEncontrarPorId() {
        when(service.findById(1L)).thenReturn(Optional.of(new Biografia(1L, "Bio")));

        ResponseEntity<BiografiaDTO> res = controller.findById(1L);

        assertEquals(HttpStatus.OK, res.getStatusCode());
        assertEquals(new BiografiaDTO(1L, "Bio"), res.getBody());
    }

    @Test
    void deveRetornar404QuandoNaoEncontrarPorId() {
        when(service.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<BiografiaDTO> res = controller.findById(1L);

        assertEquals(HttpStatus.NOT_FOUND, res.getStatusCode());
        assertNull(res.getBody());
    }

    @Test
    void deveRetornar200EAtualizarQuandoExistir() {
        when(service.update(eq(1L), any(Biografia.class))).thenReturn(new Biografia(1L, "Nova Bio"));

        ResponseEntity<BiografiaDTO> res = controller.update(1L, new BiografiaDTO(null, "Nova Bio"));

        assertEquals(HttpStatus.OK, res.getStatusCode());
        assertEquals(new BiografiaDTO(1L, "Nova Bio"), res.getBody());
    }

    @Test
    void deveRetornar404AoAtualizarQuandoNaoExistir() {
        when(service.update(eq(1L), any(Biografia.class))).thenReturn(null);

        ResponseEntity<BiografiaDTO> res = controller.update(1L, new BiografiaDTO(null, "Nova Bio"));

        assertEquals(HttpStatus.NOT_FOUND, res.getStatusCode());
        assertNull(res.getBody());
    }

    @Test
    void deveRetornar204AoDeletar() {
        ResponseEntity<Void> res = controller.deleteById(1L);

        assertEquals(HttpStatus.NO_CONTENT, res.getStatusCode());
        verify(service).deleteById(1L);
    }
}
