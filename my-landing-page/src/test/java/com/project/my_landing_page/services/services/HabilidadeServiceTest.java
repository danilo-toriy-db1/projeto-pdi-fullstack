package com.project.my_landing_page.services.services;

import com.project.my_landing_page.models.Habilidade;
import com.project.my_landing_page.models.enums.HabilidadesTipo;
import com.project.my_landing_page.repositories.repositories.HabilidadeRepository;
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
public class HabilidadeServiceTest {

    @Mock
    private HabilidadeRepository habilidadeRepository;

    @InjectMocks
    private HabilidadeService habilidadeService;

    @Test
    void deveRetornarNuloQuandoHabilidadeNaoExistir() {
        when(habilidadeRepository.findById(1L)).thenReturn(Optional.empty());

        Habilidade res = habilidadeService.update(1L, new Habilidade(0L, "Teste", HabilidadesTipo.SOFT));

        assertThat(res).isNull();
        verify(habilidadeRepository).findById(1L);
        verify(habilidadeRepository, never()).save(any());
    }

    @Test
    void deveAtualizarHabilidadeQuandoExistir() {
        Habilidade existente = new Habilidade(1L, "Antiga", HabilidadesTipo.SOFT);
        Habilidade nova = new Habilidade(2L, "Nova", HabilidadesTipo.SOFT);
        when(habilidadeRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(habilidadeRepository.save(any(Habilidade.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Habilidade res = habilidadeService.update(1L, nova);

        ArgumentCaptor<Habilidade> captor = ArgumentCaptor.forClass(Habilidade.class);
        verify(habilidadeRepository).findById(1L);
        verify(habilidadeRepository).save(captor.capture());
        Habilidade salvo = captor.getValue();
        assertEquals(1L, salvo.getId());
        assertEquals("Nova", salvo.getHabilidade());
        verifyNoMoreInteractions(habilidadeRepository);
    }



}
