package com.project.my_landing_page.services.services;

import com.project.my_landing_page.models.Desgosto;
import com.project.my_landing_page.repositories.repositories.DesgostoRepository;
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
public class DesgostoServiceTest {

    @Mock
    private DesgostoRepository desgostoRepository;

    @InjectMocks
    private DesgostoService desgostoService;

    @Test
    void deveRetornarNuloQuandoDesgostoNaoExistir() {
        when(desgostoRepository.findById(1L)).thenReturn(Optional.empty());

        Desgosto res = desgostoService.update(1L, new Desgosto(0L, "Teste"));

        assertThat(res).isNull();
        verify(desgostoRepository).findById(1L);
        verify(desgostoRepository, never()).save(any());
    }

    @Test
    void deveAtualizarDesgostoQuandoExistir() {
        Desgosto existente = new Desgosto(1L, "Antiga");
        Desgosto nova = new Desgosto(2L, "Nova");
        when(desgostoRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(desgostoRepository.save(any(Desgosto.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Desgosto res = desgostoService.update(1L, nova);

        ArgumentCaptor<Desgosto> captor = ArgumentCaptor.forClass(Desgosto.class);
        verify(desgostoRepository).findById(1L);
        verify(desgostoRepository).save(captor.capture());
        Desgosto salvo = captor.getValue();
        assertEquals(1L, salvo.getId());
        assertEquals("Nova", salvo.getDesgosto());
        verifyNoMoreInteractions(desgostoRepository);
    }
}
