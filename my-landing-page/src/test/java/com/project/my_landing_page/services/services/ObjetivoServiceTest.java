package com.project.my_landing_page.services.services;

import com.project.my_landing_page.models.Objetivo;
import com.project.my_landing_page.repositories.repositories.ObjetivoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ObjetivoServiceTest {

    @Mock
    private ObjetivoRepository objetivoRepository;

    @InjectMocks
    private ObjetivoService objetivoService;

    @Test
    void deveRetornarNuloQuandoObjetivoNaoExistir() {
        when(objetivoRepository.findById(1L)).thenReturn(Optional.empty());

        Objetivo res = objetivoService.update(1L, new Objetivo(0L, "Teste"));

        assertThat(res).isNull();
        verify(objetivoRepository).findById(1L);
        verify(objetivoRepository, never()).save(any());
    }

    @Test
    void deveAtualizarObjetivoQuandoExistir() {
        Objetivo existente = new Objetivo(1L, "Antiga");
        Objetivo nova = new Objetivo(2L, "Nova");
        when(objetivoRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(objetivoRepository.save(any(Objetivo.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Objetivo res = objetivoService.update(1L, nova);

        ArgumentCaptor<Objetivo> captor = ArgumentCaptor.forClass(Objetivo.class);
        verify(objetivoRepository).findById(1L);
        verify(objetivoRepository).save(captor.capture());
        Objetivo salvo = captor.getValue();
        assertEquals(1L, salvo.getId());
        assertEquals("Nova", salvo.getObjetivo());
        verifyNoMoreInteractions(objetivoRepository);
    }
}
